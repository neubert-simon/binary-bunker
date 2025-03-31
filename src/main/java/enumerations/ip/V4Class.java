package enumerations.ip;

public enum V4Class {
    A("0"),
    B("10"),
    C("110"),
    D("1110"),
    E("1111")
    ;

    public final String netPattern;

    V4Class(final String netPattern) {
        this.netPattern = netPattern;
    }
}
