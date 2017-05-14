package priv.rdo.rpg.domain.common.exception;

import priv.rdo.rpg.domain.character.Player;
import priv.rdo.rpg.domain.world.World;

import static priv.rdo.rpg.domain.game.StaticMessages.VICTORY;

/**
 * however strange that looks... its the easiest way to end all those pesky loops...
 */
public class Victory extends GameException {
    public Victory(String worldName, String playerName) {
        super(String.format(VICTORY, playerName, worldName));
    }

    public static void victory(World world, Player player) {
        throw new Victory(world.getName(), player.getName());
    }
}
