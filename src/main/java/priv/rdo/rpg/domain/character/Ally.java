package priv.rdo.rpg.domain.character;

import priv.rdo.rpg.common.util.Color;
import priv.rdo.rpg.domain.world.location.LocationType;

/**
 * TODO:
 * used only a bit, add to configuration
 */
public class Ally extends NPC {
    public Ally(String name, String description, String greeting, int maxHp, int damage, int damageVariation) {
        super(name, description, greeting, maxHp, damage, damageVariation);
    }

    @Override
    protected LocationType locationTypeSpecificForNpc() {
        return LocationType.ALLY;
    }

    @Override
    protected Color greetingColor() {
        return Color.GREEN;
    }
}
