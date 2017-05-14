package priv.rdo.rpg.ports.outgoing.dto;

import static priv.rdo.rpg.common.util.ColorFormatter.blue;
import static priv.rdo.rpg.common.util.ColorFormatter.red;
import static priv.rdo.rpg.common.util.ColorFormatter.yellow;
import static priv.rdo.rpg.domain.game.StaticMessages.FIGHT_BONUS_ARMOR_FOR_DEFENCE;
import static priv.rdo.rpg.domain.game.StaticMessages.FIGHT_FLEEING_HP_REDUCTION;

public enum FightMenuItem {
    ATTACK(red("Attack") + " the enemy with your weapon"),
    DEFEND(blue("Raise defences") + ", which will reduce any future damage taken in that skirmish by " + FIGHT_BONUS_ARMOR_FOR_DEFENCE),
    FLEE(yellow("Flee like a coward.") + " This will save your life, but reduce maxHp by " + FIGHT_FLEEING_HP_REDUCTION);

    private final String description;

    FightMenuItem(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
