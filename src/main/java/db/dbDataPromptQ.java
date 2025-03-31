package db;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The JAVA object for not search relevant attributes of PromptQuestions in the database.
 */
public class dbDataPromptQ {

    private final String question;
    private final String explanation;

    /**
     * initialises the dbDataPromptQ object with its attributes.
     * using JsonProperty and JsonCreator notation to deserialize the json object into a dbDataPromptQ object.
     * @param question the question String attribute from the json object.
     * @param explanation the explanation String attribute from the json object.
     */
    @JsonCreator
    public dbDataPromptQ(@JsonProperty("question")final String question, @JsonProperty("explanation")final String explanation)
    {
        this.explanation = explanation;
        this.question = question;
    }

    //region getter

    /**
     * get the Explanation of the object.
     * @return the String attribute explanation of the resolved object.
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * get the Question of the object.
     * @return the String attribute question of the resolved object.
     */
    public String getQuestion() {
        return question;
    }

    //endregion

}
