package db;
import com.fasterxml.jackson.core.JsonProcessingException;
import enumerations.db.QuestionType;
import enumerations.questions.I_DB_Filter;
import exceptions.EnvironmentVariableNotFoundException;
import questions.IQuestion;

import java.sql.SQLException;
import java.util.List;

public interface IDbManager
{
    // region getRandom
    /**
     * Pull a random IQuestion from the database based on listed filters
     * @param questionType Type of the question that is to be pulled from the database
     * @param filterList List of filters to be applied to search query
     * @return Random IQuestion based on the passed in categories
     */
    IQuestion getRandomQuestion(final QuestionType questionType, final List<I_DB_Filter> filterList) throws SQLException, EnvironmentVariableNotFoundException;
    // endregion

    // region CRUD ops
    /**
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
    void createQuestion(final QuestionType questionType, int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException;
    /**
     * Pulls the question with the given <i>id</i> from the database
     * @param questionType Type of the question that is to be pulled out of the database
     * @param id The id of the question that is to be pulled out of the database
     * @return IQuestion object constructed like the question that was pulled
     */
    IQuestion readQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException;
    /**
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
    void updateQuestion(final QuestionType questionType, final int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException;
    /**
     * Deletes one question from the database with the same id as the int id given
     * @param questionType Type of the question that is to be deleted from teh database
     * @param id The id of the to be deleted question from the database
     */
    void deleteQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException;
    // endregion

    // region dummy methods
    // region increment
    /**
     * Dummy method to demonstrate CRUD capabilities
     * @param incrementBy by how much the number should be incremented
     */
    void incrementQuestionCounter(int incrementBy) throws SQLException, EnvironmentVariableNotFoundException;

    /**
     * Dummy method to demonstrate CRUD capabilities
     * @return value of the counter
     */
    int getCounter() throws SQLException, EnvironmentVariableNotFoundException;
    // endregion
    // region unnecessary
    /**
     * Dummy method to demonstrate CRUD capabilities
     */
    void createTheUnnecessaryTable() throws SQLException, EnvironmentVariableNotFoundException;
    /**
     * Dummy method to demonstrate CRUD capabilities
     */
    void deleteTheUnnecessaryTable() throws SQLException, EnvironmentVariableNotFoundException;
    // endregion
    // endregion

}