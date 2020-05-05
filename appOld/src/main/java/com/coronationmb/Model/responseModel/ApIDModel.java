package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class ApIDModel implements Serializable {

    private String data;
    private String message;
    private int status;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
