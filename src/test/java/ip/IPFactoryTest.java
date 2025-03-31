package ip;

import enumerations.ip.V4Class;
import exceptions.InvalidIPException;
import static ip.IPFactory.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes=IPFactory.class)
public class IPFactoryTest {

    @MockBean IPv4 mockIPv4;
    @MockBean IPv6 mockIPv6;
    @MockBean SubnetV4 mockSubnetV4;
    @MockBean SubnetV6 mockSubnetV6;

    //region IPv4
    @Test
    public void createIPv4TestNegative() {
        //IPs
        assertThrows(IllegalArgumentException.class, () -> createIPv4(null, "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4(".", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("...", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("0", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("0000.0000.0000.0000", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("0".repeat(32), "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("0.0.0.0.0", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("0.0.0.0.", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("0192.168.0.0", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("255.255.255.256", "0"));
        assertThrows(InvalidIPException.class, () -> createIPv4("MUSTAAAAAAAAARD", "0"));
        //Prefixes
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", null));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", ""));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", "Test"));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", "33"));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", "9999"));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", "-1"));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", "-9999"));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", String.valueOf(Integer.MAX_VALUE)));
        assertThrows(IllegalArgumentException.class, () -> createIPv4("1.255.255.0", String.valueOf(Integer.MIN_VALUE)));

    }

    @Test
    public void createIPv4TestPositive() {
        setMockIPv4("0.0.0.0", 0, 0, false , 0, -1, 0, V4Class.A);
        assertEquals(createIPv4("0.0.0.0", "0"), mockIPv4);
        setMockIPv4("0.0.0.0", 0, 16, false, 0, 65535, -65536, V4Class.A);
        assertEquals(createIPv4("0.0.0.0", "16"), mockIPv4);
        setMockIPv4("0.0.0.0", 0, 32, false, 0, 0, -1, V4Class.A);
        assertEquals(createIPv4("0.0.0.0", "32"), mockIPv4);
        setMockIPv4("1.0.0.0", 16777216, 0, false, 0, -1, 0, V4Class.A);
        assertEquals(createIPv4("1.0.0.0", "0"), mockIPv4);
        setMockIPv4("8.8.8.8", 134744072, 0, false, 0, -1, 0, V4Class.A);
        assertEquals(createIPv4("8.8.8.8", "0"), mockIPv4);
        setMockIPv4("10.0.0.1", 167772161, 8, true, 167772160, 184549375, -16777216, V4Class.A);
        assertEquals(createIPv4("10.0.0.1", "8"), mockIPv4);
        setMockIPv4("127.0.0.1", 2130706433, 0, false, 0, -1, 0, V4Class.A);
        assertEquals(createIPv4("127.0.0.1", "0"), mockIPv4);
        setMockIPv4("172.16.56.5", -1408223227, 16, false, -1408237568, -1408172033, -65536, V4Class.B);
        assertEquals(createIPv4("172.16.56.5", "16"), mockIPv4);
        setMockIPv4("192.168.1.1", -1062731519, 13, false, -1062731776, -1062207489, -524288, V4Class.C);
        assertEquals(createIPv4("192.168.1.1", "13"), mockIPv4);
        setMockIPv4("198.51.100.14", -969710578, 25, false, -969710592, -969710465, -128, V4Class.C);
        assertEquals(createIPv4("198.51.100.14", "25"), mockIPv4);
        setMockIPv4("255.255.255.255", -1, 0, false, 0, -1, 0, V4Class.E);
        assertEquals(createIPv4("255.255.255.255", "0"), mockIPv4);
    }

    private void setMockIPv4(String address, int ip, int prefix, boolean isPrivate, int netID, int broadcast, int mask, V4Class v4Class) {
        when(mockIPv4.getAddress()).thenReturn(address);
        when(mockIPv4.getIP()).thenReturn(ip);
        setMockSubnetV4(prefix, netID, broadcast, mask);
        when(mockIPv4.getSubnet()).thenReturn(mockSubnetV4);
        when(mockIPv4.isPrivate()).thenReturn(isPrivate);
        when(mockIPv4.getV4Class()).thenReturn(v4Class);
    }
    //endregion

    //region IPv6
    @Test 
    public void createIPv6TestNegative() {
        assertThrows(IllegalArgumentException.class, () -> createIPv6(null));
        assertThrows(InvalidIPException.class, () -> createIPv6(""));
        assertThrows(InvalidIPException.class, () -> createIPv6(":"));
        assertThrows(InvalidIPException.class, () -> createIPv6(":::"));
        assertThrows(InvalidIPException.class, () -> createIPv6("2001:db8:::7334:8a2e:370:7334"));
        assertThrows(InvalidIPException.class, () -> createIPv6("2001: db8: 85a3: 7334: 8a2e: 370: 7335"));
        assertThrows(InvalidIPException.class, () -> createIPv6("0"));
        assertThrows(InvalidIPException.class, () -> createIPv6("0000:".repeat(8)));
        assertThrows(InvalidIPException.class, () -> createIPv6("0".repeat(128)));
        assertThrows(InvalidIPException.class, () -> createIPv6("00000::FFFF"));
        assertThrows(InvalidIPException.class, () -> createIPv6("::FFFF:"));
        assertThrows(InvalidIPException.class, () -> createIPv6(":FFFF::"));
        assertThrows(InvalidIPException.class, () -> createIPv6("2001::85a3::7334"));
        assertThrows(InvalidIPException.class, () -> createIPv6("2001:db8:85a3:z334:8a2e:370:7334"));
        assertThrows(InvalidIPException.class, () -> createIPv6("2001-db8-85a3-7334-8a2e-370-7334"));
        assertThrows(InvalidIPException.class, () -> createIPv6(""));
        assertThrows(InvalidIPException.class, () -> createIPv6("2001:0db8:085a3:0007334:0008a2e:0370:07334"));
        assertThrows(InvalidIPException.class, () -> createIPv6("12345:67890:abcd:efgh::"));
        assertThrows(InvalidIPException.class, () -> createIPv6("MUSTAAAAAAAAARD"));
    }

    @Test
    public void createIPv6TestPositive() {
        setMockIPv6("::", new BigInteger("0"), 64, false, new BigInteger("0"), new BigInteger("18446744073709551615"));
        assertEquals(createIPv6("::"), mockIPv6);
        setMockIPv6(  "::1", new BigInteger("1"), 64, false, new BigInteger("0"), new BigInteger("18446744073709551615"));
        assertEquals(createIPv6("::1"), mockIPv6);
        setMockIPv6("0::0", new BigInteger("0"), 64, false, new BigInteger("0"), new BigInteger("18446744073709551615"));
        assertEquals(createIPv6("0::0"), mockIPv6);
        setMockIPv6("::FFFF:C000:280", new BigInteger("281473902969472"), 64, false, new BigInteger("0"), new BigInteger("18446744073709551615"));
        assertEquals(createIPv6("::ffff:c000:280"), mockIPv6);
        setMockIPv6("FE80::1FF:FE23:4567:890A", new BigInteger("338288524927261089654163009981888563466"), 64, true, new BigInteger("338288524927261089654018896841347694592"), new BigInteger("338288524927261089672465640915057246207"));
        assertEquals(createIPv6("fe80::1ff:fe23:4567:890a"), mockIPv6);
        setMockIPv6("FD00::1", new BigInteger("336294682933583715844663186250927177729"), 64, true, new BigInteger("336294682933583715844663186250927177728"), new BigInteger("336294682933583715863109930324636729343"));
        assertEquals(createIPv6("fd00::1"), mockIPv6);
        setMockIPv6("2001:DB8::FF00:42:8329", new BigInteger("42540766411282592856904265327123268393"), 64, false, new BigInteger("42540766411282592856903984951653826560"), new BigInteger("42540766411282592875350729025363378175"));
        assertEquals(createIPv6("2001:db8::ff00:42:8329"), mockIPv6);
        setMockIPv6("2001:0DB8:0000:0000:0000:0000:0000:0001", new BigInteger("42540766411282592856903984951653826561"), 64, false, new BigInteger("42540766411282592856903984951653826560"), new BigInteger("42540766411282592875350729025363378175"));
        assertEquals(createIPv6("2001:0db8:0000:0000:0000:0000:0000:0001"), mockIPv6);
        setMockIPv6("C52D:0DB8:85A3:0000:0000:8A2E:0370:7334", new BigInteger("262091846818927805516523722362500182836"), 64, false, new BigInteger("262091846818927805516523570432269352960"), new BigInteger("262091846818927805534970314505978904575"));
        assertEquals(createIPv6("c52d:0db8:85a3:0000:0000:8a2e:0370:7334"), mockIPv6);
        setMockIPv6("2001:DB8:AAAA:BBBB:CCCC:DDDD:EEEE:FFFF", new BigInteger("42540766464101448462697986026160193535"), 64, false, new BigInteger("42540766464101448447940572001907769344"), new BigInteger("42540766464101448466387316075617320959"));
        assertEquals(createIPv6("2001:db8:aaaa:bbbb:cccc:dddd:eeee:ffff"), mockIPv6);
        setMockIPv6("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF", new BigInteger("340282366920938463463374607431768211455"), 64, false, new BigInteger("340282366920938463444927863358058659840"), new BigInteger("340282366920938463463374607431768211455"));
        assertEquals(createIPv6("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF"), mockIPv6);
    }

    private void setMockIPv6(String address, BigInteger ip, int prefix, boolean isPrivate, BigInteger netID, BigInteger broadcast) {
        when(mockIPv6.getAddress()).thenReturn(address);
        when(mockIPv6.getIP()).thenReturn(ip);
        setMockSubnetV6(prefix, netID, broadcast);
        when(mockIPv6.getSubnet()).thenReturn(mockSubnetV6);
        when(mockIPv6.isPrivate()).thenReturn(isPrivate);
    }
    //endregion

    //region SubnetV4
    @Test 
    public void createSubnetV4TestNegative() {
        assertThrows(IllegalArgumentException.class, () -> new SubnetV4(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV4(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV4(0, 33));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV4(0, 999));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV4(0, Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV4(0, Integer.MAX_VALUE));
    }

    @Test
    public void createSubnetV4TestPositive() {
        setMockSubnetV4(0, 0, -1, 0);
        assertEquals(new SubnetV4(0, 0), mockSubnetV4);
        assertEquals(new SubnetV4(1, 0), mockSubnetV4);
        setMockSubnetV4(20, -1062719488, -1062715393, -4096);
        assertEquals(new SubnetV4(-1062717694, 20), mockSubnetV4);
        setMockSubnetV4(16, 2071658496, 2071724031, -65536);
        assertEquals(new SubnetV4(2071661569, 16), mockSubnetV4);
        setMockSubnetV4(32, Integer.MAX_VALUE, Integer.MAX_VALUE, -1);
        assertEquals(new SubnetV4(Integer.MAX_VALUE, 32), mockSubnetV4);
        setMockSubnetV4(32, Integer.MIN_VALUE, Integer.MIN_VALUE, -1);
        assertEquals(new SubnetV4(Integer.MIN_VALUE, 32), mockSubnetV4);
        setMockSubnetV4(8, 134217728, 150994943, -16777216);
        assertEquals(new SubnetV4(134744072, 8), mockSubnetV4);
        setMockSubnetV4(32, -1, -1, -1);
        assertEquals(new SubnetV4(-1, 32), mockSubnetV4);
        setMockSubnetV4(1, 0, 2147483647, -2147483648);
        assertEquals(new SubnetV4(0, 1), mockSubnetV4);
        setMockSubnetV4(8, 0, 16777215, -16777216);
        assertEquals(new SubnetV4(0, 8), mockSubnetV4);
        setMockSubnetV4(24, 0, 255, -256);
        assertEquals(new SubnetV4(0, 24), mockSubnetV4);
        setMockSubnetV4(32, 0, 0, -1);
        assertEquals(new SubnetV4(0, 32), mockSubnetV4);
    }

    private void setMockSubnetV4(int prefix, int netID, int broadcast, int mask) {
        when(mockSubnetV4.getPrefix()).thenReturn(prefix);
        when(mockSubnetV4.getNetID()).thenReturn(netID);
        when(mockSubnetV4.getBroadcast()).thenReturn(broadcast);
        when(mockSubnetV4.getMask()).thenReturn(mask);
    }
    //endregion

    //region SubnetV6
    @Test 
    public void createSubnetV6TestNegative() {
        assertThrows(IllegalArgumentException.class, () -> new SubnetV6(null, 0));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV6(new BigInteger("0"), -1));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV6(new BigInteger("0"), 129));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV6(new BigInteger("0"), 999));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV6(new BigInteger("0"), Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> new SubnetV6(new BigInteger("0"), Integer.MAX_VALUE));
    }

    @Test
    public void createSubnetV6TestPositive() {
        setMockSubnetV6(64, new BigInteger("0"),  new BigInteger("18446744073709551615"));
        assertEquals(new SubnetV6(new BigInteger("0"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("0"),  new BigInteger("18446744073709551615"));
        assertEquals(new SubnetV6(new BigInteger("1"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("5192296858534827628530496329220096"),  new BigInteger("5192296858534846075274570038771711"));
        assertEquals(new SubnetV6(new BigInteger("5192296858534827628530496329220096"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("340277174624079928635746076935438991360"),  new BigInteger("340277174624079928654192821009148542975"));
        assertEquals(new SubnetV6(new BigInteger("340277174624079928635746076935438991360"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("340277174624079928635746076935438991360"),  new BigInteger("340277174624079928654192821009148542975"));
        assertEquals(new SubnetV6(new BigInteger("340277174624079928635746076935439056895"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("42540766452641154071740063647526813696"),  new BigInteger("42540766452641154090186807721236365311"));
        assertEquals(new SubnetV6(new BigInteger("42540766452641154071740215577757643572"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("338288524927261089654018896841347694592"),  new BigInteger("338288524927261089672465640915057246207"));
        assertEquals(new SubnetV6(new BigInteger("338288524927261089654163009981888563466"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("50676839602477657231176083305297608704"),  new BigInteger("50676839602477657249622827379007160319"));
        assertEquals(new SubnetV6(new BigInteger("50676839602477657231176083305297608757"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("55843974225732624856620283282333892608"),  new BigInteger("55843974225732624875067027356043444223"));
        assertEquals(new SubnetV6(new BigInteger("55843974225732624874692859202604377566"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("226851469952583776263558461156604510208"),  new BigInteger("226851469952583776282005205230314061823"));
        assertEquals(new SubnetV6(new BigInteger("226851469952583776263558461156604510208"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("441345232975460348425092187983708160"),  new BigInteger("441345232975460366871836261693259775"));
        assertEquals(new SubnetV6(new BigInteger("441345232975460348425092187983708245"), 64), mockSubnetV6);
        setMockSubnetV6(64, new BigInteger("340282366920938463444927863358058659840"),  new BigInteger("340282366920938463463374607431768211455"));
        assertEquals(new SubnetV6(new BigInteger("340282366920938463463374607431768211455"), 64), mockSubnetV6);
    }

    private void setMockSubnetV6(int prefix, BigInteger netID, BigInteger broadcast) {
        when(mockSubnetV6.getPrefix()).thenReturn(prefix);
        when(mockSubnetV6.getNetID()).thenReturn(netID);
        when(mockSubnetV6.getBroadcast()).thenReturn(broadcast);
    }
    //endregion

}
