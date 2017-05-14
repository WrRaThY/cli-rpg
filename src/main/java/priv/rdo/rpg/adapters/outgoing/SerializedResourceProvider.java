package priv.rdo.rpg.adapters.outgoing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.adapters.util.io.ExternalIO;
import priv.rdo.rpg.adapters.util.io.InternalIO;
import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.ResourceProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public abstract class SerializedResourceProvider<ItemType> implements ResourceProvider<ItemType> {
    private static final Logger LOG = LogManager.getLogger(SerializedResourceProvider.class);

    private static final String PATH_TO_CONFIG_FILES = "config";
    private static final String PATH_TO_SAVE_FILES = "saves";

    @SuppressWarnings("unchecked")
    public List<ItemType> load() throws ConfigurationException {
        LOG.debug("Trying to load resources from a file");

        try (ObjectInputStream objectInputStream = getObjectInputStream()) {
            List<ItemType> resources = (List<ItemType>) objectInputStream.readObject();
            if (null == resources || resources.isEmpty()) {
                throw new ConfigurationException("loaded configuration is empty");
            }

            LOG.debug("resources loading finished with a success");

            return resources;
        } catch (Exception e) {
            LOG.error("failed to load resources from " + getFilename());
            return handleException(e);
        }
    }

    private ObjectInputStream getObjectInputStream() throws IOException {
        if (isLoadExternal()) {
            return ExternalIO.objectInputStream(basePath(), getFilename());
        } else {
            return InternalIO.objectInputStream(basePath(), getFilename());
        }
    }

    @Override
    public void save(List<ItemType> resources) throws ConfigurationException {
        LOG.debug("Trying to save resources to a file");

        try (ObjectOutputStream objectOutputStream = ExternalIO.objectOutputStream(basePath(), getFilename())) {
            objectOutputStream.writeObject(resources);
            LOG.debug("resources saving finished with a success");
        } catch (Exception e) {
            throw new ConfigurationException("Could not save resources, shutting down", e);
        }
    }


    protected abstract List<ItemType> handleException(Exception e) throws ConfigurationException;

    protected abstract String getFilename();

    protected abstract String basePath();

    protected abstract boolean isLoadExternal();

    public static String configPath() {
        return PATH_TO_CONFIG_FILES;
    }

    public static String savePath() {
        return PATH_TO_SAVE_FILES;
    }
}
