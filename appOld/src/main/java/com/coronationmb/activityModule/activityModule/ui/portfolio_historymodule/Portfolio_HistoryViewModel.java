package com.coronationmb.activityModule.activityModule.ui.portfolio_historymodule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Portfolio_HistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Portfolio_HistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}