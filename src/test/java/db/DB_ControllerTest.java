package db;

import com.fasterxml.jackson.core.JsonProcessingException;
import enumerations.db.QuestionType;
import enumerations.questions.*;
import exceptions.EnvironmentVariableNotFoundException;
import exceptions.NoMatchException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.boot.test.context.SpringBootTest;
import questions.MCQuestion;
import questions.PromptQuestion;
import questions.QuestionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes= DB_Controller.class)
@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "development")
public class DB_ControllerTest {
    static DB_Controller db = DB_Controller.getInstance();

    // region BeforeEach
    @BeforeEach
    public void createMCTestValues() throws SQLException, EnvironmentVariableNotFoundException {
        db.createQuestion(
                QuestionType.MCQUESTION,
                1000,
                new ArrayList<>(List.of(
                        OSILayer.L1_PHYSICAL,
                        Difficulty.EASY,
                        QuestionTypeMC.SWITCHING)),
                "{\"question\": \"TestQuestion\", \"explanation\": \"TestExplanation\", \"allAnswers\": {\"1\": \"answer_1\", \"2\": \"answer_2\", \"3\": \"answer_3\"}, \"correctAnswers\": [\"2\"]}");
        db.createQuestion(
                QuestionType.MCQUESTION,
                1001,
                new ArrayList<>(List.of(
                        OSILayer.L2_DATA_LINK,
                        Difficulty.MEDIUM,
                        QuestionTypeMC.IPV6, QuestionTypeMC.DEFINITIONS)),
                "{\"question\": \"TestQuestion2\", \"explanation\": \"TestExplanation2\", \"allAnswers\": {\"1\": \"answer_1\", \"2\": \"answer_2\", \"3\": \"answer_3\"}, \"correctAnswers\": [\"1\"]}");

    }
    @BeforeEach
    public void createPromptTestValues() throws SQLException, EnvironmentVariableNotFoundException {
        db.createQuestion(
                QuestionType.PROMPTQUESTION,
                1000,
                new ArrayList<>(List.of(
                        OSILayer.L1_PHYSICAL,
                        QuestionTypePrompt.FIND_NET_ID)),
                "{\"question\": \"TestQuestion\", \"explanation\": \"TestExplanation\"}");
        db.createQuestion(
                QuestionType.PROMPTQUESTION,
                1001,
                new ArrayList<>(List.of(
                        OSILayer.L2_DATA_LINK,
                        QuestionTypePrompt.IID_TO_MAC)),
                "{\"question\": \"TestQuestion2\", \"explanation\": \"TestExplanation2\"}");
    }
    // endregion

    // region Crud tests
    // region Read
    @Test
    public void readMCQuestionPositive() throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        assertEquals(
                QuestionFactory.createMCQuestion(
                        "1000",
                        "TestQuestion",
                        "TestExplanation",
                        Set.of("2"),
                        Map.of("1", "answer_1", "2", "answer_2", "3", "answer_3"),
                        OSILayer.L1_PHYSICAL, Difficulty.EASY, List.of(QuestionTypeMC.SWITCHING)),
                db.readQuestion(QuestionType.MCQUESTION, 1000));
        assertEquals(
                QuestionFactory.createMCQuestion(
                        "1001",
                        "TestQuestion2",
                        "TestExplanation2",
                        Set.of("1"),
                        Map.of("1", "answer_1", "2", "answer_2", "3", "answer_3"),
                        OSILayer.L2_DATA_LINK, Difficulty.MEDIUM, List.of(QuestionTypeMC.IPV6, QuestionTypeMC.DEFINITIONS)),
                db.readQuestion(QuestionType.MCQUESTION, 1001));
    }
    @Test
    public void readMCQuestionNegative() {
        // null values
        assertThrows(IllegalArgumentException.class, () -> db.readQuestion(null, 1000));
        // invalid ids
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.MCQUESTION, 0));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.MCQUESTION, -1));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.MCQUESTION, Integer.MIN_VALUE));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.MCQUESTION, 999));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.MCQUESTION, Integer.MAX_VALUE));
    }
    @Test
    public void readPromptQuestionPositive() throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {

        assertEquals("1000",
                db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionID());
        assertEquals(OSILayer.L1_PHYSICAL,
                db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.FIND_NET_ID)),
                db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionTypes());
        assertEquals("TestQuestion",
                db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestion());
        assertEquals("TestExplanation",
                db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getExplanation());

        assertEquals("1001",
                db.readQuestion(QuestionType.PROMPTQUESTION, 1001).getQuestionID());
        assertEquals(OSILayer.L2_DATA_LINK,
                db.readQuestion(QuestionType.PROMPTQUESTION, 1001).getOsilayer());
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC)),
                db.readQuestion(QuestionType.PROMPTQUESTION, 1001).getQuestionTypes());
        assertEquals("TestQuestion2",
                db.readQuestion(QuestionType.PROMPTQUESTION, 1001).getQuestion());
        assertEquals("TestExplanation2",
                db.readQuestion(QuestionType.PROMPTQUESTION, 1001).getExplanation());

    }
    @Test
    public void readPromptQuestionNegative() {
        // null values
        assertThrows(IllegalArgumentException.class, () -> db.readQuestion(null, 1000));
        // invalid ids
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.MCQUESTION, 0));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.PROMPTQUESTION, -1));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.PROMPTQUESTION, Integer.MIN_VALUE));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.PROMPTQUESTION, 999));
        assertThrows(NoMatchException.class, () -> db.readQuestion(QuestionType.PROMPTQUESTION, Integer.MAX_VALUE));
    }
    // endregion
    // region Update
    @Test
    public void updateMCQuestionPositiveWithoutJson() throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        // one list elem, without json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK))));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.MCQUESTION, 1000).getOsilayer());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L4_TRANSPORT))));
        assertEquals(OSILayer.L4_TRANSPORT, db.readQuestion(QuestionType.MCQUESTION, 1000).getOsilayer());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(Difficulty.DIFFICULT))));
        assertEquals(Difficulty.DIFFICULT, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(Difficulty.MEDIUM))));
        assertEquals(Difficulty.MEDIUM, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.IPV4))));
        assertEquals(new ArrayList<>(List.of(QuestionTypeMC.IPV4)), db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestionTypes());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.SWITCHING))));
        assertEquals(new ArrayList<>(List.of(QuestionTypeMC.SWITCHING)), db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestionTypes());
        // several list elems, without json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK, Difficulty.EASY))));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.MCQUESTION, 1000).getOsilayer());
        assertEquals(Difficulty.EASY, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L4_TRANSPORT, Difficulty.DIFFICULT))));
        assertEquals(OSILayer.L4_TRANSPORT, db.readQuestion(QuestionType.MCQUESTION, 1000).getOsilayer());
        assertEquals(Difficulty.DIFFICULT, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(Difficulty.EASY, QuestionTypeMC.SUBNETTING))));
        assertEquals(Difficulty.EASY, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertEquals(new ArrayList<>(List.of(QuestionTypeMC.SUBNETTING)), db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestionTypes());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.SUBNETTING, QuestionTypeMC.DEFINITIONS, QuestionTypeMC.IPV6))));
        assertEquals(new ArrayList<>(List.of(QuestionTypeMC.SUBNETTING, QuestionTypeMC.DEFINITIONS, QuestionTypeMC.IPV6)), db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestionTypes());
    }
    @Test
    public void updateMCQuestionPositiveWithJson() throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        // weird list elems and json combos
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L4_TRANSPORT)), ""));
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L4_TRANSPORT, QuestionTypePrompt.IID_TO_MAC)), ""));
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of()), "{\"example\":\"json\"}"));
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC)), "{\"example\":\"json\"}"));

        // one list elem, with json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)),
                "{\"question\": \"newQuestion\", \"explanation\": \"newExplanation\", \"allAnswers\": {\"1\": \"answer_1\"}, \"correctAnswers\": [\"1\"]}"));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.MCQUESTION, 1000).getOsilayer());
        assertEquals("newQuestion", db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestion());
        assertEquals("newExplanation", db.readQuestion(QuestionType.MCQUESTION, 1000).getExplanation());
        assertEquals(Set.of("answer_1"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAllAnswers());
        assertEquals(Set.of("answer_1"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAnswerParameters());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(Difficulty.MEDIUM)),
                "{\"question\": \"someQuestion\", \"explanation\": \"someExplanation\", \"allAnswers\": {\"1\": \"answer_1\", \"2\": \"answer_2\"}, \"correctAnswers\": [\"2\"]}"));
        assertEquals(Difficulty.MEDIUM, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertEquals("someQuestion", db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestion());
        assertEquals("someExplanation", db.readQuestion(QuestionType.MCQUESTION, 1000).getExplanation());
        assertEquals(Set.of("answer_1", "answer_2"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAllAnswers());
        assertEquals(Set.of("answer_2"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAnswerParameters());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.IPV4)),
                "{\"question\": \"anotherQuestion\", \"explanation\": \"anotherExplanation\", \"allAnswers\": {\"1\": \"answer_1\", \"2\": \"answer_2\", \"3\": \"answer_3\"}, \"correctAnswers\": [\"1\"]}"));
        assertEquals(new ArrayList<>(List.of(QuestionTypeMC.IPV4)), db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestionTypes());
        assertEquals("anotherQuestion", db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestion());
        assertEquals("anotherExplanation", db.readQuestion(QuestionType.MCQUESTION, 1000).getExplanation());
        assertEquals(Set.of("answer_1", "answer_2", "answer_3"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAllAnswers());
        assertEquals(Set.of("answer_1"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAnswerParameters());

        // several list elems, with json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK, Difficulty.EASY)),
                "{\"question\": \"newQuestion\", \"explanation\": \"newExplanation\", \"allAnswers\": {\"1\": \"answer_1\"}, \"correctAnswers\": [\"1\"]}"));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.MCQUESTION, 1000).getOsilayer());
        assertEquals(Difficulty.EASY, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertEquals("newQuestion", db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestion());
        assertEquals("newExplanation", db.readQuestion(QuestionType.MCQUESTION, 1000).getExplanation());
        assertEquals(Set.of("answer_1"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAllAnswers());
        assertEquals(Set.of("answer_1"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAnswerParameters());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(Difficulty.EASY, QuestionTypeMC.SUBNETTING)),
                "{\"question\": \"someQuestion\", \"explanation\": \"someExplanation\", \"allAnswers\": {\"1\": \"answer_1\", \"2\": \"answer_2\"}, \"correctAnswers\": [\"2\"]}"));
        assertEquals(Difficulty.EASY, ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getDifficulty());
        assertEquals(new ArrayList<>(List.of(QuestionTypeMC.SUBNETTING)), db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestionTypes());
        assertEquals("someQuestion", db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestion());
        assertEquals("someExplanation", db.readQuestion(QuestionType.MCQUESTION, 1000).getExplanation());
        assertEquals(Set.of("answer_1", "answer_2"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAllAnswers());
        assertEquals(Set.of("answer_2"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAnswerParameters());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.SUBNETTING, QuestionTypeMC.DEFINITIONS, QuestionTypeMC.IPV6)),
                "{\"question\": \"anotherQuestion\", \"explanation\": \"anotherExplanation\", \"allAnswers\": {\"1\": \"answer_1\", \"2\": \"answer_2\", \"3\": \"answer_3\"}, \"correctAnswers\": [\"1\"]}"));
        assertEquals(new ArrayList<>(List.of(QuestionTypeMC.SUBNETTING, QuestionTypeMC.DEFINITIONS, QuestionTypeMC.IPV6)), db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestionTypes());
        assertEquals("anotherQuestion", db.readQuestion(QuestionType.MCQUESTION, 1000).getQuestion());
        assertEquals("anotherExplanation", db.readQuestion(QuestionType.MCQUESTION, 1000).getExplanation());
        assertEquals(Set.of("answer_1", "answer_2", "answer_3"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAllAnswers());
        assertEquals(Set.of("answer_1"), ((MCQuestion) db.readQuestion(QuestionType.MCQUESTION, 1000)).getAnswerParameters());
    }
    @Test
    public void updateMCQuestionNegative() {
        // without json
        // null parameters
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK))));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, null));
        // invalid characteristic combinations
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of())));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC))));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC, QuestionTypePrompt.FIND_BINARY_OCTET))));
        // with json
        // null parameters
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)), ""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, null, ""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)), null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, null, ""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, null, null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)), null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, null, null));
        // invalid value combinations
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of()),""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.MCQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC)), ""));
    }
    @Test
    public void updatePromptQuestionPositiveWithoutJson() throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        // one list elem, without json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK))));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L4_TRANSPORT))));
        assertEquals(OSILayer.L4_TRANSPORT, db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.NUMBER_OF_DEVICES))));
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.NUMBER_OF_DEVICES)), db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionTypes());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC))));
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC)), db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionTypes());
        // several list elems, without json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK, Difficulty.EASY))));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L1_PHYSICAL, QuestionTypePrompt.FIND_BINARY_OCTET))));
        assertEquals(OSILayer.L1_PHYSICAL, db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.FIND_BINARY_OCTET)), db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionTypes());
    }
    @Test
    public void updatePromptQuestionPositiveWithJson() throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        // weird list elems and json combos
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L4_TRANSPORT)), ""));
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L4_TRANSPORT, QuestionTypeMC.DEFINITIONS)), ""));
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of()), "{\"example\":\"json\"}"));
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.DEFINITIONS)), "{\"example\":\"json\"}"));

        // one list elem, with json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)),
                "{\"question\": \"newQuestion\", \"explanation\": \"newExplanation\"}"));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertEquals("newQuestion", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestion());
        assertEquals("newExplanation", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getExplanation());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of()),
                "{\"question\": \"someQuestion\", \"explanation\": \"someExplanation\"}"));
        assertEquals("someQuestion", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestion());
        assertEquals("someExplanation", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getExplanation());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.FIND_BROADCAST)),
                "{\"question\": \"anotherQuestion\", \"explanation\": \"anotherExplanation\"}"));
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.FIND_BROADCAST)), db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionTypes());
        assertEquals("anotherQuestion", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestion());
        assertEquals("anotherExplanation", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getExplanation());

        // several list elems, with json
        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK, Difficulty.EASY)),
                "{\"question\": \"newQuestion\", \"explanation\": \"newExplanation\"}"));
        assertEquals(OSILayer.L3_NETWORK, db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertEquals("newQuestion", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestion());
        assertEquals("newExplanation", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getExplanation());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L2_DATA_LINK, QuestionTypePrompt.IID_TO_MAC)),
                "{\"question\": \"someQuestion\", \"explanation\": \"someExplanation\"}"));
        assertEquals(OSILayer.L2_DATA_LINK, db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getOsilayer());
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC)), db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionTypes());
        assertEquals("someQuestion", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestion());
        assertEquals("someExplanation", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getExplanation());

        assertDoesNotThrow(() -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypePrompt.FIND_OCTET, QuestionTypeMC.SUBNETTING, QuestionTypeMC.DEFINITIONS, QuestionTypeMC.IPV6)),
                "{\"question\": \"anotherQuestion\", \"explanation\": \"anotherExplanation\"}"));
        assertEquals(new ArrayList<>(List.of(QuestionTypePrompt.FIND_OCTET)), db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestionTypes());
        assertEquals("anotherQuestion", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getQuestion());
        assertEquals("anotherExplanation", db.readQuestion(QuestionType.PROMPTQUESTION, 1000).getExplanation());
    }
    @Test
    public void updatePromptQuestionNegative() {
        // without json
        // null parameters
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK))));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, null));
        // invalid characteristic combinations
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.DEFINITIONS))));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.DEFINITIONS, QuestionTypeMC.IPV6))));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of())));
        // with json
        // null parameters
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)), ""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, null, ""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)), null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, null, ""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, null, null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, new ArrayList<>(List.of(OSILayer.L3_NETWORK)), null));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(null, 1000, null, null));
        // invalid value combinations
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of()),""));
        assertThrows(IllegalArgumentException.class, () -> db.updateQuestion(QuestionType.PROMPTQUESTION, 1000, new ArrayList<>(List.of(QuestionTypeMC.DEFINITIONS)), ""));
    }
    // endregion
    // endregion

    // region AfterEach
    @AfterEach
    public void deleteMCTestValues() throws SQLException, EnvironmentVariableNotFoundException {
        db.deleteQuestion(
                QuestionType.MCQUESTION,
                1000);
        db.deleteQuestion(
                QuestionType.MCQUESTION,
                1001);
    }
    @AfterEach
    public void deletePromptTestValues() throws SQLException, EnvironmentVariableNotFoundException {
        db.deleteQuestion(
                QuestionType.PROMPTQUESTION,
                1000);
        db.deleteQuestion(
                QuestionType.PROMPTQUESTION,
                1001);
    }
    // endregion

    // region getRandomQuestion
    @Test
    public void getRandomQuestionPositive() throws EnvironmentVariableNotFoundException, SQLException {
        // instance without filter
        assertInstanceOf(MCQuestion.class, db.getRandomQuestion(QuestionType.MCQUESTION));
        assertInstanceOf(PromptQuestion.class, db.getRandomQuestion(QuestionType.PROMPTQUESTION));
        // instance with empty filter
        assertInstanceOf(MCQuestion.class, db.getRandomQuestion(QuestionType.MCQUESTION, new ArrayList<>()));
        assertInstanceOf(PromptQuestion.class, db.getRandomQuestion(QuestionType.PROMPTQUESTION, new ArrayList<>()));
        // instance with filter
        assertTrue(db.getRandomQuestion(QuestionType.MCQUESTION,
                        new ArrayList<>(List.of(QuestionTypeMC.DEFINITIONS)))
                .getQuestionTypes().contains(QuestionTypeMC.DEFINITIONS));
        assertTrue(db.getRandomQuestion(QuestionType.MCQUESTION,
                        new ArrayList<>(List.of(QuestionTypeMC.SWITCHING)))
                .getQuestionTypes().contains(QuestionTypeMC.SWITCHING));
        assertTrue(db.getRandomQuestion(QuestionType.MCQUESTION,
                        new ArrayList<>(List.of(QuestionTypeMC.SUBNETTING)))
                .getQuestionTypes().contains(QuestionTypeMC.SUBNETTING));
        assertTrue(db.getRandomQuestion(QuestionType.MCQUESTION,
                        new ArrayList<>(List.of(QuestionTypeMC.IPV4)))
                .getQuestionTypes().contains(QuestionTypeMC.IPV4));
        assertTrue(db.getRandomQuestion(QuestionType.MCQUESTION,
                        new ArrayList<>(List.of(QuestionTypeMC.IPV6)))
                .getQuestionTypes().contains(QuestionTypeMC.IPV6));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.WHICH_PREFIX_IN_SAME_SUBNET)))
                .getQuestionTypes().contains(QuestionTypePrompt.WHICH_PREFIX_IN_SAME_SUBNET));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.FIND_NET_ID)))
                .getQuestionTypes().contains(QuestionTypePrompt.FIND_NET_ID));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.FIND_IP_IN_SUBNET)))
                .getQuestionTypes().contains(QuestionTypePrompt.FIND_IP_IN_SUBNET));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.FIND_BROADCAST)))
                .getQuestionTypes().contains(QuestionTypePrompt.FIND_BROADCAST));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.FIND_NET_ID_AND_BROADCAST)))
                .getQuestionTypes().contains(QuestionTypePrompt.FIND_NET_ID_AND_BROADCAST));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.FIND_MASK)))
                .getQuestionTypes().contains(QuestionTypePrompt.FIND_MASK));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.FIND_OCTET)))
                .getQuestionTypes().contains(QuestionTypePrompt.FIND_OCTET));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.FIND_BINARY_OCTET)))
                .getQuestionTypes().contains(QuestionTypePrompt.FIND_BINARY_OCTET));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.NUMBER_OF_DEVICES)))
                .getQuestionTypes().contains(QuestionTypePrompt.NUMBER_OF_DEVICES));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.MAC_TO_IID)))
                .getQuestionTypes().contains(QuestionTypePrompt.MAC_TO_IID));
        assertTrue(db.getRandomQuestion(QuestionType.PROMPTQUESTION,
                        new ArrayList<>(List.of(QuestionTypePrompt.IID_TO_MAC)))
                .getQuestionTypes().contains(QuestionTypePrompt.IID_TO_MAC));

        //  currently not all filters are implemented in the db so this check does not work
        /*
        for (QuestionTypeMC enumeration : QuestionTypeMC.values())
            assertInstanceOf(MCQuestion.class, db.getRandomQuestion(QuestionType.MCQUESTION, new ArrayList<>(List.of(enumeration))));
        for (QuestionTypePrompt enumerations : QuestionTypePrompt.values())
            assertInstanceOf(PromptQuestion.class, db.getRandomQuestion(QuestionType.PROMPTQUESTION, new ArrayList<>(List.of(enumerations))));
        */
    }
    @Test
    public void getRandomQuestionNegative() {
        // null question type
        assertThrows(NullPointerException.class,
                () -> db.getRandomQuestion(null));
        // null filter
        assertThrows(NullPointerException.class,
                () -> db.getRandomQuestion(QuestionType.MCQUESTION, null));
        assertThrows(NullPointerException.class,
                () -> db.getRandomQuestion(QuestionType.PROMPTQUESTION, null));
        assertThrows(NullPointerException.class,
                () -> db.getRandomQuestion(null, null));
        // null type with filter
        assertThrows(NullPointerException.class,
                () -> db.getRandomQuestion(null, new ArrayList<>()));
        // invalid filters
        assertThrows(SQLException.class,
                () -> db.getRandomQuestion(QuestionType.MCQUESTION, new ArrayList<>(List.of(QuestionTypePrompt.WHICH_PREFIX_IN_SAME_SUBNET))));
        assertThrows(IllegalArgumentException.class,
                () -> db.getRandomQuestion(QuestionType.PROMPTQUESTION, new ArrayList<>(List.of(QuestionTypeMC.NAT))));
    }
    // endregion
}