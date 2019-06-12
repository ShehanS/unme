package unme.app.com.ume.model;

public class Event {
    private String user_id;
    private String event_name;
    private String event_time;

    public Event(String user_id, String event_name, String event_time) {
        this.user_id = user_id;
        this.event_name = event_name;
        this.event_time = event_time;
    }

    private Event(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }
}
