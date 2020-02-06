package com.coronationmb.Model.requestModel;

import java.io.Serializable;

public class SignUP implements Serializable {
    private String surname;
    private String firstname;
    private String otherName;
    private String phoneNumber;
    private String email;

    public SignUP(String surname, String firstname, String otherName, String phoneNumber, String email) {
        this.surname = surname;
        this.firstname = firstname;
        this.otherName = otherName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
