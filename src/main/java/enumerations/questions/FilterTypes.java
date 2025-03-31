package enumerations.questions;


/**
 * Enums for filtering when requesting IQuestion object(s).
 */
public enum FilterTypes {
    //filter for difficulty | only on MCQuestions
    Difficulty(Difficulty.class, "difficulty", "::difficulty_categories"),
    //filter for osilayer | only on MCQuestions
    OSILayer(OSILayer.class, "osilayer", "::osi_layer"),
    //filter for QuestionTypeMC | only on MCQuestions
    QuestionTypeMC(QuestionTypeMC.class, "ANY(questiontype)", "::questiontypemc"),
    //filter for QuestionTypePrompt | only on PromptQuestions
    QuestionTypePrompt(QuestionTypePrompt.class, "questiontypePrompt", "::QuestionTypePrompt"),;


    public final Class<?> filterClass;
    public final String requestKey;
    public final String postgreDataType;

    /**
     * instantiate the enum with 3 attributes
     * @param filterClass the enum class for filtering
     * @param requestKey the key attribute of the database
     * @param postgreDataType the cast for values into Postgresql types(for postgres necessary)
     */
    FilterTypes(final Class<?> filterClass,final String requestKey,final String postgreDataType) {
        this.filterClass = filterClass;
        this.requestKey = requestKey;
        this.postgreDataType = postgreDataType;
    }

}