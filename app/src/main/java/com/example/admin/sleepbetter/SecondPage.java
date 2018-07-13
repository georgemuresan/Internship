package com.example.admin.sleepbetter;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondPage extends AppCompatActivity {

    private SeekBarWithIntervals timesPerNightBar = null;
    private SeekBarWithIntervals nightTerrorsBar = null;
    private SeekBarWithIntervals fallAsleepBar = null;
    private SeekBarWithIntervals wakeUpBar = null;
    private SeekBarWithIntervals freshBar = null;
    private SeekBarWithIntervals sadBar = null;
    private SeekBarWithIntervals sleepyBar = null;
    private SeekBarWithIntervals tiredBar = null;
    private SeekBarWithIntervals stressedBar = null;
    private SeekBarWithIntervals apetiteBar = null;
    private SeekBarWithIntervals concentrateBar = null;
    private SeekBarWithIntervals coordinateBar = null;
    private SeekBarWithIntervals irritableBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        Button button = (Button) findViewById(R.id.startButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });

        List<String> seekbarIntervals = getIntervals("upToFour");
        getSeekbarWithIntervals("times").setIntervals(seekbarIntervals);

        List<String> listOne = getIntervals("upToFive");
        getSeekbarWithIntervals("nightTerrors").setIntervals(listOne);

        getSeekbarWithIntervals("fallAsleep").setIntervals(listOne);

        getSeekbarWithIntervals("wakeUp").setIntervals(listOne);

        getSeekbarWithIntervals("fresh").setIntervals(listOne);

        getSeekbarWithIntervals("sad").setIntervals(listOne);

        getSeekbarWithIntervals("sleepy").setIntervals(listOne);

        getSeekbarWithIntervals("tired").setIntervals(listOne);

        getSeekbarWithIntervals("stressed").setIntervals(listOne);

        getSeekbarWithIntervals("irritable").setIntervals(listOne);

        getSeekbarWithIntervals("concentrate").setIntervals(listOne);

        getSeekbarWithIntervals("coordinate").setIntervals(listOne);

        getSeekbarWithIntervals("apetite").setIntervals(listOne);


    }
    private void goToThirdActivity() {

        Intent intent = new Intent(this, ThirdPage.class);

        int timesPerNight = timesPerNightBar.getProgress();
        int nightTerrors = nightTerrorsBar.getProgress();
        int fallAsleep = fallAsleepBar.getProgress();
        int wakeUp = wakeUpBar.getProgress();
        int fresh = freshBar.getProgress();
        int sad = sadBar.getProgress();
        int sleepy = sleepyBar.getProgress();
        int tired = tiredBar.getProgress();
        int stressed = stressedBar.getProgress();
        int apetite = apetiteBar.getProgress();
        int concentrate = concentrateBar.getProgress();
        int coordinate = coordinateBar.getProgress();
        int irritable = irritableBar.getProgress();

        int mood = getSharedPreferences("MOOD", MODE_PRIVATE)
                .getInt("mood", 0);
        getSharedPreferences("MOOD", MODE_PRIVATE).edit()
                .putInt("mood", moodCalculato(timesPerNight,nightTerrors,sad,sleepy,tired,stressed,irritable,concentrate,coordinate)).apply();

        startActivity(intent);

    }

    private int moodCalculato(int timesNight, int nightmares, int sad, int sleepy, int tired, int stressed, int irritable, int concentrate, int coordinate){
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
        } else  if (command.equals("upToFive")) {
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
        } else  if (name.equals("nightTerrors")) {
            if (nightTerrorsBar == null) {
                nightTerrorsBar = (SeekBarWithIntervals) findViewById(R.id.nightTerrorsBar);
            }

            return nightTerrorsBar;
        } else  if (name.equals("fallAsleep")) {
            if (fallAsleepBar == null) {
                fallAsleepBar = (SeekBarWithIntervals) findViewById(R.id.fallAsleepBar);
            }

            return fallAsleepBar;
        } else  if (name.equals("wakeUp")) {
            if (wakeUpBar == null) {
                wakeUpBar = (SeekBarWithIntervals) findViewById(R.id.easyWakeUpBar);
            }

            return wakeUpBar;
        } else  if (name.equals("fresh")) {
            if (freshBar == null) {
                freshBar = (SeekBarWithIntervals) findViewById(R.id.freshBar);
            }

            return freshBar;
        } else  if (name.equals("sad")) {
            if (sadBar == null) {
                sadBar = (SeekBarWithIntervals) findViewById(R.id.sadBar);
            }

            return sadBar;
        } else  if (name.equals("sleepy")) {
            if (sleepyBar == null) {
                sleepyBar = (SeekBarWithIntervals) findViewById(R.id.sleepyBar);
            }

            return sleepyBar;
        } else  if (name.equals("tired")) {
            if (tiredBar == null) {
                tiredBar = (SeekBarWithIntervals) findViewById(R.id.tiredBar);
            }

            return tiredBar;
        } else  if (name.equals("stressed")) {
            if (stressedBar == null) {
                stressedBar = (SeekBarWithIntervals) findViewById(R.id.stressedBar);
            }

            return stressedBar;
        } else  if (name.equals("irritable")) {
            if (irritableBar == null) {
                irritableBar = (SeekBarWithIntervals) findViewById(R.id.irritableBar);
            }

            return irritableBar;
        } else  if (name.equals("concentrate")) {
            if (concentrateBar == null) {
                concentrateBar = (SeekBarWithIntervals) findViewById(R.id.concentrateBar);
            }

            return concentrateBar;
        } else  if (name.equals("coordinate")) {
            if (coordinateBar == null) {
                coordinateBar = (SeekBarWithIntervals) findViewById(R.id.coordinateBar);
            }

            return coordinateBar;
        } else  if (name.equals("apetite")) {
            if (apetiteBar == null) {
                apetiteBar = (SeekBarWithIntervals) findViewById(R.id.apetiteBar);
            }

            return apetiteBar;
        }
        return null;
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}

