package com.example.admin.sleepbetter;

import android.app.FragmentManager;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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




        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

        int savedRadioIndex = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);

        RadioGroup radioGroup = (RadioGroup) factorsView.findViewById(R.id.experimentsGroup);

        if (!experiment.equals("nothing")){

            System.out.println(experiment);
            System.out.println(savedRadioIndex);
            RadioButton savedCheckedRadioButton = (RadioButton)radioGroup.getChildAt(savedRadioIndex);
            savedCheckedRadioButton.setChecked(true);
        }



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int checkedIndex;

                RadioButton rb =(RadioButton) factorsView.findViewById(checkedId);
                if (rb.getText().equals(getString(R.string.firstLight))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.firstLight)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 1);
                } else if (rb.getText().equals(getString(R.string.secondLight))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.secondLight)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 2);
                } else if (rb.getText().equals(getString(R.string.thirdLight))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.thirdLight)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 3);
                } else if (rb.getText().equals(getString(R.string.firstCaffeine))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.firstCaffeine)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 5);
                } else if (rb.getText().equals(getString(R.string.secondCaffeine))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.secondCaffeine)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 6);
                } else if (rb.getText().equals(getString(R.string.thirdCaffeine))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.thirdCaffeine)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 7);
                } else if (rb.getText().equals(getString(R.string.firstSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.firstSchedule)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 9);
                } else if (rb.getText().equals(getString(R.string.secondSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.secondSchedule)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 10);
                } else if (rb.getText().equals(getString(R.string.thirdSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.thirdSchedule)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 11);
                } else if (rb.getText().equals(getString(R.string.fourthSchedule))){
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.fourthSchedule)).apply();
                    SavePreferences("KEY_SAVED_RADIO_BUTTON_INDEX", 12);
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

    private void SavePreferences(String key, int value){
        getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt(key, value).apply();
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
