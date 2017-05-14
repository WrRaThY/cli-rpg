package priv.rdo.rpg.ports.outgoing.dto

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 07.05.2017
 */
class MainMenuItemTest extends Specification {
    @Unroll
    def "provides a well structured toString of #type.name() for printing purposes"() {
        when:
            String result = type.toString()

        then:
            println result //print out for silly humans to see something
            !result.contains(MainMenuItem.class.getSimpleName())
            !result.contains("colorNumber=" + type.name())
            !result.contains("optionNumber=" + type.ordinal())
            !result.contains("description=" + type.description)
            result == type.description

        where:
            type               || _
            MainMenuItem.EXIT  || _
            MainMenuItem.START || _
    }
}
