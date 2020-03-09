package com.uos.admin.sleepbetter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static android.content.Context.MODE_PRIVATE;
public class Menu extends Fragment {

    View mainPage;
    private static final String DATABASE_NAME = "user_db";
    private int shouldBe;

    public Menu(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainPage = inflater.inflate(R.layout.act_menu, container, false);

                  if (getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).getBoolean("menu", true)){

                InfoDialog dia = new InfoDialog();
                dia.show(getFragmentManager(), "dialog");

                getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("menu", false).apply();

        }
        Button myExperimentButton = mainPage.findViewById(R.id.whatSleep3);
        myExperimentButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            startActivity(new Intent(getActivity(), MyCurrentExperiment.class));
            }

        });

        Button button1 = mainPage.findViewById(R.id.whatSleep2);
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

        Button button0 = mainPage.findViewById(R.id.whatSleep);
        button0.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String expStartDate = getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");
                if (shouldBe == 0 && expStartDate.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please choose an experiment first.", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getActivity(), Settings.class));
                }
            }

        });

        TextView navUsername = (TextView) mainPage.findViewById(R.id.editText3);
        String name = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
        navUsername.setText(name);


        TextView participantID = (TextView) mainPage.findViewById(R.id.partID);
        String id = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");
        participantID.setText("User ID: " + id);

        return mainPage;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (getView() != null && tabLayout.getSelectedTabPosition() == 0) {
            loadPageDataProcessing();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (tabLayout.getSelectedTabPosition() == 0){
            loadPageDataProcessing();
        }
    }

    public void loadPageDataProcessing() {

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

        shouldBe = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

        //adding a new day only after the ques limit - e.g. after 4 am
        Calendar calendarr = Calendar.getInstance();
        SimpleDateFormat formatterr = new SimpleDateFormat("HH:mm");
        String currentHourr = formatterr.format(calendarr.getTime());

        String quesLimit = getActivity().getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).getString("limit", "0:0");
        String[] lastQuestNotifComponents = quesLimit.split(":");

        String[] currentHourComponents = currentHourr.split(":");

        if (Integer.valueOf(currentHourComponents[0]) < Integer.valueOf(lastQuestNotifComponents[0])) {
            shouldBe--;
        }

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


                int misses = finalShouldBe - loggedIn;


                boolean afterQuesLimit = false;

                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
                String currentHour = formatter1.format(calendar1.getTime());

                String quesLimit = getActivity().getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).getString("limit", "0:0");
                String[] lastQuestNotifComponents = quesLimit.split(":");

                String[] currentHourComponents = currentHour.split(":");


                afterQuesLimit = (Integer.valueOf(currentHourComponents[0]) < 19 && Integer.valueOf(currentHourComponents[0]) > Integer.valueOf(lastQuestNotifComponents[0])) || (Integer.valueOf(currentHourComponents[0]) == Integer.valueOf(lastQuestNotifComponents[0]) && Integer.valueOf(currentHourComponents[1]) > Integer.valueOf(lastQuestNotifComponents[1]));


                //if ((misses ==1 && afterQuesLimit) || misses > 1){
                if (misses >= 1) {

                    String moods_string = getActivity().getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).getString("moods", "");

                    String[] moods = moods_string.split("gcm");

                    ArrayList<String> moodsArrayList = new ArrayList<String>(Arrays.asList(moods));

                    for (int i = 0; i < misses; i++) {

                        UserQuestionnaire user = new UserQuestionnaire();
                        String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");
                        user.setUsername(username);
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

        if (shouldBe > experimentsArray.length) {
            String currentExperiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

            ArrayList<String> experimentsArrayList = new ArrayList<String>(Arrays.asList(experimentsArray));
            for (int i = 0; i < (shouldBe - experimentsArray.length); i++) {
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

        if (expStartDate.equals("")) {
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

            //adding a new day only after the ques limit - e.g. after 4 am
            if (Integer.valueOf(currentHourComponents[0]) < Integer.valueOf(lastQuestNotifComponents[0])) {
                difference++;
            }


            remainedDaysText.setText("You have " + difference + " days left of the current experiment.");


            if (expStartDate.equals(currentDate)) {
                remainedDaysText.setText("You have 5 days left of the current experiment.");
            } else if (difference < 5 && difference != 0) {
                remainedDaysText.setText(difference + " days left of the current experiment.");
            } else {
                remainedDaysText.setText(difference + " days left of the current experiment. When available, change your experiment in the Experiments section.");
            }

        }

    }

    public static class InfoDialog extends DialogFragment {

        private String message;
        private int hour, minute;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Here you can see the main menu - the place where you can read more about the study and the app, as well as your currently chosen experiment. You can also update your name and preferences regarding the app's notifications if you click on the \"Settings\" button. Moreover, this is where you will be able to see how many days you have left of your current experiment. In order to switch between tabs and sections in the app, you can swipe right or click on the titles above.");

            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            return builder.create();
        }


    }

}
