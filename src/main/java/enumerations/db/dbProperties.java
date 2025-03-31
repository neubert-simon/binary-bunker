package enumerations.db;

public enum dbProperties {

    DB_TIMEOUT(200);

    private final int value;
    dbProperties(final int value){
        this.value = value;
    }
    public int getTimeout() {
        return value;
    }
}
