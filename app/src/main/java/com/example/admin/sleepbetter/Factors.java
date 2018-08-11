package com.example.admin.sleepbetter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class Factors extends Fragment {

    View factorsView;
    private static RadioGroup radioGroup;
    private boolean shouldBlockTouches = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        factorsView = inflater.inflate(R.layout.factors, container, false);


        Button button2 = (Button) factorsView.findViewById(R.id.submit);

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                submitExperiment();

            }

        });


        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

        int savedRadioIndex = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);

        radioGroup = (RadioGroup) factorsView.findViewById(R.id.experimentsGroup);

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

        boolean isLocked = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getBoolean("locked", false);
        int fiveDays = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("days", 0);

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String currentDate = formatter1.format(calendar1.getTime());

        System.out.println(fiveDays + " IS DAYS");
        System.out.println(isLocked + " IS LOCKED");

        if (isLocked && fiveDays % 5 == 0 && currentDate.compareTo("18:59")>0){
            //daca e blocat si a venit momentul sa se schimbe experimentul
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(true);
            }
            //zice ca nu e blocat
            getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putBoolean("locked", false).apply();

            //Amm schimbat in plus
        } else if (isLocked && (fiveDays + 1) % 5 == 0 && currentDate.compareTo("18:59")<0){
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
            Toast.makeText(getActivity().getApplicationContext(), "You will be able to change the experiment, after you complete today's questionnaire.", Toast.LENGTH_SHORT).show();

        } else if (isLocked && (fiveDays + 1) % 5 == 0 && currentDate.compareTo("18:59")>0){
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
            Toast.makeText(getActivity().getApplicationContext(), "You will be able to change the experiment, after you complete today's questionnaire.", Toast.LENGTH_SHORT).show();

        } else if (isLocked && fiveDays % 5 != 0){
            //daca e blocat si e na din zilele cand nu are voie sa schimbe
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
            Toast.makeText(getActivity().getApplicationContext(), "You cannot change the current experiment before the 5-day period ends.", Toast.LENGTH_SHORT).show();
        } else {
            //daca nu e blocat - prima data cand intra
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(true);
            }
        }

        return factorsView;
    }

    private void SavePreferences(String key, int value){
        getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    private void goHelpPage() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new Help());

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();


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

    private void submitExperiment(){

        FactorsDialog dia = new FactorsDialog();
        dia.show(getFragmentManager(), "dialog");

    }

    public static class FactorsDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            int experiment = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);

            switch (experiment) {
                case 1: //increase bright light exposure
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nGetting sunlight exposure in the morrning;\nStaying at least half an hour in the sun per day;\nYour room needs to capture sunlight.");
                    break;
                case 2: //wear glasses that block blue light during the night
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nMaybe installing the f.lx app fromm google that warms up your computer display at night, to match your indoor lighting;\nIf needed - wearing glasses to block blue light during the night.");
                    break;
                case 3: // turn off any bright lights 2 hours before going to bed
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nTurning off the TV/computer with 2 hours before going to bed;\nTurning off any other bright lights in your room with 2 hours before going to bed.");
                    break;
                case 5: // Do not drink caffeine within 6 hours
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNot drinking coffee/soda/any energy drink with 6 hours before sleep.");
                    break;
                case 6: // Limit yourself to 4 cups of coffees per day; 10 canss of
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nLimiting yourself to drinking not more than 4 cups of coffee per day, 10 cans of soda or 2 energy drinks.");
                    break;
                case 7: //Do not drink empty stomach
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNever drinking caffeine (coffee, soda, energy drinks) on empty stomach.");
                    break;
                case 9://Usually get up at the same time everyday, even on weekends
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nGoing to bed and waking up at the same time everyday.");
                    break;
                case 10: // Sleep no lesss than 7 hours per night
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nSleeping at least 7 hours per night.");
                    break;
                case 11: //DO not go to bed unless you are tired. If you are not
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNot going to bed unless you are tired;\nIf you are not, you should take a bath/read a book/stretch-short exercise/drink a hot cup of tea.");
                    break;
                case 12: //Go to sleep at 22:30 PM the latest
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNot going to sleep after 22:30.");
                    break;
            }


                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            boolean isLocked = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getBoolean("locked", false);


                            if (isLocked){
                                Toast.makeText(getActivity().getApplicationContext(), "You cannot change the current experiment before the 5-day period ends.", Toast.LENGTH_SHORT).show();
                            } else {
                                //daca nu e blocat, atunci se updateaza experimentul, se blocheaza accesul si se incrementeaza nr de zile
                                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                    radioGroup.getChildAt(i).setEnabled(false);
                                }
                                getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putBoolean("locked", true).apply();

                                int days =  getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("days", 0);
                                getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("days", days + 1).apply();

                                getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putBoolean("afterExperiment", true).apply();

                                Intent intent = new Intent(getActivity().getApplicationContext(), MainMenu.class);
                                startActivity(intent);
                            }
                        }
                    })
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
