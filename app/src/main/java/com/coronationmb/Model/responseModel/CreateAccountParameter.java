package com.coronationmb.Model.responseModel;

import java.io.Serializable;
import java.util.List;

public class CreateAccountParameter implements Serializable {
    private List<String> country;
    private List<String> idType;
    private List<String> bank;

    private List<String> state;

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(List<String> country) {
        this.country = country;
    }

    public List<String> getIdType() {
        return idType;
    }

    public void setIdType(List<String> idType) {
        this.idType = idType;
    }

    public List<String> getBank() {
        return bank;
    }

    public void setBank(List<String> bank) {
        this.bank = bank;
    }

    public List<String> getState() {
        return state;
    }

    public void setState(List<String> state) {
        this.state = state;
    }

}
