package unme.app.com.ume.model;

public class MyBudgetList {
    private String service;
    private Double budget;
    private String userID;
    private String serviceID;

    private MyBudgetList(){}
    public MyBudgetList(String service, Double budget, String userID, String serviceID) {
        this.service = service;
        this.budget = budget;
        this.userID = userID;
        this.serviceID = serviceID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
