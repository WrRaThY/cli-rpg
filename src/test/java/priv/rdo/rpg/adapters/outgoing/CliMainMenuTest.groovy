package priv.rdo.rpg.adapters.outgoing

import priv.rdo.rpg.adapters.util.io.InputParser
import priv.rdo.rpg.adapters.util.io.OutputWriter
import priv.rdo.rpg.ports.outgoing.dto.MainMenuItem
import spock.lang.Specification

/**
 * @author WrRaThY
 * @since 11.05.2017
 */
class CliMainMenuTest extends Specification {
    static int NUMBER_OF_MENU_ITEMS = MainMenuItem.values().length
    static int LAST_ELEM_IN_MENU = NUMBER_OF_MENU_ITEMS
    static int LAST_ELEM_IN_ENUM = NUMBER_OF_MENU_ITEMS - 1

    def "main menu should show all messages and read input"() {
        given: "output writer that checks that all messages have been printed out"
            OutputWriter outputWriter = Mock {
                1 * showMessageWithSpace("Please choose one of those options:")
                NUMBER_OF_MENU_ITEMS * showMessage(_)
            }

        and: "input parser that checks that tryReadingInputAsInt was called exactly one time and returns a stub value"
            InputParser inputParser = Mock {
                1 * tryReadingMenuChoice(_) >> { callRealMethod() }
                1 * tryReadingInputAsInt(_) >> LAST_ELEM_IN_MENU
            }

        and: "an object built with the above mocks"
            CliMainMenu menu = new CliMainMenu(inputParser, outputWriter)

        when:
            MainMenuItem result = menu.showMenu()

        then: "resulting item should be the last one in enum, but its index is one lower than in menu (because in menu values start at 1 not at 0)"
            result == MainMenuItem.values()[LAST_ELEM_IN_ENUM]
    }
}
