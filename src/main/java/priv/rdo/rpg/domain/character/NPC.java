package priv.rdo.rpg.domain.character;

import priv.rdo.rpg.common.util.Color;
import priv.rdo.rpg.domain.world.location.LocationType;

/**
 * NPC = Non Player Character
 * all variables loaded from some external configuration
 */
public abstract class NPC extends Character {
    private final String greeting;
    private final int expReward;

    public NPC(String name, String description, String greeting, int maxHp, int damage, int damageVariation) {
        super(name, description, maxHp, damage, damageVariation);
        this.greeting = greeting;
        this.expReward = maxHp; //TODO: add to config?
    }

    public int getExpReward() {
        return expReward;
    }

    public boolean isEnemy() {
        return this instanceof Enemy;
    }

    public String greeting() {
        return greetingColor().format(greeting);
    }

    protected abstract Color greetingColor();

    public LocationType locationType() {
        if (!isAlive) {
            return LocationType.NPC_DEAD;
        } else {
            return locationTypeSpecificForNpc();
        }
    }

    protected abstract LocationType locationTypeSpecificForNpc();

}
