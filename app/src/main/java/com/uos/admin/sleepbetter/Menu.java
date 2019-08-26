package com.uos.admin.sleepbetter;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Menu extends Fragment {

    View mainPage;
    private static final String DATABASE_NAME = "user_db";

    public Menu(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainPage = inflater.inflate(R.layout.act_menu, container, false);


        Button myExperimentButton = mainPage.findViewById(R.id.submitButton);
        myExperimentButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            startActivity(new Intent(getActivity(), MyCurrentExperiment.class));
            }

        });

        Button button1 = mainPage.findViewById(R.id.whatSleep);
        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WhatIsSleep.class));
            }

        });

        Button button3 = mainPage.findViewById(R.id.WhatExperiments);
        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            startActivity(new Intent(getActivity(), WhatExperiments.class));
            }

        });


        TextView navUsername = (TextView) mainPage.findViewById(R.id.editText3);
        String name = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
        navUsername.setText(name);


        TextView participantID = (TextView) mainPage.findViewById(R.id.partID);
        String id = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");
        participantID.setText("Participant ID: " + id);

        return mainPage;

    }

    private boolean isViewShown = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            loadPageDataProcessing();
        } else {
            isViewShown = false;
        }
    }

    public void loadPageDataProcessing(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String currentDate = df.format(c);

        String startingDate = getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).getString("startingDate", "");

        Date date1 = null;
        Date date2 = null;

        SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");

        //Setting dates
        try {
            date1 = dates.parse(currentDate);
            date2 = dates.parse(startingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        int shouldBe = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

        final int finalShouldBe = shouldBe;

        String expStartDate = getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");

        if (shouldBe == 0 && expStartDate.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), "Please choose an experiment.", Toast.LENGTH_LONG).show();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDatabase uDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                int loggedIn = uDatabase.daoAccess().fetchUserQuestionnaires().size();


                int misses = finalShouldBe -loggedIn;


                if (misses >=1){

                    String moods_string = getActivity().getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).getString("moods", "");

                    String[] moods = moods_string.split("gcm");

                    ArrayList<String> moodsArrayList = new ArrayList<String>(Arrays.asList(moods));

                    for (int i=0; i<misses; i++){

                        UserQuestionnaire user = new UserQuestionnaire();
                        String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing"); user.setUsername(username);
                        user.setDate(currentDate);
                        user.setUsername(username);
                        user.setHowLong(-1);
                        user.setAwake(-1);
                        user.setEarlier(-1);
                        user.setNightsAWeek(-1);
                        user.setQuality(-1);
                        user.setImpactMood(-1);
                        user.setImpactActivities(-1);
                        user.setImpactGeneral(-1);
                        user.setProblem(-1);
                        user.setMood(-1);

                        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("howLong", -1).apply();
                        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("awake", -1).apply();
                        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("earlier", -1).apply();
                        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("quality", -1).apply();
                        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactMood", -1).apply();
                        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactActivities", -1).apply();
                        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactGeneral", -1).apply();
                        getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).edit().putFloat("mood", (float) -1).apply();

                        //setting mood value to -1 in shared preferences

                        uDatabase.daoAccess().insertSingleUserQuestionnaire(user);

                        moodsArrayList.add("-1");

                        Report rep = new Report(uDatabase, getActivity().getApplicationContext());
                        rep.save(username, false, getActivity().getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
                    }

                    moods = moodsArrayList.toArray(moods);

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < moods.length; i++) {
                        sb.append(moods[i]).append("gcm");
                    }
                    getActivity().getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).edit().putString("moods", sb.toString()).apply();

                }

            }

            //scoatem variabila days si verificam: daca se imparte la 5, si nu e locked,
        }).start();

        //update experiments


        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");

        if (shouldBe> experimentsArray.length){
            String currentExperiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

            ArrayList<String> experimentsArrayList = new ArrayList<String>(Arrays.asList(experimentsArray));
            for (int i=0; i< (shouldBe - experimentsArray.length); i++){
                experimentsArrayList.add(currentExperiment + ".");
            }

            experimentsArray = experimentsArrayList.toArray(experimentsArray);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < experimentsArray.length; i++) {
                sb.append(experimentsArray[i]).append("gcm");
            }
            getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).edit().putString("experiments", sb.toString()).apply();

        }

        TextView remainedDaysText = (TextView) mainPage.findViewById(R.id.youHave);

        if (expStartDate.equals("")){
            remainedDaysText.setText("Please choose your experiment in the Experiments section.");
        } else {
            Date date3 = null;

            //Setting dates
            try {
                date3 = dates.parse(expStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar c3 = Calendar.getInstance();
            c3.setTime(date3);

            int experimentDaysDifference = c1.get(Calendar.DAY_OF_YEAR) - c3.get(Calendar.DAY_OF_YEAR);

            int difference = 5 - (experimentDaysDifference % 5);


            remainedDaysText.setText("You have " + difference + " days left of the current experiment.");


            if (expStartDate.equals(currentDate)) {
                remainedDaysText.setText("You have 5 days left of the current experiment.");
            } else if (difference < 5 && difference != 0){
                remainedDaysText.setText(difference + " days left of the current experiment.");
            } else {
                remainedDaysText.setText(difference + " days left of the current experiment. When available, change your experiment in the Experiments section.");
            }

        }

    }
}
