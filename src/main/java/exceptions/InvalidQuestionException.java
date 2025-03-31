package exceptions;

public class InvalidQuestionException extends IllegalArgumentException {
    public InvalidQuestionException(final String err) {
        super(err);
    }
}
