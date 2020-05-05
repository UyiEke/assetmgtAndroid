package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class PortFolioModel implements Serializable {
    private String FundName;
    private String FundCode;
    private String FundType;
    private String CustAID;
    private String TotalAssetValue;
    private String ValuationDate;

    public String getFundName() {
        return FundName;
    }

    public void setFundName(String fundName) {
        FundName = fundName;
    }

    public String getFundCode() {
        return FundCode;
    }

    public void setFundCode(String fundCode) {
        FundCode = fundCode;
    }

    public String getFundType() {
        return FundType;
    }

    public void setFundType(String fundType) {
        FundType = fundType;
    }

    public String getCustAID() {
        return CustAID;
    }

    public void setCustAID(String custAID) {
        CustAID = custAID;
    }

    public String getTotalAssetValue() {
        return TotalAssetValue;
    }

    public void setTotalAssetValue(String totalAssetValue) {
        TotalAssetValue = totalAssetValue;
    }

    public String getValuationDate() {
        return ValuationDate;
    }

    public void setValuationDate(String valuationDate) {
        ValuationDate = valuationDate;
    }
}
