package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class CmbResponse implements Serializable {
    private String accountName;
    private String message;
    private String status;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
