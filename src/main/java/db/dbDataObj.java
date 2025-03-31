package db;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;


/**
 * The JSON object for not search relevant attributes in the database.
 */
public class dbDataObj {

    private final Map<String, String> allAnswers;
    private final String question;
    private final Set<String> correctAnswers;
    private final String explanation;

    @JsonCreator
    public dbDataObj(@JsonProperty("question") String question,@JsonProperty("correctAnswers") Set<String> correctAnswers,@JsonProperty("explanation") String explanation, @JsonProperty("allAnswers") Map<String, String> allAnswers)
    {
        this.explanation = explanation;
        this.question = question;
        this.allAnswers = allAnswers;
        this.correctAnswers = correctAnswers;
    }

    //region getter
    public String getExplanation() {
        return explanation;
    }
    public Map<String, String> getAllAnswers() {
        return allAnswers;
    }
    public String getQuestion() {
        return question;
    }

    public Set<String> getCorrectAnswers() {
        return correctAnswers;
    }
    //endregion

}
