package com.coronationmb.activityModule.activityModule.ui.fundtransfer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FundTransferViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FundTransferViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}