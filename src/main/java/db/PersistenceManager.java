package db;

import com.fasterxml.jackson.core.JsonProcessingException;
import enumerations.db.DbTypes;
import enumerations.db.QuestionType;
import enumerations.questions.I_DB_Filter;
import exceptions.EnvironmentVariableNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import questions.IQuestion;

import java.sql.SQLException;
import java.util.List;

/**
 * provides the basis for depositing additional databases
 * controls the usage of the dbType(different dbms)
 */
public final class PersistenceManager {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);
    private static PersistenceManager instance;
    private final IDbManager dbManager;

    private PersistenceManager(){
        //dynamic instantiation for different DbTypes(DBMS)
        log.debug("Using dbType PostgreSQL");
        dbManager = DbManagerFactory.getInstance(DbTypes.PostgreSQL);
    }

    //region getRandom
    /**
     * using dbManager dynamically for the current dbType.<br>
     * Returns a random MCQuestion object from the database
     * @return IQuestion obj
     * @throws EnvironmentVariableNotFoundException a necessary environment var wasn't found.
     */
    IQuestion getRandomQuestion(final QuestionType questionType, final List<I_DB_Filter> filterList) throws EnvironmentVariableNotFoundException, SQLException {
        return dbManager.getRandomQuestion(questionType, filterList);
    }
    //endregion

    //region CRUD

    /**
     * using dbManager dynamically for the current dbType.<br>
     * Creates and inserts a new question into the database
     * @param questionType Type of the question that is to be inserted into the database<br>
     * @param id The id the to be inserted question should have<br>
     * @param characteristics The characteristics the to be inserted question should have
     *                        <p>MCQuestions must contain:</p>
     *                             <ul style="padding-left: 20px; margin: 0;">
     *                               <li>exactly one OSILayer</li>
     *                               <li>exactly one Difficulty</li>
     *                               <li>at least one FiltertypeMC</li></ul>
     *                         <p>PromptQuestions must contain:</p>
     *                             <ul style="padding-left: 20px; margin: 0;">
     *                               <li>exactly one OSILayer</li>
     *                               <li>exactly one FiltertypePrompt</li></ul>
     * @param json The json body the question should contain, for the question to be usable it must follow the following formats<br>
     *             <p>Valid MCQuestion json objects look like this:</p>
     *              <ul style="list-style-type: none; padding-left: 20px; margin: 10px;">
     *                   <li>{</li>
     *                   <li>"question": "questionValue",</li>
     *                   <li>"explanation": "explanationValue",</li>
     *                   <li>"allAnswers":<ul style="list-style-type: none; padding-left: 20px; margin: 0px;">
     *                                 <li>{"1": "answerOption_1",</li>
     *                                 <li>"2": "answerOption_2",</li>
     *                                 <li>"n": "answerOption_n"},</li></ul></li>
     *                   <li>"correctAnswers": ["correctAnswerKey_1", "correctAnswerKey_2", "correctAnswerKey_n"]</li>
     *                   <li>}</li></ul>
     *              <p>Valid PromptQuestion json objects look like this:</p>
     *              <ul style="list-style-type: none; padding-left: 20px; margin: 10px;">
     *                   <li>{</li>
     *                   <li>"question": "questionValue",</li>
     *                   <li>"explanation": "explanationValue"</li>
     *                   <li>}</li></ul>
     */
    void createQuestion(final QuestionType questionType, int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException {
        dbManager.createQuestion(questionType, id, characteristics, json);
    }
    /**
     * using dbManager dynamically for the current dbType.<br>
     * Pulls the question with the given <i>id</i> from the database
     * @param questionType Type of the question that is to be pulled out of the database
     * @param id The id of the question that is to be pulled out of the database
     * @return IQuestion object constructed like the question that was pulled
     */
    public IQuestion readQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        return dbManager.readQuestion(questionType, id);
    }
    /**
     * using dbManager dynamically for the current dbType.<br>
     * Updates the given characteristics and/or the json body of a question in the database
     * @param questionType Type of the question that is to be changed in the database<br>
     * @param id The id of to be changed question in the database<br>
     * @param characteristics The characteristics the to be inserted question can contain any number of I_DB_Filter Enums.<br>
     *                        <p>MCQuestions must contain:</p>
     *                             <ul style="padding-left: 20px; margin: 0;">
     *                               <li>exactly one OSILayer</li>
     *                               <li>exactly one Difficulty</li>
     *                               <li>at least one FiltertypeMC</li></ul>
     *                         <p>PromptQuestions must contain:</p>
     *                             <ul style="padding-left: 20px; margin: 0;">
     *                               <li>exactly one OSILayer</li>
     *                               <li>exactly one FiltertypePrompt</li></ul>
     * @param json The new json body the question should contain, empty String ("") if json body should stay the same.<br>
     *             For the question to be usable it must follow the following formats<br>
     *             <p>Valid MCQuestion json objects look like this:</p>
     *              <ul style="list-style-type: none; padding-left: 20px; margin: 10px;">
     *                   <li>{</li>
     *                   <li>"question": "questionValue",</li>
     *                   <li>"explanation": "explanationValue",</li>
     *                   <li>"allAnswers":<ul style="list-style-type: none; padding-left: 20px; margin: 0px;">
     *                                 <li>{"1": "answerOption_1",</li>
     *                                 <li>"2": "answerOption_2",</li>
     *                                 <li>"n": "answerOption_n"},</li></ul></li>
     *                   <li>"correctAnswers": ["correctAnswerKey_1", "correctAnswerKey_2", "correctAnswerKey_n"]</li>
     *                   <li>}</li></ul>
     *              <p>Valid PromptQuestion json objects look like this:</p>
     *              <ul style="list-style-type: none; padding-left: 20px; margin: 10px;">
     *                   <li>{</li>
     *                   <li>"question": "questionValue",</li>
     *                   <li>"explanation": "explanationValue"</li>
     *                   <li>}</li></ul>
     */
    public void updateQuestion(final QuestionType questionType, final int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException {
        dbManager.updateQuestion(questionType, id, characteristics, json);
    }
    /**
     * using dbManager dynamically for the current dbType.<br>
     * Deletes one question from the database with the same id as the int id given
     * @param questionType Type of the question that is to be deleted from teh database
     * @param id The id of the to be deleted question from the database
     */
    public void deleteQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException {
        dbManager.deleteQuestion(questionType, id);
    }
    //endregion

    //region dummy methods
    //region increment
    /**
     * Dummy method to demonstrate CRUD capabilities
     * @param incrementBy Amount that an integer is to be updated by in the database
     */
    void incrementQuestionCounter(final int incrementBy) throws EnvironmentVariableNotFoundException, SQLException {
        dbManager.incrementQuestionCounter(incrementBy);
    }

    /**
     * Dummy method to demonstrate CRUD capabilities
     * @return current counter
     */
    int getCounter() throws SQLException, EnvironmentVariableNotFoundException {
       return dbManager.getCounter();
    }
    //endregion
    //region unnecessaryTable
    /**
     * Dummy method to demonstrate CRUD capabilities
     */
    void deleteTheUnnecessaryTable() throws SQLException, EnvironmentVariableNotFoundException {
        dbManager.deleteTheUnnecessaryTable();
    }
    /**
     * Dummy method to demonstrate CRUD capabilities
     */
    void createTheUnnecessaryTable() throws SQLException, EnvironmentVariableNotFoundException {
        dbManager.createTheUnnecessaryTable();
    }
    //endregion
    //endregion

    //region singleton pattern
     static PersistenceManager getInstance(){
        if (instance == null){
            instance = new PersistenceManager();
        }
        return instance;
    }
    //endregion

}
