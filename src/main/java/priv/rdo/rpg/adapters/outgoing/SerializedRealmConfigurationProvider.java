package priv.rdo.rpg.adapters.outgoing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.common.generator.SerializationRealmConfigurationGenerator;
import priv.rdo.rpg.ports.outgoing.RealmConfigurationProvider;
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration;

import java.util.List;

import static priv.rdo.rpg.adapters.util.io.ExternalIO.resourcesPath;

public class SerializedRealmConfigurationProvider extends SerializedResourceProvider<RealmConfiguration> implements RealmConfigurationProvider {
    private static final Logger LOG = LogManager.getLogger(SerializedRealmConfigurationProvider.class);

    public static final String FILENAME = "realm_configuration.ser";

    @Override
    protected String getFilename() {
        return FILENAME;
    }

    @Override
    protected String basePath() {
        return resourcesPath() + configPath();
    }

    @Override
    protected boolean isLoadExternal() {
        return false;
    }

    @Override
    protected List<RealmConfiguration> handleException(Exception e) {
        //throw new ConfigurationException("Could not load Realm Configuration", e);
        //TODO: decide what is best here.
        //with json an exception might have been a better option, but serialized files are not human readable anyway, so no one will update them
        return rollbackToBuiltInDefault();
    }

    private List<RealmConfiguration> rollbackToBuiltInDefault() {
        LOG.error("could not load realm configuration from a file, rolling back to defaults");
        return SerializationRealmConfigurationGenerator.realms();
    }
}
