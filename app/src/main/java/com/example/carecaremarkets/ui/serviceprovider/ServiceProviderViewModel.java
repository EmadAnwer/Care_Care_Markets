package com.example.carecaremarkets.ui.serviceprovider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServiceProviderViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ServiceProviderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Service Provider fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}