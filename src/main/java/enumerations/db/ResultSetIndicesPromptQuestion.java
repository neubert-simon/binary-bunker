package enumerations.db;

public enum ResultSetIndicesPromptQuestion{
    QUESTION_ID(1),OSILAYLER(2),QUESTION_TYPE_PROMPT(3),QUESTION_DATA_OBJ(4);
    private final int index;

    ResultSetIndicesPromptQuestion(final int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }
}
