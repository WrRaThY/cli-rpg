package priv.rdo.rpg.domain.world

import priv.rdo.rpg.domain.character.Player
import priv.rdo.rpg.domain.util.LegendBuilder
import priv.rdo.rpg.domain.world.location.Coordinates
import priv.rdo.rpg.domain.world.location.Location
import priv.rdo.rpg.domain.world.location.LocationType
import priv.rdo.rpg.ports.outgoing.dto.EnemyConfiguration
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration
import priv.rdo.rpg.test.util.CommonTestObjects
import spock.lang.Specification

import static priv.rdo.rpg.domain.util.WorldViewBuilder.buildWorldView

/**
 * @author WrRaThY
 * @since 12.05.2017
 */
class WorldTest extends Specification {
    def "should print map and check that it contains enough enemies and players"() {
        given:
            World world = CommonTestObjects.testWorld()
            Player player = CommonTestObjects.testPlayer()
            RealmConfiguration realm = CommonTestObjects.testRealmConfiguration()

        when:
            String worldToString = buildWorldView(world, player)

        and: "just print some stuff"
            println worldToString
            println LegendBuilder.buildLegend()

        then:
            worldToString.count("E") == realm.getEnemyConfiguration().size()
            worldToString.count("P") == 1
    }

    def "should initialize map with empty fields and an easy to use x/y ordination"() {
        given:
            World world = CommonTestObjects.testWorld()

        when:
            world.initMap()

        and: "just print some stuff"
            println buildWorldView(world)

        then:
            Location[][] locationMap = world.map

            for (int x = 0; x < locationMap.length; x++) {
                Location[] locationRow = locationMap[x]
                for (int y = 0; y < locationRow.length; y++) {
                    Location location = locationRow[y]
                    location.mapMark() == LocationType.EMPTY.getMapMark()
                    location.getCoordinates() == new Coordinates(x, y)
                }
            }
    }

    def "should initialize all enemies"() {
        given:
            List<EnemyConfiguration> enemyConfigurations = CommonTestObjects.testEnemyConfigurations()

        when:
            World world = CommonTestObjects.testWorld()

        then:
            world.enemies.size() == enemyConfigurations.size()

        and: "just print some stuff"
            println buildWorldView(world)

    }

    def "should initialize world properly with all enemies and correct locations"() {
        given:
            List<EnemyConfiguration> enemyConfigurations = CommonTestObjects.testEnemyConfigurations()

        when:
            World world = CommonTestObjects.testWorld()

        and: "just print some stuff"
            println buildWorldView(world)

        then:
            Location[][] locationMap = world.map

            int foundEnemies = 0
            for (int x = 0; x < locationMap.length; x++) {
                Location[] locationRow = locationMap[x]
                for (int y = 0; y < locationRow.length; y++) {
                    Location location = locationRow[y]
                    if (LocationType.ENEMY == location.getType()) {
                        location.getNpc() != null
                        location.mapMark() == LocationType.ENEMY.getMapMark()
                        world.enemies.containsKey(location.getCoordinates())
                        foundEnemies++
                    } else {
                        location.getType() == LocationType.EMPTY
                    }
                }
            }

            foundEnemies == enemyConfigurations.size()
    }

    def "randomCoordinates should stay within maps range for multiple"() {
        given:
            int mapSize = 10

        expect:
            100000.times {
                Coordinates result = World.randomCoordinates(mapSize)
                isWithinMap(result.x, mapSize)
                isWithinMap(result.y, mapSize)
            }
    }

    static boolean isWithinMap(int position, int mapSize) {
        position >= 0 && position < mapSize
    }

    //TODO: more tests
}
