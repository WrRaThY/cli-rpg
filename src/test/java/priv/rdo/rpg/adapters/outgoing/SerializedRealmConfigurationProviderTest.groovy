package priv.rdo.rpg.adapters.outgoing

import priv.rdo.rpg.common.generator.SerializationRealmConfigurationGenerator
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 06.05.2017
 */
class SerializedRealmConfigurationProviderTest extends Specification {

    @Unroll
    def "#configName configuration loaded without problems"() {
        when:
            List<RealmConfiguration> configurations = new SerializedRealmConfigurationProvider().load()
            SerializationRealmConfigurationGenerator.generateRealms()

        then:
            configurations.get(configNumber).name == configName
            configurations.get(configNumber).enemyConfiguration.get(enemyNumber).name == enemyName

        where:
            configNumber | configName          | enemyNumber | enemyName
            0            | "Grand Theft Auto"  | 0           | "Gangsters"
            0            | "Grand Theft Auto"  | 22          | "Uno Carb"
            1            | "Lord of the rings" | 6           | "Uruk-Hai Warriors"
            1            | "Lord of the rings" | 22          | "Smaug"
    }
}
