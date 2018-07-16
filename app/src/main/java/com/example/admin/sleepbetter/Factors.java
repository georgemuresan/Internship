package com.example.admin.sleepbetter;

import android.app.FragmentManager;
import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class Factors extends Fragment {

    View factorsView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        factorsView = inflater.inflate(R.layout.factors, container, false);


        /*
        TextView ssLight = (TextView) factorsView.findViewById(R.id.lightFactor);
        ssLight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("light");
            }
        });
        TextView deCaffeine = (TextView) factorsView.findViewById(R.id.caffeineFactor);
        deCaffeine.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("caffeine");
            }
        });
        TextView crSchedule = (TextView) factorsView.findViewById(R.id.sleepingScheduleFactor);
        crSchedule.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openFactor("schedule");
            }
        });

*/
        getActivity().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

        RadioGroup radioGroup = (RadioGroup) factorsView.findViewById(R.id.experimentsGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb =(RadioButton) factorsView.findViewById(checkedId);
                if (rb.getText().equals(getString(R.string.firstLight))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "firstLight").apply();
                } else if (rb.getText().equals(getString(R.string.secondLight))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "secondLight").apply();
                } else if (rb.getText().equals(getString(R.string.thirdLight))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "thirdLight").apply();
                } else if (rb.getText().equals(getString(R.string.firstCaffeine))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "firstCaffeine").apply();
                } else if (rb.getText().equals(getString(R.string.secondCaffeine))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "secondCaffeine").apply();
                } else if (rb.getText().equals(getString(R.string.thirdCaffeine))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "thirdCaffeine").apply();
                } else if (rb.getText().equals(getString(R.string.firstSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "firstSchedule").apply();
                } else if (rb.getText().equals(getString(R.string.secondSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "secondSchedule").apply();
                } else if (rb.getText().equals(getString(R.string.thirdSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "thirdSchedule").apply();
                } else if (rb.getText().equals(getString(R.string.fourthSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", "fourthSchedule").apply();
                }
            }
        });


        Button button = (Button) factorsView.findViewById(R.id.moreInfo);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goHelpPage();

            }

        });


        return factorsView;
    }

    private void goHelpPage() {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new Help()).commit();

    }

    /*
    public void openFactor(String factor){

        FragmentManager fragmentManager = getFragmentManager();

        if (factor.equals("light")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorSSLight()).commit();
        } else if (factor.equals("caffeine")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorDECaffeine()).commit();
        } else if (factor.equals("schedule")) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FactorCRSleepSchedule()).commit();
        }
    }*/
}
