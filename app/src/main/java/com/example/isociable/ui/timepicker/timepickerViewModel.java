package com.example.isociable.ui.timepicker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class timepickerViewModel extends ViewModel {


    public MutableLiveData<Boolean> timechoosen = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> appchoosen = new MutableLiveData<Boolean>();

    /*   private MutableLiveData<String> mText;



     *//* public HomeViewModel() {


     *//**//*mText = new MutableLiveData<>();*//**//*



     *//**//*    mText.setValue("@string/hello_nature_lover");*//**//*
    }
*//*




    public LiveData<String> getText() {
        return mText;
    }*/

    public timepickerViewModel() {
        timechoosen.setValue(false);
        appchoosen.setValue(false);
    }


    public LiveData<Boolean> settimechoosen(){
        timechoosen.setValue(true);
        return appchoosen;
    }
    public LiveData<Boolean> settappchoosen(){
        appchoosen.setValue(true);
        return timechoosen;
    }

    public LiveData<Boolean> gettimechoosen(){ return timechoosen;}
    public LiveData<Boolean> gettappchoosen(){ return appchoosen;}

}