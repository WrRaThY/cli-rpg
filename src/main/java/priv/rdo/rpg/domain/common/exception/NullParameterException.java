package priv.rdo.rpg.domain.common.exception;

public class NullParameterException extends GameException {
    public NullParameterException(String valueType) {
        super(valueType + " cannot be null");
    }
}
