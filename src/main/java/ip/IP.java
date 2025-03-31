package ip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static utility.ParameterValidation.validateParameters;

/**
 * Abstract representation of an IP Object
 */
public abstract class IP implements I_IP {

    private static final Logger log = LoggerFactory.getLogger(IP.class);

    private final String address;
    @JsonIgnore
    private final Number ip;
    private final Subnet subnet;
    private final boolean isPrivate;
    @JsonIgnore
    private final int binaryAddressLength;

    /**
     * Construct an IP object
     * @param address IP address in decimal with octet separators as String
     * @param ip IP address as 2s complement
     * @param prefix Subnet prefix
     */
    IP(final String address, final Number ip, final int prefix) {
        validateParameters(address == null || ip == null, log, "Passed in Null Parameter into IP constructor.");
        this.address = address.toUpperCase();
        this.ip = ip;
        this.binaryAddressLength = setAddressLength();
        this.subnet = setSubnet(prefix);
        this.isPrivate = setPrivate();
    }

    //region Getter
    @Override
    public Number getIP() {
        return ip;
    }

    @Override  @JsonProperty("ip")
    public String getAddress() {
        return address.toUpperCase();
    }

    @Override
    public Subnet getSubnet() {return subnet;}

    public int getBinaryAddressLength() {
        return this.binaryAddressLength;
    }
    //endregion

    //region java.lang.Object Overrides
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof IP oIP)) return false;
        return address.equals(oIP.getAddress()) && ip.equals(oIP.getIP()) && subnet.equals(oIP.getSubnet()) && this.isPrivate == oIP.isPrivate();
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, ip, subnet);
    }
    //endregion
}