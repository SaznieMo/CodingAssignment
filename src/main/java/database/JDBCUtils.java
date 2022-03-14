package database;

import assignment.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.sql.*;

/**
 * HSQLDB JDBC Utils class
 *
 * Used to create the HSQLDB Connection, create tables, execute queries and handle exceptions.
 * Some boilerplate used as per HSQLDB JDBC docs.
 */
public class JDBCUtils {

    private static Logger logger = LogManager.getLogger(JDBCUtils.class);
    private static String cwd = Paths.get(".").toAbsolutePath().normalize().toString();
    private static String jdbcURL = "jdbc:hsqldb:file:" + cwd + "/src/main/java/database/";
    private static String jdbcUsername = "SA";
    private static String jdbcPassword = "";
    private static final String createTableSQL = "CREATE TABLE LogsWithDurationsTable (\n" +
            "    EventID varchar (255),\n" +
            "    EventDuration int, \n" +
            "    Type varchar(255), \n" +
            "    Host varchar(255), \n" +
            "    Alert Boolean\n" +
            ");";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (SQLException e){
            printSQLException(e);
        }
        return connection;
    }

    public ResultSet executeQuery(String query){

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            connection.commit();
            return result;
        } catch (SQLException e){
            printSQLException(e);
        }

        return null;
    }

    public void createTable() throws SQLException {
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement();) {
            statement.execute(createTableSQL);
            logger.debug("HSQLDB created in location: " + jdbcURL);
            logger.debug("Table created with following SQL Statement: " + createTableSQL);
        } catch (SQLException e){
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
    }

    public ResultSet getMetaDataTables(String tableName) throws SQLException {
        DatabaseMetaData meta = JDBCUtils.getConnection().getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
        return resultSet;
    }
}
