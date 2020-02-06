package com.coronationmb.Model.requestModel;

import java.io.Serializable;

public class ViewTradeHistory implements Serializable {

    private String custAid;
    private String profile;
    private int viewType;

    public String getCustAid() {
        return custAid;
    }

    public void setCustAid(String custAid) {
        this.custAid = custAid;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
