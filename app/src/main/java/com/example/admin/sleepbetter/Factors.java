package com.example.admin.sleepbetter;

import android.app.FragmentManager;
import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Factors extends Fragment {

    View factorsView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        factorsView = inflater.inflate(R.layout.factors, container, false);


        TextView ssLight = (TextView) factorsView.findViewById(R.id.lightFactor);
        ssLight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("light");
            }
        });
        TextView ssTemperature = (TextView) factorsView.findViewById(R.id.temperatureFactor);
        ssTemperature.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("temperature");
            }
        });
        TextView ssNoise = (TextView) factorsView.findViewById(R.id.noiseFactor);
        ssNoise.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("noise");
            }
        });
        TextView deCaffeine = (TextView) factorsView.findViewById(R.id.caffeineFactor);
        deCaffeine.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("caffeine");
            }
        });
        TextView deNicotine = (TextView) factorsView.findViewById(R.id.nicotinneFactor);
        deNicotine.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("nicotine");
            }
        });
        TextView deAlcohol = (TextView) factorsView.findViewById(R.id.alcoholFactor);
        deAlcohol.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("alcohol");
            }
        });
        TextView hdNaps = (TextView) factorsView.findViewById(R.id.daytimeNapsFactor);
        hdNaps.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("naps");
            }
        });
        TextView crSchedule = (TextView) factorsView.findViewById(R.id.sleepingScheduleFactor);
        crSchedule.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("schedule");
            }
        });



        return factorsView;
    }

    public void openFactor(String factor){

        FragmentManager fragmentManager = getFragmentManager();

        if (factor.equals("light")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorSSLight()).commit();
        } else if (factor.equals("temperature")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorSSTemperature()).commit();
        } else if (factor.equals("noise")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorSSNoise()).commit();
        } else if (factor.equals("caffeine")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorDECaffeine()).commit();
        } else if (factor.equals("nicotine")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorDENicotine()).commit();
        } else if (factor.equals("alcohol")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorDEAlcohol()).commit();
        } else if (factor.equals("naps")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorHDNaps()).commit();
        } else if (factor.equals("schedule")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorCRSleepSchedule()).commit();
        }
    }
}
