package com.example.isociable.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {


    public MutableLiveData<Boolean> gekauft1 = new MutableLiveData<Boolean>();

    /*   private MutableLiveData<String> mText;
    


   *//* public HomeViewModel() {


        *//**//*mText = new MutableLiveData<>();*//**//*

  

    *//**//*    mText.setValue("@string/hello_nature_lover");*//**//*
    }
*//*




    public LiveData<String> getText() {
        return mText;
    }*/

    public HomeViewModel() {
        gekauft1.setValue(false);
    }

    public LiveData<Boolean> setgekauft1(){
        gekauft1.setValue(true);
        return gekauft1;
    }
    public LiveData<Boolean> getgekauft1(){ return gekauft1;}
    
}