package utility;

import static enumerations.ip.IPType.*;

import exceptions.InvalidIPException;
import org.junit.jupiter.api.Test;
import static utility.BinaryHelper.*;
import static org.junit.jupiter.api.Assertions.*;

public class BinaryHelperTest {

    @Test
    public void leftPadBinaryTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> leftPadBinary(null, 0));
        assertThrows(IllegalArgumentException.class, () -> leftPadBinary("0100", 3));
        assertThrows(IllegalArgumentException.class, () -> leftPadBinary("0000", -1));
        assertThrows(InvalidIPException.class, () -> leftPadBinary("012", 10));
        assertThrows(InvalidIPException.class, () -> leftPadBinary("MUSTAAAARD", 100));
        assertThrows(OutOfMemoryError.class, () -> leftPadBinary("", Integer.MAX_VALUE));
    }

    @Test
    public void leftPadBinaryTestPositive() {
        assertEquals("0".repeat(10), leftPadBinary("", 10));
        assertEquals("0".repeat(10), leftPadBinary("0", 10));
        assertEquals("1".repeat(10), leftPadBinary("1".repeat(10), 10));
        assertEquals("0" + "1".repeat(9), leftPadBinary("1".repeat(9), 10));
        assertEquals("000001011001001", leftPadBinary("01011001001", 15));
        assertEquals("0001101010", leftPadBinary("01101010", 10));
        assertEquals("0".repeat(90) + "0001101010", leftPadBinary("01101010", 100));
        assertEquals( "00001111", leftPadBinary("1111", 8));
        assertEquals( "0".repeat(24) + "00001111", leftPadBinary("00001111", 32));
        assertEquals( "0".repeat(96) + "10100110110011011100010101111101", leftPadBinary("10100110110011011100010101111101", 128));
    }

    @Test
    public void binaryToIPTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> binaryToIP(null, null));
        assertThrows(IllegalArgumentException.class, () -> binaryToIP(null, IPv4));
        assertThrows(IllegalArgumentException.class, () -> binaryToIP("null", null));
        assertThrows(IllegalArgumentException.class, () -> binaryToIP("012", IPv4));
        assertThrows(IllegalArgumentException.class, () -> binaryToIP("0_1", IPv6));
        assertThrows(IllegalArgumentException.class,() -> binaryToIP("1".repeat(33), IPv4));
        assertThrows(IllegalArgumentException.class,() -> binaryToIP("1".repeat(129), IPv6));
    }

    @Test
    public void binaryToIPTestPositive() {
        // IPv4
        assertEquals("0.0.0.0", binaryToIP("0", IPv4));
        assertEquals("0.0.0.0", binaryToIP("0".repeat(32), IPv4));
        assertEquals("127.0.0.1", binaryToIP("01111111000000000000000000000001", IPv4));
        assertEquals("142.52.3.245", binaryToIP("10001110.00110100.00000011.11110101", IPv4));
        assertEquals("192.168.0.1", binaryToIP("11000000.10101000.00000000.00000001", IPv4));
        assertEquals("255.255.255.255", binaryToIP("1".repeat(32), IPv4));
        // IPv6
        assertEquals("00:00:00:00:00:00:00:00", binaryToIP("0", IPv6));
        assertEquals("00:".repeat(7) + "00", binaryToIP("0".repeat(128), IPv6));
        assertEquals("2001:DB8:AAAA:BBBB:CCCC:DDDD:EEEE:FFFF", binaryToIP("00100000000000010000110110111000101010101010101010111011101110111100110011001100110111011101110111101110111011101111111111111111", IPv6));
        assertEquals("2001:DB8:00:00:00:00:00:01", binaryToIP("00100000000000010000110110111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001", IPv6));
        assertEquals("FFFF:".repeat(7) + "FFFF", binaryToIP("1".repeat(128), IPv6));
    }

    @Test
    public void binarySeparatorInsertTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> binarySeparatorInsert(null, null));
        assertThrows(IllegalArgumentException.class, () -> binarySeparatorInsert(null, IPv4));
        assertThrows(IllegalArgumentException.class, () -> binarySeparatorInsert("0101010", null));
        assertThrows(IllegalArgumentException.class, () -> binarySeparatorInsert("0101_010", IPv4));
        assertThrows(IllegalArgumentException.class, () -> binarySeparatorInsert("0101210", IPv6));
    }

    @Test
    public void binarySeparatorInsertTestPositive() {
        // IPv4
        assertEquals("00000000.".repeat(3) + "0".repeat(8), binarySeparatorInsert("0".repeat(32), IPv4));
        assertEquals("00000000.".repeat(3) + "1".repeat(8), binarySeparatorInsert("0".repeat(24) + "1".repeat(8), IPv4));
        assertEquals("10100110.11001101.11000101.01111101", binarySeparatorInsert("10100110110011011100010101111101", IPv4));
        assertEquals("11000101.11011101.11010010.10111010", binarySeparatorInsert("11000101110111011101001010111010", IPv4));
        assertEquals("11111111.".repeat(3) + "1".repeat(8), binarySeparatorInsert("1".repeat(32), IPv4));
        // IPv6
        assertEquals(("0".repeat(16) + ":").repeat(7) + "0".repeat(16), binarySeparatorInsert("0".repeat(128), IPv6));
        assertEquals("0010000000000001:0000110110111000:0000000000000000:0000000000000000:0000000000000000:0000000000000000:0000000000000000:0000000000000000", binarySeparatorInsert("00100000000000010000110110111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", IPv6));
        assertEquals("0010000000000001:0000110110111000:1111000000101010:0101001010101110:1110101011010101:0010111110110000:1101110110010101:1111000101000010", binarySeparatorInsert("00100000000000010000110110111000111100000010101001010010101011101110101011010101001011111011000011011101100101011111000101000010", IPv6));
        assertEquals(("1".repeat(16) + ":").repeat(7) + "1".repeat(16), binarySeparatorInsert("1".repeat(128), IPv6));

    }

}