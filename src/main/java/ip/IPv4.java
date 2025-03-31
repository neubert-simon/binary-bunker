package ip;

import com.fasterxml.jackson.annotation.JsonProperty;
import enumerations.ip.V4Class;
import exceptions.InvalidIPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utility.BinaryHelper.*;
import static enumerations.ip.IPType.*;
import java.util.Objects;

/**
 * IPv4 Object, using 32 bit addresses
 */
class IPv4 extends IP {

    private static final Logger log = LoggerFactory.getLogger(IPv4.class);

    private final V4Class v4Class;

    /**
     * Creates IPv4 Object and its subnet
     * @param address Textual representation of IP address in decimals
     * @param ip 2s complement representation of IP binary
     * @param prefix Subnet prefix
     */
    IPv4(final String address, final int ip, final int prefix) {
        super(address, ip, prefix);
        v4Class = getV4Class(this);
        log.info("Created IPv4 object with fields: address={}, ip={}, prefix={}", address, ip, prefix);
    }

    //region Instance methods
    @Override
    public boolean isInSameSubnet(final I_IP ip) {
        log.info("Checking if IPs are in same subnet: {} / {} and {} / {}", this.getAddress(), this.getSubnet().getPrefix(), ip.getAddress(), ip.getSubnet().getPrefix());
        if(!(ip instanceof IPv4)) {
            log.error("Wrong IP type comparison attempted.");
            throw new IllegalArgumentException("Wrong IP type, not comparable.");
        }
        return this.getSubnet().getNetID().equals(ip.getSubnet().getNetID());
    }

    //endregion

    //region Object creation
    @Override
    public Subnet setSubnet(final int prefix) {
        return new SubnetV4((Integer) getIP(), prefix);
    }

    @Override
    public boolean setPrivate() {
        return isPrivate();
    }

    @Override
    public int setAddressLength() {
        return 32;
    }
    //endregion

    //region Getter
    @Override @JsonProperty("ip_binary")
    public String getIPBinary() {
        return binarySeparatorInsert(leftPadBinary(Integer.toBinaryString((Integer) getIP()), getBinaryAddressLength()), IPv4);
    }

    @Override
    public boolean isPrivate() {
        String binaryIP = getIPBinary();
        return binaryIP.startsWith("00001010") || binaryIP.startsWith("101011000001") || binaryIP.startsWith("1100000010101000");
    }

    @JsonProperty("Class")
    public V4Class getV4Class() {
        return v4Class;
    }

    public static V4Class getV4Class(final I_IP ip) {
        String binary = leftPadBinary(Integer.toBinaryString((Integer) ip.getIP()), 32);
        for(V4Class v4Class : V4Class.values()) {
            if(binary.startsWith(v4Class.netPattern)) return v4Class;
        }
        throw new InvalidIPException("IP doesn't match any standard class.");
    }
    //endregion

    //region java.lang.Object Overrides
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof IPv4 iPv4)) return false;
        return super.equals(o) && v4Class == iPv4.getV4Class();
    }

    @Override
    public int hashCode() {
        return Objects.hash(v4Class);
    }

    @Override
    public String toString() {
        return "IPv4: " + getAddress() + ", Class: " + v4Class + ", isPrivate: " + isPrivate() + "\n" + getSubnet();
    }
    //endregion
}