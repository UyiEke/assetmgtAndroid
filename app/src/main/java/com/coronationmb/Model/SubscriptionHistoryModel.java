package com.coronationmb.Model;

import java.io.Serializable;

public class SubscriptionHistoryModel implements Serializable {
    private String CustAID;
    private String FundCode;
    private String FundName;
    private String Quantity;
    private String UnitPrice;
    private String Total;
    private String TxnType;
    private String UserID;
    private String TxnDate;


    public String getCustAID() {
        return CustAID;
    }

    public void setCustAID(String custAID) {
        CustAID = custAID;
    }

    public String getFundCode() {
        return FundCode;
    }

    public void setFundCode(String fundCode) {
        FundCode = fundCode;
    }

    public String getFundName() {
        return FundName;
    }

    public void setFundName(String fundName) {
        FundName = fundName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getTxnType() {
        return TxnType;
    }

    public void setTxnType(String txnType) {
        TxnType = txnType;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getTxnDate() {
        return TxnDate;
    }

    public void setTxnDate(String txnDate) {
        TxnDate = txnDate;
    }
}
