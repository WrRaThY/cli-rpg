package priv.rdo.rpg.ports.outgoing.dto

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 08.05.2017
 */
class RealmConfigurationTest extends Specification {
    @Unroll
    def "check that buildEnemiesToString works correctly for #desc"() {
        given:
            RealmConfiguration config = new RealmConfiguration("colorNumber", 15, enemies)

        when:
            String result = config.buildEnemiesToString()

        then:
            println result //print out for silly humans to see something
            result == expected

        where:
            desc                  | enemies              | expected
            "initialized enemies" | initializedEnemies() | expectedResultForInitializedEnemies()
            "null enemies"        | null                 | "no enemies defined"
            "no enemies defined"  | new ArrayList<>()    | "no enemies defined"
    }

    List<EnemyConfiguration> initializedEnemies() {
        List<EnemyConfiguration> enemies = new ArrayList<>()
        enemies.add(new EnemyConfiguration("name1", "desc1", "greet1", 50, 5, 5))
        enemies.add(new EnemyConfiguration("name2", "desc2", "greet2", 100, 10, 10))

        enemies
    }

    String expectedResultForInitializedEnemies() {
        "\n\t[name=name1,description=desc1,greeting=greet1,maxHp=50,damage=5,damageVariation=5]" +
                "\n\t[name=name2,description=desc2,greeting=greet2,maxHp=100,damage=10,damageVariation=10]"
    }

}
