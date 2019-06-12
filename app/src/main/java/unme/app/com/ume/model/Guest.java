package unme.app.com.ume.model;

public class Guest {
    private String userId;
    private String date;
    private String name;
    private String contact;
    private int count;
    private String priority;

    public Guest(){}

    public Guest(String userId, String date, String name, String contact, int count, String priority) {
        this.userId = userId;
        this.date = date;
        this.name = name;
        this.contact = contact;
        this.count = count;
        this.priority = priority;
    }

    public String getUserId() {
        return userId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public int getCount() {
        return count;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
