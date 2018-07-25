package com.example.admin.sleepbetter;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecondPage4 extends AppCompatActivity {


    private SeekBarWithIntervals apetiteBar = null;
    private SeekBarWithIntervals concentrateBar = null;
    private SeekBarWithIntervals coordinateBar = null;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second_page_four);

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });

        List<String> listOne = getIntervals("upToFive");

        getSeekbarWithIntervals("concentrate").setIntervals(listOne);

        getSeekbarWithIntervals("coordinate").setIntervals(listOne);

        getSeekbarWithIntervals("apetite").setIntervals(listOne);




    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, ThirdPage.class);

        final int timesPerNight = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("timesPerNight", 0);
        final int nightTerrors = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("nightTerrors", 0);

        final int fallAsleep = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("fallAsleep", 0);
        final int wakeUp = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("wakeUp", 0);
        final int fresh = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("fresh", 0);

        final int sad = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("sad", 0);
        final int sleepy = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("sleepy", 0);
        final int tired = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("tired", 0);
        final int stressed = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("stressed", 0);
        final int irritable = getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("irritable", 0);

        final int apetite = apetiteBar.getProgress();
        final int concentrate = concentrateBar.getProgress();
        final int coordinate = coordinateBar.getProgress();

        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("apetite", apetite);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("apetite", apetite).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("concentrate", concentrate);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("concentrate", concentrate).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("coordinate", coordinate);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("coordinate", coordinate).apply();

        System.out.print(getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("timesPerNight", timesPerNight));

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        int mood = getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0);
        getSharedPreferences("MOOD", MODE_PRIVATE).edit().putInt("mood", moodCalculator(timesPerNight, nightTerrors, sad, sleepy, tired, stressed, irritable, concentrate, coordinate)).apply();

        startActivity(intent);

        getSharedPreferences("date", MODE_PRIVATE).edit().putString("lastdate",formattedDate).apply();

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                userDatabase.daoAccess().deleteUserExperimentTable();
                userDatabase.daoAccess().deleteUserQuesionnaireTable();

                UserQuestionnaire user = new UserQuestionnaire();
                String username = getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                user.setUsername(username);
                user.setDate(formattedDate);
                user.setTimesPerNight(timesPerNight);
                user.setNightTerrors(nightTerrors);
                user.setFallAsleep(fallAsleep);
                user.setWakeUp(wakeUp);
                user.setFresh(fresh);
                user.setSad(sad);
                user.setSleepy(sleepy);
                user.setTired(tired);
                user.setStressed(stressed);
                user.setApetite(apetite);
                user.setConcentrate(concentrate);
                user.setCoordinate(coordinate);
                user.setIrritable(irritable);

                userDatabase.daoAccess().insertSingleUserQuestionnaire(user);

                Report rep = new Report(userDatabase, getApplicationContext());
                rep.save(username, true);
            }
        }).start();

    }

    private int moodCalculator(int timesNight, int nightmares, int sad, int sleepy, int tired, int stressed, int irritable, int concentrate, int coordinate) {
        int avgMood = (sad + sleepy + tired + stressed + irritable) / 5;
        int avgNight = (timesNight + nightmares) / 2;
        int avgAction = (concentrate + coordinate) / 2;
        int result = (avgMood * 3 + avgNight + avgAction) / 5;
        return result;
    }

    private List<String> getIntervals(String command) {

        if (command.equals("upToFour")) {
            return new ArrayList<String>() {{
                add("0");
                add("1");
                add("2");
                add("3");
                add("4/4+");
            }};
        } else if (command.equals("upToFive")) {
            return new ArrayList<String>() {{
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
            }};
        }
        return null;
    }

    private SeekBarWithIntervals getSeekbarWithIntervals(String name) {

      if (name.equals("concentrate")) {
            if (concentrateBar == null) {
                concentrateBar = (SeekBarWithIntervals) findViewById(R.id.concentrateBar);
            }

            return concentrateBar;
        } else if (name.equals("coordinate")) {
            if (coordinateBar == null) {
                coordinateBar = (SeekBarWithIntervals) findViewById(R.id.coordinateBar);
            }

            return coordinateBar;
        } else if (name.equals("apetite")) {
            if (apetiteBar == null) {
                apetiteBar = (SeekBarWithIntervals) findViewById(R.id.apetiteBar);
            }

            return apetiteBar;
        }
        return null;
    }
/*
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }*/
}

