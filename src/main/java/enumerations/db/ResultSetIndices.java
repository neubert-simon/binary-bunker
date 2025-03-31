package enumerations.db;

/**
 * the object indices of the ResultSet content.
 */
public enum ResultSetIndices {
QUESTION_ID(1),QUESTION_OSI_LAYER(2),QUESTION_DIFFICULTY(3),QUESTION_TYPE(4),QUESTION_DATA_OBJ(5);
    private final int index;

    ResultSetIndices(final int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

}
