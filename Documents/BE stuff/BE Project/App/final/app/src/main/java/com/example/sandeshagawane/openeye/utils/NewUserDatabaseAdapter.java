package com.example.sandeshagawane.openeye.utils;

/**
 * Created by sandeshagawane on 02/03/18.
 */

public class NewUserDatabaseAdapter {

    private String userID,uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode,MobileNo,email_ID;

    public NewUserDatabaseAdapter(){

    }

    public NewUserDatabaseAdapter(String userID, String uid, String name, String gender, String yearOfBirth, String careOf, String villageTehsil, String postOffice, String district, String state, String postCode, String MobileNo,String email_ID) {
        this.userID = userID;
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
        this.careOf = careOf;
        this.villageTehsil = villageTehsil;
        this.postOffice = postOffice;
        this.district = district;
        this.state = state;
        this.postCode = postCode;
        this.MobileNo = MobileNo;
        this.email_ID = email_ID;
    }

    public String getUserID() {return userID;}

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public String getCareOf() {
        return careOf;
    }

    public String getVillageTehsil() {
        return villageTehsil;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getMobileNo() {return MobileNo; }

    public String getEmail_ID() {return email_ID; }

    public void setUserID(String UserID) {
        this.userID = UserID;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public void setVillageTehsil(String villageTehsil) {
        this.villageTehsil = villageTehsil;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setMobileNo() {this.MobileNo = MobileNo; }

    public void setEmail_ID() {this.email_ID = email_ID; }

    @Override
    public String toString() {
        return "NewUserDatabaseAdapter{" +
                "userID='" + userID + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", yearOfBirth='" + yearOfBirth + '\'' +
                ", careOf='" + careOf + '\'' +
                ", villageTehsil='" + villageTehsil + '\'' +
                ", postOffice='" + postOffice + '\'' +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                ", postCode='" + postCode + '\'' +
                ", MobileNo='" + MobileNo + '\'' +
                ", email_ID='" + email_ID + '\'' +
                '}';
    }

}
