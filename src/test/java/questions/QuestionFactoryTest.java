package questions;

import enumerations.questions.QuestionTypePrompt;
import enumerations.questions.OSILayer;

import static questions.QuestionFactory.*;
import static enumerations.questions.QuestionTypeMC.*;
import static enumerations.questions.QuestionTypePrompt.*;
import static enumerations.questions.OSILayer.*;
import static enumerations.questions.Difficulty.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = QuestionFactory.class)
public class QuestionFactoryTest {

    @MockBean
    MCQuestion mockMCQuestion;
    @MockBean
    PromptQuestion mockPromptQuestion;

    //region MCQuestions
    @Test
    public void MCQuizTestsPositive() {
        // valid String tests
        assertDoesNotThrow(() -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of(NAT)));
        assertDoesNotThrow(() -> new MCQuestion("1",
                "This question contains a few more words but that shouldn't really be a problem cause questions normally contain more than 1 word right, so here I am righting a longer text to see if there are any problems a long text might cause even though I'm really sure that it should be irrelevant... anyhow here I am doing unit tests... this should be enough so thanks for listening to my tedtalk cya later buhbye!!!",
                "what a weird question, why would it even be relevant how long a String is like its not really something that can break things right. yeah I'm kinda thinking this is a stupid unit test but I already wrote one text so I might as well just test it with the explanation. not really like there is much more to write about so I'm really grasping at straws with this text here. Yeah I think I'm gonna stop cause there isn't much more to write",
                Set.of("weirdKey"), Map.of("weirdKeyWrong", "Some crazy not correct answer this is" ,"weirdKey", "rightChoiceMuchoTexto so choose this one"), L1_PHYSICAL, EASY, List.of(NAT)));

        // valid Enum tests
        assertDoesNotThrow(() -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of(NAT)));
        assertDoesNotThrow(() -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, MEDIUM, List.of(NAT)));
        assertDoesNotThrow(() -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, DIFFICULT, List.of(NAT)));

        assertDoesNotThrow(() -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L2_DATA_LINK, EASY, List.of(NAT, NAT)));
        assertDoesNotThrow(() -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L3_NETWORK, EASY, List.of(NAT)));
        assertDoesNotThrow(() -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L4_TRANSPORT, EASY, List.of(NAT, NAT)));
    }
    @Test
    public void MCQuizEmptyStringTests() {
        // empty String tests
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", "", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
    }
    @Test
    public void MCQuizNullParameterTests() {
        // null tests
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion(null, null, null, null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion(null, "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", null, "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", null, Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", "Test Explanation", null, Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), null, L1_PHYSICAL, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), null, EASY, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, null, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", "Test Explanation", Set.of("a"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, null));
    }
    @Test
    public void MCQuizInvalidValues() {
        // invalid values
        assertThrows(IllegalArgumentException.class, () -> new MCQuestion("1", "Test", "Test Explanation", Set.of("nonExistent"), Map.of("a", "ChoiceA" ,"b", "ChoiceA"), L1_PHYSICAL, EASY, List.of()));
    }
    //endregion

    //region PromptQuestions
    @Test
    public void createPromptTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> new PromptQuestion("", "Test", "Test Explanation", Collections.singleton(FIND_NET_ID), L1_PHYSICAL, 0, Set.of("0.0.0.0", "127.0.0.1"), Set.of("Answer")));
        assertThrows(IllegalArgumentException.class, () -> new PromptQuestion("1", "", "Test Explanation", Collections.singleton(FIND_NET_ID), L1_PHYSICAL, 0, Set.of("0.0.0.0", "127.0.0.1"), Set.of("Answer")));
        assertThrows(IllegalArgumentException.class, () -> new PromptQuestion("Test ID", "1", "", Collections.singleton(FIND_NET_ID), L1_PHYSICAL, 0, Set.of("0.0.0.0", "127.0.0.1"), Set.of("Answer")));
        assertThrows(IllegalArgumentException.class, () -> new PromptQuestion("Test Question", "1", "Test Explanation", Collections.singleton(FIND_NET_ID), L1_PHYSICAL, -1, Set.of("0.0.0.0", "127.0.0.1"), Set.of("Answer")));
        assertThrows(IllegalArgumentException.class, () -> createPromptQuestionFromDB(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> createPromptQuestionFromDB(null, null, "", L3_NETWORK, null));
        assertThrows(IllegalArgumentException.class, () -> createPromptQuestionFromDB("Sas", null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> createPromptQuestionFromDB(null, "Ses", null, null, null));
        assertThrows(IllegalArgumentException.class, () -> createPromptQuestionFromDB("Sos", "Sus", null, null, null));
        assertThrows(IllegalArgumentException.class, () -> createPromptQuestionFromDB("Sis", "Sqs", "Sxs", L3_NETWORK, null));
        assertThrows(IllegalArgumentException.class, () -> createPromptQuestionFromDB("Test", "Testing", "TestTest", null, MAC_TO_IID));
    }

    @Test
    public void createPromptTestPositive() {
        setMockPrompt("1", "Test Question", "Test Explanation", FIND_NET_ID, L1_PHYSICAL, 1, Set.of("0.0.0.0", "127.0.0.1"), Set.of("Answer"));
        assertEquals(new PromptQuestion("1", "Test Question", "Test Explanation", Collections.singleton(FIND_NET_ID), L1_PHYSICAL, 1, Set.of("0.0.0.0", "127.0.0.1"), Set.of("Answer")), mockPromptQuestion);

        setMockPrompt("NAT2", "Test Question", "Test Exploration", FIND_NET_ID, L1_PHYSICAL, 1, Set.of("255.255.255.255", "24"), Set.of("Answer"));
        assertEquals(new PromptQuestion("NAT2", "Test Question", "Test Exploration", Collections.singleton(FIND_NET_ID), L1_PHYSICAL, 1, Set.of("255.255.255.255", "24"), Set.of("Answer")), mockPromptQuestion);

        setMockPrompt("GNX", "See you Space Cowboy", "Test Explanation", FIND_NET_ID_AND_BROADCAST, L3_NETWORK, 5, Set.of("This is a parameter", "This is another parameter"), Set.of("Answer"));
        assertEquals(new PromptQuestion("GNX", "See you Space Cowboy", "Test Explanation", Collections.singleton(FIND_NET_ID_AND_BROADCAST), L3_NETWORK, 5, Set.of("This is a parameter", "This is another parameter"), Set.of("Answer")), mockPromptQuestion);

        PromptQuestion prompt = (PromptQuestion) createPromptQuestionFromDB("1", "Test Question", "Test Explanation", L1_PHYSICAL, FIND_NET_ID);
        setMockPrompt("1", "Test Question", "Test Explanation", FIND_NET_ID, L1_PHYSICAL, prompt.getInputFieldAmount(), prompt.getQuestionParameters(), prompt.getAnswerParameters());
        assertEquals(prompt, mockPromptQuestion);

        prompt = (PromptQuestion) createPromptQuestionFromDB("2", "Example Question", "Example Explanation", L2_DATA_LINK, IID_TO_MAC);
        setMockPrompt("2", "Example Question", "Example Explanation", IID_TO_MAC, L2_DATA_LINK, prompt.getInputFieldAmount(), prompt.getQuestionParameters(), prompt.getAnswerParameters());
        assertEquals(prompt, mockPromptQuestion);

        prompt = (PromptQuestion) createPromptQuestionFromDB("DB23", "Example Question", "Example Explanation", L3_NETWORK, MAC_TO_IID);
        setMockPrompt("DB23", "Example Question", "Example Explanation", MAC_TO_IID, L3_NETWORK, prompt.getInputFieldAmount(), prompt.getQuestionParameters(), prompt.getAnswerParameters());
        assertEquals(prompt, mockPromptQuestion);

        prompt = (PromptQuestion) createPromptQuestionFromDB("DB23", "Long Question Test: ewsxdcfvgbhjnkmljbhvgcftxrcfvgbhjknlmjnbhvzctxrtcvzbunijmk,mjnbhvgcfdxrtcvzubinjmknjbhuvzctxrctvzgbhnjmkljnbuvgzctxrdctfvgbuhnjmkljnbhvgzctxcvzgbuhnjbvzctxrtczvubhinjbhuvzctxctvzgbuhnj", "Example Explanation", L3_NETWORK, MAC_TO_IID);
        setMockPrompt("DB23", "Long Question Test: ewsxdcfvgbhjnkmljbhvgcftxrcfvgbhjknlmjnbhvzctxrtcvzbunijmk,mjnbhvgcfdxrtcvzubinjmknjbhuvzctxrctvzgbhnjmkljnbuvgzctxrdctfvgbuhnjmkljnbhvgzctxcvzgbuhnjbvzctxrtczvubhinjbhuvzctxctvzgbuhnj", "Example Explanation", MAC_TO_IID, L3_NETWORK, prompt.getInputFieldAmount(), prompt.getQuestionParameters(), prompt.getAnswerParameters());
        assertEquals(prompt, mockPromptQuestion);
    }

    private void setMockPrompt(String questionID, String question, String explanation, QuestionTypePrompt type, OSILayer osi, int inputFieldAmount, Set<String> questionParameters, Set<String> answerParameters) {
        setIQuestionForPrompt(questionID, question, explanation, type, osi);
        when(mockPromptQuestion.getInputFieldAmount()).thenReturn(inputFieldAmount);
        when(mockPromptQuestion.getQuestionParameters()).thenReturn(questionParameters);
        when(mockPromptQuestion.getAnswerParameters()).thenReturn(answerParameters);
    }

    private void setIQuestionForPrompt(String questionID, String question, String explanation, QuestionTypePrompt type, OSILayer osi) {
        when(mockPromptQuestion.getQuestionID()).thenReturn(questionID);
        when(mockPromptQuestion.getQuestion()).thenReturn(question);
        when(mockPromptQuestion.getExplanation()).thenReturn(explanation);
        when(mockPromptQuestion.getQuestionTypes()).thenReturn(Collections.singletonList(type));
        when(mockPromptQuestion.getOsilayer()).thenReturn(osi);
    }
    //endregion
}