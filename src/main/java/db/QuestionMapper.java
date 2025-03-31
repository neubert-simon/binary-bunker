package db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enumerations.db.ResultSetIndices;
import enumerations.db.ResultSetIndicesPromptQuestion;
import enumerations.questions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import questions.MCQuestion;
import questions.PromptQuestion;
import questions.QuestionFactory;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * responsible for mapping the dbDataObj using JSON and ResultSet to IQuestion
 */
public class QuestionMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(QuestionMapper.class);

    /**
     * convert JSON String -> dbDataObj
     * @param json the json String
     * @return the dbDataObj
     */
    private static dbDataObj convertFromJson(final String json) throws JsonProcessingException {
        try {
            return objectMapper.readValue(json, dbDataObj.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    private static dbDataPromptQ convertFromJsonPromptQuestion(final String json) throws JsonProcessingException {
        try {
            return objectMapper.readValue(json, dbDataPromptQ.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * get the IQuestion object from a resultSet
     * @param resultSet resultSet contains one IQuestion obj.
     * @return the extracted MCQuestion
     * @throws SQLException if the statement is invalid or there is a problem with the database
     */
    public static MCQuestion getMCQuestionFromResultSet(final ResultSet resultSet) throws SQLException, JsonProcessingException {
        //QuestionID:
        final int id = (int)resultSet.getObject(ResultSetIndices.QUESTION_ID.getIndex());
        log.debug("id: {}", id);

        //OSI-Layer:
        final OSILayer osiLayer = OSILayer.valueOf((String)resultSet.getObject(ResultSetIndices.QUESTION_OSI_LAYER.getIndex()));
        log.debug("osi-Layer {}", osiLayer);

        //Difficulty:
        final Difficulty difficulty =Difficulty.valueOf((String) resultSet.getObject(ResultSetIndices.QUESTION_DIFFICULTY.getIndex()));
        log.debug("difficulty: {}", difficulty);

        //QuestionType:
        final Array sqlObj = resultSet.getArray(ResultSetIndices.QUESTION_TYPE.getIndex());
        final String[] array = (String[]) sqlObj.getArray();
        final List<IQuestionType> questionTypes = new ArrayList<>(array.length);
        for( String element : array)
        {
            IQuestionType questionType = QuestionTypeMC.valueOf(element);
            questionTypes.add(questionType);
        }
        log.debug("questionTypes:  {}", Arrays.toString(array));


        //Data Object:
        final dbDataObj dataObj = convertFromJson(resultSet.getObject(ResultSetIndices.QUESTION_DATA_OBJ.getIndex()).toString());
        log.debug("data: {}", dataObj);

        final String explanation = dataObj.getExplanation();
        log.debug("Explanation: {}", explanation);

        return QuestionFactory.createMCQuestion(String.valueOf(id),dataObj.getQuestion(),explanation,dataObj.getCorrectAnswers(),dataObj.getAllAnswers(),osiLayer,difficulty,questionTypes);
    }

    /**
     * get the IQuestion object from a resultSet
     * @param resultSet resultSet contains one IQuestion obj.
     * @return the extracted MCQuestion
     * @throws SQLException if the statement is invalid or there is a problem with the database
     */
    public static PromptQuestion getPromptQuestionFromResultSet(final ResultSet resultSet) throws SQLException, JsonProcessingException {
        //QuestionID:
        final int id = (int)resultSet.getObject(ResultSetIndicesPromptQuestion.QUESTION_ID.getIndex());
        log.info("id: {}", id);

        //OSILayer
        final OSILayer osiLayer = OSILayer.valueOf((String)resultSet.getObject(ResultSetIndicesPromptQuestion.OSILAYLER.getIndex()));
        log.info("id: {}", id);

        //QuestionTypePrompt
        final QuestionTypePrompt questionTypePrompt = QuestionTypePrompt.valueOf((String) resultSet.getObject(ResultSetIndicesPromptQuestion.QUESTION_TYPE_PROMPT.getIndex()));
        log.debug("QuestionTypePrompt::  {}", questionTypePrompt);

        //Data Object:
        final dbDataPromptQ dataObj = convertFromJsonPromptQuestion(resultSet.getObject(ResultSetIndicesPromptQuestion.QUESTION_DATA_OBJ.getIndex()).toString());
        log.debug("data: {}", dataObj);

        final String explanation = dataObj.getExplanation();
        log.debug("Explanation: {}", explanation);

        return QuestionFactory.createPromptQuestionFromDB(String.valueOf(id),dataObj.getQuestion(),explanation,osiLayer,questionTypePrompt);
    }



}
