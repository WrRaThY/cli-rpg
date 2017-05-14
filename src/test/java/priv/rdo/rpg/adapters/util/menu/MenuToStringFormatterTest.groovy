package priv.rdo.rpg.adapters.util.menu

import priv.rdo.rpg.ports.outgoing.dto.MainMenuItem
import spock.lang.Specification

/**
 * @author WrRaThY
 * @since 07.05.2017
 */
class MenuToStringFormatterTest extends Specification {
    def "returns a properly formatted menu item"() {
        expect:
            new MenuToStringFormatter().format(item, 0) == expected

        where:
            item               | expected
            MainMenuItem.START | "\t1: Start the game"
            null               | "\tWeird stuff happened, item vanished!"
    }
}
