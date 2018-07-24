package com.example.admin.sleepbetter;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SecondPage extends AppCompatActivity {

    private SeekBarWithIntervals timesPerNightBar = null;
    private SeekBarWithIntervals nightTerrorsBar = null;

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });

        List<String> seekbarIntervals = getIntervals("upToFour");
        getSeekbarWithIntervals("times").setIntervals(seekbarIntervals);

        List<String> listOne = getIntervals("upToFive");
        getSeekbarWithIntervals("nightTerrors").setIntervals(listOne);






    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, SecondPage2.class);

        final int timesPerNight = timesPerNightBar.getProgress();
        final int nightTerrors = nightTerrorsBar.getProgress();

        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("timesPerNight", timesPerNight);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("timesPerNight", timesPerNight).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("nightTerrors", nightTerrors);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("nightTerrors", nightTerrors).apply();
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

        if (name.equals("times")) {
            if (timesPerNightBar == null) {
                timesPerNightBar = (SeekBarWithIntervals) findViewById(R.id.timesPerNightBar);
            }

            return timesPerNightBar;
        } else if (name.equals("nightTerrors")) {
            if (nightTerrorsBar == null) {
                nightTerrorsBar = (SeekBarWithIntervals) findViewById(R.id.nightTerrorsBar);
            }

            return nightTerrorsBar;
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

