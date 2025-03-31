package questions;
import com.fasterxml.jackson.annotation.JsonProperty;
import enumerations.questions.IQuestionType;
import enumerations.questions.OSILayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ParameterValidation;

import java.util.List;
import java.util.Objects;

/**
 * Abstract representation of Question objects
 * Question objects represent questions the user has to answer in the frontend
 */
public abstract class Question implements IQuestion {

    private static final Logger log = LoggerFactory.getLogger(MCQuestion.class);

    @JsonProperty
    private final String questionID, question, explanation;
    @JsonProperty
    private final List<IQuestionType> questionTypes;
    @JsonProperty
    private final OSILayer osilayer;

    Question(final String questionID, final String question, final String explanation, final List<IQuestionType> questionTypes, final OSILayer layer) {
        ParameterValidation.validateParameters(question == null || questionID == null || explanation == null || questionTypes == null || layer == null, log, "Null parameter passed into Question constructor.");
        ParameterValidation.validateParameters(questionTypes.stream().anyMatch(Objects::isNull), log, "Null value in passed in List.");
        ParameterValidation.validateParameters(question.isBlank() || questionID.isBlank() || explanation.isBlank(), log, "Must enter a question ID, question and explanation.");
        this.questionID = questionID;
        this.question = question;
        this.explanation = explanation;
        this.questionTypes = questionTypes;
        this.osilayer = layer;
    }

    @Override
    public String getQuestionID() {
        return questionID;
    }

    @Override
    public String getQuestion() {
        return this.question;
    }

    @Override
    public String getExplanation() {
        return explanation;
    }

    @Override
    public OSILayer getOsilayer() {
        return osilayer;
    }

    @Override
    public List<IQuestionType> getQuestionTypes() {
        return questionTypes;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (!(other instanceof Question otherQuestion)) return false;
        return getQuestionID().equals(otherQuestion.getQuestionID())
                && getQuestion().equals(otherQuestion.getQuestion())
                && getExplanation().equals(otherQuestion.getExplanation())
                && getQuestionTypes().equals(otherQuestion.getQuestionTypes())
                && getOsilayer().equals(otherQuestion.getOsilayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionID, question, explanation, questionTypes, osilayer);
    }
}