package priv.rdo.rpg.domain.world

import priv.rdo.rpg.domain.world.location.LocationType
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 06.05.2017
 */
class LocationTypeTest extends Specification {

    @Unroll
    def "provides a well structured toString for #type.name() for debugging purposes"() {
        when:
            String result = type.toString()

        then:
            println result //print out for silly humans to see something
            !result.contains(LocationType.class.getSimpleName())
            result.contains("name=" + type.name())
            result.contains("description=" + type.description)
            result.contains("mapMark=" + type.mapMark)

        where:
            type               || _
            LocationType.ENEMY || _
            LocationType.EMPTY || _
    }
}
