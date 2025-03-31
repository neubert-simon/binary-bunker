package utility;

import org.slf4j.Logger;

public class ParameterValidation {

    public static void validateParameters(boolean inputCondition, Logger log, String message) throws IllegalArgumentException {
        if(inputCondition) {
            IllegalArgumentException ia = new IllegalArgumentException(message);
            log.error(ia.getMessage());
            throw ia;
        }
    }

}
