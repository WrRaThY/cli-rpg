package priv.rdo.rpg.adapters.outgoing

import priv.rdo.rpg.adapters.util.io.InputParser
import priv.rdo.rpg.adapters.util.io.OutputWriter
import priv.rdo.rpg.domain.game.StaticMessages
import priv.rdo.rpg.ports.outgoing.dto.PlayerConfiguration
import spock.lang.Specification

/**
 * @author WrRaThY
 * @since 11.05.2017
 */
class CliPlayerConfigurationMenuTest extends Specification {
//    def "test askForPlayerConfig"() {
//        given:
//
//        when:
//            // TODO implement stimulus
//        then:
//            // TODO implement assertions
//    }

    //TODO add negative tests
    def "should ask all user questions and get all answers"() {
        given: "output writer that checks that all messages have been printed out"
            OutputWriter outputWriter = Spy(constructorArgs: [System.out]) {
                6 * showMessage(_)
            }

        and: "input parser that checks that tryReadingInputAsInt was called exactly one time and returns a stub value"
            InputParser inputParser = Mock(constructorArgs: [outputWriter, System.in]) {
                2 * readUserInputAsString(_) >> { callRealMethod() }
                2 * readUserInputAsString() >>> ["a", "b"]
                3 * tryReadingInputAsInt(_) >> { callRealMethod() }
                3 * tryReadingInputAsInt() >>> [1, 2, 2]
            }

        and: "an object built with the above mocks"
            CliPlayerConfigurationMenu menu = new CliPlayerConfigurationMenu(inputParser, outputWriter)

        when:
            PlayerConfiguration playerQuestions = menu.askForPlayerConfig(StaticMessages.questionsToPlayer())

        then:
            playerQuestions.name == "a"
            playerQuestions.desc == "b"
            playerQuestions.hpBonus == 1
            playerQuestions.damageBonus == 2
            playerQuestions.damageVariationBonus == 2

    }

    def "should show the introduction"() {
        given:
            String introduction = "intro"

        and:
            OutputWriter outputWriter = Mock {
                1 * showMessageWithSpace(introduction)
            }

        and:
            CliPlayerConfigurationMenu menu = new CliPlayerConfigurationMenu(null, outputWriter)

        expect:
            menu.showIntroduction(introduction)

    }

    def "should greet the Player"() {
        given:
            String greeting = "greeting"

        and:
            OutputWriter outputWriter = Mock {
                1 * showMessageWithSpace(greeting)
            }

        and:
            CliPlayerConfigurationMenu menu = new CliPlayerConfigurationMenu(null, outputWriter)

        expect:
            menu.showIntroduction(greeting)
    }
}
