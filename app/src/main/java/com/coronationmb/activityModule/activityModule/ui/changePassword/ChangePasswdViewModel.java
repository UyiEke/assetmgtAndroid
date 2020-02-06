package com.coronationmb.activityModule.activityModule.ui.changePassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePasswdViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChangePasswdViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}