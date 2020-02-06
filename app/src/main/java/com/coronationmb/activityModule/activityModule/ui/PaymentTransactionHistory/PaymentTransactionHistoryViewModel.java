package com.coronationmb.activityModule.activityModule.ui.PaymentTransactionHistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentTransactionHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PaymentTransactionHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}