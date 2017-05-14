package priv.rdo.rpg.adapters.outgoing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.domain.common.exception.LoadGameException;
import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.GameStateProvider;
import priv.rdo.rpg.ports.outgoing.dto.GameState;

import java.util.List;

import static priv.rdo.rpg.domain.game.StaticMessages.GAME_LOADING_FAILED;

/**
 * TODO because of this my domain objects must implement Serializable, which is clearly wrong
 * my first go at this was using GSON to store this as JSON, which did not require any additional changes
 * I could write my own JSON (or any other format) library, but that just does not make any sense... hence this workaround
 * <p>
 * TODO: also use SerializedResourceProvider
 */
public class SerializedGameStateProvider extends SerializedResourceProvider<GameState> implements GameStateProvider {
    private static final Logger LOG = LogManager.getLogger(SerializedGameStateProvider.class);

    private static final String DEFAULT_SAVE_FILENAME = "save.ser";

    @Override
    public List<GameState> load() throws ConfigurationException {
        LOG.traceEntry();
        try {
            return super.load();
        } catch (ConfigurationException e) {
            throw new LoadGameException(e);
        }
    }

    @Override
    protected List<GameState> handleException(Exception e) throws ConfigurationException {
        throw new ConfigurationException(GAME_LOADING_FAILED, e);
    }

    @Override
    protected String getFilename() {
        return DEFAULT_SAVE_FILENAME;
    }

    @Override
    protected String basePath() {
        return savePath();
    }

    @Override
    protected boolean isLoadExternal() {
        return true;
    }
}
