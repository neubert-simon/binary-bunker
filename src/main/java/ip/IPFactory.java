package ip;

import static enumerations.ip.IPType.*;
import static enumerations.ip.IP_Fields.*;
import enumerations.ip.IPType;
import enumerations.ip.IP_Fields;
import enumerations.validation.ValidatorTypes;
import exceptions.InvalidIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static utility.ParameterValidation.validateParameters;

import utility.BinaryHelper;
import validation.*;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

public class IPFactory {

    private static final Logger log = LoggerFactory.getLogger(IPFactory.class);

    //region Standard Factorized IP Object creation

    /**
     * Creates an IPv4 Object and returns it
     * @param address IP address in decimal representation as string
     * @param prefix Subnet prefix
     * @return Created IPv4 object
     * @throws InvalidIPException if invalid address is passed in
     */
    public static I_IP createIPv4(final String address, final String prefix) throws InvalidIPException {
        validateParameters(address == null, log, "Null parameter passed into IPv4 factory method.");
        log.info("Creating IPv4 instance with parameters: address={}, prefix={}", address, prefix);
        final IL3Validator validator = ValidatorFactory.getInstance(ValidatorTypes.IPv4);
        return new IPv4(address, (Integer) validator.validate(address), ((IPv4Validator) validator).validatePrefix(prefix));
    }

    /**
     * Creates an IPv6 Object and returns it
     * @param address IP address in hexadecimal representation as string
     * @return Created IPv6 object
     * @throws InvalidIPException if invalid address is passed in
     */
    public static I_IP createIPv6(final String address) throws InvalidIPException {
        validateParameters(address == null, log, "Null parameter passed into IPv6 factory method.");
        log.info("Creating IPv6 instance with parameters: address={}", address);
        final IL3Validator validator = ValidatorFactory.getInstance(ValidatorTypes.IPv6);
        // The prefix is statically set to 64 here, because SLAAC routing requires the /64 prefix.
        // The functionality to specify bigger/smaller subnets in V6 Objects is still kept, to future-proof against changes in the standard
        return new IPv6(address, (BigInteger) validator.validate(address), 64);
    }
    //endregion

    //region Random IP generation

    /**
     * Generates random IP Addresses
     * @param amount Amount to be generated
     * @param type Type of address to be generated
     * @return Set of generated IPs
     * @throws InvalidIPException if invalid type is passed in
     */
    public static Set<I_IP> generateRandomIPAddresses(final int amount, final IPType type) throws InvalidIPException {
        validateParameters(type == null, log, "Null parameter passed into into random IPv4 generation method");
        validateParameters(amount < 1, log, "Can't generate less than 1 random IP address.");

        log.debug("Creating set of {} random IP addresses of type {}", amount, type);
        final Set<I_IP> generatedIPs = new HashSet<>(amount);
        int i = 0;
        while (i < amount) {
            i += generatedIPs.add(generateRandomIP(type)) ? 1 : 0;
        }
        log.info("Generated random IPS: {}", generatedIPs);
        return generatedIPs;
    }

    /**
     * Generates one random IP Object
     * @param type Type of object to be created
     * @return The generated IP
     */
    private static I_IP generateRandomIP(final IPType type) {
        validateParameters(type == null, log, "Null parameter passed into random IPv4 generation method.");
        log.info("Creating a random IP address of type {}", type);
        if(type != IPv4 && type != IPv6) throw new InvalidIPException("Invalid type for IP generation.");
        int i = 0;
        final StringBuilder address = new StringBuilder();
        while (i++ < type.clusterLength / 2) {
            long cluster = Math.round(Math.random() * ((1 << type.clusterLength) - 1));    // 1 << exponent == 2 ^ exponent
            if (type == IPv4) address.append(cluster).append(type.separator);
            else address.append(Long.toHexString(cluster).toUpperCase()).append(type.separator);
        }
        address.deleteCharAt(address.length() - 1);

        if (type == IPv4) return createIPv4(address.toString(), String.valueOf((int) Math.round(Math.random() * 32)));
        else return createIPv6(address.toString());
    }
    //endregion

    //region IP of a kind generation

    /**
     * Generates IPs in same subnet
     * @param amount Amount to be generated
     * @param type Type of IP that is to be generated
     * @return Set of generated IPs
     */
    public static Set<I_IP> generateIPsInSameSubnet(final int amount, final IPType type) {
        validateParameters(type == null, log, "Null parameter passed into generateIPsInSameSubnet()");
        validateParameters(amount < 2, log, "Can't generate less than 2 IP addresses in a subnet.");
        assert type != null;

        log.debug("Generating {} IPs of type {} in same subnet", amount, type);

        final int prefix = type == IPv4 ? type.clusterLength + (int) ((Math.random() * 16)): 64;
        final Set<I_IP> tupel = new HashSet<>(2);
        String netBits = generateRandomBinarySequence(prefix);
        if(!netBits.contains("1")) netBits = netBits.replaceFirst("0", "1");

        for(int i = 0; i < amount;) {
            final String hostBits = generateRandomBinarySequence(type.binaryLength - prefix);
                final I_IP ip = type == IPv4 ?
                        createIPv4(BinaryHelper.binaryToIP(netBits + hostBits, IPv4), String.valueOf(prefix)) :
                        createIPv6(BinaryHelper.binaryToIP(netBits + hostBits, IPv6));
                i += tupel.add(ip) ? 1 : 0;
        }
        log.info("Created IPS: {}", tupel);
        return tupel;
    }

    /**
     * Generates a random sequence of bits
     * @param length Amount of bits to be generated
     * @return Bits as string
     */
    private static String generateRandomBinarySequence(final int length) {
        validateParameters(length < 1, log, "Can't generate bit sequence with length < 1.");
        log.info("Creating random bit sequence.");

        String bits = "0".repeat((int) (Math.random() * length));
        bits += "1".repeat(length - bits.length());

        final List<Character> bitChars = new ArrayList<>();
        for(Character bit : bits.toCharArray()) bitChars.add(bit);
        Collections.shuffle(bitChars);

        final StringBuilder shuffledString = new StringBuilder();
        for (char c : bitChars) shuffledString.append(c);
        log.info("Generated random bit string: {}", shuffledString);
        return shuffledString.toString();
    }
    //endregion

    //region Invalid IP Generation
    // Cant change final fields of valid IP directly and checks can't be avoided during instantiation, so fields have to be returned separately for JSON creation
    /**
     * Generates a map of invalid or altered IP fields for a given IP address based on the specified fields.
     *
     * <p>This method takes an IP object and a list of IP fields to be altered (e.g., address, subnet, net ID).
     * It applies the appropriate transformation or alteration to each of the specified fields and returns a map
     * containing the field and its corresponding altered value..</p>
     *
     * @param ipToBeAltered the IP address object to alter, which can be either IPv4 or IPv6
     * @param fields a list of IP fields (e.g., address, prefix, net ID) to alter
     * @return a map containing the altered IP fields and their corresponding values
     * @throws RuntimeException if the provided IP object is null
     */
    public static Map<IP_Fields, String> generateInvalidIPField(final I_IP ipToBeAltered, final List<IP_Fields> fields) {
        validateParameters(ipToBeAltered == null || fields == null, log, "Null parameter passed into generateInvalidIPField().");
        assert ipToBeAltered != null;
        assert fields != null;
        log.debug("Generating invalid IP fields with parameters: ipToBeAltered={}, fields={}", ipToBeAltered.getAddress(), fields);

        // Since I_IP is passed in, we elect not to require the enum IPType as well, since the Object itself tells us what type it is
        final IPType type = ipToBeAltered.getClass() == ip.IPv4.class ? IPv4 : IPv6;

        final Map<IP_Fields, Function<I_IP, String>> fieldAlterFunctions = new HashMap<>(Map.of(
                ADDRESS_DECIMAL, ip -> alterAddress(ip, ip.getAddress()),
                ADDRESS_BINARY, IPFactory::alterBinary,
                PREFIX, IPFactory::alterPrefix,
                NET_ID, ip -> alterAddress(ip, ip.getSubnet().getNetIDDecimal()),
                BROADCAST_ID, ip -> alterAddress(ip, ip.getSubnet().getBroadcastDecimal())
        ));
        if(type == IPv4) fieldAlterFunctions.put(MASK, ip -> alterAddress(ip, BinaryHelper.binaryToIP("1".repeat(ip.getSubnet().getPrefix()).concat("0".repeat(32 - ip.getSubnet().getPrefix())), IPv4)));

        final Map<IP_Fields, String> alteredFields = new HashMap<>();
        fields.stream()
                .filter(fieldAlterFunctions::containsKey)
                .forEach(field -> alteredFields.put(field, fieldAlterFunctions.get(field).apply(ipToBeAltered)));
        log.info("Generated invalid IP fields: {}", alteredFields);
        return alteredFields;
    }

    /**
     * Alters the address of a given IP by modifying a random segment (octet for IPv4 or hextet for IPv6)
     * and returning the modified address.
     *
     * @param ipToBeAltered the IP object to be altered, which can be either IPv4 or IPv6
     * @param address the current address of the IP in string format
     * @return the altered IP address as a string
     */
    private static String alterAddress(final I_IP ipToBeAltered, final String address) {
        validateParameters(ipToBeAltered == null || address == null, log, "Null parameter passed into alterAddress().");
        assert ipToBeAltered != null;
        log.debug("Altering address {}", ipToBeAltered.getAddress());

        final Random random = new Random();
        final IPType type = ipToBeAltered.getClass() == ip.IPv4.class ? IPv4 : IPv6;
        final int clusterToAlter = type == IPv4 ? random.nextInt(4) : random.nextInt(8), newValue;
        final String[] cluster = type == IPv4 ?
                ((IPv4Validator) ValidatorFactory.getInstance(ValidatorTypes.IPv4)).toValidV4FormatOctets(address) :
                ((IPv6Validator) ValidatorFactory.getInstance(ValidatorTypes.IPv6)).toValidV6FormatHextets(address);
        if(type == IPv4 && Math.random() > 0.9) cluster[clusterToAlter] = "256";
        else {
            newValue = type == IPv4 ? (Integer.parseInt(cluster[clusterToAlter]) + 1) : Integer.parseInt(cluster[clusterToAlter], 16) + 2;
            cluster[clusterToAlter] = type == IPv4 ? String.valueOf(newValue) : Integer.toHexString(newValue);
        }
        return String.join(String.valueOf(type.separator), cluster);
    }

    /**
     * Alters the binary representation of the given IP address by randomly flipping some of its bits.
     *
     * <p>This method takes the binary string representation of the IP address and randomly modifies some bits.
     * The method ensures that at least one bit is altered, and if no changes are made, it recursively calls itself until a modification occurs.</p>
     *
     * @param ipToBeAltered the IP object to be altered, which can be either IPv4 or IPv6
     * @return the altered binary string of the IP address, with the correct separator inserted for the given IP type
     */
    private static String alterBinary(final I_IP ipToBeAltered) {
        validateParameters(ipToBeAltered == null, log, "Null parameter passed into alterBinary().");
        assert ipToBeAltered != null;
        log.debug("Altered binary: {} from IP {}", ipToBeAltered.getIPBinary(), ipToBeAltered.getAddress());

        final String ip0b = ipToBeAltered.getIPBinary();
        final StringBuilder altered = new StringBuilder();
        for(char c : ip0b.toCharArray()) {
            if(Math.random() > 0.8) {
                if(c == '0') c++;
                if(c == '1') c--;
            }
            altered.append(c);
        }
        if(altered.toString().equals(ip0b)) return alterBinary(ipToBeAltered);
        return BinaryHelper.binarySeparatorInsert(altered.toString(), ipToBeAltered.getClass() == ip.IPv4.class ? IPv4 : IPv6);
    }

    /**
     * Alters the subnet prefix of a given IP address by randomly modifying its value.
     *
     * @param ipToBeAltered the IP object to be altered, which can be either IPv4 or IPv6
     * @return the altered subnet prefix as a string
     */
    private static String alterPrefix(final I_IP ipToBeAltered) {
        validateParameters(ipToBeAltered == null, log, "Null parameter passed into alterPrefix().");
        assert ipToBeAltered != null;
        log.debug("Altering prefix: {} of IP {}", ipToBeAltered.getSubnet().getPrefix(), ipToBeAltered.getAddress());

        int prefix = ipToBeAltered.getSubnet().getPrefix();
        if(prefix == 0) return String.valueOf((int) (1 + Math.random() * 31));
        return String.valueOf(Math.random() > 0.5 ? --prefix : ++prefix);
    }
    //endregion
}