package logs;


/**
 * LogEntryJson class representing a JSON object for parsing of logfile.txt entries
 */
public class LogEntryJson{

    private String id;
    private String state;
    private String type;
    private String host;
    private int timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogEntryJson{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
