package com.coronationmb.Model;

import java.io.Serializable;
import java.util.List;

public class BankModel implements Serializable {

    private String statusCode;
    private int statusCodeValue;
    private List<BankDetails> body;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCodeValue() {
        return statusCodeValue;
    }

    public void setStatusCodeValue(int statusCodeValue) {
        this.statusCodeValue = statusCodeValue;
    }


    public List<BankDetails> getBody() {
        return body;
    }

    public void setBody(List<BankDetails> body) {
        this.body = body;
    }

    public class BankDetails implements Serializable {

        private String institutionCode;
        private String institutionName;
        private String category;
        private String sortCode;

        public String getInstitutionCode() {
            return institutionCode;
        }

        public void setInstitutionCode(String institutionCode) {
            this.institutionCode = institutionCode;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSortCode() {
            return sortCode;
        }

        public void setSortCode(String sortCode) {
            this.sortCode = sortCode;
        }
    }

}