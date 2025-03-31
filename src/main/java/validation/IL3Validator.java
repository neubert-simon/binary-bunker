package validation;

@FunctionalInterface
public interface IL3Validator {
    /**
     * <p>Validates and turns a given IP address to a binary number.</p>
     * DECISION:
     * <p>This method does not strictly adhere to the Single Responsibility Principle.
     * The method performs two distinct actions (validating and returning the value), but these actions are so inextricably linked,
     * that it does not make sense to provide two separate methods.</p>
     * @param ip is the address that is to be turned into a binary number
     * @return IP in full binary depiction
     */
    Number validate(String ip);

}