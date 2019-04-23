package unme.app.com.ume.model;

public class Service {
    private String userId;
    private String serviceID;
    private String company;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;
    private String address;
    private String website;
    private String category;
    private String date;
    private double price;
    private String message;
    private boolean fliterKey1;
    private boolean fliterKey2;

    public Service(){}

    public Service(String userId, String serviceID, String company, String firstName, String lastName, String contactNumber, String email, String address, String website, String category, String date, double price, String message, boolean fliterKey1, boolean fliterKey2) {
        this.userId = userId;
        this.serviceID = serviceID;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.website = website;
        this.category = category;
        this.date = date;
        this.price = price;
        this.message = message;
        this.fliterKey1 = fliterKey1;
        this.fliterKey2 = fliterKey2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFliterKey1() {
        return fliterKey1;
    }

    public void setFliterKey1(boolean fliterKey1) {
        this.fliterKey1 = fliterKey1;
    }

    public boolean isFliterKey2() {
        return fliterKey2;
    }

    public void setFliterKey2(boolean fliterKey2) {
        this.fliterKey2 = fliterKey2;
    }
}
