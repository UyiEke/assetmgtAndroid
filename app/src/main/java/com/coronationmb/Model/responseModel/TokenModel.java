package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class TokenModel implements Serializable {

    private String generatedToken;
    private String message;
    private int status;

    public String getGeneratedToken() {
        return generatedToken;
    }

    public void setGeneratedToken(String generatedToken) {
        this.generatedToken = generatedToken;
    }

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
}
