package ip;

public interface ISubnet {

    /**
     * Calculates possible number of devices in the given subnets
     * @return Number of devices as long
     */
    long calculateNumberOfDevices();

    Number getNetID();
    String getNetIDBinary();
    String getNetIDDecimal();

    Number getBroadcast();
    String getBroadcastBinary();
    String getBroadcastDecimal();

    String getMaskDecimal();

}