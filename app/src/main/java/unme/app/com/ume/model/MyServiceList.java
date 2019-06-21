package unme.app.com.ume.model;

public class MyServiceList {
    private String id;
    private String serviceID;
    private String userID;
    private String company;
    private String category;
    private String message;
    private String contact;
    private String email;
    private String website;
    private Double price;
    private String name;
    private String address;
    private String date;
    private boolean confirm=false;
    private String status;
    private Double budget = 0.00;
    private String serviceUserId;

    public MyServiceList(){}

    public MyServiceList(String id, String serviceID,String userID, String company, String category, String message, String contact, String email, String website, Double price, String name, String address, String date, boolean confirm, String status, Double budget,String serviceUserId) {
        this.id = id;
        this.serviceID = serviceID;
        this.userID = userID;
        this.company = company;
        this.category = category;
        this.message = message;
        this.contact = contact;
        this.email = email;
        this.website = website;
        this.price = price;
        this.name = name;
        this.address = address;
        this.date = date;
        this.confirm = confirm;
        this.status = status;
        this.budget = budget;
        this.serviceUserId = serviceUserId;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceUserId() {
        return serviceUserId;
    }

    public void setServiceUserId(String serviceUserId) {
        this.serviceUserId = serviceUserId;
    }
}
