package priv.rdo.rpg.ports.outgoing.dto;

import static priv.rdo.rpg.common.util.ColorFormatter.blue;
import static priv.rdo.rpg.common.util.ColorFormatter.red;

public enum BeforeFightMenuItem {
    ATTACK(red("Attack!!!")),
    FALL_BACK(blue("Fall back from this fight."));
    private final String description;

    BeforeFightMenuItem(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
