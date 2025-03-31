package webInterface;

import db.I_DB_Controller;
import enumerations.db.QuestionType;
import enumerations.questions.*;
import exceptions.EnvironmentVariableNotFoundException;
import exceptions.InvalidIPException;
import ip.IPFactory;
import ip.I_IP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import questions.IQuestion;
import questions.*;
import static utility.ParameterValidation.validateParameters;
import visualizer.IVisualizer;
import visualizer.VisualizerFactory;
import utility.WebControllerUtility;
import java.sql.SQLException;
import java.util.*;
import static utility.WebControllerUtility.*;
import static enumerations.ip.IPType.IPv4;
import static enumerations.ip.IPType.IPv6;

/**
 * The interface between the Backend and the Frontend, it's responsible for the deserialization and serialization of Data
 */

@RestController
@RequestMapping("/api/v1")
public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);
    private final I_DB_Controller manager;
    private final String postReqFail = "POST Request failed: {}";
    private final String postReq = "POST Request to route: {}";
    private final String getReqFail = "GET Request failed: {}";
    private final String getReq = "GET Request to route: {}";

    @Autowired
    public WebController(final I_DB_Controller manager) {
        this.manager = manager;
    }

    //region IPv4 Calculator
    /**
     * Calculates information from the given IPv4-Address and it's prefix
     * @param jsonBody Keys: "ip" - the IP that needs to be calculated "prefix" - the prefix of the IP
     * @return JSON-Object | String values: prefix ,broadcast-address, netID, subnet mask binary, netID binary, subnet mask, broadcast binary, IP-Address, classification, IP binary |
     * boolean value: private
     */
    @CrossOrigin
    @PostMapping(path = "/ipv4-calculator")
    @ResponseStatus(HttpStatus.OK)
    public I_IP calculateIPv4(@RequestBody final Map<String, String> jsonBody) {
        try {
            log.info(postReq, "/ipv4-calculator");
            validateParameters(!jsonBody.containsKey("ip") && !jsonBody.containsKey("prefix"),
                    log, "Invalid JSON Object passed into /ipv4-calculator endpoint.");
            return IPFactory.createIPv4(jsonBody.get("ip"), jsonBody.get("prefix"));
        } catch (final IllegalArgumentException ia) {
            log.error(postReqFail, ia.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ia.getMessage());
        }
    }

    /**
     * Generates random IPv4-Addresses
     * @param amount The amount of Addresses that needs to be generated
     * @return JSON-Object | int value: prefix
     *                       String values: broadcast-address, netID, subnet mask binary, netID binary, subnet mask, broadcast binary, IP-Address, classification, IP binary |
     *                       boolean value: private
     */
    @CrossOrigin
    @PostMapping(path = "/ipv4-calculator/gen")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<I_IP> randomIPv4(@RequestBody final int amount) {
        try {
            log.info(postReq, "/ipv4-calculator/gen");
            return IPFactory.generateRandomIPAddresses(amount, IPv4);
        } catch (final InvalidIPException i) {
            log.error(postReqFail, i.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, i.getMessage());
        } catch (final IllegalArgumentException ia) {
            log.error(postReqFail, ia.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ia.getMessage());
        }
    }
    //endregion

    //region IPv6 Calculator

    /**
     * Calculates information from the given IPv6-Address
     * @param jsonBody Key: "ip" - the IP that needs to be calculated
     * @return JSON-Object | int value: prefix
     *                       String values: broadcast-address, netID, netID binary, subnet mask, broadcast binary, IP-Address, classification, IP binary |
     *                       boolean value: private
     */
    @CrossOrigin
    @PostMapping(path = "/ipv6-calculator")
    @ResponseStatus(HttpStatus.OK)
    public I_IP calculateIPv6(@RequestBody final Map<String, String> jsonBody) { // The parameter could be 'String ip' instead of a JSON object, but we elect to keep it as a JSON object to stay in line with the v4 routes implementation
        try {
            log.info(postReq, "/ipv6-calculator");
            validateParameters(!jsonBody.containsKey("ip"), log, "Invalid JSON Object passed into /ipv6-calculator endpoint.");
            return IPFactory.createIPv6(jsonBody.get("ip"));
        } catch (final IllegalArgumentException ia) {
            log.error(postReqFail, ia.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ia.getMessage());
        }
    }

    /**
     * Generates random IPv4-Addresses
     * @param amount The amount of Addresses that needs to be generated
     * @return JSON-Object | int value: prefix
     *                       String values: broadcast-address, netID, netID binary, subnet mask, broadcast binary, IP-Address, classification, IP binary |
     *                       boolean value: private
     */
    @CrossOrigin
    @PostMapping(path = "/ipv6-calculator/gen")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<I_IP> randomIPv6(@RequestBody final int amount) {
        try {
            log.info(postReq, "/ipv6-calculator/gen");
            return IPFactory.generateRandomIPAddresses(amount, IPv6);
        } catch (final InvalidIPException i) {
            log.error(postReqFail, i.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, i.getMessage());
        } catch (final IllegalArgumentException ia) {
            log.error(postReqFail, ia.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ia.getMessage());
        }
    }
    //endregion

    //region Subnet Visualizer

    /**
     * Calculates the net and host part of an IPv4-Address
     * @param jsonBody Keys | "ip" - the IP Address that needs to be visualized "prefix" - the prefix of the IP-Address
     * @return JSON-Body | String values: IP, host part binary, net part binary, IP binary, network ID, prefix
     */
    @CrossOrigin
    @PostMapping(path = "/subnet-visualizer/ipv4")
    @ResponseStatus(HttpStatus.OK)
    public IVisualizer iPv4Visualizer(@RequestBody final Map<String, String> jsonBody){
        try {
            log.info(postReq, "/subnet-visualizer/ipv4");
            validateParameters(!jsonBody.containsKey("ip") && !jsonBody.containsKey("prefix"), log, "Invalid JSON Object passed into /subnet-visualizer/ipv4 endpoint.");
            return VisualizerFactory.createVisualizerV4(IPFactory.createIPv4(jsonBody.get("ip"), jsonBody.get("prefix")));
        } catch (final IllegalArgumentException i) {
            log.error(postReqFail, i.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, i.getMessage());
        }
    }

    /**
     * Calculates the net and host part of an IPv6-Address
     * @param jsonBody Keys | "ip" - the IP Address that needs to be visualized "prefix" - the prefix of the IP-Address
     * @return JSON-Body | String values: IP, host part binary, net part binary, IP binary, network ID, prefix
     */
    @CrossOrigin
    @PostMapping(path = "/subnet-visualizer/ipv6")
    @ResponseStatus(HttpStatus.OK)
    public IVisualizer iPv6Visualizer(@RequestBody final Map<String, String> jsonBody){
        try {
            log.info(postReq, "/subnet-visualizer/ipv6");
            validateParameters(!jsonBody.containsKey("ip"), log, "Invalid JSON Object passed into /subnet-visualizer/ipv6 endpoint.");
            return VisualizerFactory.createVisualizerV6(IPFactory.createIPv6(jsonBody.get("ip")));
        } catch (final IllegalArgumentException i) {
            log.error(postReqFail, i.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, i.getMessage());
        }
    }
    //endregion

    //region Quiz

    /**
     * Gets random Quiz Question from the database filtered by the given parameters
     * @param jsonBody Keys | "layer" - OSI layer "difficulty" easy, medium or difficult, "questionType" - multiple filter params, for example NAT
     * @return JSON-Body | String values: explanation, question, questionID, osilayer, questiontypes
     */
    @CrossOrigin
    @PostMapping(path = "/quiz")
    @ResponseStatus(HttpStatus.OK)
    public IQuestion requestQuizQuestion(@RequestBody final Map<String, String> jsonBody) {
        try {
            log.info(postReq, "/quiz");
            if (jsonBody.isEmpty()) return manager.getRandomQuestion(QuestionType.MCQUESTION);

            final List<I_DB_Filter> filters = new ArrayList<>();
            final Set<String> keySet = jsonBody.keySet();
            WebControllerUtility.validateQuizKey(keySet);

            for (String key : keySet) {
                WebControllerUtility.getFilterFromKey(jsonBody, key, filters);
            }

            return manager.getRandomQuestion(QuestionType.MCQUESTION, filters);
        } catch (final EnvironmentVariableNotFoundException | SQLException sql) {
            log.error(postReqFail, sql.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, sql.getMessage());
        } catch (final IllegalArgumentException i) {
            log.error(postReqFail, i.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, i.getMessage());
        }
    }

    /**
     * Fetches all categories a multiple choice question might have
     * @return Map of categories and their values
     */
    @CrossOrigin
    @GetMapping(path = "/categories")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<I_DB_Filter>> getQuizCategories() {
        log.info(getReq, "/categories");
        Map<String, List<I_DB_Filter>> categories = new HashMap<>();
        List<Class<? extends Enum<? extends I_DB_Filter>>> list = List.of(Difficulty.class, OSILayer.class, QuestionTypeMC.class);
        for(Class<? extends Enum<? extends I_DB_Filter>> filter : list) {
            categories.put(formatEnumClassName(filter), getCategoriesFromDBFilter(filter));
        }
        return categories;
    }
    //endregion

    //region Practice Tool

    /**
     * Gets a random Prompt Question from the Database
     * @return JSON | String values: explanation, question, questionID, osi layer, questionTypes
     */
    @CrossOrigin
    @GetMapping(path = "/practice-tool")
    @ResponseStatus(HttpStatus.OK)
    public IQuestion requestPromptQuestion() {
        try {
            log.info(getReq, "/practice-tool");
            final IQuestionType tempType = drawRandomQuestionType();
            if (((QuestionTypePrompt) tempType).inputFieldAmount == 0) {
                return manager.getRandomQuestion(QuestionType.MCQUESTION, List.of(tempType));
            }
            return manager.getRandomQuestion(QuestionType.PROMPTQUESTION);
        } catch (final EnvironmentVariableNotFoundException | SQLException sql) {
            log.error(getReq, sql.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, sql.getMessage());
        } catch (IllegalArgumentException i) {
            log.error(getReq, i.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, i.getMessage());
        }
    }

    private IQuestionType drawRandomQuestionType() {
        final QuestionTypePrompt[] questionTypes = QuestionTypePrompt.values();
        return questionTypes[new Random().nextInt(questionTypes.length)];
    }

    /**
     * Resolves the answer from the user and checks if its right or wrong
     * @param promptResponse Keys | "questionData" - The whole prompt question that the user has answered "userAnswer" - the answer of the user
     * @return JSON | returns the questionData Object again and additionally the right answers and the user answer
     */
    @CrossOrigin
    @PostMapping(path = "/practice-tool/validate")
    @ResponseStatus(HttpStatus.OK)
    public boolean resolvePromptQuestion(@RequestBody final PromptResponse promptResponse) {
        try {
            log.info(postReq, "/practice-tool/validate");
            return promptResponse.getQuestionData().validateAnswer(promptResponse.getUserAnswers());
        } catch (IllegalArgumentException | IllegalStateException e) {
            log.error(postReqFail, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Response Class for User Answer Validation
    @SuppressWarnings("all") //Suppresses Warnings: Fields never assigned
    public static class PromptResponse {
        private PromptQuestion questionData; // The concrete type is neccessary here for Spring to be able to resolve the data to its correct constructor
        private Set<String> userAnswers;
        public PromptQuestion getQuestionData() {
            return questionData;
        }
        public Set<String> getUserAnswers() {
            return userAnswers;
        }
    }
    //endregion

    //region CRUD Dummy

    /**
     * Dummy implementation to demonstrate the update operation between the database and the frontend
     * @param increment a positive number that's greater than 0
     * @return boolean that acts as indicator that the update operation worked
     */
    @CrossOrigin
    @PutMapping(path = "/put")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean updateDBEntry(@RequestBody final int increment) {
        try {
            log.info("PUT Request to route: /put");
            if(increment < 1) throw new IllegalArgumentException("Increment must be greater than 0.");
            manager.incrementQuestionCounter(increment);
        } catch (IllegalArgumentException ia) {
            log.error("PUT Request failed: {}", ia.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ia.getMessage());
        }
        catch (final SQLException | EnvironmentVariableNotFoundException sql) {
            log.error("PUT Request failed: {}", sql.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, sql.getMessage());
        }
        return true;
    }

    /**
     * Dummy implementation to demonstrate the delete operation between the database and the frontend
     * @param tableName Name of the table that needs to be deleted (unnecessaryTable is the only one that's possible to delete)
     * @return boolean that acts as indicator that the delete operation worked
     */
    @CrossOrigin
    @DeleteMapping(path = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteDBEntry(@RequestBody final String tableName) {
        try {
            log.info("DELETE Request to route: /delete");
            validateParameters(tableName == null || tableName.isEmpty(), log, "No valid table name passed in.");
            manager.deleteTheUnnecessaryTable(tableName);
        } catch (IllegalArgumentException ia) {
            log.error("DELETE Request failed: {}", ia.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ia.getMessage());
        }
        catch (final SQLException | EnvironmentVariableNotFoundException sql) {
            log.error("DELETE Request failed: {}", sql.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, sql.getMessage());
        }
        return true;
    }
    //endregion

}