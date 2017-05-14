package priv.rdo.rpg.common.util

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 12.05.2017
 */
class IntUtilsTest extends Specification {
    @Unroll
    def "is #tested within <#min, max)? #expected"() {
        expect:
            IntUtils.isWithinRangeExclusive(tested, min, max) == expected

        where:
            tested | min | max || expected
            4      | 3   | 10  || true
            -1     | 3   | 10  || false
            10     | 3   | 10  || false
            3      | 3   | 10  || true
    }

    @Unroll
    def "is #tested within <0, max)? #expected"() {
        expect:
            IntUtils.isBetweenZeroAndMaxExclusive(tested, max) == expected

        where:
            tested | max || expected
            1      | 10  || true
            -1     | 10  || false
            10     | 10  || false
            0      | 10  || true
    }
}
