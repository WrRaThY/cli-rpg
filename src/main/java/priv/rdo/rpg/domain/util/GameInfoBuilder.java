package priv.rdo.rpg.domain.util;

import priv.rdo.rpg.domain.character.Player;
import priv.rdo.rpg.domain.world.World;

abstract class GameInfoBuilder<BuilderType extends GameInfoBuilder<BuilderType>> extends OutputBuilderBase {
    protected World world;
    protected Player player;

    public BuilderType withWorld(World world) {
        this.world = world;
        return that();
    }

    public BuilderType withPlayer(Player player) {
        this.player = player;
        return that();
    }

    protected abstract BuilderType that();

    public String build() {
        if (null == world) {
            return errorOccurred();
        }

        return buildInner();
    }

    protected String errorOccurred() {
        return "Cannot build request information, an error occurred";
    }

    //TODO: rename it to something meaningful
    protected abstract String buildInner();
}
