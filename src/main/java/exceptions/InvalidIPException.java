package exceptions;

public class InvalidIPException extends IllegalArgumentException{
    public InvalidIPException(final String err) {
        super(err);
    }
}
