package priv.rdo.rpg.ports.outgoing.dto;

import priv.rdo.rpg.common.util.ToStringBuilder;

public enum ExplorationMenuItem {
    UP("W", "Go North"),
    DOWN("S", "Go South"),
    LEFT("A", "Go West"),
    RIGHT("D", "Go East"),
    COMMANDS("1", "Show this menu"),
    MAP("2", "Show map"),
    LEGEND("3", "Show legend"),
    PLAYER("4", "Show player info"),
    STATS("5", "Show statistics"),
    SAVE("9", "Save the game"),
    EXIT("0", "Exit to main menu");

    private final String keyBinding;
    private final String description;

    ExplorationMenuItem(String keyBinding, String description) {
        this.keyBinding = keyBinding;
        this.description = description;
    }

    public String getKeyBinding() {
        return keyBinding;
    }

    public String getDescription() {
        return description;
    }

    public static ExplorationMenuItem fromString(String input) {
        if (input == null) {
            return null;
        }

        for (ExplorationMenuItem item : ExplorationMenuItem.values()) {
            if (item.keyBinding.equalsIgnoreCase(input)) {
                return item;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.defaultBuilder(this)
                .append("keyBinding", keyBinding)
                .append("description", description)
                .build();
    }
}
