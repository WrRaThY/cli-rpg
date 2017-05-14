package priv.rdo.rpg.ports.outgoing.dto;

public enum MainMenuItem {
    START("Start the game"),
    LOAD("Load a saved status of the game"),
    EXIT("Leave the game");

    private final String description;

    MainMenuItem(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
