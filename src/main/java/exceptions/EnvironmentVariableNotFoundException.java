package exceptions;

/**
 * custom exception if a environment variable couldn't be found.
 */
public class EnvironmentVariableNotFoundException extends Exception{
    public EnvironmentVariableNotFoundException() {
        super("environment variable not found check build script");
    }
    public EnvironmentVariableNotFoundException(final String message) {
        super(message);
    }
}
