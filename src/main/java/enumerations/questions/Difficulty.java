package enumerations.questions;

public enum Difficulty implements I_DB_Filter{
    EASY,
    MEDIUM,
    DIFFICULT,
    ;

    public final FilterTypes dbFilterType = FilterTypes.Difficulty;

    @Override
    public FilterTypes getFilterType() {
        return dbFilterType;
    }
}