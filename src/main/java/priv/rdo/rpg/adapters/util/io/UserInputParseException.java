package priv.rdo.rpg.adapters.util.io;

public class UserInputParseException extends RuntimeException {

    private static final String MSG = "After many attempts user could not enter any valid input. Game will shut down. Good luck next time!";

    public UserInputParseException(String message) {
        super(message);
    }

    public UserInputParseException() {
        super(MSG);
    }

    public UserInputParseException(Throwable cause) {
        super(MSG, cause);
    }
}
