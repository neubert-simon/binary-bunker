package exceptions;
import java.util.NoSuchElementException;

public class NoMatchException extends NoSuchElementException {
    public NoMatchException(final String err) {
        super(err);
    }
}
