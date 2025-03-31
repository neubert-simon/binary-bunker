package validation;

import enumerations.validation.ValidatorTypes;
import exceptions.InvalidIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ParameterValidation;

/**
 * Validator for the subnets
 */
class SubnetValidator extends IPValidator {

    private static final Logger log = LoggerFactory.getLogger(IPv4Validator.class);

    @Override
    public Number validate(final String mask) {
        ParameterValidation.validateParameters(mask == null, log, "Null parameter passed in.");
        log.debug("Method called: validate with mask " + mask);

        // turn mask into octets
        final String [] octets = ((IPv4Validator) ValidatorFactory.getInstance(ValidatorTypes.IPv4)).toValidV4FormatOctets(mask);

        // turn mask to long binary string
        String binaryMask = "";
        for(String octet : octets)
            binaryMask = binaryMask.concat(Long.toBinaryString(Long.parseLong(octet)));
        log.info("Mask " + mask + " converted to binary: " + binaryMask);

        // mask contains ones after zeros
        if(binaryMask.substring(binaryMask.indexOf("0")).contains("1")) {
            log.error("Mask " + mask + " is not formatted correctly: " + binaryMask);
            throw new InvalidIPException("Subnet-Mask not formatted correctly.");
        }
        return Long.parseUnsignedLong(binaryMask, 2);
    }
}