package priv.rdo.rpg.common.context

import priv.rdo.rpg.adapters.outgoing.SerializedGameStateProvider
import priv.rdo.rpg.domain.common.exception.DIException
import priv.rdo.rpg.ports.outgoing.GameStateProvider
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 06.05.2017
 */
class DIContextTest extends Specification {

    @Unroll
    def "returns an expected implementation (#expectedImplType.getSimpleName()) for the given type (#type.getSimpleName())"() {

        when:
            def impl = DIContext.getBean(type)

        then:
            impl.getClass() == expectedImplType

        where:
            type                              | expectedImplType
            SerializedGameStateProvider.class | SerializedGameStateProvider.class
            GameStateProvider.class           | SerializedGameStateProvider.class
    }

    @Unroll
    def "returns an error for the unexpected input - #desc"() {

        when:
            def impl = DIContext.getBean(type)

        then:
            thrown(DIException)

        where:
            type                | desc
            null                | "an empty type"
            DIContextTest.class | "a class that does not exist in the context"
    }
}
