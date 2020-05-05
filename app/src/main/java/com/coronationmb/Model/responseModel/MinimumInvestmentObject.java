package com.coronationmb.Model.responseModel;


import java.io.Serializable;
import java.util.List;

public class MinimumInvestmentObject implements Serializable {
    private String minimumAmount;
    private List<ProductObj> product;


    public String getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public List<ProductObj> getProduct() {
        return product;
    }

    public void setProduct(List<ProductObj> product) {
        this.product = product;
    }

    public static class ProductObj implements Serializable {
        private String productName;
        private String productCode;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }
    }
}