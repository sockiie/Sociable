package com.example.isociable.ui.timepicker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.isociable.R;

import java.text.DateFormat;
import java.util.Calendar;


public class timepicker extends Fragment {
    Button confirmButton;
    TimePicker timerPicker,tvTimer1,tvTimer2;
    TextView textView;
    int t1hour,t1minute,t2hour,t2minute;

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
        View view =inflater.inflate(R.layout.fragment_timepicker, container, false);
        textView = view.findViewById(R.id.textView2);
        confirmButton =  view.findViewById(R.id.confirmButton);
        timerPicker = view.findViewById(R.id.timepicker);
        timerPicker.setIs24HourView(true);

        timerPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                t1hour=hourOfDay;
                t1minute=minute;
                Calendar calender = Calendar.getInstance();
                calender.set(0,0,0,t1hour,t1minute,0);
                long millis = (calender.getTimeInMillis()/1000);
                String mindAndSex = "You selected timelimit is "+hourOfDay + ":" + minute+" hours" + millis;
                textView.setText(mindAndSex);
            }

        });

        timerPicker.getMinute();
        // Inflate the layout for this fragment
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);

        Button button = view.findViewById(R.id.confirmButton);
        button.setOnClickListener((v) -> {

            navController.navigate(R.id.action_timepicker2_to_blankFragment2);

        });
    }
    public void onDateSet(){
        Calendar c = Calendar.getInstance();


    }
}