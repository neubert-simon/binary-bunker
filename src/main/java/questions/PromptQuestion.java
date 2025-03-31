package questions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import enumerations.ip.AddressType;
import enumerations.questions.IQuestionType;
import enumerations.questions.QuestionTypePrompt;
import enumerations.questions.OSILayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static utility.PromptQuestionUtility.*;

/**
 * Questions with predefined structure and variable parameters that are generated randomly
 */
public class PromptQuestion extends Question {

    @JsonProperty("inputFieldAmount")
    private final int inputFieldAmount;
    @JsonProperty("questionParameters")
    private final Set<String> questionParameters;
    @JsonProperty("answerParameters")
    private final Set<String> answerParameters;

    private static final Logger log = LoggerFactory.getLogger(PromptQuestion.class);

    /**
     * Creates JSON representation for PromptQuestions that is to be delivered to the frontend
     * @param question Question text
     * @param questionID Question Identifier from database
     * @param explanation Question explanation text
     * @param questionTypes Question type of the question as singleton list
     * @param osilayer OSI Layer the question asks about
     * @param inputFieldAmount Amount of fields to be rendered
     * @param questionParameters Dynamically and randomly generated question parameters
     * @param answerParameters Answers to dynamically generated question
     */
    @JsonCreator
    PromptQuestion(final String questionID, final String question, final String explanation, final Set<QuestionTypePrompt> questionTypes,
                   final OSILayer osilayer, final int inputFieldAmount, final Set<String> questionParameters, final Set<String> answerParameters) {
        super(questionID, question, explanation, Collections.singletonList(questionTypes.iterator().next()), osilayer);
        log.debug("Creating JSON Response with PromptQuestion with parameters: questionID={}, question={}, explanation={}, questionTypes={}, osilayer={}, inputFieldAmount={}, questionParameters={}, answerParameters={}", questionID, question, explanation, questionTypes, osilayer, inputFieldAmount, questionParameters, answerParameters);
        if (inputFieldAmount < 0) {
            throw new IllegalArgumentException("Input field amount cannot be negative.");
        }
        this.inputFieldAmount = inputFieldAmount;
        this.questionParameters = questionParameters;
        this.answerParameters = answerParameters;
        log.info("JSON PromptQuestion Response created.");
    }

    /**
     * Creates PromptQuestion and randomly generates the necessary parameters dynamically
     * @param questionID Question Identifier from database
     * @param question Question text
     * @param explanation Question explanation text
     * @param osiLayer OSI Layer the question asks about
     * @param type Predefined question type
     */
    PromptQuestion(final String questionID, final String question, final String explanation, final OSILayer osiLayer, final QuestionTypePrompt type) {
        super(questionID, question, explanation, Collections.singletonList(type), osiLayer);
        log.debug("PromptQuestion created with parameters: questionID={}, question={}, explanation={}, questionType={}, osilayer={}", questionID, question, explanation, type, osiLayer);
        if (type == null) {
            throw new IllegalArgumentException("QuestionTypePrompt cannot be null.");
        }
        if (type.inputFieldAmount < 0) {
            throw new IllegalArgumentException("Input field amount cannot be negative.");
        }
        this.inputFieldAmount = type.inputFieldAmount;
        this.questionParameters = generateQuestion(type);
        this.answerParameters = generateAnswer(type);
        log.info("PromptQuestion instance created");
    }

    //region Instance methods

    /**
     * Randomly generate a {@code PromptQuestion} following a predefined pattern with dynamic parameters
     * @param type Type of question to create
     * @return Set of question parameters as strings
     */
    private Set<String> generateQuestion(final QuestionTypePrompt type) {
        log.info("generateQuestion() called with type: {}", type);
        return switch (type) {
            case WHICH_PREFIX_IN_SAME_SUBNET -> findPrefixParameters();
            case FIND_MASK, FIND_NET_ID, FIND_BROADCAST, FIND_NET_ID_AND_BROADCAST, FIND_OCTET, FIND_BINARY_OCTET -> generateIPWithPrefix();
            case FIND_IP_IN_SUBNET -> findIP(0);
            case MAC_TO_IID -> turnMacToIID(true);
            case IID_TO_MAC -> turnMacToIID(false);
            case NUMBER_OF_DEVICES -> numberOfDevices();
            default -> throw new IllegalArgumentException("Unsupported QuestionTypePrompt: " + type);
        };
    }

    /**
     * Generate Answer for already generated question
     * @param type Type of question in need of an answer
     * @return Set of answers as strings
     */
    private Set<String> generateAnswer(final QuestionTypePrompt type) {
        log.info("generateAnswer() called with type: {}", type);
        return switch (type) {
            case WHICH_PREFIX_IN_SAME_SUBNET -> generateAnswerForPrefix(questionParameters);
            case FIND_IP_IN_SUBNET -> generateAnswerForIP(questionParameters);
            case FIND_MASK -> generateAnswerForSubnet(questionParameters, Collections.singletonList(AddressType.Mask));
            case FIND_NET_ID -> generateAnswerForSubnet(questionParameters, Collections.singletonList(AddressType.NetID));
            case FIND_BROADCAST -> generateAnswerForSubnet(questionParameters, Collections.singletonList(AddressType.Broadcast));
            case FIND_NET_ID_AND_BROADCAST -> generateAnswerForSubnet(questionParameters, List.of(AddressType.NetID, AddressType.Broadcast));
            case FIND_OCTET -> generateAnswerForOctet(questionParameters);
            case FIND_BINARY_OCTET -> generateAnswerForBinaryOctet(questionParameters);
            case MAC_TO_IID, IID_TO_MAC -> generateAnswerForMac_IID_Translation(questionParameters, type);
            case NUMBER_OF_DEVICES -> generateAnswerForDevices(questionParameters);
            default -> throw new IllegalArgumentException("Unsupported QuestionTypePrompt: " + type);
        };
    }

    @Override @SuppressWarnings("all")
    public boolean validateAnswer(final Set<String> userAnswers) {
        log.info("validateAnswer() called with userAnswers: {}", userAnswers);
        if(userAnswers == null || userAnswers.isEmpty()) throw new IllegalArgumentException("Null parameter - no user answers passed in.");
        final IQuestionType type = getQuestionTypes().getFirst();
        if(!(type instanceof QuestionTypePrompt)) throw new IllegalStateException("PromptQuestions must have QuestionType of QuestionTypePrompt.");
        // Switch has only one case label, but more QuestionTypes requiring extra cases for validation are planned
        switch((QuestionTypePrompt) type) {
            case FIND_IP_IN_SUBNET -> {
                return validateFoundIP(questionParameters, userAnswers);
            }
        }
        return answerParameters.equals(userAnswers);
    }
    //endregion

    //region Getter
    public Set<String> getQuestionParameters() {
        return questionParameters;
    }

    public Set<String> getAnswerParameters() {
        return answerParameters;
    }

    public int getInputFieldAmount() {
        return inputFieldAmount;
    }
    //endregion
}