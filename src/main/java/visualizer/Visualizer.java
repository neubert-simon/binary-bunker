package visualizer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import enumerations.visualizer.AddressPart;
import enumerations.ip.IPType;
import ip.I_IP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Abstract representation of Visualizer
 */

public abstract class Visualizer implements IVisualizer {

    private static final Logger log = LoggerFactory.getLogger(Visualizer.class);

    private final String ipDecimal, hostPartBinary, netPartBinary, ipBinary, netPartDecimal, prefix;

    /**
     * The Visualizer Constructor
     * @param ipObject The IP-Address that needs to be visualized
     * @param ipType The IP-Address Version
     */
    Visualizer(I_IP ipObject, IPType ipType){
        this.ipDecimal = ipObject.getAddress();
        this.prefix = String.valueOf(ipObject.getSubnet().getPrefix());
        this.netPartBinary = netBitsCalc(ipObject, ipType);
        this.hostPartBinary = hostBitsCalc(ipObject, ipType);
        this.ipBinary = ipObject.getIPBinary();
        this.netPartDecimal = ipObject.getSubnet().getNetIDDecimal();
    }

    /**
     * Returns the Network Part of an IP-Address in binary format
     * @param ipObject The IP-Address from where the binary is taken
     * @param ipType Used for deciding which separator the address needs
     * @return The Network Part in binary format of the given IpObject
     */
    @JsonIgnore
    private String netBitsCalc(I_IP ipObject, IPType ipType) {
        //deletes all the separators from the IP-Address and returns the binary IP from index 0 to prefix
        log.info("Calculating Network Part Bits");
        String binary = ipObject.getIPBinary().replaceAll("[" + ipType.separator + "]", "").substring(0, ipObject.getSubnet().getPrefix());
        //inserts the separators again
        return separatorInsert(binary, ipType, AddressPart.NET);
    }

    /**
     * Returns the Host Part of an IP-Address in binary format
     * @param ipObject The IP-Address from where the binary is taken
     * @param ipType Used for deciding which separator the address needs
     * @return The Host Part in binary format of the given IpObject
     */
    @JsonIgnore
    private String hostBitsCalc(I_IP ipObject, IPType ipType) {
        //deletes all the separators from the IP-Address and returns the binary IP from index prefix to the length of the address
        log.info("Calculating Host Part Bits");
        String binary = ipObject.getIPBinary().replaceAll("[" + ipType.separator + "]", "").substring(ipObject.getSubnet().getPrefix(), ipObject.setAddressLength());
        //inserts the separators again
        return separatorInsert(binary, ipType, AddressPart.HOST);
    }

    /**
     * Inserts the specific separators for the IP-Addresses in binary, depending on if it's the Net or the Host part
     * @param binary The net or host part of the Address in binary, without separators
     * @param ipType The IP Version of the Address to decide which separators should be used
     * @param part Declare which part of the Address, Net or Host part
     * @return The IP-Addresses in binary with the inserted separators
     */
    @JsonIgnore
    private String separatorInsert(String binary, IPType ipType, AddressPart part) {
        int numberOfDots = (binary.length() - 1) / ipType.clusterLength;
        String result = "";

        StringBuilder binaryOutput = new StringBuilder(binary.length() + numberOfDots);

        //Iterates over the net part binary of an address and adds a separator every 8 or 16 chars depending on IP version
        if (part == AddressPart.NET) {
            log.info("Inserting separators into the Network Part of the Address");
            for (int i = 0; i < binary.length(); i += ipType.clusterLength) {
                int end = Math.min(i + ipType.clusterLength, binary.length());
                binaryOutput.append(binary, i, end);

                if (end < binary.length()) {
                    binaryOutput.append(ipType.separator);
                }
            }
            result = binaryOutput.toString();
        }

        //Iterates backwards over the host part binary of an address and adds a separator every 8 or 16 chars and one separator at the front of the Address
        if (part == AddressPart.HOST) {
            log.info("Inserting separators into the Host Part of the Address");
            for (int i = binary.length(); i > 0; i -= ipType.clusterLength) {
                int start = Math.max(i - ipType.clusterLength, 0);
                binaryOutput.insert(0, binary.substring(start, i));

                if (start > 0 || i % ipType.clusterLength == 0) {
                    binaryOutput.insert(0, ipType.separator);
                }
            }
            result = binaryOutput.toString();
        }

        return result;
    }

    @Override  @JsonProperty("net_part_binary")
    public String getNetPartBinary(){
        return netPartBinary;
    }

    @Override  @JsonProperty("host_part_binary")
    public String getHostPartBinary() {
        return hostPartBinary;
    }

    @Override  @JsonProperty("prefix")
    public String getPrefix() {
        return prefix;
    }

    @Override  @JsonProperty("ip_decimal")
    public String getIpDecimal() {
        return ipDecimal;
    }

    @Override  @JsonProperty("ip_binary")
    public String getIpBinary() {
        return ipBinary;
    }

    @Override  @JsonProperty("net_part_decimal")
    public String getNetPartDecimal() {
        return netPartDecimal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visualizer that = (Visualizer) o;
        return Objects.equals(ipDecimal, that.getIpDecimal()) &&
                Objects.equals(prefix, that.getPrefix()) &&
                Objects.equals(netPartBinary, that.getNetPartBinary()) &&
                Objects.equals(hostPartBinary, that.getHostPartBinary()) &&
                Objects.equals(ipBinary, that.getIpBinary()) &&
                Objects.equals(netPartDecimal, that.getNetPartDecimal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipDecimal, prefix, netPartBinary, hostPartBinary, ipBinary, netPartDecimal);
    }
}
