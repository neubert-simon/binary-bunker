package exceptions;

public class IllegalValidatorCallException extends IllegalArgumentException{
    public IllegalValidatorCallException(final String err) {
        super(err);
    }
}
