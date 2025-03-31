package ip;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static enumerations.ip.IPType.*;
import static utility.BinaryHelper.*;
import java.math.BigInteger;

/**
 * IPv6 Object, using 128 bit addresses
 */
class IPv6 extends IP {

    private static final Logger log = LoggerFactory.getLogger(IPv6.class);

    /**
     * Creates IPv6 Object and its subnet
     * @param address Textual representation of IP address in decimals
     * @param ip 2s complement representation of IP binary
     * @param prefix Subnet prefix
     */
    IPv6(final String address, final BigInteger ip, final int prefix) {
        super(address, ip, prefix);
        if (ip.bitLength() > 128) {
            log.error("Invalid IP address passed in.");
            throw new IllegalArgumentException("IPv6 address cannot exceed 128 bits.");
        }
        log.info("Created IPv6 object with fields: address={}, ip={}, prefix={}", address, ip, prefix);
    }

    //region Instance methods

    //endregion

    //region Object creation
    @Override
    public Subnet setSubnet(final int prefix) {
        return new SubnetV6((BigInteger) getIP(), prefix);
    }

    @Override
    public boolean setPrivate() {
        return isPrivate();
    }

    @Override
    public int setAddressLength() {
        return 128;
    }

    @Override
    public boolean isInSameSubnet(final I_IP ip) {
        // Constant value of True because of SLAAC routing
        return true;
    }
    //endregion

    //region Getter
    @Override @JsonProperty("ip_binary")
    public String getIPBinary() {
        return binarySeparatorInsert(leftPadBinary(((BigInteger) getIP()).toString(2), 128), IPv6);
    }

    @Override
    public boolean isPrivate() {
        String ip = getIPBinary();
        return ip.startsWith("1111110") || ip.startsWith("1111111010"); // Private range and link local address
    }
    //endregion

    //region java.lang.Object Overrides
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof IPv6)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "IPv6: " + getAddress().toUpperCase() + ", isPrivate: " + isPrivate() + "\n" + getSubnet();
    }
    //endregion
}