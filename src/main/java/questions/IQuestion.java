package questions;

import enumerations.questions.IQuestionType;
import enumerations.questions.OSILayer;
import java.util.List;
import java.util.Set;

public interface IQuestion {

    /**
     * Validates the passed in answers
     * @param answers User answers to be validated
     * @return true if the answers are all correct, otherwise false
     */
    boolean validateAnswer(final Set<String> answers);

    String getQuestionID();

    String getQuestion();

    String getExplanation();

    OSILayer getOsilayer();

    List<IQuestionType> getQuestionTypes();

}