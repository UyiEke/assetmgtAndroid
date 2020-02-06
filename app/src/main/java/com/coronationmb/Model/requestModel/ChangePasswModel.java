package com.coronationmb.Model.requestModel;

import java.io.Serializable;

public class ChangePasswModel implements Serializable {

    private String currentPassword;
    private String custAID;
    private String newPassword;
    private String profile;
    private boolean pwdChangeRequired;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getCustAID() {
        return custAID;
    }

    public void setCustAID(String custAID) {
        this.custAID = custAID;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isPwdChangeRequired() {
        return pwdChangeRequired;
    }

    public void setPwdChangeRequired(boolean pwdChangeRequired) {
        this.pwdChangeRequired = pwdChangeRequired;
    }
}
