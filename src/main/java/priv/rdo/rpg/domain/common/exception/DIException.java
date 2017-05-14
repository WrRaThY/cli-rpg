package priv.rdo.rpg.domain.common.exception;

public class DIException extends RuntimeException {
    private static String MSG = "All hell broke loose! Shutting everything down...";

    public DIException() {
        super(MSG);
    }

    public DIException(Throwable cause) {
        super(MSG, cause);
    }
}
