package com.example.isociable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_shopnew, R.id.navigation_dashboardFragment3)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        /*NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               if(destination.getId()==R.id.blankFragment2 || destination.getId()==R.id.timepicker2|| destination.getId()==R.id.coreconcept2) {
                   //navView.setAlpha(0);
                   navView.setVisibility(View.INVISIBLE);
               }else {//navView.setAlpha(1);
                   navView.setVisibility(View.VISIBLE);
               }
            }
        });


    }
    public void sendInternalBroadcast(View view)
    {
        Intent intent = new Intent("ACTION_TIME_CHANGED");

        intent.putExtra("From", "sendInternalBroadcast");
        sendBroadcast(intent);
    }


}


