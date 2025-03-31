package exceptions;

public class IncorrectAnswerException extends IllegalArgumentException {
    public IncorrectAnswerException(final String err) {
        super(err);
    }
}
