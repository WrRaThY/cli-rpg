package priv.rdo.rpg.domain.common.exception;

import priv.rdo.rpg.ports.exception.ConfigurationException;

import static priv.rdo.rpg.common.util.ColorFormatter.bold;
import static priv.rdo.rpg.common.util.ColorFormatter.red;

public class LoadGameException extends ConfigurationException {
    public LoadGameException(Throwable cause) {
        super(bold(red("Failed to load game, please start a new one")), cause);
    }
}