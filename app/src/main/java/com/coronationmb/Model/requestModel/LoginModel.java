package com.coronationmb.Model.requestModel;

import java.io.Serializable;

public class LoginModel implements Serializable {
    private String CustUserID;
    private String PWD;
    private String profile;

    public String getCustUserID() {
        return CustUserID;
    }

    public void setCustUserID(String custUserID) {
        CustUserID = custUserID;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
