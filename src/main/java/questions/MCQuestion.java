package questions;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import enumerations.questions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Multiple Choice Questions with predefined questions and answers
 */
public class MCQuestion extends Question {

    @JsonProperty("allAnswers")
    private final Map<String, String> allAnswers;
    @JsonProperty("correctAnswerKeys")
    private final Set<String> correctAnswerKeys;
    @JsonProperty("difficulty")
    private final Difficulty difficulty;

    private static final Logger log = LoggerFactory.getLogger(MCQuestion.class);

    /**
     * Creates Multiple Choice question, both the object and its JSON representation that is to be delivered to the frontend
     * @param question Question text
     * @param questionID Question Identifier from database
     * @param explanation Question explanation text
     * @param correctAnswerKeys Identifiers for correct answers (from keys in allAnswers)
     * @param answers All answers and their identifiers as a map (example: [a : Answer A])
     * @param osiLayer OSI Layer the question asks about
     * @param difficulty Difficulty of the question
     * @param questionType Types the question has (can be multiple)
     */
    @JsonCreator
    MCQuestion(final String question,final String questionID,final String explanation,final Set<String> correctAnswerKeys,final Map<String, String> answers,final OSILayer osiLayer,final Difficulty difficulty,final List<IQuestionType> questionType) {
        super(questionID, question, explanation, questionType, osiLayer);
        log.debug("Creating MCQuestion instance with parameters: question={}, questionID={}, explanation={}, correctAnswerKeys={}, answers={}, osiLayer={}, difficulty={}, questionType={}", question, questionID, explanation, correctAnswerKeys, answers, osiLayer, difficulty, questionType);
        if(difficulty == null) throw new IllegalArgumentException("Difficulty can't be null.");
        if (answers == null || answers.isEmpty() || correctAnswerKeys == null || correctAnswerKeys.isEmpty()) {
            throw new IllegalArgumentException("Answers cannot be empty.");
        }
        if (!answers.keySet().containsAll(correctAnswerKeys) || correctAnswerKeys.size() > answers.size()) {
            throw new IllegalArgumentException("Correct answers must be keys in the answer map.");
        }
        this.allAnswers = answers;
        this.correctAnswerKeys = correctAnswerKeys;
        this.difficulty = difficulty;
        log.info("Created MCQuestion instance.");
    }

    //region Instance methods
    @Override
    public boolean validateAnswer(Set<String> userAnswers) {
        log.debug("Validating answers {}", userAnswers);
        return userAnswers.equals(getAnswerParameters());
    }
    //endregion

    // region Getter
    @JsonIgnore
    public Set<String> getAnswerParameters() {
        final Set<String> correctAnswers = new HashSet<>(correctAnswerKeys.size());
        correctAnswerKeys.forEach(key -> correctAnswers.add(allAnswers.get(key)));
        return correctAnswers;
    }

    @JsonIgnore
    public Set<String> getAllAnswers() {
        final Set<String> answers = new HashSet<>(allAnswers.size());
        allAnswers.forEach((key, answer) -> answers.add(answer));
        return answers;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    //endregion

}