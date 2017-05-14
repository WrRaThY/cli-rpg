package priv.rdo.rpg.ports.outgoing.dto;

import priv.rdo.rpg.domain.character.Player;
import priv.rdo.rpg.domain.world.World;

import java.io.Serializable;

public class GameState implements Serializable {
    private final World world;
    private final Player player;

    public GameState(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }
}
