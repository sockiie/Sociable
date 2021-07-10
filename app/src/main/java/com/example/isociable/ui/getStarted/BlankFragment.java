package com.example.isociable.ui.getStarted;

import android.app.AppOpsManager;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.isociable.R;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;


public class BlankFragment extends Fragment {
    Button enableBtn, showBtn;
    TextView permissionDescriptionTv, usageTv;


    public BlankFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
       // final NavController navController = Navigation.findNavController(view);
        enableBtn = view.findViewById(R.id.enable_btn);
        showBtn =  view.findViewById(R.id.show_btn);
        permissionDescriptionTv =view.findViewById(R.id.permission_description_tv);

  /*      if (getGrantStatus()) {
            Log.d("STATE", "test2");
            showHideWithPermission();
            // showBtn.setOnClickListener(view -> loadStatistics());
        } else {
            showHideNoPermission();
            Log.d("STATE", "test3");
            enableBtn.setOnClickListener((v) -> {

                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                //showHideWithPermission();
            });

        }
*/

        return view;
    }

    public void onStart() {
        super.onStart();
        if (getGrantStatus()) {
            Log.d("STATE", "test2");
            showHideWithPermission();
            // showBtn.setOnClickListener(view -> loadStatistics());
        } else {
            showHideNoPermission();
            Log.d("STATE", "test3");
            enableBtn.setOnClickListener((v) -> {

                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                //showHideWithPermission();
            });

        }
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);

        Button button = view.findViewById(R.id.show_btn);
        button.setOnClickListener((v) -> {

            navController.navigate(R.id.action_blankFragment2_to_navigation_home);

        });
    }


    private boolean getGrantStatus() {
        AppOpsManager appOps = (AppOpsManager) getActivity().getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getActivity().getApplicationContext().getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (getActivity().getApplicationContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == MODE_ALLOWED);
        }
    }
    public void showHideNoPermission() {
        enableBtn.setVisibility(View.VISIBLE);
        permissionDescriptionTv.setVisibility(View.VISIBLE);
        showBtn.setVisibility(View.GONE);
//        usageTv.setVisibility(View.GONE);
        //appsList.setVisibility(View.GONE);

    }
    public void showHideWithPermission() {
        enableBtn.setVisibility(View.GONE);
        permissionDescriptionTv.setVisibility(View.GONE);
        showBtn.setVisibility(View.VISIBLE);
        //usageTv.setVisibility(View.GONE);
       // appsList.setVisibility(View.GONE);
    }

}