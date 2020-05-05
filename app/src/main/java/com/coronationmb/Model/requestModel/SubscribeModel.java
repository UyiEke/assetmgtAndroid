package com.coronationmb.Model.requestModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SubscribeModel implements Serializable {
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("appid")
    private String appid;
    @JsonProperty("email")
    private String email;
    @JsonProperty("productcode")
    private String productcode;
    @JsonProperty("profile")
    private String profile;

    @JsonProperty("custID")
    private String custid;

    @JsonProperty("deviceid")
    private String deviceid;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("narration")
    private String narration;

    private String bankaccount;
    private String bankcode;

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }


    /*
  "": 67000,
          "appCallBackUrl": "http://localhost:8900",
          "": "cmbgrp",
          "bankAccount": "1990001234",
          "bankcode": "060001",
          "creditAccountMaster": "0",
          "creditAccountSub": "000940",
          "currency": "NGN",
          "debitAccountMaster": "288000",
          "debitAccountSub": "0",
          "": "sakinola@coronationmb.com",
          "narration": "Test funding of account",
          "": "CMMFUND",
          "": "am",
          "reference": "refer09899ewrquefwh2"

          */

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
