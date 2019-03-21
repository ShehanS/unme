package unme.app.com.ume.model;

public class Countdown {
    private String event_name;
    private Double event_time;
    private String userId;




    public Countdown(){}


    public Countdown(String userId, String event_name, Double event_time) {
       this.userId = userId;
        this.event_name = event_name;
        this.event_time = event_time;
    }

    public void setEvent_name(String event_name){
        this.event_name = event_name;
    }

    public void setEvent_time(Double event_time){
        this.event_time = event_time;
    }

    public void setUserId (String userId){
        this.userId = userId;
    }

    public String getEvent_name(){
        return event_name;
    }

    public Double getEvent_time(){
        return event_time;
    }

    public String getUserId(){
        return userId;
    }
}
