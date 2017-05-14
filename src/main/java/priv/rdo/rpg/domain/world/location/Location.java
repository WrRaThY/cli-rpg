package priv.rdo.rpg.domain.world.location;

import priv.rdo.rpg.common.util.ToStringBuilder;
import priv.rdo.rpg.domain.character.NPC;

import java.io.Serializable;

public class Location implements Serializable {
    private final Coordinates coordinates;
    private LocationType type; //may change, for example from ENEMY to NPC_DEAD
    private NPC npc;

    public Location(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.type = LocationType.EMPTY;
    }

    public Location(Coordinates coordinates, NPC npc) {
        this.coordinates = coordinates;
        this.type = npc.locationType();
        this.npc = npc;
    }

    public boolean isAnyoneThere() {
        return null != npc && npc.isAlive();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public NPC getNpc() {
        return npc;
    }

    public LocationType getType() {
        return relevantLocationType();
    }

    public String mapMark() {
        return relevantLocationType().getMapMark();
    }

    public String desc() {
        return relevantLocationType().getDescription();
    }

    private LocationType relevantLocationType() {
        if (null != npc) {
            return npc.locationType();
        }

        return type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.defaultBuilder(this)
                .append("coordinates", coordinates.toString())
                .append("type", type.name())
                .append("mapMark", mapMark())
                .build();
    }
}
