package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class AssetProduct implements Serializable {
    private String FundCode;
    private String FundName;
    private String MktValue;

    private String FundType;
    private String UnitPrice;
    private String BidPrice;
    private String OfferPrice;
    private String MinInvest;

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

    public String getMktValue() {
        return MktValue;
    }

    public void setMktValue(String mktValue) {
        MktValue = mktValue;
    }

    public String getFundType() {
        return FundType;
    }

    public void setFundType(String fundType) {
        FundType = fundType;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getBidPrice() {
        return BidPrice;
    }

    public void setBidPrice(String bidPrice) {
        BidPrice = bidPrice;
    }

    public String getOfferPrice() {
        return OfferPrice;
    }

    public void setOfferPrice(String offerPrice) {
        OfferPrice = offerPrice;
    }

    public String getMinInvest() {
        return MinInvest;
    }

    public void setMinInvest(String minInvest) {
        MinInvest = minInvest;
    }
}
