package com.coronationmb.Model.responseModel;

import java.io.Serializable;

public class ImageDetails implements Serializable {
private int id;
private String custid;
private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
