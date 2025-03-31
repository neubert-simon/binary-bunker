package ip;

public interface I_IP {

    Number getIP();
    String getIPBinary();

    Subnet setSubnet(final int prefix);
    Subnet getSubnet();

    boolean setPrivate();
    boolean isPrivate();

    String getAddress();
    int setAddressLength();

    /**
     * Checks if 2 IPs are in the same subnet
     * @param ip The other IP that is to be checked against
     * @return true if they are in the same subnet, false otherwise
     */
    boolean isInSameSubnet(final I_IP ip);


}