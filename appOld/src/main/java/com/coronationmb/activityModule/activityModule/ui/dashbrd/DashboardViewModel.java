package com.coronationmb.activityModule.activityModule.ui.dashbrd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.coronationmb.Model.responseModel.AssetProduct;
import com.coronationmb.Model.responseModel.PortFolioModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    // ArrayList<AssetProduct>

    private MutableLiveData<String> mText;

    private MutableLiveData<List<PortFolioModel>> portfolioval;//= new MutableLiveData<List<PortFolioModel>>();

    private MutableLiveData<List<AssetProduct>> productList;



    public LiveData<List<PortFolioModel>> getPortfolio() {
        return portfolioval;
    }

    public LiveData<List<AssetProduct>> getProductList() {
        return productList;
    }


    public void setPortfolio(List<PortFolioModel> portfolio) {
        if(portfolioval==null){
            this.portfolioval  = new MutableLiveData<List<PortFolioModel>>();
        }
        this.portfolioval.setValue(portfolio);
    }

    public void setProductList(List<AssetProduct> productList) {

        if(this.productList==null){
            this.productList  = new MutableLiveData<List<AssetProduct>>();
        }
        this.productList.setValue(productList);

    }


    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        this.portfolioval  = new MutableLiveData<List<PortFolioModel>>();

        this.productList=new MutableLiveData<List<AssetProduct>>();;

        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}