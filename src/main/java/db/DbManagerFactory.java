package db;

import enumerations.db.DbTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * factory to create Db Manager based on different DBMS.
 */
public class DbManagerFactory
{
    private static final Logger log = LoggerFactory.getLogger(DbManagerFactory.class);

    public static IDbManager getInstance(DbTypes dbType){
        if (dbType == DbTypes.PostgreSQL){
            log.debug("using PostgreSQL");
            return PostgreSQLDbManager.getInstance();
        }
        //add additional options for other DB's
        else{

            throw new IllegalArgumentException("dbType invalid: " + dbType);
        }

    }


}
