package com.example.isociable.ui.home;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.isociable.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    HomeViewModel homeViewModel;
    ImageView imageView;
    LayerDrawable enviormentdrawable;
    Drawable item;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);





        // Referenz auf LayerList
        enviormentdrawable = (LayerDrawable) getResources().getDrawable(R.drawable.layerlistimage);

        // Referenz auf ein Item in der Layerlist
        item = enviormentdrawable.findDrawableByLayerId(R.id.gekauft1);

        // Wenn Pflanze gekauft, wird Alpha gesetzt
        homeViewModel.getgekauft1().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(homeViewModel.getgekauft1().getValue()==true){

                    item.setAlpha(255);
                }else{

                    item.setAlpha(0);
                }
            }
        });



        Button button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(this);
        imageView = root.findViewById(R.id.imageView);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.layerlistimage));
/*        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/




        return root;
    }

    @Override
    public void onClick(View v) {
        homeViewModel.setgekauft1();


    }
}
