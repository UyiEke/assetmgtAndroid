package com.coronationmb.Model.requestModel;

import java.io.Serializable;

public class CustomerInfoModel implements Serializable {

    private String custAID;
    private String infoFNID;
    private String profile;

    public String getCustAID() {
        return custAID;
    }

    public void setCustAID(String custAID) {
        this.custAID = custAID;
    }

    public String getInfoFNID() {
        return infoFNID;
    }

    public void setInfoFNID(String infoFNID) {
        this.infoFNID = infoFNID;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
