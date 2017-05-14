package priv.rdo.rpg.domain.character;

import priv.rdo.rpg.common.util.Color;
import priv.rdo.rpg.domain.world.location.LocationType;
import priv.rdo.rpg.ports.outgoing.dto.EnemyConfiguration;

public class Enemy extends NPC {
    public Enemy(String name, String description, String greeting, int maxHp, int damage, int damageVariation) {
        super(name, description, greeting, maxHp, damage, damageVariation);
    }

    public Enemy(EnemyConfiguration conf) {
        this(conf.getName(), conf.getDescription(), conf.getGreeting(), conf.getMaxHp(), conf.getDamage(), conf.getDamageVariation());
    }

    @Override
    protected LocationType locationTypeSpecificForNpc() {
        return LocationType.ENEMY;
    }

    @Override
    protected Color greetingColor() {
        return Color.RED;
    }
}
