package priv.rdo.rpg.domain.game

import priv.rdo.rpg.domain.character.Enemy
import priv.rdo.rpg.domain.character.NPC
import priv.rdo.rpg.domain.character.Player
import priv.rdo.rpg.domain.common.exception.PlayerDied
import priv.rdo.rpg.domain.menu.AllMenus
import priv.rdo.rpg.domain.world.location.Location
import priv.rdo.rpg.ports.outgoing.BeforeFightMenu
import priv.rdo.rpg.ports.outgoing.FightMenu
import priv.rdo.rpg.ports.outgoing.dto.BeforeFightMenuItem
import priv.rdo.rpg.ports.outgoing.dto.FightMenuItem
import spock.lang.Specification
import spock.lang.Unroll

import static priv.rdo.rpg.common.util.IntUtils.isWithinRangeInclusive
import static priv.rdo.rpg.ports.outgoing.dto.FightMenuItem.*
import static priv.rdo.rpg.test.util.CommonTestObjects.*

/**
 * @author WrRaThY
 * @since 14.05.2017
 */
class FightManagerTest extends Specification {

    @Unroll
    def "should #menuChoice.name()"() {
        given:
            BeforeFightMenu beforeFightMenu = Mock {
                showMenu() >> menuChoice
            }

        and:
            FightManager sut = initSut(beforeFightMenu, null)

        expect:
            sut.userWantsToFight() == expected

        where:
            menuChoice                    || expected
            BeforeFightMenuItem.FALL_BACK || false
            BeforeFightMenuItem.ATTACK    || true
    }

    @Unroll
    def "should return #expected if #desc"() {
        expect:
            sut.fightGoesOn(npc, menuChoice) == expected

        where:
            sut                      | menuChoice           | npc              || expected || desc
            defaultSut()             | FightMenuItem.ATTACK | sut.npc          || true     || "player attacks"
            defaultSut()             | DEFEND               | sut.npc          || true     || "player defends"
            defaultSut()             | FLEE                 | sut.npc          || false    || "player flees"
            defaultSut()             | FightMenuItem.ATTACK | killNPC(sut.npc) || false    || "npc died"
            killPlayer(defaultSut()) | FightMenuItem.ATTACK | sut.npc          || false    || "player died"
            defaultSut()             | null                 | sut.npc          || false    || "menu item is null"
    }

    @Unroll
    def "Player should deal #expectedMin-#expectedMax damage to NPC"() {
        given:
            FightMenu fightMenu = Mock {
                1 * showMessage(_)
            }
            FightManager sut = initSut(null, fightMenu)

        when:
            int result = sut.attack(player, enemy)

        then:
            isWithinRangeInclusive(result, expectedMin, expectedMax)

        where:
            player       | enemy       || expectedMin | expectedMax
            testPlayer() | testEnemy() || 14          | 16
    }

    @Unroll
    def "Enemy should deal #expectedDmg damage to Player with enemyDamage = #enemy.getDamage() and armorUps = #armorUps #desc"() {
        given:
            FightMenu fightMenu = Mock {
                1 * showMessage(_)
            }
            FightManager sut = initSut(null, fightMenu)
            Player player = testPlayerWithArmor(armorUps)

        when:
            int result = sut.attack(enemy, player)

        then:
            result == expectedDmg

        where:
            armorUps | enemy       || expectedDmg || desc
            0        | testEnemy() || 10           | ""
            1        | testEnemy() || 8            | ""
            2        | testEnemy() || 8            | ", because armor up won't add more armor than player level"
    }

    @Unroll
    def "Player should leave the fight after choosing #menuChoice.name()"() {
        given:
            FightMenu fightMenu = Mock {
                2 * showMenu() >>> menuChoice
                1 * showMessage(_)
            }
        and:
            FightManager sut = initSut(null, fightMenu)

        expect:
            sut.startTheBrawl()

        where:
            menuChoice   || _
            [FLEE, null] || _
    }

    @Unroll
    def "Player should raise armor by #expectedArmor after #desc"() {
        given:
            FightMenu fightMenu = Mock {
                showMenu() >>> menuChoice
            }

        and:
            Player player = playerArmorUpsSpy(armorUps, level)

        and:
            FightManager sut = initSut(null, fightMenu, player, testEnemyLocation())

        when:
            sut.startTheBrawl()

        then:
            player.calculateDamageMitigation() == expectedArmor

        where:
            menuChoice                     | armorUps | level || expectedArmor
            [DEFEND, null]                 | 1        | 1     || 2
            [DEFEND, DEFEND, null]         | 2        | 1     || 2
            [DEFEND, DEFEND, null]         | 2        | 2     || 4
            [DEFEND, DEFEND, DEFEND, null] | 3        | 2     || 4
            [DEFEND, DEFEND, DEFEND, null] | 3        | 3     || 6

            desc = "$armorUps armor ups on level $level"
    }

    @Unroll
    def "Player should attack npc for #playerAtkDmg damage and #desc"() {
        given:
            FightMenu fightMenu = Mock {
                showMenu() >>> menuChoice
            }

        and:
            Player player = playerAttackSpy(1, expPlayerRcvDmgTimes, npcAtkDmg, playerAtkDmg)
            Enemy enemy = enemySpy(1, playerAtkDmg, npcAtkDmg)
            Location location = new Location(testEnemyCoordinates(), enemy)

        and:
            FightManager sut = initSut(null, fightMenu, player, location)

        when:
            sut.startTheBrawl()

        then: "plus what is defined in spies"
            enemy.isAlive() == expEnemyIsAlive

        where:
            menuChoice     | playerAtkDmg || npcAtkDmg | expPlayerRcvDmgTimes | expEnemyIsAlive || desc
            [ATTACK, null] | 14           || 10        | 1                    | true             | "receive $npcAtkDmg damage"
            [ATTACK, null] | 110          || 10        | 0                    | false            | "no damage, because enemy is dead"
    }

    @Unroll
    def "Player should die after receiving #npcAtkDmg damage"() {
        given:
            FightMenu fightMenu = Mock {
                showMenu() >>> menuChoice
            }

        and:
            Player player = playerAttackSpy(1, expPlayerRcvDmgTimes, npcAtkDmg, playerAtkDmg)
            Enemy enemy = enemySpy(1, playerAtkDmg, npcAtkDmg)
            Location location = new Location(testEnemyCoordinates(), enemy)

        and:
            FightManager sut = initSut(null, fightMenu, player, location)

        when:
            sut.startTheBrawl()

        then: "plus what is defined in spies"
            thrown(PlayerDied)

        where:
            menuChoice     | playerAtkDmg || npcAtkDmg | expPlayerRcvDmgTimes | expEnemyIsAlive
            [ATTACK, null] | 14           || 150       | 1                    | true
    }

    Enemy enemySpy(int damageReceivedTimes, int expectedEnemyDamageReceived, int npcAtkDmg) {
        Enemy spy = Spy(constructorArgs: [testEnemyConfiguration()]) {
            damageReceivedTimes * calculateDamageReceived(expectedEnemyDamageReceived) >> { callRealMethod() }
        }
        spy.damage = npcAtkDmg
        spy
    }

    Player playerAttackSpy(int attacks, int gotAttackedTimes, int expectedPlayerDamageReceived, int expectedPlayerAttackDamage) {
        Player spy = Spy(constructorArgs: [testPlayerConfiguration(), testPlayerCoordinates()]) {
            attacks * attack(_) >> { callRealMethod() }
            gotAttackedTimes * calculateDamageReceived(expectedPlayerDamageReceived) >> { callRealMethod() }
        }
        spy.damage = expectedPlayerAttackDamage
        commonSpyConfiguration(spy)
    }

    Player playerArmorUpsSpy(int armorUps, int playerLevel) {
        Player spy = Spy(constructorArgs: [testPlayerConfiguration(), testPlayerCoordinates()]) {
            armorUps * armorUp() >> { callRealMethod() }
        }
        spy.getExperience().level = playerLevel
        commonSpyConfiguration(spy)
    }

    Player commonSpyConfiguration(Player spy) {
        spy.damageVariation = 0
        spy
    }

    Player testPlayerWithArmor(int armorUps) {
        Player player = testPlayer()
        armorUps.times {
            player.armorUp()
        }

        player
    }

    FightManager killPlayer(FightManager sut) {
        sut.player.isAlive = false
        sut
    }

    NPC killNPC(NPC npc) {
        npc.die()
        npc
    }

    private FightManager defaultSut() {
        initSut(Mock(BeforeFightMenu), Mock(FightMenu))
    }

    private FightManager initSut(BeforeFightMenu beforeFightMenu, FightMenu fightMenu) {
        initSut(beforeFightMenu, fightMenu, testPlayer(), testEnemyLocation())
    }

    private FightManager initSut(BeforeFightMenu beforeFightMenu, FightMenu fightMenu, Player player, Location enemyLocation) {
        AllMenus allMenus = new AllMenus(null, null, null, null, beforeFightMenu, fightMenu)
        new FightManager(allMenus, player, enemyLocation)
    }
}
