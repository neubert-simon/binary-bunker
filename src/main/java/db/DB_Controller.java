package db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import enumerations.db.QuestionType;
import enumerations.questions.*;
import exceptions.EnvironmentVariableNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import questions.IQuestion;
import utility.ParameterValidation;


/**
 * top level DB. Used for Instantiation of PersistenceManager and calling its methods.
 */
@Component
public class DB_Controller implements I_DB_Controller {


    //region singleton
    private static final DB_Controller instance = new DB_Controller();
    public static DB_Controller getInstance(){return instance;}
    private DB_Controller() {
       // log.info("object created: {}",DB_Controller.class.getSimpleName());
    }
    //endregion

    private static final Logger log = LoggerFactory.getLogger(DB_Controller.class);

    // region getRandom
    /**
     * Pull a random IQuestion based on listed filters
     * @param filter Could be: OSILayer, Difficulty, QuestionTypeMC - Build Select statements to filter for an arbitrary combination of these
     * @return IQuestion via createMCQuestion or createPromptQuestion from QuestionFactory
     */
    @Override
    public IQuestion getRandomQuestion(final QuestionType questionType,final List<I_DB_Filter> filter) throws EnvironmentVariableNotFoundException, SQLException, IllegalArgumentException {

        //check questionType = null
        final QuestionType qTypeNotNull = Objects.requireNonNull(questionType,"You must provide valid questionType! Null isn't allowed.");
        //check filter = null
        final List<I_DB_Filter> filterNotNull = Objects.requireNonNull(filter,"You must provide valid filter! Null isn't allowed.");
        //check if list contains an item = null
        final java.util.function.Predicate<List<I_DB_Filter>> predicateNotNull = l -> l.contains(null);

        if (predicateNotNull.test(filterNotNull)) {
            throw new IllegalArgumentException("filter contains null filter!");
        }
        else
        {
            log.debug("method getRandomQuestion called with parameters questionType {}", questionType);
            return PersistenceManager.getInstance().getRandomQuestion(qTypeNotNull, filterNotNull);
        }
    }

    /**
     * Pull a random IQuestion, without any filters
     * uses an empty list of I_DB_Filter same as above to minimal code duplication
     * @return Random IQuestion
     */
    @Override
    public IQuestion getRandomQuestion(final QuestionType questionType) throws EnvironmentVariableNotFoundException, SQLException ,IllegalArgumentException {
        //check questionType = null
        final QuestionType qTypeNotNull = Objects.requireNonNull(questionType,"You must provide valid questionType! Null isn't allowed.");
        log.debug("method getRandomQuestion called with parameters questionType {}", questionType);
        return PersistenceManager.getInstance().getRandomQuestion(qTypeNotNull, new ArrayList<>());
    }
    // endregion

    // region CRUD
    @Override
    public void createQuestion(final QuestionType questionType, final int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException {
        // making sure questionType, characteristics and json are not null
        ParameterValidation.validateParameters(!(questionType != null && characteristics != null && json != null), log, "Null parameter");
        log.debug("method createQuestion called with parameters questionType {}, id {}, characteristics {}, json {}", questionType, id, characteristics, json);
        PersistenceManager.getInstance().createQuestion(questionType, id, characteristics, json);
    }
    @Override
    public IQuestion readQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        // making sure questionType is not null
        ParameterValidation.validateParameters(questionType == null, log, "Null parameter");
        log.debug("method readQuestion called with parameters questionType {}, id {}", questionType, id);
        return PersistenceManager.getInstance().readQuestion(questionType, id);
    }
    @Override
    public void updateQuestion(final QuestionType questionType, final int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException {
        // making sure questionType, characteristics and json are not null
        ParameterValidation.validateParameters(questionType == null || json == null || characteristics == null, log, "Null parameter");
        assert characteristics != null;
        assert json != null;
        ParameterValidation.validateParameters(characteristics.isEmpty() && json.isEmpty(), log, "no values to update in characteristics List or json String");
        log.debug("method updateQuestion called with parameters questionType {}, id {}, characteristics {}, json {}", questionType, id, characteristics, json);
        PersistenceManager.getInstance().updateQuestion(questionType, id, characteristics, json);
    }
    @Override
    public void updateQuestion(final QuestionType questionType, final int id, final List<I_DB_Filter> characteristics) throws SQLException, EnvironmentVariableNotFoundException {
        // making sure questionType and characteristics are not null
        ParameterValidation.validateParameters(questionType == null || characteristics == null, log, "Null parameter");
        assert characteristics != null;
        ParameterValidation.validateParameters(characteristics.isEmpty(), log, "Empty characteristics list");
        log.debug("method updateQuestion called with parameters questionType {}, id {}, characteristics {}", questionType, id, characteristics);
        PersistenceManager.getInstance().updateQuestion(questionType, id, characteristics, "");
    }
    @Override
    public void deleteQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException {
        // making sure questionType is not null
        ParameterValidation.validateParameters(questionType == null, log, "Null parameter");
        log.debug("method deleteQuestion called with parameters questionType {}, id {}", questionType, id);
        PersistenceManager.getInstance().deleteQuestion(questionType, id);
    }
    // endregion

    //region dummy methods
    //region increment
    @Override
     public void incrementQuestionCounter(final int incrementBy) throws EnvironmentVariableNotFoundException, SQLException {
        //checks if the integer is valid
        switch (Integer.compare(incrementBy, 0)) {
            case 0:
                throw new IllegalArgumentException("increment by 0 is not allowed!");
            case -1:
                throw new IllegalArgumentException("you can't increment by negative values!");
            case 1:
                log.debug("method incrementQuestionCounter called with parameters incrementBy {}", incrementBy);
                PersistenceManager.getInstance().incrementQuestionCounter(incrementBy);
                break;
        }
     }
     @Override
     public int getCounter() throws EnvironmentVariableNotFoundException, SQLException {
         log.debug("method getCounter called");
         return PersistenceManager.getInstance().getCounter();
     }
    //endregion
    //region unnecessaryTable
    @Override
    public void createTheUnnecessaryTable(final String tableName) throws EnvironmentVariableNotFoundException, SQLException {
        final String tableNameNotNull = Objects.requireNonNull(tableName, "You must provide a valid tableName! Null isn't allowed.");
        // make sure tableName is unnecessaryTable
        if (tableNameNotNull.isEmpty()) {
            throw new IllegalArgumentException("tableName cannot be empty!");
        }
        if (!tableNameNotNull.equals("unnecessaryTable")) {
            throw new IllegalArgumentException("You can only create the unnecessary table!");
        }
        log.debug("method incrementQuestionCounter called with parameters tableName {}", tableName);
        PersistenceManager.getInstance().createTheUnnecessaryTable();
    }
    @Override
    public void deleteTheUnnecessaryTable(final String tableName) throws EnvironmentVariableNotFoundException, SQLException {
        final String tableNameNotNull = Objects.requireNonNull(tableName, "You must provide a valid tableName! Null isn't allowed.");
        // make sure tableName is unnecessaryTable
        if (tableNameNotNull.isEmpty()) {
            throw new IllegalArgumentException("tableName cannot be empty!");
        }
        if (!tableNameNotNull.equals("unnecessaryTable")) {
            throw new IllegalArgumentException("You can only delete the unnecessary table!");
        }
        log.debug("method incrementQuestionCounter called with parameters tableName {}", tableName);
        PersistenceManager.getInstance().deleteTheUnnecessaryTable();
    }
    //endregion
    //endregion

}

