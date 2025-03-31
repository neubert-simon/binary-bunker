package ip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import static enumerations.ip.IPType.*;
import static utility.BinaryHelper.*;
import enumerations.validation.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.BinaryHelper;
import utility.ParameterValidation;

import java.util.Arrays;
import java.util.Objects;

/**
 * Subnet objects for IPv4 addresses
 */
class SubnetV4 extends Subnet {

    private final byte LENGTH = 32;
    @JsonIgnore
    private final int netID;
    @JsonIgnore
    private final int broadcast;
    @JsonIgnore
    private final int mask;

    private static final Logger log = LoggerFactory.getLogger(SubnetV4.class);

    SubnetV4(final Integer ip, final int prefix) {
        super(prefix);
        ParameterValidation.validateParameters(ip == null, log, "Null parameter passed into SubnetV4 constructor.");
        ParameterValidation.validateParameters(prefix < 0, log, "Prefix can't be less than 0.");
        log.debug("Creating instance of SubnetV4 with parameters: ip={}, prefix={}", ip, prefix);

        if(prefix < 0 || prefix > LENGTH) throw new IllegalArgumentException("Prefix must be between 0 and 32 (inclusive).");
        this.mask = prefix == 0 ? 0 : -1 << (32 - prefix); // -1L = All Ones in Twos Complement, Bit-Shifted Left to create mask by number of host bits (32-Prefix)
        this.netID = ip & mask;
        this.broadcast = netID | ~mask;
        log.info("Created SubnetV4 instance.");
    }

    //region Class methods

    /**
     * Converts an address into a valid IPv4 format
     * @param address The address to be converted
     * @param base The base in which the IP should be represented
     * @return The formatted address
     */
    public String addressToV4(final int address, final Base base) {
        ParameterValidation.validateParameters(base == null, log, "Null parameter passed into addressToV4().");
        log.info("Converting {} to valid IPv4 format in base: {}.", address, base);

        if(base == Base.HEXADECIMAL) throw new IllegalArgumentException("Hexadecimal representation not applicable for IPv4.");

        String addressStr = Integer.toBinaryString((address));
        addressStr = BinaryHelper.leftPadBinary(addressStr, 32);

        final String octets[] = new String[4];
        for(int i = 0; i < octets.length;) {
            octets[i] = addressStr.substring(i * 8, ++i * 8);
        }
        log.debug("Created octets: {}", Arrays.toString(octets));
        if(base == Base.BINARY) return String.join(".", octets);

        for(int i = 0; i < octets.length; i++) {
            octets[i] = String.valueOf(Integer.parseUnsignedInt(octets[i], 2));
        }
        log.debug("Created binary octets: {}", Arrays.toString(octets));
        return String.join(".", octets);
    }

    @Override
    public long calculateNumberOfDevices() {
        log.info("Calculating number of devices of {}", this.getNetID());
        return (long) Math.pow(2, LENGTH - getPrefix()) - 2L;
    }
    //endregion

    //region Getter
    @Override
    public Number getNetID() {
        return this.netID;
    }

    @Override @JsonProperty("netID_binary")
    public String getNetIDBinary() {
        return binarySeparatorInsert(leftPadBinary(Integer.toBinaryString(this.netID), LENGTH), IPv4);
    }

    @Override @JsonProperty("netID")
    public String getNetIDDecimal() {
        return BinaryHelper.binaryToIP(getNetIDBinary(), IPv4);
    }

    @Override
    public Number getBroadcast() {
        return this.broadcast;
    }

    @Override @JsonProperty("broadcast_binary")
    public String getBroadcastBinary() {
        return binarySeparatorInsert(leftPadBinary(Integer.toBinaryString(this.broadcast), LENGTH), IPv4);
    }

    @Override @JsonProperty("broadcast")
    public String getBroadcastDecimal() {
        return BinaryHelper.binaryToIP(getBroadcastBinary(), IPv4);
    }

    public int getMask() {
        return mask;
    }

    @JsonProperty("mask_binary")
    public String getMaskBinary() {
        return binarySeparatorInsert(leftPadBinary(Integer.toBinaryString(this.mask), LENGTH), IPv4);
    }

    @JsonProperty("mask")
    public String getMaskDecimal() {
        return BinaryHelper.binaryToIP(getMaskBinary(), IPv4);
    }
    //endregion

    //region java.lang.Object Overrides
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SubnetV4 subnetV4) || !super.equals(o)) return false;
        return netID == (int) subnetV4.getNetID() && broadcast == (int) subnetV4.getBroadcast() && mask == subnetV4.getMask();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), LENGTH, netID, broadcast, mask);
    }

    @Override
    public String toString() {
        return "Subnet:\n" + "netID: " + addressToV4(netID, Base.DECIMAL) + ", broadcast: " + addressToV4(broadcast, Base.DECIMAL) + ", mask: " + addressToV4(mask, Base.DECIMAL);
    }
    //endregion
}