package unme.app.com.ume.model;

public class RequestClientList {
    private String userID;
    private String name;
    private String contact;
    private String service;
    private String serviceID;

private RequestClientList(){}
    public RequestClientList(String userID, String name, String contact, String service, String serviceID) {
        this.userID = userID;
        this.name = name;
        this.contact = contact;
        this.service = service;
        this.serviceID = serviceID;


    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getContact() {
        return contact;

    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
