package com.coronationmb.activityModule.activityModule.ui.NewSubscription;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewSubscriptionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewSubscriptionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}