package priv.rdo.rpg.common.util

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 08.05.2017
 */
class ToStringBuilderTest extends Specification {

    @Unroll
    def "provides a well structured toString for builder without class (var1=#var1, #name2=#var2)"() {
        when:
            String result = ToStringBuilder.defaultBuilder(this)
                    .append("var1", var1)
                    .append(name2, var2)
                    .build()

        then:
            println result //print out for silly humans to see something
            result == expected

        where:
            var1    | name2  | var2    || expected
            "var11" | "var2" | "var21" || "[var1=var11,var2=var21]"
            "var11" | "var2" | 3       || "[var1=var11,var2=3]"
            "var11" | "var2" | null    || "[var1=var11]"
            "var11" | ""     | "var21" || "[var1=var11]"
            "var11" | null   | "var21" || "[var1=var11]"
            "var11" | " "    | "var21" || "[var1=var11]"
    }

    @Unroll
    def "provides a well structured toString for builder (var1=#var1, #name2=#var2)"() {
        when:
            String result = ToStringBuilder.defaultBuilder(this)
                    .append("var1", var1)
                    .append(name2, var2)
                    .build()

        then:
            println result //print out for silly humans to see something
            result == expected

        where:
            var1    | name2  | var2    || expected
            "var11" | "var2" | "var21" || "[var1=var11,var2=var21]"
            "var11" | "var2" | 3       || "[var1=var11,var2=3]"
            "var11" | "var2" | null    || "[var1=var11]"
            "var11" | ""     | "var21" || "[var1=var11]"
            "var11" | null   | "var21" || "[var1=var11]"
            "var11" | " "    | "var21" || "[var1=var11]"
    }

    @Unroll
    def "provides a well structured toString for builder with class (var1=#var1, #name2=#var2)"() {
        when:
            String result = ToStringBuilder.defaultBuilder(this)
                    .append("var1", var1)
                    .append(name2, var2)
                    .buildWithClassName()

        then:
            println result //print out for silly humans to see something
            result == expected

        where:
            var1    | name2  | var2    || expected
            "var11" | "var2" | "var21" || "ToStringBuilderTest[var1=var11,var2=var21]"
            "var11" | "var2" | 3       || "ToStringBuilderTest[var1=var11,var2=3]"
            "var11" | "var2" | null    || "ToStringBuilderTest[var1=var11]"
            "var11" | ""     | "var21" || "ToStringBuilderTest[var1=var11]"
            "var11" | null   | "var21" || "ToStringBuilderTest[var1=var11]"
            "var11" | " "    | "var21" || "ToStringBuilderTest[var1=var11]"
    }

    def "throws an exception for a toString call"() {
        when:
            String result = ToStringBuilder.defaultBuilder(this)
                    .append("var1", "var1")
                    .toString()

        then:
            thrown(UnsupportedOperationException)
    }

    def "correctly joins parts of a field in newline mode"() {
        when:
            String result = ToStringBuilder.fieldsWithNewlinesAndTabs(this).fieldToString(buildField(1), false)

        then:
            println result //print out for silly humans to see something
            result == "\tsomeName1: someValue1\n"
    }

    def "correctly joins many fields in newline mode"() {
        when:
            String result = ToStringBuilder.fieldsWithNewlinesAndTabs(this)
                    .append(buildField(1))
                    .append(buildField(2))
                    .append(buildField(3))
                    .build()

        then:
            println result //print out for silly humans to see something
            result == "\tsomeName1: someValue1\n" +
                    "\tsomeName2: someValue2\n" +
                    "\tsomeName3: someValue3\n"
    }

    static ToStringBuilder.Field buildField(int index) {
        new ToStringBuilder.Field("someName" + index, "someValue" + index, Color.CLEAR_ALL_FORMATTING)
    }

}
