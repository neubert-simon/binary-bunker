package questions;

import enumerations.questions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class QuestionFactory {

    private static final Logger log = LoggerFactory.getLogger(QuestionFactory.class);

    /**
     * Creates a new {@link MCQuestion} instance with the specified parameters.
     *
     * @param questionID the unique identifying number that is to be pulled from the database
     * @param question the question text
     * @param explanation an explanation of the correct answer(s)
     * @param correctAnswers a set of correct answers for the question
     * @param answers a map where the key is the answer choice identifier (e.g., "A", "B") and the value is the corresponding answer text
     * @param osiLayer the OSI layer to which the question pertains
     * @param difficulty the difficulty level of the question
     * @param questionType a list of question types associated with the question
     * @return a new instance of {@link MCQuestion} initialized with the provided parameters
     */
    public static IQuestion createMCQuestion(final String questionID, final String question,final String explanation,final Set<String> correctAnswers,final Map<String, String> answers,final OSILayer osiLayer,final Difficulty difficulty,final List<IQuestionType> questionType) {
        log.info("Creating MCQuestion instance with parameters: questionID={}, question={}, explanation={}, correctAnswers={}, answers={}, osiLayer={}, difficulty={}, questionType={}", questionID, question, explanation, correctAnswers, answers, osiLayer, difficulty, questionType);
        return new MCQuestion(question, questionID, explanation, correctAnswers, answers, osiLayer, difficulty, questionType);
    }

    /**
     * Creates a new {@link PromptQuestion} instance with the specified parameters, which are to be retrieved from a database.
     *
     * <p>This method is a factory function for creating an instance of {@link PromptQuestion}. It initializes the
     * prompt question with the provided question text, ID, explanation, OSI layer, and question type.</p>
     *
     * @param questionID the unique identifier for the question
     * @param question the question texts
     * @param explanation an explanation of the correct answer(s)
     * @param osiLayer the OSI layer to which the question pertains
     * @param questionType the specific predefined question type
     * @return a new instance of {@link PromptQuestion} initialized with the provided parameters
     */
    public static IQuestion createPromptQuestionFromDB(final String questionID,final String question,final String explanation,final OSILayer osiLayer,final QuestionTypePrompt questionType) {
        log.info("Creating PromptQuestion instance with parameters: questionID={}, question={}, explanation={}, osiLayer={}, questionType={}", questionID, question, explanation, osiLayer, questionType);
        return new PromptQuestion(questionID, question, explanation, osiLayer, questionType);
    }

    /**
     * Creates a PromptQuestion directly with specified parameters
     *
     * @param questionID the unique identifying number for the question
     * @param question the question text
     * @param explanation an explanation of the correct answer(s)
     * @param questionTypes a set of {@link QuestionTypePrompt} defining the type of question
     * @param osilayer the OSI layer to which the question pertains
     * @param inputFieldAmount the number of input fields required for the question to be answered in the frontend
     * @param questionParameters The questions dynamically created parameters
     * @param answerParameters Set of correct answers
     * @return a new instance of {@link PromptQuestion} initialized with the provided parameters
     */
    public static IQuestion createPromptQuestion(final String questionID, final String question, final String explanation, final Set<QuestionTypePrompt> questionTypes, final OSILayer osilayer, final int inputFieldAmount, final Set<String> questionParameters, final Set<String> answerParameters) {
        log.info("Creating PromptQuestion instances with parameters: questionID={}, question={}, explanation={}, questionTypes={}, osilayer={}, inputFieldAmount={}, questionParameters={}, answerParameters={}", questionID, question, explanation, questionTypes, osilayer, inputFieldAmount, questionParameters, answerParameters);
        return new PromptQuestion(questionID, question, explanation, questionTypes, osilayer, inputFieldAmount, questionParameters, answerParameters);
    }

}