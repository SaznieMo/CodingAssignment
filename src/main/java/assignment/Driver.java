package assignment;

import database.JDBCUtils;
import logs.LogFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Driver class to run the application.
 *
 * App takes in a filepath to logfile.txt as a commandline argument, else it defaults to a logfile.txt found in the resources folder.
 */
public class Driver {
    private static Logger logger = LogManager.getLogger(Driver.class);

    public static void main(String[] args) throws SQLException{
        JDBCUtils jdbcUtils = new JDBCUtils();
        LogFile logFile;
        jdbcUtils.createTable();

        if (args.length != 0){
            logFile = new LogFile(args[0]);
        } else {
            logFile = new LogFile("src/main/resources/logfile.txt");
        }

        logger.debug("Path to input log file:" + logFile.getPathToLogFile());

        logFile.parseLogFile();

        // For each entry in the map, execute an INSERT SQL statement to create the rows as specified in the task.
        logFile.getLogTableEntryMap().forEach((k,v) -> jdbcUtils.executeQuery("INSERT INTO LogsWithDurationsTable VALUES ('" + v.getId() + "','" + v.getEventDuration() + "','" + v.getType() + "','" + v.getHost() + "',"  + v.isAlert() + ")"));

        // Output of final table.
        ResultSet result = jdbcUtils.executeQuery("SELECT * from LogsWithDurationsTable");
        while (result.next()){
            logger.info(result.getString("EventID") + " | " +
                    result.getString("EventDuration") + " | " +
                    result.getString("Type") + " | " +
                    result.getString("Host") + " | " +
                    result.getString("Alert"));
        }

    }
}
