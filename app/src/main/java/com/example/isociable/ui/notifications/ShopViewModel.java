package com.example.isociable.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopViewModel extends ViewModel {


    public MutableLiveData<Boolean> gekauft1 = new MutableLiveData<Boolean>();

    public ShopViewModel() {
        gekauft1.setValue(false);
    }

    public LiveData<Boolean> setgekauft1(){
        gekauft1.setValue(true);
        return gekauft1;
    }

    public LiveData<Boolean> getgekauft1(){
        return gekauft1;
    }
}