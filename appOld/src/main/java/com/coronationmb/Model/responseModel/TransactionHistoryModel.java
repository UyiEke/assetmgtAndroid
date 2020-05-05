package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class TransactionHistoryModel implements Serializable {
    private String date;
    private String transactionRef;
    private String amount;
    private String approvedAmt;
    private String currency;
    private String status;
    private  String responseDesc;
    private String code;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApprovedAmt() {
        return approvedAmt;
    }

    public void setApprovedAmt(String approvedAmt) {
        this.approvedAmt = approvedAmt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
