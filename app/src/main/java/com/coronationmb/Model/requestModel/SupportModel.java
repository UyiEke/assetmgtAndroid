package com.coronationmb.Model.requestModel;

import java.io.Serializable;

public class SupportModel implements Serializable {

    private String custAid;
    private String message;
    private String profile;
    private String subject;

    public String getCustAid() {
        return custAid;
    }

    public void setCustAid(String custAid) {
        this.custAid = custAid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
