package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class ProfileModel implements Serializable {

    private String Symbol;
    private String Qty;
    private String MostRecentPrice;
    private String MktValue;
    private String CostBasis;
    private String Chg;
    private String ChgSincePurchase;

    public ProfileModel() {
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getMostRecentPrice() {
        return MostRecentPrice;
    }

    public void setMostRecentPrice(String mostRecentPrice) {
        MostRecentPrice = mostRecentPrice;
    }

    public String getMktValue() {
        return MktValue;
    }

    public void setMktValue(String mktValue) {
        MktValue = mktValue;
    }

    public String getCostBasis() {
        return CostBasis;
    }

    public void setCostBasis(String costBasis) {
        CostBasis = costBasis;
    }

    public String getChg() {
        return Chg;
    }

    public void setChg(String chg) {
        Chg = chg;
    }

    public String getChgSincePurchase() {
        return ChgSincePurchase;
    }

    public void setChgSincePurchase(String chgSincePurchase) {
        ChgSincePurchase = chgSincePurchase;
    }
}
