package utility;

import enumerations.ip.AddressType;
import enumerations.ip.IPType;
import static ip.IPFactory.*;
import enumerations.questions.IQuestionType;
import enumerations.questions.QuestionTypePrompt;
import enumerations.validation.ValidatorTypes;
import exceptions.IncorrectAnswerException;
import exceptions.InvalidIPException;
import ip.I_IP;
import validation.IPv4Validator;
import validation.ValidatorFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains methods for question generation and validation.
 * Methods take in parameters that have to be formatted in a specific way.
 * These parameters, for validation especially, stem from the already automatically generated question parameters.
 * The intended usage is for them to only be utilized in conjunction.
 */
public class PromptQuestionUtility {

    //region IPv4

    //region Are two IPs in the same subnet? / Can two IPs talk to each other?

    /**
     * Randomly generates IPs that are to be assessed by the user to find out if they are in the same subnet.
     * @return Two IPs that may or may not be in the same subnet
     */
    public static Set<String> sameSubnet() {
        final Set<I_IP> ips;
        if (Math.random() >= 0.1) {
            ips = generateIPsInSameSubnet(2, IPType.IPv4);
            if (Math.random() > 0.3) changeSubnet(ips);
        } else {
            ips = generateRandomIPAddresses(2, IPType.IPv4);
        }
        return ips.stream().map(ip -> ip.getAddress() + " / " + ip.getSubnet().getPrefix()).collect(Collectors.toSet());
    }

    /**
     * Checks if user correctly identified if passed in IPs are in the same subnet.
     * @param questionParameters IP addresses previously generated
     * @param userAnswer User answer to the question
     * @return true if userAnswer is correct, false otherwise
     */
    public static boolean isSameSubnet(final Set<String> questionParameters, final String userAnswer) {
        final Set<I_IP> ips = new HashSet<>();
        if (questionParameters.size() != 2) {
            throw new IllegalArgumentException("Question parameters must be exactly two IP addresses.");
        }
        for (String parameter : questionParameters) {
            ips.add(getIPFromAddressAndPrefix(parameter));
        }
        final boolean inSameSubnet = ips.stream().map(ip -> ip.getSubnet().getNetID()).distinct().count() == 1;
        return userAnswer.equalsIgnoreCase(String.valueOf(inSameSubnet));
    }

    /**
     * Alters the subnet of two IPs.
     * @param originalIPs Original IPs in the same subnet
     */
    private static void changeSubnet(final Set<I_IP> originalIPs) {
        final I_IP sampleIP = originalIPs.iterator().next();
        final int prefix = sampleIP.getSubnet().getPrefix();
        final int relevantOctet = getRelevantOctet(prefix);
        final String[] octets = ((IPv4Validator) ValidatorFactory.getInstance(ValidatorTypes.IPv4)).toValidV4FormatOctets(sampleIP.getAddress());

        int newValue = Integer.parseInt(((IPv4Validator) ValidatorFactory.getInstance(ValidatorTypes.IPv4)).toValidV4FormatOctets(sampleIP.getSubnet().getBroadcastDecimal())[relevantOctet]) + 1;
        if (newValue < 0 || newValue > 255) newValue = 0;
        octets[relevantOctet] = String.valueOf(newValue);
        if (originalIPs.add(createIPv4(String.join(".", octets), String.valueOf(prefix)))) {
            originalIPs.remove(sampleIP);
        }
    }
    //endregion

    //region Which prefix for two IPs to be in the same subnet?

    /**
     * Extracts prefixes from two randomly generated IP addresses.
     * @return Prefixes of two random IPs
     */
    public static Set<String> findPrefixParameters() {
        final Set<I_IP> ips = generateIPsInSameSubnet(2, IPType.IPv4);
        return ips.stream().map(I_IP::getAddress).collect(Collectors.toSet());
    }
    //endregion

    //region Find IP for given subnet ID
    /**
     * Generates a set containing an IP address and its subnet prefix while ensuring the subnet has a valid number of devices.
     * <p>This method generates random IPv4 addresses meeting the following criteria:</p>
     * <ul>
     *   <li>The number of devices in the subnet is at least 3.</li>
     *   <li>The subnet prefix is neither 0 nor 32.</li>
     * </ul>
     *
     * <p>If the criteria are not met, the method recursively calls itself to generate a new IP address.
     * If the number of recursive calls exceeds 50, an {@code InvalidIPException} is thrown.</p>
     *
     * @param recursiveCalls the current count of recursive calls used to track the recursion depth
     * @return a set containing the IP address as a string and its subnet prefix as a string
     * @throws InvalidIPException if the method exceeds 50 recursive calls during the generation process
     */
    public static Set<String> findIP(int recursiveCalls) {
        if (recursiveCalls > 50) {
            throw new InvalidIPException("Error during random generation");
        }
        final I_IP sample = generateRandomIPAddresses(1, IPType.IPv4).iterator().next();
        final int prefix = sample.getSubnet().getPrefix();
        if (sample.getSubnet().calculateNumberOfDevices() < 3 || prefix == 0 || prefix == 32) {
            return findIP(++recursiveCalls);
        }
        return Set.of(sample.getAddress(), String.valueOf(sample.getSubnet().getPrefix()));
    }


    /**
     * Validates if a user found the correct IP asked for in the questions parameters.
     *
     * <p>This method ensures the user's answer meets the following criteria:</p>
     * <ul>
     *   <li>The answer contains exactly one IP address.</li>
     *   <li>The provided IP address differs from the prompt IP address.</li>
     *   <li>The provided IP address belongs to the same subnet as the prompt IP address.</li>
     * </ul>
     *
     * <p>If the user's answer contains more than one IP address, an {@code IncorrectAnswerException} is thrown.</p>
     *
     * @param questionParameters a set containing the question's IP address and subnet prefix
     * @param userAnswers a set containing the user's proposed IP address
     * @return {@code true} if the user's answer is valid and satisfies the conditions; {@code false} otherwise
     * @throws IncorrectAnswerException if the user's answer does not contain exactly one IP address
     */
    public static boolean validateFoundIP(final Set<String> questionParameters, final Set<String> userAnswers) {
        if (userAnswers.size() != 1) {
            throw new IncorrectAnswerException("Answer must be a singular IP address.");
        }
        final String prefix = getPrefixFromIPTupel(questionParameters);
        final I_IP prompt = createIPv4(getAddressFromIPTupel(questionParameters), prefix);
        final I_IP control = createIPv4(userAnswers.iterator().next(), prefix);
        return !prompt.equals(control) && control.getSubnet().getNetID().equals(prompt.getSubnet().getNetID());
    }
    //endregion

    //endregion

    //region Calculate relevant octet
    /**
     * Generates relevant octets of an IPv4 address based on the provided question parameters which specify what evaluation requires the octet to be assessed.
     *
     * <p>This method extracts the IPv4 address and subnet prefix from the question parameters, determines the relevant
     * octet based on the subnet prefix, and returns the corresponding octet in a valid IPv4 format.</p>
     *
     * @param questionParameters a set containing the IP address and subnet prefix for the question
     * @return a singleton set containing the relevant octet of the IPv4 address as a string
     */
    public static Set<String> generateAnswerForOctet(final Set<String> questionParameters) {
        final I_IP sample = createIPv4(getAddressFromIPTupel(questionParameters), getPrefixFromIPTupel(questionParameters));
        final int relevantOctet = getRelevantOctet(sample.getSubnet().getPrefix());
        return Collections.singleton(((IPv4Validator) ValidatorFactory.getInstance(ValidatorTypes.IPv4)).toValidV4FormatOctets(sample.getAddress())[relevantOctet]);
    }

    /**
     * Generates relevant octets of an IPv4 address in binary representation based on the provided question parameters which specify what evaluation requires the octet to be assessed.
     *
     * <p>This method extracts the IPv4 address and subnet prefix from the question parameters, determines the relevant
     * octet based on the subnet prefix, and returns the corresponding octet in a valid IPv4 format.</p>
     *
     * @param questionParameters a set containing the IP address and subnet prefix for the question
     * @return a singleton set containing the relevant octet of the IPv4 address as a string in binary representation
     */
    public static Set<String> generateAnswerForBinaryOctet(final Set<String> questionParameters) {
        final String decimalOctet = generateAnswerForOctet(questionParameters).iterator().next();
        return Collections.singleton(Integer.toBinaryString(Integer.parseInt(decimalOctet)));
    }
    //endregion

    //region Calculate number of devices
    /**
     * Generates a random IPv4 address and returns it to serve the "number of devices" question type.
     *
     * <p>This method creates a random IPv4 address and retrieves its subnet prefix. The result is formatted
     * as a single string in the format "IP Address / Prefix" and returned as a singleton set.</p>
     *
     * @return a singleton set containing the formatted string representation of the IPv4 address and its subnet prefix
     */
    public static Set<String> numberOfDevices() {
        final I_IP sample = generateRandomIPAddresses(1, IPType.IPv4).iterator().next();
        return Set.of(sample.getAddress() + " / " + sample.getSubnet().getPrefix());
    }
    //endregion

    //region IPv6

    //region MAC-IID Translation
    /**
     * Converts a randomly generated MAC address to an IPv6 Interface Identifier (IID) or returns the MAC address.
     *
     * @param askForMac a boolean flag indicating whether to return the MAC address ({@code true}) or the IID ({@code false})
     * @return a singleton set containing either the MAC address or the IID, depending on the input flag
     */
    public static Set<String> turnMacToIID(final boolean askForMac) {
        final String mac = generateMAC(), iID = macToIID(mac);
        return askForMac ? Set.of(mac) : Set.of(iID);
    }
    //endregion

    //endregion

    //region Auxiliary functions
    /**
     * Generates a random IPv4 address and its associated subnet prefix.
     *
     * @return a set of strings containing the IPv4 address and the subnet prefix
     */
    public static Set<String> generateIPWithPrefix() {
        final I_IP sample = generateRandomIPAddresses(1, IPType.IPv4).iterator().next();
        return Set.of(sample.getAddress(), String.valueOf(sample.getSubnet().getPrefix()));
    }

    /**
     * Creates an {@code I_IP} object from a string containing an IPv4 address and subnet prefix.
     *
     * <p>This method parses a string formatted as "IP Address / Prefix" to extract the IPv4 address
     * and the subnet prefix.</p>
     *
     * @param addressPlusPrefix a string containing the IPv4 address and subnet prefix, formatted as "IP Address / Prefix"
     * @return an {@code I_IP} object representing the parsed IPv4 address and subnet prefix
     */
    private static I_IP getIPFromAddressAndPrefix(final String addressPlusPrefix) {
        return createIPv4(addressPlusPrefix.substring(0, addressPlusPrefix.indexOf(" ")), addressPlusPrefix.substring(addressPlusPrefix.indexOf("/ ") + 2));
    }

    /**
     * <p>Determines the relevant octet of an IPv4 address that has to be assessed by the user.
     * This method calculates which octet (0-indexed) of an IPv4 address is relevant for
     * the given subnet prefix.</p>
     *
     * @param prefix the subnet prefix (CIDR notation)
     * @return the index of the relevant octet
     */
    private static int getRelevantOctet(final int prefix) {
        return (prefix - 1) / 8;
    }

    /**
     * Generates a random MAC address in hexadecimal format.
     *
     * <p>This method creates a random MAC address by generating a 48-bit binary string, where a random number
     * of bits are set to 1.</p>
     *
     * @return a randomly generated MAC address in uppercase hexadecimal format
     */
    private static String generateMAC() {
        final int ones = new Random().nextInt(48);
        StringBuilder macBinary = new StringBuilder("1".repeat(ones) + "0".repeat(48 - ones));
        final List<Character> characters = new ArrayList<>();
        macBinary.chars().forEach(c -> characters.add((char) c));
        Collections.shuffle(characters);
        macBinary = new StringBuilder();
        characters.forEach(macBinary::append);

        final StringBuilder mac = new StringBuilder();
        for (int i = 0; i < macBinary.length(); i += 8) {
            StringBuilder octet = new StringBuilder(Integer.toHexString(Integer.parseInt(macBinary.substring(i, i + 8), 2)));
            if (octet.length() == 1) octet.insert(0, "0");
            mac.append(octet).append(":");
        }
        return mac.substring(0, mac.length() - 1).toUpperCase();
    }

    /**
     * Converts a MAC address to an IPv6 Interface Identifier (IID).
     *
     * <p>This method follows the EUI-64 format to generate an IID from a given MAC address. The conversion includes:</p>
     * <ul>
     *   <li>Inserting the sequence ":FF:FE" in the middle of the MAC address.</li>
     *   <li>Flipping the 7th bit (Universal/Local bit) of the first octet of the MAC address.</li>
     *   <li>Ensuring the result is in the correct format and padding with a leading zero if necessary.</li>
     * </ul>
     *
     * @param mac the MAC address in the format "XX:XX:XX:XX:XX:XX"
     * @return the generated IPv6 Interface Identifier (IID) in uppercase
     */
    private static String macToIID(final String mac) {
        final StringBuilder iID = new StringBuilder(mac);
        iID.insert(mac.length() / 2, ":FF:FE");
        final int firstOctet = Integer.parseInt(mac.substring(0, 2), 16) ^ 0x2;
        iID.replace(0, 2, Integer.toHexString(firstOctet));
        if (iID.length() != 23) iID.insert(0, "0");
        return iID.toString().toUpperCase();
    }

    /**
     * Converts an IPv6 Interface Identifier (IID) to a MAC address.
     *
     * <p>This method follows the reverse of the EUI-64 format to extract a MAC address from a given IID. The conversion includes:</p>
     * <ul>
     *   <li>Removing the sequence ":FF:FE" from the IID.</li>
     *   <li>Flipping the 7th bit (Universal/Local bit) of the first octet of the IID.</li>
     *   <li>Ensuring the result is in the correct format and padding with a leading zero if necessary.</li>
     * </ul>
     *
     * @param iid the IPv6 Interface Identifier (IID) in the format "XX:XX:XX:XX:XX:XX"
     * @return the extracted MAC address in uppercase
     */
    private static String iidToMac(String iid) {
        iid = iid.replace(":FF:FE", "");
        final int firstOctet = Integer.parseInt(iid.substring(0, 2), 16) ^ 0x2;
        final String firstOctetHex = Integer.toHexString(firstOctet);
        iid = iid.replace(iid.substring(0, 2), firstOctetHex);
        if (iid.length() != 17) iid = "0" + iid;
        return iid.toUpperCase();
    }

    /**
     * Extracts the IP address from a set of question parameters.
     *
     * <p>This method assumes that the set contains multiple strings, and it returns the longest string,
     * which is assumed to be the IP address. Since this method is intended to be used with the automatically generated parameters
     * stemming from other methods in this class, the comparison is guaranteed to work in this case.</p>
     *
     * @param questionParameters a set of strings containing the question parameters, of which one is the IP address
     * @return the string representing the IP address, which is the longest string in the set
     */
    private static String getAddressFromIPTupel(final Set<String> questionParameters) {
        return Collections.max(questionParameters, Comparator.comparingInt(String::length));
    }

    /**
     * Extracts the subnet prefix from a set of question parameters.
     *
     * <p>This method assumes that the set contains multiple strings, and it returns the shortest string,
     * which is assumed to be the subnet prefix. Since this method is intended to be used with the automatically generated parameters
     *      * stemming from other methods in this class, the comparison is guaranteed to work in this case.</p>
     *
     * @param questionParameters a set of strings containing the question parameters, one of which is the subnet prefix
     * @return the string representing the subnet prefix, which is the shortest string in the set
     */
    private static String getPrefixFromIPTupel(final Set<String> questionParameters) {
        return Collections.min(questionParameters, Comparator.comparingInt(String::length));
    }

    /**
     * Calculates the common prefix length between two IPv4 addresses.
     *
     * <p>This method takes a set of IP addresses, converts them to their binary representations, and compares
     * the corresponding bits of the addresses. The method returns the length of the common prefix (in bits)
     * where the addresses match.</p>
     *
     * @param ipParameters a set containing two IP addresses to compare
     * @return the length of the common prefix between the two IP addresses (in bits)
     */
    private static int calculateCommonPrefixLength(final Set<String> ipParameters) {
        final List<String> ipBinaryList = new ArrayList<>();
        for (String parameter : ipParameters) {
            ipBinaryList.add(createIPv4(parameter, "0").getIPBinary().replace(".", ""));
        }
        int prefix = 0;
        while (prefix < 32) {
            if (ipBinaryList.get(0).charAt(prefix) != ipBinaryList.get(1).charAt(prefix)) break;
            prefix++;
        }
        return prefix;
    }
    //endregion

    //region Answer Generation
    /**
     * Generates the answer for the common prefix length between two IP addresses.
     *
     * <p>This method calculates the common prefix length (in bits) between two IP addresses by comparing their
     * binary representations. If the provided parameters are invalid (i.e., the set does not contain exactly two elements),
     * an {@code IllegalArgumentException} is thrown.</p>
     *
     * @param prefixParams a set containing two IP addresses to compare
     * @return a singleton set containing the common prefix length as a string
     * @throws IllegalArgumentException if the set does not contain exactly two IP addresses
     */
    public static Set<String> generateAnswerForPrefix(final Set<String> prefixParams) {
        if (prefixParams.size() != 2) {
            throw new IllegalArgumentException("Invalid question parameters.");
        }
        return Set.of(String.valueOf(calculateCommonPrefixLength(prefixParams)));
    }

    /**
     * Generates the network ID in decimal format for a given IP address and subnet prefix.
     *
     * <p>This method extracts the IP address and subnet prefix from the provided parameters, creates an IPv4
     * object using those values, and then returns the network ID of the subnet in decimal format.</p>
     *s
     * @param ipParams a set containing the IP address and subnet prefix
     * @return a singleton set containing the network ID of the subnet in decimal format as a string
     */
    public static Set<String> generateAnswerForIP(final Set<String> ipParams) {
        final String ip = getAddressFromIPTupel(ipParams);
        final String prefix = getPrefixFromIPTupel(ipParams);
        final I_IP sample = createIPv4(ip, prefix);
        return Set.of(sample.getSubnet().getNetIDDecimal());
    }

    /**
     * Generates answers for different types of subnet-related information (NetID, Broadcast, Mask) for a given IP address and subnet prefix.
     *
     * <p>This method takes a set of parameters containing the IP address and subnet prefix, and a list of {@code AddressType} values.
     * For each {@code AddressType}, it computes the corresponding subnet information (e.g., network ID, broadcast address, subnet mask)
     * in decimal format and returns a set containing the results.</p>
     *
     * <p>If an invalid {@code AddressType} is specified, an {@code IllegalArgumentException} is thrown.</p>
     *
     * @param subnetParams a set containing the IP address and subnet prefix
     * @param addressTypes a list of {@code AddressType} values specifying the types of subnet information to retrieve
     * @return a set containing the requested subnet-related information in decimal format
     * @throws IllegalArgumentException if an invalid {@code AddressType} is specified
     */
    public static Set<String> generateAnswerForSubnet(final Set<String> subnetParams, final List<AddressType> addressTypes) {
        final String ip = getAddressFromIPTupel(subnetParams);
        final String prefix = getPrefixFromIPTupel(subnetParams);
        final I_IP sample = createIPv4(ip, prefix);
        final Set<String> answers = new HashSet<>();
        for (AddressType type : addressTypes) {
            switch (type) {
                case NetID -> answers.add(sample.getSubnet().getNetIDDecimal());
                case Broadcast -> answers.add(sample.getSubnet().getBroadcastDecimal());
                case Mask -> answers.add(sample.getSubnet().getMaskDecimal());
                default -> throw new IllegalArgumentException("Invalid AddressType specified.");
            }
        }
        return answers;
    }

    /**
     * Generates the answer for MAC to IID or IID to MAC translation based on the provided question type.
     *
     * <p>This method checks the question type, and depending on whether the question is about converting a MAC address to
     * an IPv6 Interface Identifier (IID) or vice versa, it performs the appropriate conversion. It returns the result as
     * a singleton set containing the translated value.</p>
     *
     * @param questionParameters a set containing the MAC address or IID for translation
     * @param type the type of the question, either {@code MAC_TO_IID} or {@code IID_TO_MAC}
     * @return a singleton set containing the translated result (either MAC to IID or IID to MAC)
     */
    public static Set<String> generateAnswerForMac_IID_Translation(final Set<String> questionParameters, final IQuestionType type) {
        if (type == QuestionTypePrompt.MAC_TO_IID) {
            return Collections.singleton(macToIID(questionParameters.iterator().next()));
        } else {
            return Collections.singleton(iidToMac(questionParameters.iterator().next()));
        }
    }

    /**
     * Calculates the number of devices that can be assigned in a subnet for a given IP address and prefix.
     *
     * <p>This method takes a set containing an IP address with its subnet prefix, calculates the number of
     * devices that can be assigned in the corresponding subnet, and returns the result as a singleton set in string format.</p>
     *
     * @param deviceParams a set containing a single string representing the IP address and subnet prefix (in CIDR notation)
     * @return a singleton set containing the number of devices that can be assigned in the subnet
     * @throws IllegalArgumentException if the set does not contain exactly one parameter
     */
    public static Set<String> generateAnswerForDevices(final Set<String> deviceParams) {
        if (deviceParams.size() != 1) {
            throw new IllegalArgumentException("Question parameters must be exactly one IP address.");
        }
        final String ipWithPrefix = deviceParams.iterator().next();
        final I_IP sample = getIPFromAddressAndPrefix(ipWithPrefix);
        return Set.of(String.valueOf(sample.getSubnet().calculateNumberOfDevices()));
    }

    //endregion
}