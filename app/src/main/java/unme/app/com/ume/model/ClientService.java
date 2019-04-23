package unme.app.com.ume.model;

public class ClientService {
    private int id;
    private String userID;
    private String company;
    private String category;
    private String serviceID;


    public ClientService(int id, String userID, String company, String category, String serviceID) {
        this.id = id;
        this.userID = userID;
        this.company = company;
        this.category = category;
        this.serviceID = serviceID;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUserID(){
        return userID;
    }

    public void setUserID(String userID){
        this.userID=userID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

}
