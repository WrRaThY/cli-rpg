package priv.rdo.rpg.domain.util;

import priv.rdo.rpg.common.util.ToStringBuilder;
import priv.rdo.rpg.domain.character.Player;
import priv.rdo.rpg.domain.world.World;

public class StatisticsBuilder extends GameInfoBuilder<StatisticsBuilder> {
    public static String buildStatistics(World world, Player player) {
        return statistics().withWorld(world).withPlayer(player).build();
    }

    public static StatisticsBuilder statistics() {
        return new StatisticsBuilder();
    }

    @Override
    protected StatisticsBuilder that() {
        return this;
    }

    @Override
    protected String buildInner() {
        //TODO
        return ToStringBuilder.fieldsWithNewlinesAndTabs(this)
                .append("enemies left", world.aliveEnemiesLeft() + "/" + world.getEnemies().size())
                .append("", player.toStringWithColors())
                .build();
    }
}
