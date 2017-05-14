package priv.rdo.rpg.domain.world.location;

import priv.rdo.rpg.common.util.ToStringBuilder;

import static priv.rdo.rpg.common.util.ColorFormatter.boldMagenta;
import static priv.rdo.rpg.common.util.ColorFormatter.green;
import static priv.rdo.rpg.common.util.ColorFormatter.red;
import static priv.rdo.rpg.common.util.ColorFormatter.underlinedBlue;

public enum LocationType {
    //    NOT_EXPLORED("Miracles yet to be discovered", "#"), //TODO: implement fog of war later. this will alter WorldMap creation process

    EMPTY("Nothing is here", " "), //TODO: add different types of "EMPTY" like "JUNGLE", "DESERT", "STREET" or something. sometime thugs might attack you (yes, like in pokemon)
    ALLY("A friend looks gently at you", green("A")),
    ENEMY("Enemy sighted!", red("E")),
    NPC_DEAD("Someone died here", boldMagenta("X")), //"†" nice character, but does not work very well with windows
    PLAYER("This is you, my dear Player", underlinedBlue("P"))

//    OBSTACLE("You shall not pass!", "\u008F"), //TODO: implement obstacles later. this will alter WorldMap creation process
    ;

    private final String description;
    private final String mapMark;

    LocationType(String description, String mapMark) {
        this.description = description;
        this.mapMark = mapMark;
    }

    public String getDescription() {
        return description;
    }

    public String getMapMark() {
        return mapMark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.defaultBuilder(this)
                .append("name", name())
                .append("mapMark", mapMark)
                .append("description", description)
                .build();
    }
}
