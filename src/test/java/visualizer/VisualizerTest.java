package visualizer;


import exceptions.InvalidIPException;
import ip.IPFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static ip.IPFactory.createIPv6;
import static visualizer.VisualizerFactory.*;

import static ip.IPFactory.createIPv4;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes=VisualizerFactory.class)
public class VisualizerTest {

    @MockBean VisualizerV4 mockVisualizerV4;
    @MockBean VisualizerV6 mockVisualizerV6;

    @Test
    public void createVisualizerV4TestPositive() {
        setMockVisualizerV4("192.68.140.10", "24", "11000000.01000100.10001100", ".00001010", "11000000.01000100.10001100.00001010", "192.68.140.0");
        assertEquals(createVisualizerV4(createIPv4("192.68.140.10", "24")), mockVisualizerV4);
        setMockVisualizerV4("100.100.100.100","15","01100100.0110010","0.01100100.01100100", "01100100.01100100.01100100.01100100","100.100.0.0");
        assertEquals(createVisualizerV4(createIPv4("100.100.100.100", "15")), mockVisualizerV4);
        setMockVisualizerV4("10.0.0.1", "8", "00001010", ".00000000.00000000.00000001", "00001010.00000000.00000000.00000001", "10.0.0.0");
        assertEquals(createVisualizerV4(createIPv4("10.0.0.1", "8")), mockVisualizerV4);
        setMockVisualizerV4("172.16.5.12", "16", "10101100.00010000", ".00000101.00001100", "10101100.00010000.00000101.00001100", "172.16.0.0");
        assertEquals(createVisualizerV4(createIPv4("172.16.5.12", "16")), mockVisualizerV4);
        setMockVisualizerV4("203.0.113.1", "29", "11001011.00000000.01110001.00000", "001", "11001011.00000000.01110001.00000001", "203.0.113.0");
        assertEquals(createVisualizerV4(createIPv4("203.0.113.1", "29")), mockVisualizerV4);
        setMockVisualizerV4("169.254.0.1", "16", "10101001.11111110", ".00000000.00000001", "10101001.11111110.00000000.00000001", "169.254.0.0");
        assertEquals(createVisualizerV4(createIPv4("169.254.0.1", "16")), mockVisualizerV4);
        setMockVisualizerV4("8.8.8.8", "32", "00001000.00001000.00001000.00001000", "", "00001000.00001000.00001000.00001000", "8.8.8.8");
        assertEquals(createVisualizerV4(createIPv4("8.8.8.8", "32")), mockVisualizerV4);
        setMockVisualizerV4("128.12.10.1", "1", "1", "0000000.00001100.00001010.00000001", "10000000.00001100.00001010.00000001", "128.0.0.0");
        assertEquals(createVisualizerV4(createIPv4("128.12.10.1", "1")), mockVisualizerV4);
    }

    private void setMockVisualizerV4(String ipDecimal, String prefix, String netPartBinary, String hostPartBinary, String ipBinary, String netPartDecimal) {

        when(mockVisualizerV4.getIpDecimal()).thenReturn(ipDecimal);
        when(mockVisualizerV4.getPrefix()).thenReturn(prefix);
        when(mockVisualizerV4.getNetPartBinary()).thenReturn(netPartBinary);
        when(mockVisualizerV4.getHostPartBinary()).thenReturn(hostPartBinary);
        when(mockVisualizerV4.getIpBinary()).thenReturn(ipBinary);
        when(mockVisualizerV4.getNetPartDecimal()).thenReturn(netPartDecimal);
    }

    @Test
    public void createVisualizerV4TestNegative() {
        assertThrows(IllegalArgumentException.class, () -> createVisualizerV4(IPFactory.createIPv4(null, "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("", "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("A.a.A.A", "24")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4(".","0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("..", "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("....", "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("0000.0000.0000.0000", "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("1.1.1.1.1", "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("2.2.2.", "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("1.1.1", "0")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV4(IPFactory.createIPv4("1.1.1.256", "0")));

        assertThrows(IllegalArgumentException.class, () -> createVisualizerV4(IPFactory.createIPv4("192.68.0.1", null)));
        assertThrows(IllegalArgumentException.class, () -> createVisualizerV4(IPFactory.createIPv4("192.68.0.1", "")));
        assertThrows(IllegalArgumentException.class, () -> createVisualizerV4(IPFactory.createIPv4("192.68.0.1", "AAA")));
        assertThrows(IllegalArgumentException.class, () -> createVisualizerV4(IPFactory.createIPv4("192.68.0.1", "33")));
        assertThrows(IllegalArgumentException.class, () -> createVisualizerV4(IPFactory.createIPv4("192.68.0.1", "-1")));
        assertThrows(IllegalArgumentException.class, () -> createVisualizerV4(IPFactory.createIPv4("192.68.0.1", "99999")));
    }
    

    private void setMockVisualizerV6(String ipDecimal, String prefix, String netPartBinary, String hostPartBinary, String ipBinary, String netPartDecimal) {

        when(mockVisualizerV6.getIpDecimal()).thenReturn(ipDecimal);
        when(mockVisualizerV6.getPrefix()).thenReturn(prefix);
        when(mockVisualizerV6.getNetPartBinary()).thenReturn(netPartBinary);
        when(mockVisualizerV6.getHostPartBinary()).thenReturn(hostPartBinary);
        when(mockVisualizerV6.getIpBinary()).thenReturn(ipBinary);
        when(mockVisualizerV6.getNetPartDecimal()).thenReturn(netPartDecimal);
    }

    @Test
    public void createVisualizerV6TestPositive() {
        setMockVisualizerV6("2001:0000:130F:0000:0000:09C0:876A:130B", "64", "0010000000000001:0000000000000000:0001001100001111:0000000000000000", ":0000000000000000:0000100111000000:1000011101101010:0001001100001011", "0010000000000001:0000000000000000:0001001100001111:0000000000000000:0000000000000000:0000100111000000:1000011101101010:0001001100001011", "2001:00:130F:00:00:00:00:00" );
        assertEquals(createVisualizerV6(createIPv6("2001:0000:130F:0000:0000:09C0:876A:130B")), mockVisualizerV6);
        setMockVisualizerV6("FE80:0000:0000:0000:0202:B3FF:FE1E:8329", "64", "1111111010000000:0000000000000000:0000000000000000:0000000000000000", ":0000001000000010:1011001111111111:1111111000011110:1000001100101001", "1111111010000000:0000000000000000:0000000000000000:0000000000000000:0000001000000010:1011001111111111:1111111000011110:1000001100101001", "FE80:00:00:00:00:00:00:00");
        assertEquals(createVisualizerV6(createIPv6("FE80:0000:0000:0000:0202:B3FF:FE1E:8329")), mockVisualizerV6);
        setMockVisualizerV6("3001:0ABC:0000:0000:1234:5678:9ABC:DEF0", "64", "0011000000000001:0000101010111100:0000000000000000:0000000000000000", ":0001001000110100:0101011001111000:1001101010111100:1101111011110000", "0011000000000001:0000101010111100:0000000000000000:0000000000000000:0001001000110100:0101011001111000:1001101010111100:1101111011110000", "3001:ABC:00:00:00:00:00:00");
        assertEquals(createVisualizerV6(createIPv6("3001:0ABC:0000:0000:1234:5678:9ABC:DEF0")), mockVisualizerV6);
        setMockVisualizerV6("FD00:0000:0000:0000:ABCD:1234:5678:9ABC", "64", "1111110100000000:0000000000000000:0000000000000000:0000000000000000", ":1010101111001101:0001001000110100:0101011001111000:1001101010111100", "1111110100000000:0000000000000000:0000000000000000:0000000000000000:1010101111001101:0001001000110100:0101011001111000:1001101010111100", "FD00:00:00:00:00:00:00:00");
        assertEquals(createVisualizerV6(createIPv6("FD00:0000:0000:0000:ABCD:1234:5678:9ABC")), mockVisualizerV6);
        setMockVisualizerV6("FF00:0000:0000:0000:0000:0000:0000:0001", "64", "1111111100000000:0000000000000000:0000000000000000:0000000000000000", ":0000000000000000:0000000000000000:0000000000000000:0000000000000001", "1111111100000000:0000000000000000:0000000000000000:0000000000000000:0000000000000000:0000000000000000:0000000000000000:0000000000000001", "FF00:00:00:00:00:00:00:00");
        assertEquals(createVisualizerV6(createIPv6("FF00:0000:0000:0000:0000:0000:0000:0001")), mockVisualizerV6);
        setMockVisualizerV6("3001:0CDE:0000:0000:1234:5678:ABCD:EF00", "64", "0011000000000001:0000110011011110:0000000000000000:0000000000000000", ":0001001000110100:0101011001111000:1010101111001101:1110111100000000", "0011000000000001:0000110011011110:0000000000000000:0000000000000000:0001001000110100:0101011001111000:1010101111001101:1110111100000000", "3001:CDE:00:00:00:00:00:00");
        assertEquals(createVisualizerV6(createIPv6("3001:0CDE:0000:0000:1234:5678:ABCD:EF00")), mockVisualizerV6);
        setMockVisualizerV6("FC00:0000:ABCD:EF12:3456:7890:DEAD:BEEF", "64", "1111110000000000:0000000000000000:1010101111001101:1110111100010010", ":0011010001010110:0111100010010000:1101111010101101:1011111011101111", "1111110000000000:0000000000000000:1010101111001101:1110111100010010:0011010001010110:0111100010010000:1101111010101101:1011111011101111", "FC00:00:ABCD:EF12:00:00:00:00");
        assertEquals(createVisualizerV6(createIPv6("FC00:0000:ABCD:EF12:3456:7890:DEAD:BEEF")), mockVisualizerV6);
        setMockVisualizerV6("2001:0DB8:1234:5678:0000:0000:0000:ABCD", "64", "0010000000000001:0000110110111000:0001001000110100:0101011001111000", ":0000000000000000:0000000000000000:0000000000000000:1010101111001101", "0010000000000001:0000110110111000:0001001000110100:0101011001111000:0000000000000000:0000000000000000:0000000000000000:1010101111001101", "2001:DB8:1234:5678:00:00:00:00");
        assertEquals(createVisualizerV6(createIPv6("2001:0DB8:1234:5678:0000:0000:0000:ABCD")), mockVisualizerV6);
    }

    @Test
    public void createVisualizerV6TestNegative() {
        assertThrows(IllegalArgumentException.class, () -> createVisualizerV6(IPFactory.createIPv6(null)));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("A:a:A:A")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6(":")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("::::")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6(":::::::")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("2001:0000:130F:0000:0000:09C0:876A:130B:0001")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("2001:0000:130F:0000:0000:09C0:876A")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("2001:0000:130F::09C0::0001")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")));
        assertThrows(InvalidIPException.class, () -> createVisualizerV6(IPFactory.createIPv6("2001:0000:130F:0000:0000:09C0:876A:001G")));
    }

}
