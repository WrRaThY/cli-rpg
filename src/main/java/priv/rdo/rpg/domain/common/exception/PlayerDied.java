package priv.rdo.rpg.domain.common.exception;

import static priv.rdo.rpg.domain.game.StaticMessages.DEFEAT;

public class PlayerDied extends GameException {
    public PlayerDied() {
        super(DEFEAT);
    }
}
