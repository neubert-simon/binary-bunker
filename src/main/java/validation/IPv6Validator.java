package validation;

import java.math.BigInteger;
import java.util.Arrays;

import exceptions.InvalidIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ParameterValidation;

/**
 * Validator for the address type IPv6
 */
public class IPv6Validator extends IPValidator {

    IPv6Validator() {}

    private static final Logger log = LoggerFactory.getLogger(IPv4Validator.class);

    @Override
    public Number validate(final String address) throws InvalidIPException {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: validate with address " + address);

        // check if address follows IPv6 format and cut it into hextets
        final String[] hextets = toValidV6FormatHextets(address.trim().toLowerCase());
        final StringBuilder addressBuilder = new StringBuilder();
        // turn hextets into binary
        for (String hextet : hextets) {
            String bits = Integer.toBinaryString(Integer.parseInt(hextet, 16));
            bits = utility.BinaryHelper.leftPadBinary(bits, 16);
            addressBuilder.append(bits);
        }
        log.info("Address turned to binary: " + addressBuilder);
        return new BigInteger(addressBuilder.toString(), 2);
    }

    /**
     * checks if the given address follows IPv6 format and cuts the address into full IPv6 hextets
     * @param address the IPv6 address that is to be validated and cut into hextets
     * @return String array containing the IPs hextets
     */
    public String[] toValidV6FormatHextets(final String address) {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: toValidV6FormatHextets with address " + address);

        // validate if ipv6 address contains for example mapped ipv4 and turn to generell ipv6
        final String generalizedAddress = validateAndGeneralizeAddress(address);

        // turn address to hextets and return
        return turnToHextetStringArray(generalizedAddress);
    }

    /**
     * validates that given address is a valid IPv6 format and turn different IPv6 formats into general IPv6 format
     * @param address the IPv6 address that is to be validated and turned generell
     * @return generalized IPv6 address
     */
    private String validateAndGeneralizeAddress(String address) {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: validateAndFillAddress with address " + address);

        // check for special IPv6 formats
        if (address.startsWith("::ffff:") && address.contains(".")) {
            log.info("Address " + address + " is mapped ip using ::ffff:<IPv4>");
            // check if IPv4 is mapped onto IPv6
            address = translateMappedIPv4ontoIPv6(address);
            log.info("Address turned to standard IPv6 format: " + address);
        } else {
            log.info("Address " + address + " is not a special ip");
            // check for valid generell IPv6 format
            ValidationUtility.validateIPv6Format(address);
        }

        return address;
    }

    /**
     * Turns a given IPv6 address using the general IPv6 format into Hextets in an array
     * @param address the IPv6 address that is to be turned into hextets
     * @return String array containing given IPv6 addresses hextets
     */
    private String[] turnToHextetStringArray(final String address) {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: turnToHextetStringArray with address " + address);

        // creating hextet arrays
        final String[] hextets = address.split(":");
        final String[] completeHextets = new String[8];
        Arrays.fill(completeHextets, "0000");

        // filling completeHextets array with relevant hextets
        int counter = 0;
        for (String hextet : hextets) {
            if (hextet.isEmpty()) {
                if (counter != 0)
                    counter = 8 - (hextets.length-1) + counter;
                else
                    counter++;
            } else {
                hextet = "0".repeat(4 - hextet.length()).concat(hextet);
                completeHextets[counter] = hextet;
                counter++;
            }
        }

        log.info("Address " + address + " turned to hextets " + completeHextets);
        return completeHextets;
    }

    /**
     * translates IPv4 part of mapped IPv6 address using ::ffff: to standard IPv6 address format
     * <br><p>e.g. ::ffff:192.168.0.1  ->  ::ffff:c0a8:1</p>
     * @param address the to be translated IP address
     * @return valid IPv6 formatted address
     */
    String translateMappedIPv4ontoIPv6(final String address) {
        ParameterValidation.validateParameters(address == null, log, "Address parameter null.");
        assert address != null;
        log.debug("Method called: translateMappedIPv4ontoIPv6 with address " + address);
        // create substrings for the IPv6 part and the IPv4 part of the address
        String iPv6Address = address.substring(0, 7),
                iPv4Address = address.substring(7),
                translatedIPv4;

        // validate the IPv4 part of the address
        ValidationUtility.validateIPv4Format(iPv4Address);

        // create String array containing the IPv4 octets
        final String[] IPv4Octets = iPv4Address.split("[.]");
        // create IPv6 hextets containing two IPv4 octets each in binary
        String firstIPv4Hextet =
                utility.BinaryHelper.leftPadBinary(
                        Integer.toBinaryString(
                                Integer.parseInt(IPv4Octets[0])), 8) +
                        utility.BinaryHelper.leftPadBinary(
                                Integer.toBinaryString(
                                        Integer.parseInt(IPv4Octets[1])), 8),
                secondIPv4Hextet =
                        utility.BinaryHelper.leftPadBinary(
                                Integer.toBinaryString(
                                        Integer.parseInt(IPv4Octets[2])), 8) +
                                utility.BinaryHelper.leftPadBinary(
                                        Integer.toBinaryString(
                                                Integer.parseInt(IPv4Octets[3])), 8);

        // translate binary hextets to hexadecimal numbers
        translatedIPv4 =
                Integer.toHexString(
                        Integer.parseInt(firstIPv4Hextet, 2)) + ":" +
                        Integer.toHexString(
                                Integer.parseInt(secondIPv4Hextet, 2));

        log.info("Address " + address + " turned into general IPv6 address format " + iPv6Address + translatedIPv4);
        return iPv6Address + translatedIPv4;
    }


}
