package db;

import com.fasterxml.jackson.core.JsonProcessingException;
import enumerations.db.EnvironmentKeys;
import enumerations.db.QuestionType;
import enumerations.db.dbProperties;
import enumerations.questions.*;
import exceptions.EnvironmentVariableNotFoundException;
import exceptions.NoMatchException;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import questions.IQuestion;
import questions.MCQuestion;
import questions.PromptQuestion;
import java.sql.*;
import java.util.List;

/**
 * responsible for the connection to the database with Postgresql
 * - get PreparedStatements from SqlQueries
 * - executes prepared statements
 * - deserializes the json string into MC- and PromptQuestion objects from the QuestionMapper
 */
public class PostgreSQLDbManager implements IDbManager {
    private final static Logger log = LogManager.getLogger(PostgreSQLDbManager.class);
    private static PostgreSQLDbManager instance;

    public static PostgreSQLDbManager getInstance(){
        if (instance == null){
            instance = new PostgreSQLDbManager();
        }
        return instance;
    }

    //region resultSet -> objects

    /**
     * extract an IQuestion out of a ResultSet
     * @param resultSet the ResultSet with one IQuestion
     * @return the interpreted IQuestion object
     * @throws SQLException if sql exception
     */
    private IQuestion getIQuestionFromResultSet(final ResultSet resultSet, final QuestionType questionType) throws SQLException, JsonProcessingException {
        IQuestion question = null;
        if (resultSet.next()){
            if (questionType.equals(QuestionType.MCQUESTION)) {
                question = QuestionMapper.getMCQuestionFromResultSet(resultSet);
            }
            else if (questionType.equals(QuestionType.PROMPTQUESTION)) {
                question = QuestionMapper.getPromptQuestionFromResultSet(resultSet);
            }
            else
            {
                log.error("Invalid question type: {}", questionType);
            }
        }
        else
        {
            log.error("no Question to extract");
        }
        if(question == null) throw new NoMatchException("There is no question with chosen parameters.");
        return question;
    }

    //endregion

    //region getRandom
    /**
     *get a random IQuestion obj out of the database
     * @return the random IQuestion obj
     */
    @Override
    public IQuestion getRandomQuestion(final QuestionType questionType,final List<I_DB_Filter> filterList) throws SQLException, NotImplementedException, EnvironmentVariableNotFoundException {
        //try with resources to end connection when finished
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection())
        {
            return switch (questionType) {
                case MCQUESTION -> {
                    log.info("MCQuestion with filter {}", filterList);
                    yield getRandomMCQuestion(connection, filterList);
                }
                case PROMPTQUESTION -> {
                    log.info("PromptQuestion with filter {}", filterList);
                    yield getRandomPromptQuestion(connection, filterList);
                }
                default -> {
                    log.error("questionType {} is not implemented", questionType);
                    throw new NotImplementedException("QuestionType " + questionType + " has not yet been implemented");
                }
            };
        }
        catch (final SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
        catch (final EnvironmentVariableNotFoundException e) {
            log.fatal(e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * get a MCQuestion with a filter.
     * @param connection the database connection to the PostgreSQL Server -> Docker
     * @param dbFilterList the filter enums which implement I_DB_Filter. Exclude QuestionTypePrompt on MCQuestion and everything without QuestionTypePrompt on PromptQuestion
     * @return the requested MCQuestion object.
     * @throws SQLException if sth. went wrong in SQL
     */
    private MCQuestion getRandomMCQuestion(final Connection connection, final List<I_DB_Filter> dbFilterList) throws SQLException, JsonProcessingException {
        final SqlQueries query = new SqlQueries(connection);
        final PreparedStatement prepStmt = query.getRandomMCQuestion(dbFilterList);
        log.info("Executing PreparedStatement {}", prepStmt);
        final ResultSet resultSet = prepStmt.executeQuery();
        log.info("got resultSet {} \nfrom prepStmt {}", resultSet.toString(), prepStmt);
        return (MCQuestion) getIQuestionFromResultSet(resultSet, QuestionType.MCQUESTION);
    }
    /**
     * get a PromptQuestion with a filter.
     * @param connection the database connection to the PostgreSQL Server -> Docker
     * @param dbFilterList the filter enums which implement I_DB_Filter. Exclude QuestionTypePrompt on MCQuestion and everything without QuestionTypePrompt on PromptQuestion
     * @return the requested MCQuestion object.
     * @throws SQLException if sth. went wrong in sql
     */
    private PromptQuestion getRandomPromptQuestion(final Connection connection, final List<I_DB_Filter> dbFilterList) throws SQLException, JsonProcessingException {
        final SqlQueries query = new SqlQueries(connection);
        final PreparedStatement prepStmt = query.getRandomPromptQuestion(dbFilterList);
        log.info("Executing PreparedStatement {}", prepStmt);
        final ResultSet resultSet = prepStmt.executeQuery();
        log.info("got resultSet {} \nfrom prepStmt {}", resultSet.toString(), prepStmt);
        return (PromptQuestion) getIQuestionFromResultSet(resultSet,QuestionType.PROMPTQUESTION);
    }
    //endregion

    //region CRUD
    @Override
    public void createQuestion(final QuestionType questionType, int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection()) {
            final SqlQueries query = new SqlQueries(connection);

            switch (questionType) {
                case MCQUESTION -> {
                    log.info("questionType is MCQUESTION");
                    final PreparedStatement prepStmt = query.createMCQuestion(id, characteristics, json);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    prepStmt.executeUpdate();
                }
                case PROMPTQUESTION -> {
                    log.info("questionType is PROMPTQUESTION");
                    final PreparedStatement prepStmt = query.createPromptQuestion(id, characteristics, json);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    prepStmt.executeUpdate();
                }
                default -> {
                    log.error("questionType " + questionType + " is not implemented");
                    throw new NotImplementedException("QuestionType " + questionType + " has not yet been implemented");
                }
            }

        } catch (final SQLException e) {
            log.error(e.getMessage());
            throw e;
        } catch (final EnvironmentVariableNotFoundException e) {
            log.fatal(e.getMessage());
            throw e;
        }
    }
    @Override
    public IQuestion readQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException, JsonProcessingException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection()) {
            final SqlQueries query = new SqlQueries(connection);

            return switch (questionType) {
                case MCQUESTION -> {
                    log.info("questionType is MCQUESTION");

                    final PreparedStatement prepStmt = query.readMCQuestion(id);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    final ResultSet resultSet = prepStmt.executeQuery();
                    log.info("got resultSet {} \nfrom prepStmt {}", resultSet.toString(), prepStmt);
                    yield (MCQuestion) getIQuestionFromResultSet(resultSet, questionType);
                }
                case PROMPTQUESTION -> {
                    log.info("questionType is PROMPTQUESTION");

                    final PreparedStatement prepStmt = query.readPromptQuestion(id);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    final ResultSet resultSet = prepStmt.executeQuery();
                    log.info("got resultSet {} \nfrom prepStmt {}", resultSet.toString(), prepStmt);
                    yield (PromptQuestion) getIQuestionFromResultSet(resultSet, questionType);
                }
                default -> {
                    log.error("questionType " + questionType + " is not implemented");
                    throw new NotImplementedException("QuestionType " + questionType + " has not yet been implemented");
                }
            };

        } catch (final SQLException | JsonProcessingException e) {
            log.error(e.getMessage());
            throw e;
        } catch (final EnvironmentVariableNotFoundException e) {
            log.fatal(e.getMessage());
            throw e;
        }
    }
    @Override
    public void updateQuestion(final QuestionType questionType, final int id, final List<I_DB_Filter> characteristics, String json) throws SQLException, EnvironmentVariableNotFoundException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection()) {
            final SqlQueries query = new SqlQueries(connection);

            switch (questionType) {
                case MCQUESTION -> {
                    log.info("questionType is MCQUESTION");
                    final PreparedStatement prepStmt = query.updateMCQuestion(id, characteristics, json);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    prepStmt.executeUpdate();
                }
                case PROMPTQUESTION -> {
                    log.info("questionType is PROMPTQUESTION");
                    final PreparedStatement prepStmt = query.updatePromptQuestion(id, characteristics, json);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    prepStmt.executeUpdate();
                }
                default -> {
                    log.error("questionType " + questionType + " is not implemented");
                    throw new NotImplementedException("QuestionType " + questionType + " has not yet been implemented");
                }
            }
        } catch (final SQLException e) {
            log.error(e.getMessage());
            throw e;
        } catch (final EnvironmentVariableNotFoundException e) {
            log.fatal(e.getMessage());
            throw e;
        }
    }
    @Override
    public void deleteQuestion(final QuestionType questionType, final int id) throws SQLException, EnvironmentVariableNotFoundException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection()) {
            final SqlQueries query = new SqlQueries(connection);

            switch (questionType) {
                case MCQUESTION -> {
                    log.info("questionType is MCQUESTION");
                    final PreparedStatement prepStmt = query.deleteMCQuestion(id);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    prepStmt.executeUpdate();
                }
                case PROMPTQUESTION -> {
                    log.info("questionType is PROMPTQUESTION");
                    final PreparedStatement prepStmt = query.deletePromptQuestion(id);
                    log.info("Executing PreparedStatement {}", prepStmt);
                    prepStmt.executeUpdate();
                }
                default -> {
                    log.error("questionType " + questionType + " is not implemented");
                    throw new NotImplementedException("QuestionType " + questionType + " has not yet been implemented");
                }
            }

        } catch (final SQLException e) {
            log.error(e.getMessage());
            throw e;
        } catch (final EnvironmentVariableNotFoundException e) {
            log.fatal(e.getMessage());
            throw e;
        }
    }
    //endregion

    //region dummy methods
    //region increment
    @Override
    public void incrementQuestionCounter(final int incrementBy) throws SQLException, EnvironmentVariableNotFoundException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection())
        {
            final SqlQueries query = new SqlQueries(connection);
            try( final PreparedStatement prepStmt = query.incrementQuestionCounter(incrementBy)) {
                prepStmt.execute();
            }
            catch (final SQLException e)
            {
                log.error(e.getMessage());
                throw e;
            }

        }
        catch (final SQLException e)
        {
            log.error(e.getMessage());
            throw e;
        }
        catch (final EnvironmentVariableNotFoundException e)
        {
            log.fatal(e.getMessage());
            throw e;
        }

    }
    @Override
    public int getCounter() throws SQLException, EnvironmentVariableNotFoundException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection())
       {
           final SqlQueries query = new SqlQueries(connection);
           final PreparedStatement prepStmt = query.getCounter();
           final ResultSet resultSet = prepStmt.executeQuery();
           resultSet.next();
           final int value = (int)resultSet.getObject(1);
           log.info("QuestionCounter: {}", value);
           return value;
       }
       catch (final SQLException e)
       {
           log.error(e.getMessage());
           throw e;
       }
       catch (final EnvironmentVariableNotFoundException e)
       {
           log.fatal(e.getMessage());
           throw e;
       }

    }
    //endregion
    //region unnecessaryTable
    @Override
    public void createTheUnnecessaryTable() throws SQLException, EnvironmentVariableNotFoundException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection())
        {
            final SqlQueries query = new SqlQueries(connection);
            try( final PreparedStatement prepStmt = query.createTheUnnecessaryTable()) {
                prepStmt.execute();
            }
            catch (final SQLException e)
            {
                log.error(e.getMessage());
                throw e;
            }
        }
        catch (final SQLException e)
        {
            log.error(e.getMessage());
            throw e;
        }
        catch (final EnvironmentVariableNotFoundException e)
        {
            log.fatal(e.getMessage());
            throw e;
        }
    }
    @Override
    public void deleteTheUnnecessaryTable() throws SQLException, EnvironmentVariableNotFoundException {
        log.info("trying to connect to db...");
        try(final Connection connection = getDBConnection())
        {
            final SqlQueries query = new SqlQueries(connection);
            try (PreparedStatement prepStmt = query.deleteTheUnnecessaryTable()) {
                prepStmt.execute();
            }
            catch (final SQLException e)
            {
                log.error(e.getMessage());
                throw e;
            }
        }
        catch (final SQLException e)
        {
            log.error(e.getMessage());
            throw e;
        }
        catch (final EnvironmentVariableNotFoundException e)
        {
            log.fatal(e.getMessage());
            throw e;
        }

    }
    //endregion
    //endregion

    //region Connection
    /**
     * get the DB-Credentials and connects to the database
     * @return The PostgreSQL DB-Connection
     */
    private Connection getDBConnection() throws SQLException, EnvironmentVariableNotFoundException, NotImplementedException {
        Connection connection;
        log.debug("Connecting to database...");
        try {
            final String PASSWORD = getEnvironmentVarValue(EnvironmentKeys.POSTGRES_PASSWORD);
            final String DB_URL = getEnvironmentVarValue(EnvironmentKeys.POSTGRES_JDBC_URL);
            final String USER = getEnvironmentVarValue(EnvironmentKeys.POSTGRES_JDBC_USER);
            final int timeOut = dbProperties.DB_TIMEOUT.getTimeout();
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (connection != null && connection.isValid(timeOut)) {
                log.info("Database connection successful");
            }
            else
            {
                log.fatal("Database connection failed or takes longer than {}ms.", timeOut);
            }
        }
        catch (final EnvironmentVariableNotFoundException e) {
            log.fatal("EnvironmentVariableNotFoundException: {}", e.getMessage());
            throw e;
        }
        catch (final SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
        return connection;
    }

    /**
     * get the value of an environment variable used for the database.
     * @param environmentKey the key of the environment variable.
     * @return the value of the environment variable.
     * @throws EnvironmentVariableNotFoundException threw if the environment variable wasn't found.
     */
    private String getEnvironmentVarValue(final EnvironmentKeys environmentKey) throws EnvironmentVariableNotFoundException {
        log.info("get Environment variable: {}", environmentKey);
        final Dotenv dotenv = Dotenv.load();
        final String environmentValue = dotenv.get(environmentKey.toString());
        if (environmentValue == null || environmentValue.isEmpty()) {
            log.fatal("EnvironmentVariable not found: {}", environmentKey);
            throw new EnvironmentVariableNotFoundException( environmentKey+" is not set");
        }
        return environmentValue;
    }
    //endregion
}
