package priv.rdo.rpg.ports.outgoing;

import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.dto.GameState;

/**
 * TODO: extend to support loading/saving multiple game states
 */
public interface GameStateProvider extends ResourceProvider<GameState> {
    default GameState loadGame() throws ConfigurationException {
        return loadOne();
    }

    default void saveGame(GameState gameState) throws ConfigurationException {
        saveOne(gameState);
    }
}
