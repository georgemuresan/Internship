package com.example.admin.sleepbetter;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecondPage2 extends AppCompatActivity {

    private SeekBarWithIntervals fallAsleepBar = null;
    private SeekBarWithIntervals wakeUpBar = null;
    private SeekBarWithIntervals freshBar = null;

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page_two);

        SharedPreferences preferences = getSharedPreferences("bmwalking", MODE_PRIVATE);
        ImageView imageView = findViewById(R.id.imageView7);
        imageView.setImageResource(preferences.getInt("slectedbitmoji", 0));

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });


        List<String> listOne = getIntervals("upToFive");

        getSeekbarWithIntervals("fallAsleep").setIntervals(listOne);

        getSeekbarWithIntervals("wakeUp").setIntervals(listOne);

        getSeekbarWithIntervals("fresh").setIntervals(listOne);





    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, SecondPage3.class);


        final int fallAsleep = fallAsleepBar.getProgress();
        final int wakeUp = wakeUpBar.getProgress();
        final int fresh = freshBar.getProgress();

        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("fallAsleep", fallAsleep + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("fallAsleep", fallAsleep + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("wakeUp", wakeUp + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("wakeUp", wakeUp + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("fresh", fresh + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("fresh", fresh + 1).apply();


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

        if (name.equals("fallAsleep")) {
            if (fallAsleepBar == null) {
                fallAsleepBar = (SeekBarWithIntervals) findViewById(R.id.fallAsleepBar);
            }

            return fallAsleepBar;
        } else if (name.equals("wakeUp")) {
            if (wakeUpBar == null) {
                wakeUpBar = (SeekBarWithIntervals) findViewById(R.id.easyWakeUpBar);
            }

            return wakeUpBar;
        } else if (name.equals("fresh")) {
            if (freshBar == null) {
                freshBar = (SeekBarWithIntervals) findViewById(R.id.freshBar);
            }

            return freshBar;
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

