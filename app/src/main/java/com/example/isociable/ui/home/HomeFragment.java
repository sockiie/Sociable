package com.example.isociable.ui.home;

import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.isociable.App;
import com.example.isociable.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    public static boolean dayover = false;
    HomeViewModel homeViewModel;
    ImageView imageView;
    TextView left_time,usage_duration_tv,coins;
    SharedPreferences sp;
    PendingIntent pi;
    LayerDrawable enviormentdrawable;
    Drawable item1,item2,item3,progessbarred, enviormentduster,enviormentnormal;
    ProgressBar progessBar;
    Boolean Instagrambool,Facebookbool,Snapchatbool,gekauft1,gekauft2,gekauft3;
    int usagePercentage;
    BroadcastReceiver br;
    Context _context;

   // UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(Context.USAGE_STATS_SERVICE);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        Log.d("Home", "gestartet");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        coins = root.findViewById(R.id.coins);
        usage_duration_tv = root.findViewById(R.id.usage_duration_tv);
        left_time = root.findViewById(R.id.left_time);
        progessBar = root.findViewById(R.id.determinateBar);
         sp = getActivity().getApplicationContext().getSharedPreferences("selectedApps",Context.MODE_PRIVATE);
        Instagrambool = sp.getBoolean("Instagram",false);
        Facebookbool = sp.getBoolean("Facebook",false);
        Snapchatbool = sp.getBoolean("Snapchat",false);
        sp = getActivity().getApplicationContext().getSharedPreferences("time",Context.MODE_PRIVATE);
        usage_duration_tv.setText(sp.getInt("hour",0) + " : " + sp.getInt("minute",0));
        /*if(Instagram==true){
            Toast.makeText(getActivity(),"Instagram selected",Toast.LENGTH_LONG).show();
        }*/
        progessbarred = getResources().getDrawable(R.drawable.curved_progress_bar_red);





        // Referenz auf LayerList
        enviormentdrawable = (LayerDrawable) getResources().getDrawable(R.drawable.layerlistimage);

        // Referenz auf ein Item in der Layerlist
        item1 = enviormentdrawable.findDrawableByLayerId(R.id.gekauft1);
        item2 = enviormentdrawable.findDrawableByLayerId(R.id.gekauft2);
        item3 = enviormentdrawable.findDrawableByLayerId(R.id.gekauft3);
        enviormentduster = enviormentdrawable.findDrawableByLayerId(R.id.enviormentduster);
        enviormentnormal = enviormentdrawable.findDrawableByLayerId(R.id.enviormentnormal);
        // Wenn Pflanze gekauft, wird Alpha gesetzt
        sp = getActivity().getApplicationContext().getSharedPreferences("boughtPlants",Context.MODE_PRIVATE);
        gekauft1 = sp.getBoolean("gekauft1",false);
        gekauft2 = sp.getBoolean("gekauft2",false);
        gekauft3 = sp.getBoolean("gekauft3",false);

     /*   if(usagePercentage<100){
            enviormentnormal.setAlpha(255);

      *//*      item1.clearColorFilter();
            item2.clearColorFilter();
            item3.clearColorFilter();*//*
            *//*item1.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);

            item2.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
            item3.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);*//*
            enviormentduster.setAlpha(0);
        }else if(usagePercentage>100){

        *//*    item1.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);

            item2.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
            item3.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);*//*
            enviormentnormal.setAlpha(0);
            enviormentduster.setAlpha(255);*//* if(usagePercentage>100){

            item1.clearColorFilter();
            item2.clearColorFilter();
            item3.clearColorFilter();
        }*//*
        }*/



        if(gekauft1==true){

                    item1.setAlpha(255);

                }else{

                    item1.setAlpha(0);
                }


        if(gekauft2==true){

            item2.setAlpha(255);
        }else{

            item2.setAlpha(0);
        }
        if(gekauft3==true){

            item3.setAlpha(255);
        }else{

            item3.setAlpha(0);
        }






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


        setup();
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
        usagePercentage = (int)(usageDurationall*100 / test);
        Toast.makeText(getActivity(),"Wieviel prozent:" + usagePercentage + "totaltime"+ totalTime ,Toast.LENGTH_LONG).show();
        left_time.setText(getDurationBreakdown(lefttime) + " left");
        if(usagePercentage>99){
            item1.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);

            item2.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
            item3.setColorFilter( getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
            enviormentnormal.setAlpha(0);
            enviormentduster.setAlpha(255);
            lefttime = -lefttime;
        progessBar.setProgressDrawable(progessbarred);
            left_time.setText(getDurationBreakdown(lefttime) + " over");
        }else{
            enviormentnormal.setAlpha(255);
            enviormentduster.setAlpha(0);
            item1.clearColorFilter();
            item2.clearColorFilter();
            item3.clearColorFilter();}
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
        if(Snapchatbool == true && app.appName.equals("snapchat")){
            return true;
        }
        if(Facebookbool == true && app.appName.equals("facebook")){
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _context.unregisterReceiver(br);
    }

    private void setup(){

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                Log.d("Broadcast","geht");
                if (action.equals(Intent.ACTION_TIME_TICK) || action.equals(Intent.ACTION_TIMEZONE_CHANGED) || action.equals(Intent.ACTION_DATE_CHANGED) && usagePercentage < 100) {
                    dayover=true;
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        _context.registerReceiver(br,filter);
        pi = PendingIntent.getBroadcast(_context,0,new Intent(Intent.ACTION_TIME_TICK),0);
    }


    public void onResume() {
        super.onResume();
        loadStatistics(Instagrambool,Facebookbool,Snapchatbool);


        setup();
        if(dayover==true && usagePercentage < 100){
            sp = getActivity().getApplicationContext().getSharedPreferences("Coins",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            int coinsmengealt = sp.getInt("Coins",0);
            sp = getActivity().getApplicationContext().getSharedPreferences("coinformel",Context.MODE_PRIVATE);
             sp.getInt("extracoins",0);
            int gewonnenecoins =  sp.getInt("extracoins",0);;
            int coinsmengeneu =+ gewonnenecoins + coinsmengealt;
            editor.putInt("Coins",coinsmengeneu);
            editor.commit();

            dayover=false;
        }
        sp = getActivity().getApplicationContext().getSharedPreferences("Coins",Context.MODE_PRIVATE);
        int coinsmenge = sp.getInt("Coins",0);
        coins.setText(""+ coinsmenge);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);


        ImageButton button = view.findViewById(R.id.imageButton);
        button.setOnClickListener((v) -> {

            navController.navigate(R.id.action_navigation_home_to_bottomSheetFragment);

        });
    }
}


  /*  public void onResume() {
        super.onResume();
     BroadcastReceiver mReceiver = new DateBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(Intent.ACTION_DATE_CHANGED);

        getActivity().registerReceiver(mReceiver, filter);
        if(DateBroadcast.dayover==true){
             sp = getActivity().getApplicationContext().getSharedPreferences("Coins",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Coins",100);
            editor.commit();

            DateBroadcast.dayover=false;
        }
        int coinsmenge = sp.getInt("Coins",0);
        coins.setText(""+ coinsmenge);

     *//*
        DateFormat date = new SimpleDateFormat("dd");
        String dateFormatted = date.format(Calendar.getInstance().getTime());
        DateFormat savedate = date;
        if(savedate != date)
        Toast.makeText(getActivity(),"OnResume brate" + dateFormatted,Toast.LENGTH_LONG).show();*//*
    }*/




