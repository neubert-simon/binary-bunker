package enumerations.questions;

public enum OSILayer implements I_DB_Filter {
    L1_PHYSICAL,
    L2_DATA_LINK,
    L3_NETWORK,
    L4_TRANSPORT,
    ;

    public final FilterTypes dbFilterType = FilterTypes.OSILayer;

    @Override
    public FilterTypes getFilterType() {
        return dbFilterType;
    }

}