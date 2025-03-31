package enumerations.ip;

public enum IPType {

    IPv4('.', 32, 8),
    IPv6(':', 128, 16),

    ;

    public final char separator;
    public final int binaryLength, clusterLength;

    IPType(final char separator, final int binaryLength, final int clusterLength) {
        this.separator = separator;
        this.binaryLength = binaryLength;
        this.clusterLength = clusterLength;
    }
}
