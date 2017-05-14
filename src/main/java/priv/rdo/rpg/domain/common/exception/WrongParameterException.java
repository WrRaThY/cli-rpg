package priv.rdo.rpg.domain.common.exception;

public class WrongParameterException extends GameException {
    public WrongParameterException(String valueType, String value) {
        super("Provided " + valueType + " value (" + value + ") is not valid");
    }
}
