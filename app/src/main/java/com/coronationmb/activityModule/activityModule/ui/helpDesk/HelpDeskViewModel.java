package com.coronationmb.activityModule.activityModule.ui.helpDesk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpDeskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpDeskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}