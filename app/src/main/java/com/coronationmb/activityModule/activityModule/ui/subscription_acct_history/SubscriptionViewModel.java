package com.coronationmb.activityModule.activityModule.ui.subscription_acct_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubscriptionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SubscriptionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}