package unme.app.com.ume.model;

public class ClientRequest {
    private String userId;
    private String name;
    private String contact;
    private String email;
    private String web;
    private String service;
    private String serviceId;
    private boolean requestStatus;
    private String eventDate;
    private String status;
    private String serviceUserID;


    public ClientRequest(String userId, String name, String contact, String email, String web, String service, String serviceId, boolean requestStatus, String eventDate, String status, String serviceUserID) {
        this.userId = userId;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.web = web;
        this.service = service;
        this.serviceId = serviceId;
        this.requestStatus = requestStatus;
        this.eventDate = eventDate;
        this.status = status;
        this.serviceUserID = serviceUserID;
    }
    private ClientRequest(){}
    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(boolean requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getServiceUserID() {
        return serviceUserID;
    }

    public void setServiceUserID(String serviceUserID) {
        this.serviceUserID = serviceUserID;
    }
}
