package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class PortfolioTransactionModel implements Serializable {
    private String valueDate;
    private String fundName;
    private String subscription;
    private String price;
    private String mktValue;

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMktValue() {
        return mktValue;
    }

    public void setMktValue(String mktValue) {
        this.mktValue = mktValue;
    }
}
