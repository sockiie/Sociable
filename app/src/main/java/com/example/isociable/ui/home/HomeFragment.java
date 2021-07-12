package com.example.isociable.ui.home;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.isociable.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    ImageView imageView;
    LayerDrawable enviormentdrawable;
    Drawable item;
    ProgressBar progessBar;
   // UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        progessBar = root.findViewById(R.id.determinateBar);


        // Referenz auf LayerList
        enviormentdrawable = (LayerDrawable) getResources().getDrawable(R.drawable.layerlistimage);

        // Referenz auf ein Item in der Layerlist
        item = enviormentdrawable.findDrawableByLayerId(R.id.gekauft1);

        // Wenn Pflanze gekauft, wird Alpha gesetzt
        homeViewModel.getgekauft1().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean==true){

                    item.setAlpha(255);
                }else{

                    item.setAlpha(0);
                }
            }
        });



        Button button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener((v) -> {

            homeViewModel.setgekauft1().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean==true){

                        item.setAlpha(255);
                    }else{

                        item.setAlpha(0);
                    }

                }
            });
            Log.d("onclick", "click");


        });

        imageView = root.findViewById(R.id.imageView);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.layerlistimage));
/*        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/



        loadStatistics();



        return root;
    }


    public void loadStatistics() {
        UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  System.currentTimeMillis() - 1000*3600*24,  System.currentTimeMillis());
        appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 0).collect(Collectors.toList());

        // Group the usageStats by application and sort them by total time in foreground
        if (appList.size() > 0) {
            Map<String, UsageStats> mySortedMap = new TreeMap<>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getPackageName(), usageStats);
            }
            Log.d("loadStatistcits", "geladen");
            showAppsUsage(mySortedMap);
        }
    }

    public void showAppsUsage(Map<String, UsageStats> mySortedMap) {

        List<UsageStats> usageStatsList = new ArrayList<>(mySortedMap.values());
        // get total time of apps usage to calculate the usagePercentage for each app
        long totalTime = usageStatsList.stream().map(UsageStats::getTotalTimeInForeground).mapToLong(Long::longValue).sum();
        // 120000 sind 2 stunden zum testen
        int timelimit = 120000;
        int usagePercentage = (int) (  (totalTime/timelimit)*100);

        progessBar.setProgress(usagePercentage);

    }
}
