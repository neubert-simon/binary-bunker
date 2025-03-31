package db;


import enumerations.db.PostgreSQLQueries;
import enumerations.db.QuestionType;
import enumerations.questions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * all methods that return prepared statements are or are necessary to build them are collected here.
 */
class SqlQueries {
    private final static Logger log = LogManager.getLogger(SqlQueries.class);
    private final Connection connection;

    SqlQueries(Connection connection) {
        this.connection = connection;
    }

    //region getRandom

    /**
     * Creates the complete PreparedStatement to get a random prompt question
     * @param filterList a filter that the random prompt question should be part of
     * @return the executable PreparedStatement
     */
    PreparedStatement getRandomPromptQuestion(List<I_DB_Filter> filterList) throws SQLException
    {
        log.debug("method getRandomPromptQuestion called with parameters filterList {}", filterList);
        //if to be filtered
        if (!filterList.isEmpty()) {
            //masking sure every filter is only once in the list
            filterList = cleanupFilterList(filterList, QuestionType.PROMPTQUESTION);

            // getting query
            final String query = generateQueryPattern(PostgreSQLQueries.GET_RANDOM_QUESTION.getQueryPromptQuestion(),filterList);
            return createAndFillPrepStatementByQuery(query, filterList);
        }
        else {
            final String query = PostgreSQLQueries.GET_RANDOM_QUESTION.getQueryPromptQuestion() + " ORDER BY RANDOM() LIMIT 1";
            return connection.prepareStatement(query);
        }
    }
    /**
     * Creates the complete PreparedStatement to get a random multiple choice question
     * @param filterList a filter that the random multiple choice question should be part of
     * @return the executable PreparedStatement
     */
    PreparedStatement getRandomMCQuestion(List<I_DB_Filter> filterList) throws SQLException
    {
        log.debug("method getRandomMCQuestion called with parameters filterList {}", filterList);
        //if to be filtered
        if (!filterList.isEmpty()) {
            //masking sure every filter is only once in the list
            filterList = cleanupFilterList(filterList, QuestionType.MCQUESTION);

            // getting query
            final String query = generateQueryPattern(PostgreSQLQueries.GET_RANDOM_QUESTION.getQueryMCQuestion(), filterList);
            return createAndFillPrepStatementByQuery(query, filterList);
        }
        else
        {
            final String query = PostgreSQLQueries.GET_RANDOM_QUESTION.getQueryMCQuestion() + " ORDER BY RANDOM() LIMIT 1";
            return connection.prepareStatement(query);
        }
    }

    /**
     * creates and fills a PreparedStatement with the values given
     * @param query The query that is to be turned into a filled PreparedStatement
     * @param filterList The filterList that the PreparedStatement should be filled with
     * @return the executable PreparedStatement
     */
    private PreparedStatement createAndFillPrepStatementByQuery(final String query, final List<I_DB_Filter> filterList) throws SQLException {
        final PreparedStatement prep = connection.prepareStatement(query);
        log.debug("Empty query: {}", prep);

        // filling prepStatement
        int index = 0;
        for (final I_DB_Filter filter : filterList) {
            index = addFilterToPrep(filter, prep, index);
        }
        log.debug("Filled query: {}", prep);
        return prep;
    }
    /**
     * adds conditions by how many relevant filters there are to the prepared statement
     * @param filter the filters that should be added to the prepared statement
     * @param prep prepared statement to wich the filters should be added
     * @param index at wich index the prepared statement should start
     * @return the current index of the prepared statement
     */
    private int addFilterToPrep(final I_DB_Filter filter, PreparedStatement prep, int index) throws SQLException {
        switch (filter.getFilterType()) {
            case Difficulty, OSILayer, QuestionTypeMC,QuestionTypePrompt -> {
                log.debug("set filter {} into prep {} at index {}", filter, prep, index);
                prep.setString(index = index + 1, filter.toString());
            }
            default ->
            {
                log.error("invalid filterType {}", filter.getFilterType());
                throw new IllegalArgumentException("invalid filterType " + filter.getFilterType());
            }
        }
        return index;
    }

    /**
     * Removes the irrelevant filters from the filterList for the given questionType
     * @param filterList the list that is to be cleaned
     * @param questionType the questionType for wich the list should be cleaned
     * @return the filter list containing only the relevant filters for the given questionType
     */
    private List<I_DB_Filter> cleanupFilterList(final List<I_DB_Filter> filterList, final QuestionType questionType) {
       if (QuestionType.PROMPTQUESTION.equals(questionType)) {
           // make sure list contains only QuestionTypePrompt filter
           // reason: every promptQuestion has a special QuestionTypePrompt
           final Predicate<I_DB_Filter> condition = obj -> obj instanceof QuestionTypePrompt;
            if (filterList.stream().allMatch(condition)) {
                log.info("Filter {} is ok", filterList);
                //remove duplicates
                return filterList.stream().distinct().collect(Collectors.toList());
            }
            else {
                log.error("cleanupFilterList failed: filter not ok");
                throw new IllegalArgumentException("cleanupFilterList failed: filter not ok");
            }
       } else if (QuestionType.MCQUESTION.equals(questionType)) {
           log.info("Filter {} is ok", filterList);
           // MCQuestion allows more filters
           // remove duplicates
           return filterList.stream().distinct().collect(Collectors.toList());
       } else {
           log.error("invalid questionType {}", questionType);
            throw new IllegalArgumentException("Invalid questionType " + questionType);
       }
    }

    /**
     * generates the query with the filters wich are to be filtered from the database
     * @param query the query that is to be filled
     * @param filterList the list of filters wich are to be added to the query
     * @return the fully generated query as String
     */
    private String generateQueryPattern(final String query,final List<I_DB_Filter> filterList) {

        final StringBuilder queryBuilder = new StringBuilder(query);
        boolean filterStart = true;
        for (I_DB_Filter filter : filterList) {
            if (filterStart) {
                // the query needs WHERE first to filter
                queryBuilder.append(" WHERE ");
                filterStart = false;
            }
            else {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(" ?")
                    .append(filter.getFilterType().postgreDataType)
                    .append(" = ")
                    .append(filter.getFilterType().requestKey);
            log.debug("query changed to: {}", queryBuilder);
        }
        queryBuilder.append(" ORDER BY RANDOM() LIMIT 1");
        log.debug("final query: {}", queryBuilder.toString());
        return queryBuilder.toString();
    }
    //endregion

    //region CRUD operations
    //region C of CRUD

    /**
     * Creates the complete PreparedStatement to insert a new question into the MCQuestion database
     * @param id the id that the question should have
     * @param characteristics a list of characteristics the question should have
     * @param json the json body the question should have
     * @return the executable PreparedStatement
     */
    PreparedStatement createMCQuestion(int id, List<I_DB_Filter> characteristics, String json) throws SQLException {
        log.debug("method createMCQuestion called with parameters id {}, characteristics {} and json {}", id, characteristics, json);

        // remove duplicates from characteristics
        characteristics = characteristics.stream().distinct().collect(Collectors.toList());

        // check if list contains invalid values (e.g. multiple osiLayers)
        if (characteristics.stream().filter(filter -> filter.getFilterType() == FilterTypes.OSILayer).count() != 1) {       // only 1 osiLayer
            log.error("characteristics must contain exactly one OsiLayer");
            throw new IllegalArgumentException("characteristics must contain exactly one OsiLayer");}
        if (characteristics.stream().filter(filter -> filter.getFilterType() == FilterTypes.Difficulty).count() != 1) {   // only 1 difficulty
            log.error("characteristics must contain exactly one Difficulty");
            throw new IllegalArgumentException("characteristics must contain exactly one Difficulty");}
        if (characteristics.stream().noneMatch(filter -> filter.getFilterType() == FilterTypes.QuestionTypeMC)) {          // at least 1 type
            log.error("characteristics must contain at least one QuestionType");
            throw new IllegalArgumentException("characteristics must contain at least one QuestionType");}

        // collect characteristics
        final String osiLayer = characteristics.stream().filter(object -> object.getFilterType() == FilterTypes.OSILayer).findFirst().get().toString(),     // we already made sure there's only one osiLayer and difficulty
                    difficulty = characteristics.stream().filter(object -> object.getFilterType() == FilterTypes.Difficulty).findFirst().get().toString(),
                    questionTypes = createQuestionTypeString(characteristics.stream().filter(object -> object.getFilterType() == FilterTypes.QuestionTypeMC).toList());

        // create prep statement
        final String query = PostgreSQLQueries.CREATE_QUESTION.getQueryMCQuestion();
        final PreparedStatement prep = connection.prepareStatement(query);

        // set ? parameters
        // 1 = id, 2 = osilayer, 3 = difficulty, 4 = questiontype, 5 = json_obj
        prep.setInt(1, id);
        prep.setString(2, osiLayer);
        prep.setString(3, difficulty);
        prep.setString(4, questionTypes);
        prep.setString(5, json);

        log.info(prep.toString());
        return prep;
    }
    /**
     * Creates the complete PreparedStatement to  insert a new question into the PromptQuestion database
     * @param id the id that the question should have
     * @param characteristics a list of characteristics the question should have
     * @param json the json body the question should have
     * @return the executable PreparedStatement
     */
    PreparedStatement createPromptQuestion(int id, List<I_DB_Filter> characteristics, String json) throws SQLException {
        log.debug("method createPromptQuestion called with parameters id {}, characteristics {} and json {}", id, characteristics, json);

        // remove duplicates from characteristics
        characteristics = characteristics.stream().distinct().collect(Collectors.toList());

        // check if list contains invalid
        if (characteristics.stream().filter(filter -> filter.getFilterType() == FilterTypes.OSILayer).count() != 1) {     // only 1 osiLayer
            log.error("characteristics must contain exactly one OsiLayer");
            throw new IllegalArgumentException("characteristics must contain exactly one OsiLayer");
        }
        if (characteristics.stream().noneMatch(filter -> filter.getFilterType() == FilterTypes.QuestionTypePrompt)) {       // at least 1 type
            log.error("characteristics must contain at least one QuestionType");
            throw new IllegalArgumentException("characteristics must contain at least one QuestionType");
        }

        // collect characteristics
        final String osiLayer = characteristics.stream().filter(object -> object.getFilterType() == FilterTypes.OSILayer).findFirst().get().toString(),
                    questionTypes = characteristics.stream().filter(object -> object.getFilterType() == FilterTypes.QuestionTypePrompt).findFirst().get().toString();

        // create prep statement
        String query = PostgreSQLQueries.CREATE_QUESTION.getQueryPromptQuestion();
        PreparedStatement prep = connection.prepareStatement(query);

        // set ? parameters
        // 1 = id, 2 = osilayer, 3 = questiontype, 4 = json_obj
        prep.setInt(1, id);
        prep.setString(2, osiLayer);
        prep.setString(3, questionTypes);
        prep.setString(4, json);

        log.info(prep.toString());
        return prep;
    }

    /**
     * Generates the questionType elements as a String. Formatted so they can be inserted into the database
     * @param questionTypes the questionTypes that are to be formatted, all elements of the list must be QuestionTypeX Filters
     * @return String following the format "{qType1, qType2,...}"
     */
    private String createQuestionTypeString(List<I_DB_Filter> questionTypes) {
        // make sure all elements in questionTypes are of valid Types
        if (!questionTypes.stream().allMatch(elem -> elem.getFilterType() == FilterTypes.QuestionTypeMC ||
                                                    elem.getFilterType() == FilterTypes.QuestionTypePrompt)) {
            log.error("questionType list contains values that arent of QuestionTypeMC or QuestionTypePrompt {}", questionTypes);
            throw new IllegalArgumentException("questionType list contains values that arent of QuestionTypeMC or QuestionTypePrompt");
        }
        // create string formed like this: {qtype1, qtype2}
        StringBuilder questionTypesString = new StringBuilder("{");
        for (int index = 0; index < questionTypes.size(); index++) {
            questionTypesString.append(questionTypes.get(index));
            if (questionTypes.size()-1 != index)
                questionTypesString.append(",");
        }
        questionTypesString.append("}");

        log.debug("final questionType String: {}", questionTypesString.toString());
        return  questionTypesString.toString();
    }
    //endregion
    //region R of CRUD

    /**
     * Creates the complete PreparedStatement to get one specific question from the MCQuestion database
     * @param id the id that the question has
     * @return the executable PreparedStatement
     */
    PreparedStatement readMCQuestion(final int id) throws SQLException {
        log.debug("method readMCQuestion called with parameters id {}", id);
        // create prep statement
        final String query = PostgreSQLQueries.READ_QUESTION.getQueryMCQuestion();
        final PreparedStatement prep = connection.prepareStatement(query);

        // set ? parameters
        // 1 = id
        prep.setInt(1, id);

        log.info(prep.toString());
        return prep;
    }
    /**
     * Creates the complete PreparedStatement to get one specific question from the PromptQuestion database
     * @param id the id that the question has
     * @return the executable PreparedStatement
     */
    PreparedStatement readPromptQuestion(final int id) throws SQLException {
        log.debug("method readPromptQuestion called with parameters id {}", id);
        // create prep statement
        final String query = PostgreSQLQueries.READ_QUESTION.getQueryPromptQuestion();
        final PreparedStatement prep = connection.prepareStatement(query);

        // set ? parameters
        // 1 = id
        prep.setInt(1, id);

        log.info(prep.toString());
        return prep;
    }
    //endregion
    //region U of CRUD

    /**
     * Creates the complete PreparedStatement to update one specific question in the MCQuestion database
     * @param id the id of the to be updated question
     * @param characteristics a List of the new characteristics for the question
     * @param json the new json body for the question (stays the same if it's an empty String "")
     * @return the executable PreparedStatement
     */
    PreparedStatement updateMCQuestion(final int id, final List<I_DB_Filter> characteristics, final String json) throws SQLException {
        log.debug("method updateMCQuestion called with parameters id {}, characteristics {} and json {}", id, characteristics, json);
        if (characteristics.stream().noneMatch(elem ->
                                elem.getFilterType() == FilterTypes.OSILayer ||
                                elem.getFilterType() == FilterTypes.Difficulty ||
                                elem.getFilterType() == FilterTypes.QuestionTypeMC)
                            && json.isEmpty()) {
            log.error("There is no relevant data to update for MCQuestion, characteristics: {}", characteristics);
            throw new IllegalArgumentException("There is no relevant data to update for MCQuestion");
        }

        final String[][] characteristicsMap = {
                {characteristics.stream()
                        .filter(filter -> filter.getFilterType() == FilterTypes.OSILayer)
                        .findFirst()
                        .map(Object::toString)
                        .orElse("")
                        , " osilayer = ?" + FilterTypes.OSILayer.postgreDataType + ", "},
                {characteristics.stream()
                        .filter(filter -> filter.getFilterType() == FilterTypes.Difficulty)
                        .findFirst()
                        .map(Object::toString)
                        .orElse("")
                        , " difficulty = ?" + FilterTypes.Difficulty.postgreDataType + ", "},
                {characteristics.stream().anyMatch(filter -> filter.getFilterType() == FilterTypes.QuestionTypeMC)
                        ? createQuestionTypeString(
                        characteristics.stream()
                                .filter(filter -> filter.getFilterType() == FilterTypes.QuestionTypeMC)
                                .toList())
                        : ""
                        , " questiontype = ?" + FilterTypes.QuestionTypeMC.postgreDataType + "[], "},
                {json.isEmpty() ? "" : json, " data = ?::jsonb, "}
        };
        log.debug("characteristicsMap looks like this: {}", Arrays.deepToString(characteristicsMap));

        // create prep statement
        String query = PostgreSQLQueries.UPDATE_QUESTION.getQueryMCQuestion();
        query = insertCharacteristics(query, characteristicsMap);

        final PreparedStatement prep = connection.prepareStatement(query);
        fillPrepByIndex(prep, characteristicsMap, id);

        log.info(prep.toString());
        return prep;
    }
    /**
     * Creates the complete PreparedStatement to update one specific question in the PromptQuestion database
     * @param id the id of the to be updated question
     * @param characteristics a List of the new characteristics for the question
     * @param json the new json body for the question (stays the same if it's an empty String "")
     * @return the executable PreparedStatement
     */
    PreparedStatement updatePromptQuestion(final int id, final List<I_DB_Filter> characteristics, final String json ) throws SQLException {
        log.debug("method createPromptQuestion called with parameters id {}, characteristics {} and json {}", id, characteristics, json);
        if (characteristics.stream().noneMatch(elem ->
                    elem.getFilterType() == FilterTypes.OSILayer ||
                    elem.getFilterType() == FilterTypes.QuestionTypePrompt)
                && json.isEmpty()) {
            log.error("There is no relevant data to update for PromptQuestion, characteristics: {}", characteristics);
            throw new IllegalArgumentException("There is no relevant data to update for PromptQuestion");
        }

        final String[][] characteristicsMap = {
                {characteristics.stream()
                        .filter(filter -> filter.getFilterType() == FilterTypes.OSILayer)
                        .findFirst()
                        .map(Object::toString)
                        .orElse("")
                        , " osilayer = ?" + FilterTypes.OSILayer.postgreDataType + ", "},
                {characteristics.stream()
                        .filter(filter -> filter.getFilterType() == FilterTypes.QuestionTypePrompt)
                        .findFirst()
                        .map(Object::toString)
                        .orElse("")
                        , " questiontypeprompt = ?" + FilterTypes.QuestionTypePrompt.postgreDataType + ", "},
                {json.isEmpty() ? "" : json, " data = ?::jsonb, "}
        };
        log.debug("characteristicsMap looks like this: {}", Arrays.deepToString(characteristicsMap));

        // create prep statement
        String query = PostgreSQLQueries.UPDATE_QUESTION.getQueryPromptQuestion();
        query = insertCharacteristics(query, characteristicsMap);

        final PreparedStatement prep = connection.prepareStatement(query);
        fillPrepByIndex(prep, characteristicsMap, id);

        log.info(prep.toString());
        return prep;
    }

    /**
     * Fills the given query at the '~' with the values given in the characteristicsMap
     * if the first element of the String arrays are not empty Strings ("")
     * @param query the query that is to be changes
     * @param characteristicsMap a two-dimensional String array that contains {"", " dbColumn = ? "}, ...
     *                           where the first element decides if the value is added ("" if not)
     *                           and the second elements of each array are to be added to the query
     * @return the query with the characteristics that are to be changes
     */
    private String insertCharacteristics(String query, final String[][] characteristicsMap) {
        // goes through characteristicsMap and fills the query with characteristics that are to be changed
        // e.g.     ~ -> osilayer = ?, ~
        for (String[] characteristic : characteristicsMap) {
            if (!characteristic[0].isEmpty()) {
                query = query.substring(0, query.indexOf('~')) +
                        characteristic[1] +
                        query.substring(query.indexOf('~'));
            }
        }
        query = query.replace("~","");
        query = query.substring(0, query.lastIndexOf(",")) +
                query.substring(query.lastIndexOf(",") + 1);

        log.debug(query);
        return query;
    }

    /**
     * Fills the given PreparedStatement with relevant characteristics and id at the end
     * @param prep the PreparedStatement that is to be filled
     * @param characteristicsMap two-dimensional String array that contains {"value", ""}, ...
     *                           where the first element decides if the value is added ("" if not)
     *                           and then added to the PreparedStatement
     * @param id of the question that is to be changed
     */
    private void fillPrepByIndex(PreparedStatement prep, String[][] characteristicsMap, int id) throws SQLException {
        int index = 1;
        for(String[] characteristic : characteristicsMap) {
            if (!characteristic[0].isEmpty()) {
                prep.setString(index, characteristic[0]);
                index++;
            }
        }
        prep.setInt(index, id);
    }
    //endregion
    //region D of CRUD
    /**
     * Creates the complete PreparedStatement to delete one specific question from the MCQuestion database
     * @param id the id that the question has
     * @return the executable PreparedStatement
     */
    PreparedStatement deleteMCQuestion(final int id) throws SQLException {
        log.debug("method deleteMCQuestion called with parameters id {}", id);
        // create prep statement
        String query = PostgreSQLQueries.DELETE_QUESTION.getQueryMCQuestion();
        PreparedStatement prep = connection.prepareStatement(query);

        // set ? parameters
        // 1 = id
        prep.setInt(1, id);

        log.info(prep.toString());
        return prep;
    }
    /**
     * Creates the complete PreparedStatement to delete one specific question from the PromptQuestion database
     * @param id the id that the question has
     * @return the executable PreparedStatement
     */
    PreparedStatement deletePromptQuestion(final int id) throws SQLException {
        log.debug("method deletePromptQuestion called with parameters id {}", id);
        // create prep statement
        String query = PostgreSQLQueries.DELETE_QUESTION.getQueryPromptQuestion();
        PreparedStatement prep = connection.prepareStatement(query);

        // set ? parameters
        // 1 = id
        prep.setInt(1, id);

        log.info(prep.toString());
        return prep;
    }
    //endregion
    //endregion

    //region dummy methods
    //region increment
    /**
     * Dummy method to demonstrate CRUD capabilities
     * @param incrementBy how much the counter is to be incremented
     * @return the executable PreparedStatement
     */
    PreparedStatement incrementQuestionCounter(final int incrementBy) throws SQLException {

        final String query = PostgreSQLQueries.INCREMENT_QUESTION_COUNTER.getQueryMCQuestion() + (incrementBy);
        return connection.prepareStatement(query);
    }
    /**
     * Dummy method to demonstrate CRUD capabilities
     * @return the executable PreparedStatement
     */
    PreparedStatement getCounter() throws SQLException {

       return connection.prepareStatement(PostgreSQLQueries.GET_COUNTER.getQueryMCQuestion());
    }
    //endregion
    //region unnecessaryTable
    /**
     * Dummy method to demonstrate CRUD capabilities
     * @return the executable PreparedStatement
     */
    PreparedStatement deleteTheUnnecessaryTable() throws SQLException {

        return connection.prepareStatement(PostgreSQLQueries.DELETE_THE_UNNECESSARYTABLE.getQueryMCQuestion());
    }
    /**
     * Dummy method to demonstrate CRUD capabilities
     * @return the executable PreparedStatement
     */
    PreparedStatement createTheUnnecessaryTable() throws SQLException {
        return connection.prepareStatement(PostgreSQLQueries.CREATE_THE_UNNECESSARYTABLE.getQueryMCQuestion());
    }
    //endregion
    //endregion
}
