package ip;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ParameterValidation;

import static enumerations.ip.IPType.*;
import static utility.BinaryHelper.*;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Subnet objects for IPv6 addresses
 */
class SubnetV6 extends Subnet {

    private final int LENGTH = 128;
    private final BigInteger netID;
    private final BigInteger broadcast;

    private static final Logger log = LoggerFactory.getLogger(SubnetV6.class);

    SubnetV6(final BigInteger ip, final int prefix) {
        super(prefix);
        ParameterValidation.validateParameters(ip == null, log, "Null parameter passed into SubnetV6 constructor.");
        ParameterValidation.validateParameters(prefix < 0, log, "Prefix can't be less than 0.");
        log.debug("Creating instance of SubnetV6 with parameters: ip={}, prefix={}", ip, prefix);
        if(prefix < 0 || prefix > LENGTH) {
            IllegalArgumentException ia = new IllegalArgumentException("Prefix must be between 0 and " + LENGTH + " (inclusive).");
            log.error(ia.getMessage());
            throw ia;
        }

        BigInteger mask = BigInteger.ONE.shiftLeft(LENGTH - prefix).subtract(BigInteger.ONE).not();
        this.netID = ip.and(mask);
        this.broadcast = netID.or(mask.not());

        log.info("Created SubnetV6 instance.");
    }

    //region Class methods
    @Override
    public long calculateNumberOfDevices() {
        log.info("Calculating number of devices of {}", this.getNetID());
        final BigInteger devices = BigInteger.ONE.shiftLeft(LENGTH - getPrefix()).subtract(BigInteger.TWO);
        return devices.max(BigInteger.ZERO).longValue();
    }
    //endregion

    //region Getter
    @Override
    public Number getNetID() {
        return this.netID;
    }

    @Override @JsonProperty("netID_binary")
    public String getNetIDBinary() {
        return binarySeparatorInsert(leftPadBinary(this.netID.toString(2), LENGTH), IPv6);
    }

    @Override @JsonProperty("netID")
    public String getNetIDDecimal() {
        return binaryToIP(getNetIDBinary(), IPv6);
    }

    @Override
    public Number getBroadcast() {
        return this.broadcast;
    }

    @Override @JsonProperty("broadcast_binary")
    public String getBroadcastBinary() {
        return binarySeparatorInsert(leftPadBinary(this.broadcast.toString(2), LENGTH), IPv6);
    }

    @Override @JsonProperty("broadcast")
    public String getBroadcastDecimal() {
        return binaryToIP(getBroadcastBinary(), IPv6);
    }

    @Override
    public String getMaskDecimal() {
        return binaryToIP("1".repeat(getPrefix()) + "0".repeat(128 - getPrefix()), IPv6);
    }
    //endregion

    //region java.lang.Object Overrides
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SubnetV6 subnetV6) || !super.equals(o)) return false;
        return netID.equals(subnetV6.getNetID()) && broadcast.equals(subnetV6.getBroadcast());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), LENGTH, netID, broadcast);
    }

    @Override
    public String toString() {
        return "SubnetV6:\n" + "netID: " + netID.toString(16).toUpperCase() + "\n" + "broadcast: " + broadcast.toString(16).toUpperCase();
    }
    //endregion
}