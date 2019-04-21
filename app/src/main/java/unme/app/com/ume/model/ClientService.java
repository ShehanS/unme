package unme.app.com.ume.model;

public class ClientService {
    private int id;
    private String company;
    private String category;
    private String serviceID;
    private int rate;


    public ClientService(int id, String serviceID, String company, String category, int rate) {
        this.id = id;
        this.serviceID = serviceID;
        this.company = company;
        this.category = category;
        this.rate = rate;

    }


    public int getId() {
        return id;
    }

    public String getServiceID(){
        return serviceID;
    }

    public void setServiceID(String serviceID){
        this.serviceID = serviceID;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }



}
