package com.coronationmb.activityModule.activityModule.ui.redemption;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RedemptionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RedemptionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}