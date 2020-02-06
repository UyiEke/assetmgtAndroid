package com.coronationmb.Model.requestModel;

import java.io.Serializable;

public class FundAcct implements Serializable {
   private Double amount;
   private String custAid;
   private String fundCode;
   private String fundName;
   private String quantity;
   private int txnType;
   private String unitPrice;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCustAid() {
        return custAid;
    }

    public void setCustAid(String custAid) {
        this.custAid = custAid;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getTxnType() {
        return txnType;
    }

    public void setTxnType(int txnType) {
        this.txnType = txnType;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
