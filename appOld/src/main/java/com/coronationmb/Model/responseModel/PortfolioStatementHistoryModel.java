package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class PortfolioStatementHistoryModel implements Serializable {

    private String effectiveDate;
  //  private String accountID;
  //  private String sub;
   // private String ledgerType;
    private String amount;
    private String description;
    private String transtype;
  //  private String sysRef;
  //  private String Ref01;
  //  private String Ref02;
  //  private String ID;
   // private String fiscalyear;
  //  private String batchNo;
   // private String txnDate;
   // private String branchCode;


    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
