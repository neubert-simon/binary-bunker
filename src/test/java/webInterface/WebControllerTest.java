package webInterface;
import db.I_DB_Controller;
import enumerations.db.QuestionType;
import enumerations.questions.*;
import exceptions.EnvironmentVariableNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import questions.IQuestion;
import questions.QuestionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import java.util.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private I_DB_Controller mockManager;
    @MockBean
    private IQuestion mockQuestion;
    @Autowired
    private ObjectMapper objectMapper;

    private final String TEST_DIFFICULTY = "EASY";
    private final String TEST_OSILAYER = "L3_NETWORK";
    private final String TEST_TYPE = "DEFINITIONS";

    //region IPv4 Calculator & Generator Routes
    @Test
    public void testIPv4CalculatorPositive() throws Exception {
        Map<String, Object> requestBody = Map.of(
                "ip", "192.68.0.1",
                "prefix", "24"
        );

        ResultActions result = mockMvc.perform(
                post("/api/v1/ipv4-calculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.ip").value("192.68.0.1"))
                .andExpect(jsonPath("$.private").value(false))
                .andExpect(jsonPath("$.Class").value("C"))
                .andExpect(jsonPath("$.ip_binary").value("11000000.01000100.00000000.00000001"))
                .andExpect(jsonPath("$.subnet.prefix").value(24))
                .andExpect(jsonPath("$.subnet.broadcast").value("192.68.0.255"))
                .andExpect(jsonPath("$.subnet.netID").value("192.68.0.0"))
                .andExpect(jsonPath("$.subnet.broadcast_binary").value("11000000.01000100.00000000.11111111"))
                .andExpect(jsonPath("$.subnet.mask_binary").value("11111111.11111111.11111111.00000000"))
                .andExpect(jsonPath("$.subnet.mask").value("255.255.255.0"))
                .andExpect(jsonPath("$.subnet.netID_binary").value("11000000.01000100.00000000.00000000"));
    }

    @Test
    public void testIPv4CalculatorNegative() throws Exception {
        Map<String, String> requestBody = Map.of(
                "ip", "1234.1234.1234",
                "prefix", "24"
        );

        mockMvc.perform(post("/api/v1/ipv4-calculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testIPv4GeneratorPositive() throws Exception {
        int requestValue = 3;

        ResultActions result = mockMvc.perform(
                post("/api/v1/ipv4-calculator/gen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValue))
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(requestValue))
                .andExpect(jsonPath("$[0].ip").isString())
                .andExpect(jsonPath("$[0].private").isBoolean())
                .andExpect(jsonPath("$[0].Class").isString())
                .andExpect(jsonPath("$[0].ip_binary").isString())
                .andExpect(jsonPath("$[0].subnet.prefix").isNumber())
                .andExpect(jsonPath("$[0].subnet.netID").isString())
                .andExpect(jsonPath("$[0].subnet.broadcast").isString())
                .andExpect(jsonPath("$[0].subnet.mask").isString())
                .andExpect(jsonPath("$[0].subnet.broadcast_binary").isString())
                .andExpect(jsonPath("$[0].subnet.netID_binary").isString());
    }

    @Test
    public void testIPv4GeneratorNegative() throws Exception {
        int requestValue = -1;

        mockMvc.perform(post("/api/v1/ipv4-calculator/gen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValue)))
                .andExpect(status().isBadRequest());

    }
    //endregion

    //region IPv6 Calculator & Generator Routes
    @Test
    public void testIPv6CalculatorPositive () throws Exception {
        Map<String, String> requestBody = Map.of(
                "ip", "2001:0000:130F:0000:0000:09C0:876A:130B"
        );

        ResultActions result = mockMvc.perform(
                post("/api/v1/ipv6-calculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.ip").value("2001:0000:130F:0000:0000:09C0:876A:130B"))
                .andExpect(jsonPath("$.private").isBoolean())
                .andExpect(jsonPath("$.private").value(false))
                .andExpect(jsonPath("$.ip_binary").value("0010000000000001:0000000000000000:0001001100001111:0000000000000000:0000000000000000:0000100111000000:1000011101101010:0001001100001011"))
                .andExpect(jsonPath("$.subnet.prefix").value(64))
                .andExpect(jsonPath("$.subnet.netID").value("2001:00:130F:00:00:00:00:00"))
                .andExpect(jsonPath("$.subnet.broadcast").value("2001:00:130F:00:FFFF:FFFF:FFFF:FFFF"))
                .andExpect(jsonPath("$.subnet.maskDecimal").value("FFFF:FFFF:FFFF:FFFF:00:00:00:00"))
                .andExpect(jsonPath("$.subnet.broadcast_binary").value("0010000000000001:0000000000000000:0001001100001111:0000000000000000:1111111111111111:1111111111111111:1111111111111111:1111111111111111"))
                .andExpect(jsonPath("$.subnet.netID_binary").value("0010000000000001:0000000000000000:0001001100001111:0000000000000000:0000000000000000:0000000000000000:0000000000000000:0000000000000000"));
    }

    @Test
    public void testIPv6CalculatorNegative() throws Exception {
        Map<String, String> requestBody = Map.of(
                "ip", "2001:0000:130F:0000:0000:09C0:876A:130G"
        );

        mockMvc.perform(post("/api/v1/ipv6-calculator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testIPv6GeneratorPositive() throws Exception {
        int requestValue = 3;

        ResultActions result = mockMvc.perform(
                post("/api/v1/ipv6-calculator/gen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValue))
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(requestValue))
                .andExpect(jsonPath("$[0].ip").isString())
                .andExpect(jsonPath("$[0].private").isBoolean())
                .andExpect(jsonPath("$[0].ip_binary").isString())
                .andExpect(jsonPath("$[0].subnet.prefix").isNumber())
                .andExpect(jsonPath("$[0].subnet.netID").isString())
                .andExpect(jsonPath("$[0].subnet.broadcast").isString())
                .andExpect(jsonPath("$[0].subnet.maskDecimal").isString())
                .andExpect(jsonPath("$[0].subnet.broadcast").isString())
                .andExpect(jsonPath("$[0].subnet.netID_binary").isString());
    }

    @Test
    public void testIPv6GeneratorNegative() throws Exception {
        int requestValue = -1;

        mockMvc.perform(post("/api/v1/ipv6-calculator/gen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValue)))
                .andExpect(status().isBadRequest());

    }
    //endregion

    //region Visualizer Routes
    @Test
    public void testVisualizerV4Positive() throws Exception {
        Map<String, String> requestBody = Map.of(
                "ip", "192.68.0.1",
                "prefix", "24"
        );

        ResultActions result = mockMvc.perform(
                post("/api/v1/subnet-visualizer/ipv4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.ip_decimal").value("192.68.0.1"))
                .andExpect(jsonPath("$.prefix").value("24"))
                .andExpect(jsonPath("$.net_part_decimal").value("192.68.0.0"))
                .andExpect(jsonPath("$.ip_binary").value("11000000.01000100.00000000.00000001"))
                .andExpect(jsonPath("$.net_part_binary").value("11000000.01000100.00000000"))
                .andExpect(jsonPath("$.host_part_binary").value(".00000001"));
    }

    @Test
    public void testVisualizerV4Negative() throws Exception {
        Map<String, String> requestBody = Map.of(
                "ip", "-123.1.23.22",
                "prefix", "A"
        );

        mockMvc.perform(post("/api/v1/subnet-visualizer/ipv4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testVisualizerV6Positive() throws Exception {
        Map<String, String> requestBody = Map.of(
                "ip", "2001:0000:130F:0000:0000:09C0:876A:130B"
        );

        ResultActions result = mockMvc.perform(
                post("/api/v1/subnet-visualizer/ipv6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.ip_decimal").value("2001:0000:130F:0000:0000:09C0:876A:130B"))
                .andExpect(jsonPath("$.prefix").value("64"))
                .andExpect(jsonPath("$.net_part_decimal").value("2001:00:130F:00:00:00:00:00"))
                .andExpect(jsonPath("$.ip_binary").value("0010000000000001:0000000000000000:0001001100001111:0000000000000000:0000000000000000:0000100111000000:1000011101101010:0001001100001011"))
                .andExpect(jsonPath("$.net_part_binary").value("0010000000000001:0000000000000000:0001001100001111:0000000000000000"))
                .andExpect(jsonPath("$.host_part_binary").value(":0000000000000000:0000100111000000:1000011101101010:0001001100001011"));
    }

    @Test
    public void testVisualizerV6Negative() throws Exception {
        Map<String, String> requestBody = Map.of(
                "ip", "AGGG:00001:130F:0000:0000:09C0:876A:130G"
        );

        mockMvc.perform(post("/api/v1/subnet-visualizer/ipv6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
    }
    //endregion

    //region Practice Tool Routes
    @Test
    public void testRequestPromptQuestionPositive() throws Exception {
        setMockQuestion(QuestionType.PROMPTQUESTION, "1");
        when(mockManager.getRandomQuestion(QuestionType.PROMPTQUESTION)).thenReturn(mockQuestion);
        mockMvc.perform(get("/api/v1/practice-tool"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionID", is("PR1")))
                .andExpect(jsonPath("$.question", is("Question1")))
                .andExpect(jsonPath("$.explanation", is("Explanation1")))
                .andExpect(jsonPath("$.questionTypes", is(List.of(QuestionTypePrompt.FIND_NET_ID.toString()))))
                .andExpect(jsonPath("$.osilayer", is(OSILayer.L1_PHYSICAL.toString())))
                .andExpect(jsonPath("$.inputFieldAmount", is(1)))
                .andExpect(jsonPath("$.questionParameters", is(List.of("qp1"))))
                .andExpect(jsonPath("$.answerParameters", is(List.of("ap1"))));
    }

    @Test
    public void testRequestPromptQuestionNegativeEnv() throws Exception {
        when(mockManager.getRandomQuestion(QuestionType.PROMPTQUESTION)).thenThrow(new EnvironmentVariableNotFoundException("Env error"));
        mockMvc.perform(get("/api/v1/practice-tool")).andExpect(status().isInternalServerError());
    }

    @Test
    public void testRequestPromptQuestionNegativeSql() throws Exception {
        when(mockManager.getRandomQuestion(QuestionType.PROMPTQUESTION)).thenThrow(new SQLException("Env error"));
        mockMvc.perform(get("/api/v1/practice-tool")).andExpect(status().isInternalServerError());
    }

    @Test
    public void testResolvePromptQuestionPositive() throws Exception {

        Map<String, Object> content = new HashMap<>();
        Map<String, Object> questionData = new HashMap<>();
        questionData.put("questionID", "2");
        questionData.put("question", "Find the Net-ID for this IP-Address and prefix:");
        questionData.put("explanation", "The Net-ID can be calculated by converting the IP to binary and nulling every host bit (every bit after the amount of bits signified by the prefix)");
        questionData.put("questionTypes", new String[]{"FIND_NET_ID"});
        questionData.put("osilayer", "L3_NETWORK");
        questionData.put("inputFieldAmount", 1);
        questionData.put("questionParameters",new String[]{"24", "10.202.127.183"});
        questionData.put("answerParameters", new String[]{"10.202.127.0"});
        content.put("questionData", questionData);

        Set<String> userAnswers = new HashSet<>();
        userAnswers.add("10.202.127.0");
        content.put("userAnswers", userAnswers);
        ResultActions result = mockMvc.perform(
                post("/api/v1/practice-tool/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
        );

        result.andExpect(status().isOk())
              .andExpect(content().string("true"));

        userAnswers.clear();
        userAnswers.add("10.202.127.1");
        content.put("userAnswers", userAnswers);
        result = mockMvc.perform(
                post("/api/v1/practice-tool/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
        );

        result.andExpect(status().isOk())
              .andExpect(content().string("false"));
    }

    @Test
    public void testResolvePromptQuestionNegative() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/v1/practice-tool/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("content"))
        );
        result.andExpect(status().isBadRequest());

        Map<String, String> content = Map.of("questionData", "invalidQuestion", "userAnswers", "answer");
        result = mockMvc.perform(
                post("/api/v1/practice-tool/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
        );
        result.andExpect(status().isBadRequest());
    }
    //endregion

    //region Quiz Routes
    @Test
    public void testRequestQuizQuestionPositive() throws Exception {

        setMockQuestion(QuestionType.MCQUESTION, "1");
        when(mockManager.getRandomQuestion(QuestionType.MCQUESTION)).thenReturn(mockQuestion);

        ResultActions result = mockMvc.perform(
                post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.questionID", is("MC1")))
                .andExpect(jsonPath("$.question", is("Question1")))
                .andExpect(jsonPath("$.explanation", is("Explanation1")))
                .andExpect(jsonPath("$.correctAnswerKeys", is(List.of("a1")))) // Due to the JSON conversion, this is treated as a list. We check if the values are correct
                .andExpect(jsonPath("$.allAnswers", is(Map.of("a1", "Answer1"))))
                .andExpect(jsonPath("$.osilayer", is(OSILayer.L1_PHYSICAL.toString()))) // Same reasoning for the toString().call as in the comment above
                .andExpect(jsonPath("$.difficulty", is(Difficulty.EASY.toString())))
                .andExpect(jsonPath("$.questionTypes", is(List.of(QuestionTypeMC.NAT.toString()))));

        setMockQuestion(QuestionType.MCQUESTION, "2");
        List<I_DB_Filter> list = new ArrayList<>(List.of(QuestionTypeMC.DEFINITIONS, OSILayer.L3_NETWORK, Difficulty.EASY));
        when(mockManager.getRandomQuestion(QuestionType.MCQUESTION, list)).thenReturn(mockQuestion);

        Map<String, String> content = new HashMap<>();
        content.put("DIFFICULTY", TEST_DIFFICULTY);
        content.put("OSILAYER", TEST_OSILAYER);
        content.put("QUESTIONTYPEMC", TEST_TYPE);

        result = mockMvc.perform(
                post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.questionID", is("MC2")))
                .andExpect(jsonPath("$.question", is("Question2")))
                .andExpect(jsonPath("$.explanation", is("Explanation2")))
                .andExpect(jsonPath("$.correctAnswerKeys", is(List.of("a2")))) // Due to the JSON conversion, this is treated as a list. We check if the values are correct
                .andExpect(jsonPath("$.allAnswers", is(Map.of("a2", "Answer2"))))
                .andExpect(jsonPath("$.osilayer", is(OSILayer.L1_PHYSICAL.toString()))) // Same reasoning for the toString().call as in the comment above
                .andExpect(jsonPath("$.difficulty", is(Difficulty.EASY.toString())))
                .andExpect(jsonPath("$.questionTypes", is(List.of(QuestionTypeMC.NAT.toString()))));
    }

    @Test
    public void testRequestQuizQuestionNegative() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("nonsense"))
        );
        result.andExpect(status().isBadRequest());

        result = mockMvc.perform(
                post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("1"))
        );
        result.andExpect(status().isBadRequest());

        result = mockMvc.perform(
                post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("invalidKey", "invalidValue")))
        );
        result.andExpect(status().isBadRequest());

        List<I_DB_Filter> list = new ArrayList<>(List.of(Difficulty.EASY));
        when(mockManager.getRandomQuestion(QuestionType.MCQUESTION, list)).thenThrow(new SQLException("SQL error"));

        mockMvc.perform(
                post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("difficulty", "EASY")))
        ).andExpect(status().isInternalServerError());

        list = new ArrayList<>(List.of(OSILayer.L1_PHYSICAL));
        when(mockManager.getRandomQuestion(QuestionType.MCQUESTION, list)).thenThrow(new EnvironmentVariableNotFoundException("Env error"));
        mockMvc.perform(
                post("/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("osilayer", "L1_PHYSICAL")))
        ).andExpect(status().isInternalServerError());

    }

    private void setMockQuestion(QuestionType type, String id) {
        switch (type) {
            case MCQUESTION -> mockQuestion = QuestionFactory.createMCQuestion(
                    "MC" + id,
                    "Question" + id,
                    "Explanation" + id,
                    Set.of("a" + id),
                    Map.of("a" + id, "Answer" + id),
                    OSILayer.L1_PHYSICAL,
                    Difficulty.EASY,
                    List.of(QuestionTypeMC.NAT));
            case PROMPTQUESTION -> mockQuestion = QuestionFactory.createPromptQuestion(
                    "PR" + id,
                    "Question" + id,
                    "Explanation" + id,
                    Set.of(QuestionTypePrompt.FIND_NET_ID),
                    OSILayer.L1_PHYSICAL,
                    1,
                    Set.of("qp" + id),
                    Set.of("ap" + id)
            );
        }
    }
    //endregion

    //region CRUD Dummy Tests
    @Test
    public void testUpdateDBEntryPositive() throws Exception {
        mockMvc.perform(
                    put("/api/v1/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1")
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("true"));
    }

    @Test
    public void testUpdateDBEntryNegative() throws Exception {
        mockMvc.perform(
                    put("/api/v1/put")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("-1")
                )
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        put("/api/v1/put")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("0")
                )
                .andExpect(status().isBadRequest());

        doThrow(new SQLException("SQL error")).when(mockManager).incrementQuestionCounter(1);
        mockMvc.perform(
                        put("/api/v1/put").contentType(MediaType.APPLICATION_JSON).content("1"))
                .andExpect(status().isInternalServerError());

        doThrow(new EnvironmentVariableNotFoundException("Env error")).when(mockManager).incrementQuestionCounter(2);
        mockMvc.perform(
                        put("/api/v1/put").contentType(MediaType.APPLICATION_JSON).content("2"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeleteDBEntryPositive() throws Exception {
        mockMvc.perform(
                    delete("/api/v1/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"content\"")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testDeleteDBEntryNegative() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("")
                )
                .andExpect(status().isBadRequest());

        when(mockMvc.perform(
                delete("/api/v1/delete").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString("sql"))))
                .thenThrow(new SQLException("SQL error"));
        mockMvc.perform(
                delete("/api/v1/delete").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString("sql")))
                .andExpect(status().isInternalServerError());

        when(mockMvc.perform(
                delete("/api/v1/delete").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString("env"))))
                .thenThrow(new EnvironmentVariableNotFoundException("Env error"));
        mockMvc.perform(
                delete("/api/v1/delete").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString("env")))
                .andExpect(status().isInternalServerError());

    }
    //endregion

}
