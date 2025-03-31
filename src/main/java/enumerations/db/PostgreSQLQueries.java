package enumerations.db;


import enumerations.questions.FilterTypes;

/**
 * here are all the PostgreSQL Queries
 * for each enum there are two attributes, one for each Table in  Postgresql
 */
public enum PostgreSQLQueries {

    //region queries

    //get a random question out of the database
    GET_RANDOM_QUESTION("SELECT id, osilayer, difficulty,questiontype, data FROM questions ","SELECT id, osilayer, questionTypePrompt,data FROM promptQuestions "),

    //region CRUD MC and Prompt
    CREATE_QUESTION("INSERT INTO questions(id, osilayer, difficulty, questiontype, data) VALUES (?, ?"+ FilterTypes.OSILayer.postgreDataType +", ?"+ FilterTypes.Difficulty.postgreDataType +", ?"+ FilterTypes.QuestionTypeMC.postgreDataType +"[], ?::jsonb);",
            "INSERT INTO promptQuestions(id, osilayer, questiontypeprompt, data) VALUES (?, ?"+ FilterTypes.OSILayer.postgreDataType +", ?"+ FilterTypes.QuestionTypePrompt.postgreDataType +", ?::jsonb)"),
    READ_QUESTION("SELECT id, osilayer, difficulty, questiontype, data FROM questions WHERE id = ?",
            "SELECT id, osilayer, questiontypeprompt, data FROM promptQuestions WHERE id = ?"),
    UPDATE_QUESTION("UPDATE questions SET ~ WHERE id = ?",
            "UPDATE promptQuestions SET ~ WHERE id = ?"),
    DELETE_QUESTION("DELETE FROM questions WHERE id = ?",
            "DELETE FROM promptQuestions WHERE id = ?"),
    //endregion

    // other dummy queries
    INCREMENT_QUESTION_COUNTER("UPDATE counter SET countQuestions = countQuestions + ","UPDATE counter SET countQuestions = countQuestions + "),
    GET_COUNTER("SELECT * FROM counter","SELECT * FROM counter"),

    CREATE_THE_UNNECESSARYTABLE("CREATE TABLE unnecessarytable(id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, description TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, is_active BOOLEAN DEFAULT TRUE);","CREATE TABLE unnecessarytable(id SERIAL PRIMARY KEY, name VARCHAR(100) NOT NULL, description TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, is_active BOOLEAN DEFAULT TRUE);"),
    DELETE_THE_UNNECESSARYTABLE("DROP TABLE unnecessarytable","DELETE TABLE unnecessarytable");
    //endregion

    //region functionals
    private final String queryMCQuestion;
    private final String queryPromptQuestion;

    /**
     * initialize enums with 2 values one for each database
     * @param queryMCQuestion for the MCQuestion database
     * @param queryPromptQuestion for the PromptQuestionDatabase
     */
    PostgreSQLQueries(final String queryMCQuestion, final String queryPromptQuestion) {
        this.queryMCQuestion = queryMCQuestion;
        this.queryPromptQuestion = queryPromptQuestion;
    }

    /**
     * getter for MCQuestion queries
     * @return Postgresql query String
     */
    public String getQueryMCQuestion() {
        return this.queryMCQuestion;
    }

    /**
     * getter for PromptQuestion queries
     * @return Postgresql query String
     */
    public String getQueryPromptQuestion() {
        return this.queryPromptQuestion;
    }
    //endregion
}
