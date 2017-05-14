package priv.rdo.rpg.adapters.util.io

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 10.05.2017
 */
class InputParserTest extends Specification {
    @Shared
    OutputWriter testOutputWriter = Mock()

    def "should return an entered integer value subtracted by one when reading for menu item"() {
        given:
            InputParser inputParser = Mock(InputParser, constructorArgs: [testOutputWriter, System.in]) {
                timesCalled * tryReadingInputAsInt(_) >>> whatReadIntReturns
                tryReadingMenuChoice(optionsSize) >> { callRealMethod() }
            }

        expect:
            inputParser.tryReadingMenuChoice(optionsSize) == expected

        where:
            whatReadIntReturns | optionsSize || expected | timesCalled
            [3]                | 5           || 2        | 1
    }

    def "should thrown an exception when reading an int with with 5th failed try"() {
        given:
            InputParser inputParser = Mock(InputParser, constructorArgs: [testOutputWriter, System.in]) {
                timesCalled * readInt(optionsSize) >> { int size -> throwUserInputException() }
                tryReadingInputAsInt(optionsSize) >> { callRealMethod() }
            }

        when:
            def result = inputParser.tryReadingInputAsInt(optionsSize)

        then:
            thrown(UserInputParseException.class)

        where:
            optionsSize || timesCalled
            5           || 5
    }

    static int throwUserInputException() {
        throw new UserInputParseException("")
    }

    def "should read an int with a #timesCalled try"() {
        given:
            InputParser inputParser = Mock(InputParser, constructorArgs: [testOutputWriter, System.in]) {
                timesCalled * readInt(optionsSize) >>> whatReadIntReturns
                tryReadingInputAsInt(optionsSize) >> { callRealMethod() }
            }

        expect:
            inputParser.tryReadingInputAsInt(optionsSize) == expected

        where:
            whatReadIntReturns                  | optionsSize || expected | timesCalled
            [3]                                 | 5           || 3        | 1
            [fail(), 1]                         | 5           || 1        | 2
            [fail(), fail(), fail(), fail(), 1] | 5           || 1        | 5
    }

    /*
     * groovy magic :D
     */

    static Closure<Integer> fail() {
        return { throwUserInputException() }
    }

    def "should read an int value"() {
        given:
            ByteArrayInputStream userInputStream = new ByteArrayInputStream(userInput.getBytes())
            InputParser inputParser = new InputParser(testOutputWriter, userInputStream)

        expect:
            expected == inputParser.readInt(optionsSize)

        where:
            optionsSize | userInput | expected
            5           | "3"       | 3
            5           | "0"       | 0
    }

    @Unroll
    def "should thrown an exception while reading a badly defined int value - #desc"() {
        given:
            ByteArrayInputStream userInputStream = new ByteArrayInputStream(userInput.getBytes())
            InputParser inputParser = new InputParser(testOutputWriter, userInputStream)

        when:
            inputParser.readInt(optionsSize)

        then:
            thrown(expected)

        where:
            optionsSize | userInput || expected                | desc
            5           | "3asd"    || UserInputParseException | "not an int"
            5           | "3,2"     || UserInputParseException | "still not an int"
            5           | "3.2"     || UserInputParseException | "try again, not an int"
            5           | "-1"      || UserInputParseException | "value below 0"
            5           | "5"       || UserInputParseException | "value equal to " + optionsSize
            5           | "555"     || UserInputParseException | "value over " + optionsSize
    }


}
