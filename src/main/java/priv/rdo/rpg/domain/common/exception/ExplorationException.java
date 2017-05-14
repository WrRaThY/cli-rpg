package priv.rdo.rpg.domain.common.exception;

public class ExplorationException extends Exception {
    public static void cannotGo(int index) throws ExplorationException {
        throw new ExplorationException("Cannot go to " + index + "! You can't just jump out of the reality!");
    }

    public ExplorationException(String message) {
        super(message);
    }
}
