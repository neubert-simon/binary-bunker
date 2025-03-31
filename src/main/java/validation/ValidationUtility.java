package validation;

import enumerations.regex.RegexV4;
import enumerations.regex.RegexV6;
import exceptions.InvalidIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ParameterValidation;

/**
 * Validator utility for all validators if needed
 */
public class ValidationUtility {

    private static final Logger log = LoggerFactory.getLogger(IPv4Validator.class);

    //region IPv4 Validation
    /**
     * Checks if the given address follows the IPv4 format and throws InvalidIPException if it doesn't
     * @param address the IP address that is to be checked
     * @throws InvalidIPException thrown if the IP address does not follow the IPv4 format
     */
    static void validateIPv4Format(final String address) throws InvalidIPException {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: validateIPv4Format with address " + address);
        // check if address passes all regex checks
        for (RegexV4 regex : RegexV4.values()) {
            if(!address.matches(regex.regex)) {
                log.error("Address " + address + " is invalid: " + regex.errorMessage);
                throw new InvalidIPException(regex.errorMessage);
            }
        }

        log.info("IP " + address + " passed validation tests");
    }
    //endregion

    //region IPv6 Validation
    /**
     * Checks if the given address follows the IPv6 format and throws InvalidIPException if it doesn't
     * @param address the IP address that is to be checked
     * @throws InvalidIPException thrown if the IP address does not follow the IPv6 format
     */
    static void validateIPv6Format(final String address) throws InvalidIPException {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: validateIPv6Format with address " + address);
        // check if address passes all regex checks
        for (RegexV6 formatCheck : RegexV6.values())
            if (!address.matches(formatCheck.regex)) {
                log.error("Address " + address + " is invalid: " + formatCheck.errorMessage);
                throw new InvalidIPException(formatCheck.errorMessage);
            }

        // check address ip contains several double-colons
        if (address.contains("::") && address.replaceFirst("::", "").contains("::")) {
            log.error("Address " + address + " is invalid: Address contains several double-colons");
            throw new InvalidIPException("Address contains several double-colons");
        }
        // validate addresses hextets
        validateHextets(address);

        log.info("IP " + address + " passed validation tests");
    }

    /**
     * Checks if the given address follows the IPv6s hextets format and throws InvalidIPException if it doesn't
     * @param address the IP address that is to be checked
     */
    private static void validateHextets(final String address) {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: validateHextets with address " + address);
        // separate hextets to String array
        final String[] hextets = address.split(":");
        // check if all hextets follow the IPv6 hextet format
        for (String hextet : hextets) {
            if (!hextet.isEmpty() && !hextet.matches("^[0-9a-f]{0,4}$")) { // chars 1-9 and a-f between 0-4 times
                log.error("Address " + address + " is invalid: Addresses hextets may contain a maximum of 4 hexadecimal numbers");
                throw new InvalidIPException("Addresses hextets may contain a maximum of 4 hexadecimal numbers");
            }
        }

        log.info("IP " + address + " passed hextet validation tests");
    }
    //endregion
}
