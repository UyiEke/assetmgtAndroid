package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class SupportResponseModel implements Serializable {

    private int status;
    private String message;
    private String outValue;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOutValue() {
        return outValue;
    }

    public void setOutValue(String outValue) {
        this.outValue = outValue;
    }
}
