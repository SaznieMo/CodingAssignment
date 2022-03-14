import database.JDBCUtils;
import logs.LogFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogFileTest {
    LogFile logFile;
    JDBCUtils jdbcUtils;

    @BeforeEach
    public void init() throws SQLException {
        logFile = new LogFile("src/main/resources/logfile.txt");
        logFile.parseLogFile();
    }

    @Test
    public void testCorrectlyIdentifyingDurations(){
        assertEquals(4, logFile.getLogTableEntryMap().get("aaa").getEventDuration());
    }

    @Test
    public void testAlertWithDurationGreaterThan4(){
        assertEquals(TRUE, logFile.getLogTableEntryMap().get("ccc").isAlert());
    }

    @Test
    public void testAlertWithDurationLessThan4(){
        assertEquals(FALSE, logFile.getLogTableEntryMap().get("bbb").isAlert());
    }

    @Test
    public void testAlertWithDurationEqualTo4(){
        assertEquals(FALSE, logFile.getLogTableEntryMap().get("aaa").isAlert());
    }

    @Test
    public void testCorrectTypeAttributeExtractedFromJSON() {
        assertEquals("APPLICATION_LOG", logFile.getLogTableEntryMap().get("aaa").getType());
        assertEquals(null, logFile.getLogTableEntryMap().get("bbb").getType());
    }

    @Test
    public void testCorrectHostAttributeExtractedFromJSON() {
        assertEquals("12345", logFile.getLogTableEntryMap().get("aaa").getHost());
        assertEquals(null, logFile.getLogTableEntryMap().get("bbb").getHost());
    }

}
