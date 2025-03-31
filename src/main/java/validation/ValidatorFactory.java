package validation;

import enumerations.validation.ValidatorTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Java is stupid, lasst diesen import mal Zeitweise
//import static enumerations.validation.ValidatorTypes.*;

/**
 * Validator factory to create instances of Validators
 */
public class ValidatorFactory {

    private static IL3Validator ipv4Instance, ipv6Instance, subnetInstance;

    private static final Logger log = LoggerFactory.getLogger(ValidatorFactory.class);

    /**
     * creates and returns an instance of the given validator type
     * @param type the type of validator that is needed
     * @return instance of the given validator type
     */
    public static IL3Validator getInstance(final ValidatorTypes type) {
        log.debug("Method getInstance called: getInstance with type: " + type);

        log.info("Create Validator instance of type: " + type.name());
        switch (type) {
            case ValidatorTypes.IPv4 -> {
                if (ipv4Instance == null) ipv4Instance = new IPv4Validator();
                return ipv4Instance;
            }
            case ValidatorTypes.IPv6 -> {
                if (ipv6Instance == null) ipv6Instance = new IPv6Validator();
                return ipv6Instance;
            }
            case ValidatorTypes.Subnet -> {
                if (subnetInstance == null) subnetInstance = new SubnetValidator();
                return subnetInstance;
            }
            default -> {
                log.error("Invalid Validator type call");
                throw new IllegalCallerException("Calling invalid Validator Type.");
            }
        }
    }

}