package com.example.isociable.ui.home;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.isociable.App;
import com.example.isociable.GetStartedActivity;
import com.example.isociable.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    ImageView imageView;
    TextView left_time,usage_duration_tv;

    LayerDrawable enviormentdrawable;
    Drawable item;
    ProgressBar progessBar;
    Boolean Instagrambool;
    Boolean Facebookbool;
    Boolean Snapchatbool;
   // UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        usage_duration_tv = root.findViewById(R.id.usage_duration_tv);
        left_time = root.findViewById(R.id.left_time);
        progessBar = root.findViewById(R.id.determinateBar);
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
        Instagrambool = sp.getBoolean("Instagram",false);
        Facebookbool = sp.getBoolean("Facebook",false);
        Snapchatbool = sp.getBoolean("Snapchat",false);
        sp = getActivity().getApplicationContext().getSharedPreferences("time",Context.MODE_PRIVATE);
        usage_duration_tv.setText(sp.getInt("hour",0) + " : " + sp.getInt("minute",0));
        /*if(Instagram==true){
            Toast.makeText(getActivity(),"Instagram selected",Toast.LENGTH_LONG).show();
        }*/
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



        loadStatistics(Instagrambool,Facebookbool,Snapchatbool);



        return root;
    }


    public void loadStatistics(Boolean instagram, Boolean Facebook, Boolean Snapchat) {
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
        ArrayList<App> appsList = new ArrayList<>();
        List<UsageStats> usageStatsList = new ArrayList<>(mySortedMap.values());
        // get total time of apps usage to calculate the usagePercentage for each app
        long totalTime = usageStatsList.stream().map(UsageStats::getTotalTimeInForeground).mapToLong(Long::longValue).sum();
        //  sind 10 stunden zum testen
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("time",Context.MODE_PRIVATE);

        long test = getmillis(sp.getInt("hour",0),sp.getInt("minute",0));
        long usageDurationall = 0;


        for (UsageStats usageStats : usageStatsList) {
            try {
                String packageName = usageStats.getPackageName();

                Log.d("Appname",packageName);
                String[] packageNames = packageName.split("\\.");
                String appName = packageNames[packageNames.length-1].trim();
                Log.d("Appname2",appName);

              /*  if(packageNames[packageNames.length-1].trim()=="isociable")  {

                  long usageDurationisociable = usageStats.getTotalTimeInForeground();
                }
                if(packageNames[packageNames.length-1].trim()=="instagram")  {

                    long usageDurationinstagram = usageStats.getTotalTimeInForeground();
                }
                if(packageNames[packageNames.length-1].trim()=="snapchat")  {

                    long usageDurationsnapchat = usageStats.getTotalTimeInForeground();
                }*/

                if(isAppInfoAvailable(usageStats)){
                    ApplicationInfo ai = getActivity().getApplicationContext().getPackageManager().getApplicationInfo(packageName, 0);

                    appName = getActivity().getApplicationContext().getPackageManager().getApplicationLabel(ai).toString();
                }

                long usageDuration = usageStats.getTotalTimeInForeground();
                App usageStatDTO = new App(appName, usageDuration);
                Log.d("App hinzugefügt davor", String.valueOf(isAppselected(usageStatDTO)));
                Log.d("App name davor", usageStatDTO.appName);
                if(isAppselected(usageStatDTO) == true){
                    appsList.add(usageStatDTO);
                    Log.d("App hinzugefügt",appName);
                }


            }catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
        }
        for(App app : appsList){
            usageDurationall =+ app.usageDuration;
        }
        Log.d("wie lang benutzt", String.valueOf(getDurationBreakdown(usageDurationall)));
        long lefttime = test-usageDurationall;
        int usagePercentage = (int)(usageDurationall*100 / test);
        Toast.makeText(getActivity(),"Wieviel prozent:" + usagePercentage + "totaltime"+ totalTime ,Toast.LENGTH_LONG).show();
        left_time.setText(getDurationBreakdown(lefttime) + " left");
        progessBar.setProgress(usagePercentage);


    }

    private boolean isAppselected(App app){
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
        Instagrambool = sp.getBoolean("Instagram",false);
        Facebookbool = sp.getBoolean("Facebook",false);
        Snapchatbool = sp.getBoolean("Snapchat",false);
        if(Instagrambool && app.appName.equals("iSociable")){
            return true;
        }
        if(Snapchatbool == true && app.appName == "snapchat"){
            return true;
        }
        if(Facebookbool == true && app.appName == "facebook"){
            return true;
        }

      return false;

    }

    private boolean isAppInfoAvailable(UsageStats usageStats) {
        try {
            getActivity().getApplicationContext().getPackageManager().getApplicationInfo(usageStats.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private String getDurationBreakdown(long millis) {
   /*     if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }
*/
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);


        return (hours + " h " +  minutes + " m " );
    }

    private Long getmillis(int hours, int minutes) {
    /*    if (hours < 0 && minutes <0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }*/

        long millis = TimeUnit.HOURS.toMillis(hours);
        millis += TimeUnit.MINUTES.toMillis(minutes);


        return millis;
    }
}
