package unme.app.com.ume.model;

public class Budget {
    private String userID;
    private Double budget;
    private String date;

    private Budget(){}
    public Budget(String userID, Double budget, String date) {
        this.userID = userID;
        this.budget = budget;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
