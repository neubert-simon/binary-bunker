package questions;

import enumerations.questions.IQuestionType;
import enumerations.questions.QuestionTypePrompt;
import org.junit.jupiter.api.Test;
import static enumerations.questions.QuestionTypePrompt.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static enumerations.questions.OSILayer.L1_PHYSICAL;

public class PromptQuestionTest {

    @Test
    public void validateAnswerTestNegative() {

        IQuestion sample = generateGeneralTestPromptQuestion(FIND_NET_ID);
        IQuestion lambdaSample = sample;
        assertThrows(IllegalArgumentException.class, () -> lambdaSample.validateAnswer(null));
        assertThrows(IllegalArgumentException.class, () -> lambdaSample.validateAnswer(Set.of()));

        for(QuestionTypePrompt type : QuestionTypePrompt.values()) {
            sample = generateGeneralTestPromptQuestion(type);
            cycleThroughGeneralInvalidAnswers(sample, type);
        }

        //region One correct set of answers
        sample = generateTestPromptQuestionsWithAnswers(WHICH_PREFIX_IN_SAME_SUBNET, Set.of("0"));
        assertFalse(sample.validateAnswer(Set.of("Links Rechts ich bin kardinal")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_NET_ID, Set.of("127.0.0.2"));
        assertFalse(sample.validateAnswer(Set.of("127.0.0.3")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_BROADCAST, Set.of("0.0.0.12"));
        assertFalse(sample.validateAnswer(Set.of("Fall in Sekundenschlaf bei 120km/h")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_NET_ID_AND_BROADCAST, Set.of("123.212.53.0", "213.21.45.9"));
        assertFalse(sample.validateAnswer(Set.of("213.21.45.9")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_MASK, Set.of("255.255.128.0"));
        assertFalse(sample.validateAnswer(Set.of("Air Force liegen schwer auf dem Gaspedal")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_OCTET, Set.of("23"));
        assertFalse(sample.validateAnswer(Set.of("129")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_BINARY_OCTET, Set.of("11110011"));
        assertFalse(sample.validateAnswer(Set.of("Ich sammle rote Laser Beams auf der Polo-Cap")));

        sample = generateTestPromptQuestionsWithAnswers(NUMBER_OF_DEVICES, Set.of("12"));
        assertFalse(sample.validateAnswer(Set.of("Zwölf")));

        sample = generateTestPromptQuestionsWithAnswers(MAC_TO_IID, Set.of("02:1A:2B:FF:FE:3C:4D:5E"));
        assertFalse(sample.validateAnswer(Set.of("02:1A:2B:FF :FE:3C:4D:5E")));

        sample = generateTestPromptQuestionsWithAnswers(IID_TO_MAC, Set.of("00:1A:2B:3C:4D:5E"));
        assertFalse(sample.validateAnswer(Set.of("00:1A:2B:3C:4D:5ö")));
        //endregion

        //region Multiple Answers possible
        sample = generateTestPromptQuestionsWithMultipleCorrectAnswers(FIND_IP_IN_SUBNET, Set.of("0.0.0.0", "30"));
        assertFalse(sample.validateAnswer(Set.of("12.1.1.1")));

        sample = generateTestPromptQuestionsWithMultipleCorrectAnswers(FIND_IP_IN_SUBNET, Set.of("123.0.0.0", "16"));
        assertFalse(sample.validateAnswer(Set.of("122.0.12.223")));
        //endregion
    }

    @Test
    public void validateAnswerTestPositive() {
        //region One correct set of answers
        IQuestion sample = generateTestPromptQuestionsWithAnswers(WHICH_PREFIX_IN_SAME_SUBNET, Set.of("0"));
        assertTrue(sample.validateAnswer(Set.of("0")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_NET_ID, Set.of("127.0.0.2"));
        assertTrue(sample.validateAnswer(Set.of("127.0.0.2")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_BROADCAST, Set.of("0.0.0.12"));
        assertTrue(sample.validateAnswer(Set.of("0.0.0.12")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_NET_ID_AND_BROADCAST, Set.of("123.212.53.0", "213.21.45.9"));
        assertTrue(sample.validateAnswer(Set.of("123.212.53.0", "213.21.45.9")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_MASK, Set.of("255.255.128.0"));
        assertTrue(sample.validateAnswer(Set.of("255.255.128.0")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_OCTET, Set.of("23"));
        assertTrue(sample.validateAnswer(Set.of("23")));

        sample = generateTestPromptQuestionsWithAnswers(FIND_BINARY_OCTET, Set.of("11110011"));
        assertTrue(sample.validateAnswer(Set.of("11110011")));

        sample = generateTestPromptQuestionsWithAnswers(NUMBER_OF_DEVICES, Set.of("12"));
        assertTrue(sample.validateAnswer(Set.of("12")));

        sample = generateTestPromptQuestionsWithAnswers(MAC_TO_IID, Set.of("02:1A:2B:FF:FE:3C:4D:5E"));
        assertTrue(sample.validateAnswer(Set.of("02:1A:2B:FF:FE:3C:4D:5E")));

        sample = generateTestPromptQuestionsWithAnswers(IID_TO_MAC, Set.of("00:1A:2B:3C:4D:5E"));
        assertTrue(sample.validateAnswer(Set.of("00:1A:2B:3C:4D:5E")));
        //endregion

        //region Multiple Answers possible
        sample = generateTestPromptQuestionsWithMultipleCorrectAnswers(FIND_IP_IN_SUBNET, Set.of("0.0.0.0", "0"));
        assertTrue(sample.validateAnswer(Set.of("1.1.1.1")));

        sample = generateTestPromptQuestionsWithMultipleCorrectAnswers(FIND_IP_IN_SUBNET, Set.of("123.0.0.0", "16"));
        assertTrue(sample.validateAnswer(Set.of("123.0.12.223")));
        //endregion
    }

    //region Auxiliary
    private void cycleThroughGeneralInvalidAnswers(IQuestion sample, IQuestionType type) {
        List<Set<String>> invalidAnswers = List.of(Set.of("I don't know."), Set.of("-1"), Set.of("266.0.0.0"), Set.of("DROP TABLE PromptQuestions"), Set.of("0.0.0.0.0"), Set.of("25 5.0.0.0.0"), Set.of("99"), Set.of("120:00:2.2"), Set.of("abc", "dfg"));
        for(Set<String> answers : invalidAnswers) {
            //region Special case dynamic validation for questions with multiple possible answers
            if(type == FIND_IP_IN_SUBNET) assertThrows(IllegalArgumentException.class, () -> sample.validateAnswer(answers));
                //endregion
            else assertFalse(sample.validateAnswer(answers));
        }
    }

    private IQuestion generateGeneralTestPromptQuestion(QuestionTypePrompt type) {
        return new PromptQuestion("1", "Test", "Test", Collections.singleton(type), L1_PHYSICAL, 1, Set.of("0.0.0.0", "127.0.0.1"), Set.of("Answer"));
    }

    private IQuestion generateTestPromptQuestionsWithAnswers(QuestionTypePrompt type, Set<String> answers) {
        return new PromptQuestion("1", "Test", "Test", Collections.singleton(type), L1_PHYSICAL, 1, Set.of("0.0.0.0", "0"), answers);
    }

    private IQuestion generateTestPromptQuestionsWithMultipleCorrectAnswers(QuestionTypePrompt type, Set<String> questionParameters) {
        return new PromptQuestion("1", "Test", "Test", Collections.singleton(type), L1_PHYSICAL, 1, questionParameters, Set.of(""));
    }
    //endregion

}
