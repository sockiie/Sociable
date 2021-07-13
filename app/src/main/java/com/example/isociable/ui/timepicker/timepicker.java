package com.example.isociable.ui.timepicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.isociable.MainActivity;
import com.example.isociable.R;
import com.example.isociable.ui.home.HomeViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Observable;


public class timepicker extends Fragment {
    Button confirmButton;
    TimePicker timerPicker;
    TextView textView;
    ToggleButton toggleButtoninsta,toggleButtonface,toggleButtonsnap;
    ImageButton instagram,facebook,snapchat;
    SharedPreferences sp;
    int t1hour,t1minute;
    Boolean timeChanged = false;
    timepickerViewModel timepickerViewModel;

    public timepicker() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        timepickerViewModel =
                new ViewModelProvider(this).get(timepickerViewModel.class);
        View view =inflater.inflate(R.layout.fragment_timepicker, container, false);
        textView = view.findViewById(R.id.textView2);
        instagram = view.findViewById(R.id.imageButton);

        confirmButton =  view.findViewById(R.id.confirmButton);
        timerPicker = view.findViewById(R.id.timepicker);
        timerPicker.setIs24HourView(true);
        toggleButtoninsta = view.findViewById(R.id.toggleButtoninsta);
        toggleButtonsnap = view.findViewById(R.id.toggleButtonsnap);
        toggleButtonface = view.findViewById(R.id.toggleButtonface);

        timerPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timepickerViewModel.settimechoosen().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean==true){
                            confirmButton.setEnabled(true);
                            confirmButton.setAlpha(1);

                        }
                    }
                });

                t1hour=hourOfDay;
                t1minute=minute;
/*                Calendar calender = Calendar.getInstance();
                calender.set(0,0,0,t1hour,t1minute,0);
                long millis = calender.getTimeInMillis();*/
                sp = getActivity().getSharedPreferences("time", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("hour", hourOfDay);
                editor.commit();
                editor.putInt("minute",minute);
                editor.commit();
                String mindAndSex = "You selected timelimit is "+hourOfDay + ":" + minute+" hours";
                textView.setText(mindAndSex);
            }

        });

        toggleButtoninsta.setOnClickListener((v) -> {
            if(toggleButtoninsta.isChecked()){
                timepickerViewModel.settappchoosen().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean==true) {
                            confirmButton.setEnabled(true);
                            confirmButton.setAlpha(1);
                        }
                    }
                });
                toggleButtoninsta.setAlpha(1);
                SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Instagram",true);
                editor.commit();
                Toast.makeText(getActivity(),"Instagram selected",Toast.LENGTH_LONG).show(); }

            else{




                toggleButtoninsta.setAlpha((float) 0.5);
                SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Instagram",false);
                editor.commit();
                Toast.makeText(getActivity(),"Instagram deselected",Toast.LENGTH_LONG).show();


            }
        });
        toggleButtonsnap.setOnClickListener((v) -> {
            if(toggleButtonsnap.isChecked()){

                toggleButtonsnap.setAlpha(1);
                SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Snapchat",true);
                editor.commit();
                Toast.makeText(getActivity(),"Snapchat selected",Toast.LENGTH_LONG).show(); }

            else{




                toggleButtonsnap.setAlpha((float) 0.5);
                SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Snapchat",false);
                editor.commit();
                Toast.makeText(getActivity(),"Snapchat deselected",Toast.LENGTH_LONG).show();


            }
        });
        toggleButtonface.setOnClickListener((v) -> {
            if(toggleButtonface.isChecked()){

                toggleButtonface.setAlpha(1);

                SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Facebook",true);
                editor.commit();
                Toast.makeText(getActivity(),"Facebook selected",Toast.LENGTH_LONG).show(); }

            else{




                toggleButtonface.setAlpha((float) 0.5);
                SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("Facebook",false);
                editor.commit();
                Toast.makeText(getActivity(),"Facebook deselected",Toast.LENGTH_LONG).show();


            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);
        if(timeChanged==true) {
            confirmButton.setEnabled(true);
        }
        Button button = view.findViewById(R.id.confirmButton);
        button.setOnClickListener((v) -> {

            navController.navigate(R.id.action_timepicker2_to_blankFragment2);

        });
    }

    public void onStart() {
        super.onStart();
        if(timeChanged==true) {
            confirmButton.setEnabled(true);
    }}
}