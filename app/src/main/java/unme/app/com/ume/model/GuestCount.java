package unme.app.com.ume.model;

public class GuestCount {
    private int count;
    private String userId;
    private String date;

    public GuestCount(int count, String userId, String date) {
        this.count = count;
        this.userId = userId;
        this.date = date;
    }

    public GuestCount(){}

    public int getCount(){
        return count;
    }

    public String getUserId(){
        return userId;
    }

    public String getDate(){
        return date;
    }


    public void setCount(int count){
        this.count = count;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setDate(String date){
        this.date = date;


    }

}
