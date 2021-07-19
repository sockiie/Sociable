package com.example.isociable;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    AppCompatButton button,button2;
    public BottomSheetFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        button = root.findViewById(R.id.timer);
        button2 = root.findViewById(R.id.coreconcept);
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);




        button.setOnClickListener((v) -> {
            Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_bottomSheetFragment_to_timepicker2);
        });
        button2.setOnClickListener((v) -> {
            Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_bottomSheetFragment_to_coreconcept2);
        });
    }
}