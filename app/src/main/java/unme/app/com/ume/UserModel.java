package unme.app.com.ume;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserModel {
    private String name;
    private String surename;
    private String phone;
    private String email;
    private String adddress;
    private String select1;
    private String select2;
    private String select3;


    public UserModel(String name, String surename, String phone, String email, String adddress, String select1, String select2, String select3) {
        this.name = name;
        this.surename = surename;
        this.phone = phone;
        this.email = email;
        this.adddress = adddress;
        this.select1 = select1;
        this.select2 = select2;
        this.select3 = select3;


    }

    public String getName(){
       return name;
    }

    public String getSurename(){
        return surename;
    }

    public String getPhone(){
        return phone;
    }

    public String getEmail(){
        return email;
    }

    public String getAdddress(){
        return adddress;
    }

    public String getSelect1(){
        return select1;
    }

    public String getSelect2(){
        return select2;
    }

    public String getSelect3(){
        return select3;
    }



    public void setName(String name){
        this.name = name;
    }

    public void setSurename(String surename){
        this.surename = surename;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAdddress(String adddress) {
        this.adddress = adddress;
    }

    public void setSelect1(String select1){
        this.select1 = select1;
    }

    public void setSelect2(String select2){
        this.select2 = select2;
    }

    public void setSelect3(String select3){
        this.select2 = select3;
    }

}
