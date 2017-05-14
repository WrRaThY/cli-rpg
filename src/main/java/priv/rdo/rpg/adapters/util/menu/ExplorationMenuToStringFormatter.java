package priv.rdo.rpg.adapters.util.menu;

import priv.rdo.rpg.ports.outgoing.dto.ExplorationMenuItem;

public class ExplorationMenuToStringFormatter extends MenuToStringFormatter<ExplorationMenuItem> {
    public ExplorationMenuToStringFormatter() {
    }

    public String format(ExplorationMenuItem item, int itemNumberInList) {
        return format(item.getDescription(), item.getKeyBinding());
    }
}
