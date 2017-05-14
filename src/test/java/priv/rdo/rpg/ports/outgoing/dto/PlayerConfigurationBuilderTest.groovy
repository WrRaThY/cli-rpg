package priv.rdo.rpg.ports.outgoing.dto

import priv.rdo.rpg.domain.common.exception.PlayerValidationException
import spock.lang.Specification
import spock.lang.Unroll

import static priv.rdo.rpg.domain.game.StaticMessages.MAX_BONUS_POINTS

/**
 * @author WrRaThY
 * @since 08.05.2017
 */
class PlayerConfigurationBuilderTest extends Specification {
    @Unroll
    def "player '#name' passes configuration validation"() {
        given:
            PlayerConfiguration.PlayerConfigurationBuilder builder = PlayerConfiguration.builder(MAX_BONUS_POINTS)
                    .withName(name)
                    .withDesc(desc)
                    .withHpBonus(hpBonus)
                    .withDamageBonus(dmgBonus)
                    .withDamageVariationBonus(dmgVariationBonus)
        when:
            builder.build()

        then:
            noExceptionThrown()

        where:
            name             | desc                                   | hpBonus | dmgBonus | dmgVariationBonus
            "Stefan"         | ""                                     | 1       | 1        | 3
            "Jack"           | "A great tale of Jacks achievements"   | 2       | 2        | 1
            "Klaus"          | "A great tale of Klaus's achievements" | 0       | 2        | 3
            "Murloc Morrrgl" | "Sllllurp"                             | 0       | 0        | 0
    }

    @Unroll
    def "player '#name' fails configuration validation"() {
        given:
            PlayerConfiguration.PlayerConfigurationBuilder builder = PlayerConfiguration.builder(MAX_BONUS_POINTS)
                    .withName(name)
                    .withDesc(desc)
                    .withHpBonus(hpBonus)
                    .withDamageBonus(dmgBonus)
                    .withDamageVariationBonus(dmgVariationBonus)
        when:
            builder.build()

        then:
            PlayerValidationException exc = thrown()
            exc.getMessage().contains(expectedMessage)

        where:
            name      | desc     | hpBonus | dmgBonus | dmgVariationBonus || expectedMessage
            "Stefan"  | ""       | 1       | 2        | 3                 || "Too many bonus stats, the limit is " + MAX_BONUS_POINTS
            "Klaus"   | ""       | 2       | 2        | 2                 || "Too many bonus stats, the limit is " + MAX_BONUS_POINTS
            "Bernard" | ""       | 3       | 2        | 1                 || "Too many bonus stats, the limit is " + MAX_BONUS_POINTS
            "Paul"    | ""       | -1      | 0        | 0                 || "Too many bonus stats, the limit is " + MAX_BONUS_POINTS
            ""        | "fafafa" | 1       | 2        | 2                 || "Name cannot be empty"
            null      | "fafafa" | 1       | 2        | 2                 || "Name cannot be empty"
    }
}
