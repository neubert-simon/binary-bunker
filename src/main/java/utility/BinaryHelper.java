package utility;

import static enumerations.ip.IPType.*;
import enumerations.ip.IPType;
import exceptions.InvalidIPException;

public class BinaryHelper {

    /**
     * Left-pads a binary string with zeros to the specified length.
     * @param binary the binary string to be padded.
     * @param length the desired length after padding.
     * @return the left-padded binary string.
     * @throws IllegalArgumentException if the binary string is null, the length is less than the binary string's length, or the binary string contains non-binary characters.
     * @throws InvalidIPException if the binary string is not a valid binary string.
     */
    public static String leftPadBinary(final String binary, final int length) {
        if(binary == null) throw new IllegalArgumentException("Null parameter.");
        if(length < 0) throw new IllegalArgumentException("Length must be greater than zero.");
        if(binary.length() > length) throw new IllegalArgumentException("Length can't be lesser than length of String to be padded.");
        if(!binary.isEmpty() && !binary.matches("^[01]+$")) throw new InvalidIPException("String must be in binary.");
        return "0".repeat(length - binary.length()).concat(binary);
    }

    /**
     * Converts a binary string to an IP address in the specified format (either IPv4 or IPv6).
     * @param binary the binary string to be converted.
     * @param type the IP type (IPv4 or IPv6) specifying the format to be used.
     * @return the IP address in the desired format.
     * @throws IllegalArgumentException if the binary string is invalid.
     * @throws InvalidIPException if the binary string is of invalid length or format for the given IP type.
     */
    public static String binaryToIP(String binary, final IPType type) {
        binary = validateBinary(binary, type);
        final StringBuilder addressDecimal = new StringBuilder();
        for(int i = 0; i < type.binaryLength; i += type.clusterLength) {
            final int bitCluster = Integer.parseUnsignedInt(binary.substring(i, i + type.clusterLength), 2);
            final String appendage = type == IPv4 ? String.valueOf(bitCluster) : String.format("%02x", bitCluster);
            addressDecimal.append(appendage.toUpperCase()).append(type.separator);
        }
        return addressDecimal.substring(0 ,addressDecimal.length() - 1);
    }

    /**
     * Inserts separators into a binary string to format it according to the specified IP type (IPv4 or IPv6).
     * @param binary the binary string to be formatted.
     * @param type the IP type (IPv4 or IPv6) specifying how the separators should be inserted.
     * @return the binary string with separators inserted.
     * @throws IllegalArgumentException if the binary string is invalid.
     */
    public static String binarySeparatorInsert(String binary, final IPType type) {
        binary = validateBinary(binary, type);
        final StringBuilder sb = new StringBuilder(binary);
        for(int i = type.clusterLength; i < type.binaryLength; i += type.clusterLength + 1) {
            sb.insert(i, type.separator);
        }
        return sb.toString();
    }

    /**
     * Validates and formats the binary string to ensure it conforms to the specified IP type's format.
     * @param binary the binary string to be validated and formatted.
     * @param type the IP type (IPv4 or IPv6) specifying the required binary length and format.
     * @return the validated and formatted binary string.
     * @throws IllegalArgumentException if the binary string is invalid or null.
     * @throws InvalidIPException if the binary string's length is incorrect for the given IP type.
     */
    private static String validateBinary(String binary, final IPType type) {
        if(binary == null || type == null) throw new IllegalArgumentException("Null parameter.");
        binary = binary.replaceAll("[" + type.separator + "]", "");
        if(!binary.matches("^[01]+$")) throw new IllegalArgumentException("String must be in binary.");
        binary = leftPadBinary(binary, type.binaryLength);
        if(binary.length() != type.binaryLength) throw new InvalidIPException("Invalid binary format for " + type.name());
        return binary;
    }
}