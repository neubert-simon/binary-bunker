package validation;

import exceptions.InvalidIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ParameterValidation;

import static utility.BinaryHelper.*;

/**
 * Validator for the address type IPv4
 */
public class IPv4Validator extends IPValidator {
    private static final Logger log = LoggerFactory.getLogger(IPv4Validator.class);

    @Override
    public Number validate(final String address) throws InvalidIPException {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: validate with address {}", address);

        final String trimmedAddress = address.trim();

        // validate address and cut it into octets
        final String[] octets = toValidV4FormatOctets(trimmedAddress);
        // turn octets to binary, fill them up and make it one String again
        final StringBuilder addressBinary = new StringBuilder();
        for(String octet : octets)
            addressBinary.append(leftPadBinary(Integer.toBinaryString(Integer.parseInt(octet)), 8));

        log.info("address " + trimmedAddress + " turned to binary: " + addressBinary);
        // parse addressBinary to int
        return Integer.parseUnsignedInt(addressBinary.toString(), 2);
    }

    /**
     * checks if given address follows the IPv4 format and cuts it into octets
     * @param address the address that is to be checked and turned into octets
     * @return String array containing the addresses octets
     * @throws InvalidIPException means an invalid IP has been detected
     */
    public String[] toValidV4FormatOctets(final String address) throws InvalidIPException {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: tiValidV4FormatOctets with address " + address);
        // validate IPv4 Format
        ValidationUtility.validateIPv4Format(address);
        // split address to octets
        return address.split("[.]");
    }

    /**
     * checks if a given prefix follows the IPv4 format and returns it as an int
     * @param prefixInput the given prefix that is to be checked
     * @return prefixInput parsed to an int
     */
    public int validatePrefix(final String prefixInput) {
        ParameterValidation.validateParameters(prefixInput == null || prefixInput.isEmpty(), log, "Prefix " + prefixInput + " is invalid: empty or null");
        assert prefixInput != null;
        if(!prefixInput.matches("^0?(?:[0-9]|[1-2][0-9]|3[0-2])$")) {
            log.error("Address " + prefixInput + " is invalid: Prefix is not integer between 0 and 32");
            throw new IllegalArgumentException("Prefix must be an integer between 0 and 32.");
        }
        log.debug("Method called: validatePrefix with prefix " + prefixInput);
        return Integer.parseInt(prefixInput);
    }
}