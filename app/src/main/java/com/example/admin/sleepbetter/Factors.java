package com.example.admin.sleepbetter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class Factors extends Fragment {

    View factorsView;
    private static RadioGroup radioGroup;
    private boolean shouldBlockTouches = false;

    private static final String DATABASE_NAME = "user_db";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        factorsView = inflater.inflate(R.layout.act_experiments, container, false);


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
            System.out.println("YESSSSSSSSSSSSSSS");
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



                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String currentDate = df.format(c);

                String previousExperimentStartDate = getActivity().getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");
                String studyStartDate = getActivity().getSharedPreferences("date", MODE_PRIVATE).getString("startingDate", "");


                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat formatter1 = new SimpleDateFormat("HH");
                String currentHour = formatter1.format(calendar1.getTime());


        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");

               int loggedIn = experimentsArray.length;
                Date date1 = null;
                Date date2 = null;

                SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");

                if (previousExperimentStartDate.equals("")){
                    previousExperimentStartDate = currentDate;
                }
                //Setting dates
                try {
                    date1 = dates.parse(currentDate);
                    date2 = dates.parse(previousExperimentStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println(date1);
                System.out.println(date2);
                Calendar c1 = Calendar.getInstance();
                c1.setTime(date1);

                Calendar c2 = Calendar.getInstance();
                c2.setTime(date2);

                int differenceBetweenOldExperimentAndCurrent = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);


                if (differenceBetweenOldExperimentAndCurrent % 5 < 5 && !getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "").equals("No experiment for the initial day.")){
                    if (currentDate.equals(studyStartDate) ){
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            radioGroup.getChildAt(i).setEnabled(false);
                        }

                        Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to change your experiment yet as you've just picked it.", Toast.LENGTH_LONG).show();
                    } else if (differenceBetweenOldExperimentAndCurrent % 5 == 0){
                        if (Integer.valueOf(currentHour) < 19){
                            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                radioGroup.getChildAt(i).setEnabled(false);
                            }
                            Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to change your experiment yet. You can change it after 19:00 today.", Toast.LENGTH_LONG).show();

                        } else if (loggedIn % 5 != 1){
                            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                radioGroup.getChildAt(i).setEnabled(false);
                            }
                            Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to change your experiment yet. You can change it after completing today's questionnaire.", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        for (int i = 0; i < radioGroup.getChildCount(); i++) {
                            radioGroup.getChildAt(i).setEnabled(false);
                        }
                        Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to change your experiment as 5 days have not passed yet.", Toast.LENGTH_LONG).show();

                    }
                }
        return factorsView;
    }



    private void SavePreferences(String key, int value){
        getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt(key, value).apply();
    }

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
                             //daca nu e blocat, atunci se updateaza experimentul, se blocheaza accesul si se incrementeaza nr de zile
                                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                    radioGroup.getChildAt(i).setEnabled(false);
                                }
                                Intent intent = new Intent(getActivity().getApplicationContext(), MainMenu.class);

                                //update starting date
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                final String currentDate = df.format(c);

                                getActivity().getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");
                                getActivity().getSharedPreferences("date", MODE_PRIVATE).edit().putString("startExperiment", currentDate).apply();
                                //
                                String experiments = getActivity().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

                                String currentExperiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");


                                String[] experimentsArray = experiments.split("gcm");

                                ArrayList<String> experimentsArrayList = new ArrayList<String>(Arrays.asList(experimentsArray));
                                experimentsArrayList.add(currentExperiment + ".");

                                experimentsArray = experimentsArrayList.toArray(experimentsArray);

                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < experimentsArray.length; i++) {
                                    sb.append(experimentsArray[i]).append("gcm");
                                }
                                getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).edit().putString("experiments", sb.toString()).apply();

                                startActivity(intent);
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
