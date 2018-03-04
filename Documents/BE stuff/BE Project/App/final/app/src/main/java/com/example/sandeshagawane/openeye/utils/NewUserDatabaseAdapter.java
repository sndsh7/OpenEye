package com.example.sandeshagawane.openeye.utils;

/**
 * Created by sandeshagawane on 02/03/18.
 */

public class NewUserDatabaseAdapter {

    private String userID,uid,name,gender,yearOfBirth,careOf,villageTehsil,postOffice,district,state,postCode;

    public NewUserDatabaseAdapter(){

    }

    public NewUserDatabaseAdapter(String userID, String uid, String name, String gender, String yearOfBirth, String careOf, String villageTehsil, String postOffice, String district, String state, String postCode) {
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
    }

    public String getUserID() {
        return userID;
    }

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
}
