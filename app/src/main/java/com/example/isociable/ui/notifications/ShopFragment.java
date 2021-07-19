package com.example.isociable.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.isociable.R;


public class ShopFragment extends Fragment {

    private ShopViewModel ShopViewModel;
    Boolean gekauft1,gekauft2,gekauft3;
    Button shopButton1,shopButton2,shopButton3;
    TextView price1,price2,price3;
    SharedPreferences sp;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        Log.d("Shop", "gestartet");
        price1 = root.findViewById(R.id.t3);
        price2 = root.findViewById(R.id.t3_1);
        price3 = root.findViewById(R.id.t3_2);

        shopButton1 = root.findViewById(R.id.b1);
        shopButton2 = root.findViewById(R.id.b1_1);
        shopButton3 = root.findViewById(R.id.b1_2);

        sp = getActivity().getApplicationContext().getSharedPreferences("boughtPlants",Context.MODE_PRIVATE);
        gekauft1 = sp.getBoolean("gekauft1",false);
        gekauft2 = sp.getBoolean("gekauft2",false);
        gekauft3 = sp.getBoolean("gekauft3",false);

/*
        sp = getActivity().getApplicationContext().getSharedPreferences("boughtPlants",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor = sp.edit();
        editor.putBoolean("gekauft1",false);
        editor.putBoolean("gekauft2",false);
        editor.putBoolean("gekauft3",false);

        editor.commit();
*/







        shopButton1.setOnClickListener((v) -> {
         checkShopitem(price1,"gekauft1");

        });

        shopButton2.setOnClickListener((v) -> {
            checkShopitem(price2,"gekauft2");
        });

        shopButton3.setOnClickListener((v) -> {
            checkShopitem(price3,"gekauft3");
        });


        checkbutton();

        return root;
    }

    private void checkbutton() {

        if(gekauft1==true){
            shopButton1.setText("Bought");
            shopButton1.setEnabled(false);
            shopButton1.setAlpha((float) 0.5);
        }else{

            shopButton1.setEnabled(true);
            shopButton1.setAlpha(1);
        }


        if(gekauft2==true){
            shopButton2.setText("Bought");
            shopButton2.setEnabled(false);
            shopButton2.setAlpha((float) 0.5);
        }else{

            shopButton2.setEnabled(true);
            shopButton2.setAlpha(1);
        }

        if(gekauft3==true){
            shopButton3.setText("Bought");
            shopButton3.setEnabled(false);
            shopButton3.setAlpha((float) 0.5);
        }else{

            shopButton3.setEnabled(true);
            shopButton3.setAlpha(1);
        }

    }



    private void checkShopitem(TextView price,String gekauftx) {
        sp = getActivity().getApplicationContext().getSharedPreferences("Coins",Context.MODE_PRIVATE);
        int coinAmount = sp.getInt("Coins",0);


        if(coinAmount - Integer.parseInt(price.getText().toString()) >0){
            int newCoinAmount = coinAmount - Integer.parseInt(price.getText().toString());
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Coins",newCoinAmount);
            editor.commit();
            sp = getActivity().getApplicationContext().getSharedPreferences("boughtPlants",Context.MODE_PRIVATE);
            editor = sp.edit();
            editor.putBoolean(gekauftx,true);
            editor.commit();
            Toast.makeText(getActivity(),"gekauft",Toast.LENGTH_LONG).show();
            ShopViewModel.getgekauft1().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(sp.getBoolean("gekauft1",false)==true){
                        shopButton1.setText("Bought");
                        shopButton1.setEnabled(false);
                        shopButton1.setAlpha((float) 0.5);
                    }
                    if(sp.getBoolean("gekauft2",false)==true){
                        shopButton1.setText("Bought");
                        shopButton2.setEnabled(false);
                        shopButton2.setAlpha((float) 0.5);
                    }
                    if(sp.getBoolean("gekauft3",false)==true){
                        shopButton1.setText("Bought");
                        shopButton3.setEnabled(false);
                        shopButton3.setAlpha((float) 0.5);
                    }
                }
            });

        }else{

            Toast.makeText(getActivity(),"Not enough Coins",Toast.LENGTH_LONG).show();
        }
    }
}