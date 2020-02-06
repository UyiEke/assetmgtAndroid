package com.coronationmb.activityModule.activityModule.ui.NewRedemption;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewRedemptionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewRedemptionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}