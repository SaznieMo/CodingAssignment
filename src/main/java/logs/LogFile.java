package logs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

/**
 * LogFile class representing the input log file itself.
 *
 * Used to Parse the LogFile, compute values and mapping to objects as necessary.
 * Initiated by providing the pathToLogFile via command line argument, or default to look in location resources/logfile.txt.
 */
public class LogFile {
    private static Logger logger = LogManager.getLogger(LogFile.class);
    private String pathToLogFile;
    private HashMap<String, LogTableEntry> logTableEntryMap;

    public LogFile(String pathToLogFile){
        this.pathToLogFile = pathToLogFile;
    }

    /**
     * Computation logic contained within this method:
     * - Maps JSON object rows in logfile.txt to LogEntryJson objects.
     * - Creates a HashMap mapping EventId (String) to LogTableEntry objects.
     * - If the current LogEntryJson.eventID doesn't exist in the hashmap, then it's the first time the event itself has been encountered, so add it to the hashmap but
     * with the eventDuration == timestamp, and the alert == false (Default values necessary for later computation.)
     * - If the current LogEntryJson.eventID does exist in the hashmap, then the event itself has been encountered before, so compute the necessary duration and alert values by comparing to the existing value in the hashmap,
     * and put the new LogTableEntry object in place of the old one.
     * - Since the task specified all events have exactly and only 2 events, the modulus difference is used - this is because it doesn't matter if it's a Start or Finish event, as since finish timestamps will always be after start timestamps,
     * even if a finish event was encountered first, a modulus subtraction would provide the correct difference between the start and finish time still.
     *
     * @return boolean - parse completed or not (for extensibility in future)
     */
    public boolean parseLogFile(){

        try (BufferedReader br = new BufferedReader(new FileReader(pathToLogFile))){
            logTableEntryMap = new HashMap<>();
            String line;
            ObjectMapper objectMapper = new ObjectMapper();

            while ((line = br.readLine()) != null){
                LogEntryJson JSONentry = objectMapper.readValue(line, LogEntryJson.class);

                //Computational logic was placed here for sake of maximum efficiency, but in an ideal abstracted world you would Parse first and then do relevant computations on entries seperately but this would be at the cost of efficiency (multiple FOR loops of the full data set).
                if (logTableEntryMap.get(JSONentry.getId()) == null) {
                    logTableEntryMap.put(JSONentry.getId(), new LogTableEntry(JSONentry.getId(), JSONentry.getTimestamp(), JSONentry.getType(), JSONentry.getHost(),FALSE));
                } else {
                    int updatedDuration = Math.abs(logTableEntryMap.get(JSONentry.getId()).getEventDuration() - JSONentry.getTimestamp());
                    boolean updatedAlert = (updatedDuration > 4) ? TRUE : FALSE;
                    logTableEntryMap.put(JSONentry.getId(), new LogTableEntry(JSONentry.getId(), updatedDuration, JSONentry.getType(), JSONentry.getHost(),updatedAlert));
                }
            }
        } catch (Exception e){
            logger.error("Error occurred during parsing of log file: " + e.getMessage());
            return false;
        }
        return true;
    }

    public HashMap<String,LogTableEntry> getLogTableEntryMap(){
        return logTableEntryMap;
    }

    public String getPathToLogFile() {
        return pathToLogFile;
    }

    public void setPathToLogFile(String pathToLogFile) {
        this.pathToLogFile = pathToLogFile;
    }
}
