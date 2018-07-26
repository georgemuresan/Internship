package com.example.admin.sleepbetter;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecondPage3 extends AppCompatActivity {


    private SeekBarWithIntervals sadBar = null;
    private SeekBarWithIntervals sleepyBar = null;
    private SeekBarWithIntervals tiredBar = null;
    private SeekBarWithIntervals stressedBar = null;
    private SeekBarWithIntervals irritableBar = null;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page_three);

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });


        List<String> listOne = getIntervals("upToFive");

        getSeekbarWithIntervals("sad").setIntervals(listOne);

        getSeekbarWithIntervals("sleepy").setIntervals(listOne);

        getSeekbarWithIntervals("tired").setIntervals(listOne);

        getSeekbarWithIntervals("stressed").setIntervals(listOne);

        getSeekbarWithIntervals("irritable").setIntervals(listOne);





    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, SecondPage4.class);

        final int sad = sadBar.getProgress();
        final int sleepy = sleepyBar.getProgress();
        final int tired = tiredBar.getProgress();
        final int stressed = stressedBar.getProgress();
        final int irritable = irritableBar.getProgress();

        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("sad", sad + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("sad", sad + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("sleepy", sleepy + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("sleepy", sleepy + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("tired", tired + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("tired", tired + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("stressed", stressed + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("stressed", stressed + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("irritable", irritable + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("irritable", irritable + 1).apply();

        startActivity(intent);
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

       if (name.equals("sad")) {
            if (sadBar == null) {
                sadBar = (SeekBarWithIntervals) findViewById(R.id.sadBar);
            }

            return sadBar;
        } else if (name.equals("sleepy")) {
            if (sleepyBar == null) {
                sleepyBar = (SeekBarWithIntervals) findViewById(R.id.sleepyBar);
            }

            return sleepyBar;
        } else if (name.equals("tired")) {
            if (tiredBar == null) {
                tiredBar = (SeekBarWithIntervals) findViewById(R.id.tiredBar);
            }

            return tiredBar;
        } else if (name.equals("stressed")) {
            if (stressedBar == null) {
                stressedBar = (SeekBarWithIntervals) findViewById(R.id.stressedBar);
            }

            return stressedBar;
        } else if (name.equals("irritable")) {
            if (irritableBar == null) {
                irritableBar = (SeekBarWithIntervals) findViewById(R.id.irritableBar);
            }

            return irritableBar;
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

