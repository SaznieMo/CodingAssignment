package logs;

/**
 * LogTableEntry class representing entries in the SQL table.
 */
public class LogTableEntry{

    private String id;
    private int eventDuration;
    private String type;
    private String host;
    private boolean alert;

    public LogTableEntry(String id, int eventDuration, String type, String host, boolean alert) {
        this.id = id;
        this.eventDuration = eventDuration;
        this.type = type;
        this.host = host;
        this.alert = alert;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(int eventDuration) {
        this.eventDuration = eventDuration;
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

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "LogTableEntry{" +
                "eventID='" + id + '\'' +
                ", eventDuration=" + eventDuration +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", alert=" + alert +
                '}';
    }
}
