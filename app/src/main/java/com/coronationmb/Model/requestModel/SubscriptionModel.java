package com.coronationmb.Model.requestModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SubscriptionModel implements Serializable {
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("appCallBackUrl")
    private String appcallbackurl;
    @JsonProperty("appid")
    private String appid;
    @JsonProperty("bankAccount")
    private String bankaccount;
    @JsonProperty("bankcode")
    private String bankcode;
    @JsonProperty("creditAccountMaster")
    private String creditaccountmaster;
    @JsonProperty("creditAccountSub")
    private String creditaccountsub;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("debitAccountMaster")
    private String debitaccountmaster;
    @JsonProperty("debitAccountSub")
    private String debitaccountsub;

    @JsonProperty("email")
    private String email;
    @JsonProperty("narration")
    private String narration;
    @JsonProperty("productcode")
    private String productcode;
    @JsonProperty("profile")
    private String profile;
    @JsonProperty("reference")
    private String reference;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAppcallbackurl() {
        return appcallbackurl;
    }

    public void setAppcallbackurl(String appcallbackurl) {
        this.appcallbackurl = appcallbackurl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

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

    public String getCreditaccountmaster() {
        return creditaccountmaster;
    }

    public void setCreditaccountmaster(String creditaccountmaster) {
        this.creditaccountmaster = creditaccountmaster;
    }

    public String getCreditaccountsub() {
        return creditaccountsub;
    }

    public void setCreditaccountsub(String creditaccountsub) {
        this.creditaccountsub = creditaccountsub;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDebitaccountmaster() {
        return debitaccountmaster;
    }

    public void setDebitaccountmaster(String debitaccountmaster) {
        this.debitaccountmaster = debitaccountmaster;
    }

    public String getDebitaccountsub() {
        return debitaccountsub;
    }

    public void setDebitaccountsub(String debitaccountsub) {
        this.debitaccountsub = debitaccountsub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
