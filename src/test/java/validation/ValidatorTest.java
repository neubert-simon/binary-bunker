package validation;

import enumerations.validation.ValidatorTypes;
import exceptions.InvalidIPException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;


public class ValidatorTest {
    // region IPv4Validator
    @Test
    public void IPv4ValidatorPositive() {
        assertEquals(0, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("0.0.0.0"));
        assertEquals(-1, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("255.255.255.255"));
        assertEquals(2130706433, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("127.0.0.1"));
        assertEquals(-1062731775, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("192.168.0.1"));
        assertEquals(167772161, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("10.0.0.1"));
        assertEquals(-1408237567, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("172.16.0.1"));
        assertEquals(-1073741311, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("192.0.2.1"));
        assertEquals(-969710591, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("198.51.100.1"));
        assertEquals(-889163520, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("203.0.113.0"));
        assertEquals(134744072, ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("8.8.8.8"));
    }
    @Test
    public void IPv4ValidatorNegative() {
        assertThrows(IllegalArgumentException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate(null));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("256.256.256.256"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("-1.0.0.0"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("192.168.1.999"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("999.999.999.999"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("300.300.300.300"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("192.168.-1.1"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("10.0.0.-1"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("172.16.500.1"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("1.2.3.256"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv4).validate("192.168.0.999"));
    }
    // endregion

    // region IPv6Validator
    @Test
    public void IPv6ValidatorPositive() {
        assertEquals(new BigInteger("1"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::1"));
        assertEquals(new BigInteger("42540766452641154071740215577757643572"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
        assertEquals(new BigInteger("42540766411282592856904265327123268393"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:db8::ff00:42:8329"));
        assertEquals(new BigInteger("338288524927261089654018896841347694593"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("fe80::1"));
        assertEquals(new BigInteger("336294682933583715857042757217636883132"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("fd00::abcd:1234:5678:9abc"));
        assertEquals(new BigInteger("281473913979137"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::ffff:192.168.1.1"));
        assertEquals(new BigInteger("42540766452641154071740215577757643572"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:0db8:85a3::8a2e:0370:7334"));
        assertEquals(new BigInteger("42540766411282594074389245746715063092"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:0db8:0000:0042:0000:8a2e:0370:7334"));
        assertEquals(new BigInteger("42540766411282594074389245746715063092"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:db8:0000:0042:0000:8a2e:0370:7334"));
        assertEquals(new BigInteger("338288524927261089666398467808057399996"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("fe80::abcd:1234:5678:9abc"));
        assertEquals(new BigInteger("42540766411282592856903984954536209357"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:db8:0000:0000:0000:0000:abcd:abcd"));
        assertEquals(new BigInteger("0"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::0"));
        assertEquals(new BigInteger("1"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("0:0:0:0:0:0:0:1"));
        assertEquals(new BigInteger("42540766411282592856903984951653826560"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:0db8:0000:0000:0000:0000:0000:0000"));
        assertEquals(new BigInteger("42540766411282592856903984951653826562"), ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:db8:0:0:0:0:0:2"));
    }
    @Test
    public void IPv6ValidatorNegative() {
        assertThrows(IllegalArgumentException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate(null));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("mumbojumbo"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("1.2.3.4"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("Some Text"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::abcd:ghij"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:db8::12345"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:db8:85a3::8a2e:0370:7334:zzz"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::ffff::ffff"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("abcd:efgh:ijkl:mnop:qrst:uvwx:yzzz::1234"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001::abcd:abcd:abcd:abcd:abcd:abcd:abcd:abcd"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:0db8::ff00:0042:8329::5"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::1::1"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001::12345::6789"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::abcd::1234"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("2001:db8::8a2e:370::7334"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("fd00:abcd:efgh::1234:5678"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::1234:5678:abcd::1234"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::a:b:c:d:e:f:g:h"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("::ffff:999.999.999.999"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("0:0:0:0:0:0:0:0:0"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.IPv6).validate("abcd:efgh::abcd:abcd:abcd"));
    }
    // endregion

    // region SubnetValidator
    @Test
    public void SubnetValidatorPositive() {
        assertEquals(2040L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.0.0.0"));
        assertEquals(4294967292L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.252"));
        assertEquals(33554176L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.128.0"));
        assertEquals(33554304L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.192.0"));
        assertEquals(33554368L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.224.0"));
        assertEquals(33554400L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.240.0"));
        assertEquals(33554416L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.248.0"));
        assertEquals(33554424L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.252.0"));
        assertEquals(33554428L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.254.0"));
        assertEquals(33554430L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.0"));
        assertEquals(261632L, ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.128.0.0"));
    }
    @Test
    public void SubnetValidatorNegative() {
        assertThrows(IllegalArgumentException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate(null));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.256"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.300"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.-1"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.500.0"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.0.500"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("256.256.256.256"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.1.1"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.0.0"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("0.0.255.255"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("255.255.255.255.255"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("50.0.0.0"));
        assertThrows(InvalidIPException.class, () -> ValidatorFactory.getInstance(ValidatorTypes.Subnet).validate("155.0.0.0"));
    }
    // endregion
}
