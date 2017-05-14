package priv.rdo.rpg.test.util

import priv.rdo.rpg.common.generator.SerializationRealmConfigurationGenerator
import priv.rdo.rpg.domain.character.Enemy
import priv.rdo.rpg.domain.character.Player
import priv.rdo.rpg.domain.world.World
import priv.rdo.rpg.domain.world.location.Coordinates
import priv.rdo.rpg.domain.world.location.Location
import priv.rdo.rpg.ports.outgoing.dto.EnemyConfiguration
import priv.rdo.rpg.ports.outgoing.dto.PlayerConfiguration
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration

/**
 * @author WrRaThY
 * @since 12.05.2017
 */
class CommonTestObjects {
    static String PLAYER_NAME = "Player"
    static String PLAYER_DESC = "PlayerDesc"
    static int HP_BONUS = 1
    static int DMG_BONUS = 4
    static int DMG_VARIATION_BONUS = 0

    private CommonTestObjects() {
    }

    static World testWorld() {
        new World(testRealmConfiguration())
    }

    static RealmConfiguration testRealmConfiguration() {
        SerializationRealmConfigurationGenerator.gta2Realm()
    }

    static List<EnemyConfiguration> testEnemyConfigurations() {
        testRealmConfiguration().getEnemyConfiguration()
    }

    static Player testPlayer(Coordinates coordinates) {
        new Player(testPlayerConfiguration(), coordinates)
    }

    static Player testPlayer() {
        testPlayer(testPlayerCoordinates())
    }

    static PlayerConfiguration testPlayerConfiguration() {
        PlayerConfiguration.builder(5)
                .withName(PLAYER_NAME)
                .withDesc(PLAYER_DESC)
                .withHpBonus(HP_BONUS)
                .withDamageBonus(DMG_BONUS)
                .withDamageVariationBonus(DMG_VARIATION_BONUS)
                .build()
    }

    static Coordinates testPlayerCoordinates() {
        new Coordinates(1, 0)
    }

    static Location testEnemyLocation() {
        new Location(testEnemyCoordinates(), testEnemy())
    }

    static Enemy testEnemy() {
        new Enemy(testEnemyConfiguration())
    }

    static EnemyConfiguration testEnemyConfiguration() {
        new EnemyConfiguration("a", "b", "c", 100, 10, 0)
    }

    static Coordinates testEnemyCoordinates() {
        new Coordinates(2, 0)
    }

    static Location testEmptyLocation() {
        new Location(testEmptyCoordinates())
    }

    static Coordinates testEmptyCoordinates() {
        new Coordinates(1, 1)
    }
}
