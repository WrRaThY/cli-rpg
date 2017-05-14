package priv.rdo.rpg.domain.game

import priv.rdo.rpg.domain.character.Player
import priv.rdo.rpg.domain.common.exception.PlayerDied
import priv.rdo.rpg.domain.common.exception.Victory
import priv.rdo.rpg.domain.menu.AllMenus
import priv.rdo.rpg.domain.world.World
import priv.rdo.rpg.domain.world.location.Coordinates
import priv.rdo.rpg.domain.world.location.Location
import priv.rdo.rpg.ports.outgoing.ExplorationMenu
import priv.rdo.rpg.ports.outgoing.GameStateProvider
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static priv.rdo.rpg.test.util.CommonTestObjects.*

/**
 * @author WrRaThY
 * @since 14.05.2017
 */
class ExplorationManagerTest extends Specification {
    //TODO finish tests
    @Ignore
    def "test startExploring"() {
        given: ""
            ExplorationManager sut = initSut()

        when: ""
            // TODO implement stimulus
        then:
            1 == 0
    }

    @Ignore
    def "test showMap"() {
        given: ""

        when: ""
            // TODO implement stimulus
        then:
            1 == 0
    }

    @Ignore
    def "test showLegend"() {
        given: ""

        when: ""
            // TODO implement stimulus
        then:
            1 == 0
    }

    @Ignore
    def "test travel"() {
        given: ""

        when: ""
            // TODO implement stimulus
        then:
            1 == 0
    }

    def "should carry on normally after fighting"() {
        given:
            Location newLocation = testEnemyLocation()

        and:
            ExplorationMenu explorationMenu = showMessageCalledTimesMock(3)

        and:
            ExplorationManager sut = initSutSpyWithFightResult(newLocation, playerWonTheFight, isLastEnemy, explorationMenu)

        expect: "stuff defined in mocks"
            sut.interactWithNpc(newLocation)

        where:
            playerWonTheFight | isLastEnemy || desc
            true              | false       || "nothing special, another enemy dead"
    }

    @Unroll
    def "should #desc after fighting"() {
        given:
            Location newLocation = testEnemyLocation()

        and:
            ExplorationMenu explorationMenu = showMessageCalledTimesMock(3)

        and:
            ExplorationManager sut = initSutSpyWithFightResult(newLocation, playerWonTheFight, isLastEnemy, explorationMenu)

        when: "stuff defined in mocks"
            sut.interactWithNpc(newLocation)

        then:
            thrown(expectedException)

        where:
            playerWonTheFight | isLastEnemy || expectedException || desc
            false             | false       || PlayerDied        || "die"
            true              | true        || Victory           || "celebrate victory"
    }

    //TODO: this is a bad idea, refactor
    ExplorationManager initSutSpyWithFightResult(Location loc, boolean playerWonTheFight, boolean isLastEnemy, ExplorationMenu menu) {
        World world = testWorld()
        if (isLastEnemy) {
            world.enemies.clear()
            world.enemies.put(loc.getCoordinates(), loc.getNpc())
        }

        Player player = testPlayer()

        ExplorationManager spy = Spy(constructorArgs: [Mock(GameStateProvider), initAllMenus(menu), world, player]) {
            if (playerWonTheFight) {
                1 * fight(loc) >> { loc.getNpc().die() }
            } else {
                1 * fight(loc) >> { player.die() }
            }

        }

        spy
    }

    def "should move to a specified location"() {
        given:
            Location newLocation = testEmptyLocation()

        and:
            Player player = playerCoordinatesSpy(newLocation.getCoordinates())

        and:
            ExplorationManager sut = initSut(1, player)

        expect: "stuff defined in mocks"
            sut.moveToEmptySpace(newLocation)
    }

    def "should save game"() {
        given:
            GameStateProvider gameStateProvider = Mock {
                1 * saveGame(_)
            }

        and:
            ExplorationManager sut = initSut(gameStateProvider, showMessageCalledTimesMock(1))

        expect: "stuff defined in mocks"
            sut.saveGame()
    }

    Player playerCoordinatesSpy(Coordinates coordinates) {
        Player spy = Spy(constructorArgs: [testPlayerConfiguration(), testPlayerCoordinates()]) {
            1 * setCoordinates(coordinates) >> { callRealMethod() }
        }

        spy
    }

    ExplorationMenu showMessageCalledTimesMock(int showMessageCalledTimes) {
        ExplorationMenu explorationMenu = Mock {
            showMessageCalledTimes * showMessage(_)
        }
        explorationMenu
    }

    ExplorationManager initSut() {
        initSut(Mock(GameStateProvider), Mock(ExplorationMenu))
    }

    ExplorationManager initSut(int showMessageCalledTimes) {
        initSut(Mock(GameStateProvider), showMessageCalledTimesMock(showMessageCalledTimes))
    }

    ExplorationManager initSut(int showMessageCalledTimes, Player player) {
        initSut(Mock(GameStateProvider), showMessageCalledTimesMock(showMessageCalledTimes), testWorld(), player)
    }

    ExplorationManager initSut(GameStateProvider gameStateProvider, ExplorationMenu explorationMenu) {
        initSut(gameStateProvider, explorationMenu, testWorld(), testPlayer())
    }

    ExplorationManager initSut(GameStateProvider gameStateProvider, ExplorationMenu explorationMenu, World world, Player player) {
        AllMenus allMenus = initAllMenus(explorationMenu)
        new ExplorationManager(gameStateProvider, allMenus, world, player)
    }

    AllMenus initAllMenus(ExplorationMenu explorationMenu) {
        AllMenus allMenus = new AllMenus(null, null, null, explorationMenu, null, null)
        allMenus
    }
}
