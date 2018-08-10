package com.example.admin.sleepbetter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
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

        SharedPreferences preferences = getSharedPreferences("bmjump", MODE_PRIVATE);
        ImageView imageView = findViewById(R.id.imageView9);
        imageView.setImageResource(preferences.getInt("slectedbitmoji", 0));

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                loopForSending();



            }

        });

        List<String> listOne = getIntervals("upToFive");

        getSeekbarWithIntervals("concentrate").setIntervals(listOne);

        getSeekbarWithIntervals("coordinate").setIntervals(listOne);

        getSeekbarWithIntervals("apetite").setIntervals(listOne);


    }

    private void loopForSending(){
   //     System.out.println("IS NETWORK : " + isConnected());
            if (isConnected()){
                goToThirdActivity();
            } else {
                InternetDialog dial = new InternetDialog();
                dial.show(getFragmentManager(), "dialog");
            }
    }

    public boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static class InternetDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("You need to have internet connection to proceed.");
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                    dialog.dismiss();


                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, MainMenu.class);

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


        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("apetite", apetite + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("apetite", apetite + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("concentrate", concentrate + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("concentrate", concentrate + 1).apply();
        getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("coordinate", coordinate + 1);
        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("coordinate", coordinate + 1).apply();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        //CALCULATE THE MOOOD
        int mood = moodCalculator(timesPerNight, nightTerrors, sad, sleepy, tired, stressed, irritable, concentrate + 1, coordinate + 1);
        System.out.println("UPPER MOOD is " + mood);
        this.doMood(mood);


        startActivity(intent);

        EditText commentBox = (EditText) findViewById(R.id.yourName2);
        final String comment = commentBox.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                userDatabase.daoAccess().deleteUserExperimentTable();
                userDatabase.daoAccess().deleteUserQuesionnaireTable();
                userDatabase.daoAccess().deleteUserDiaryTable();

                UserQuestionnaire user = new UserQuestionnaire();
                String username = getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");
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
                user.setApetite(apetite + 1);
                user.setConcentrate(concentrate + 1);
                user.setCoordinate(coordinate + 1);
                user.setIrritable(irritable);
                user.setMood(getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0));

                userDatabase.daoAccess().insertSingleUserQuestionnaire(user);


                if (!comment.equals("")) {


                    UserDiary userDiary = new UserDiary();
                    userDiary.setUsername(username);
                    userDiary.setDate(formattedDate);
                    userDiary.setComment(comment);

                    userDatabase.daoAccess().insertSingleUserDiary(userDiary);
                }
                Report rep = new Report(userDatabase, getApplicationContext());
                rep.save(username, true, getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
            }
        }).start();


        String experiment = getSharedPreferences("name", Context.MODE_PRIVATE).getString("experiment", " ");

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate2 = df2.format(c);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        HomeCollection.date_collection_arr = new ArrayList<HomeCollection>();
        HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, "No experiment started yet", String.valueOf(getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), "No experiment started yet", comment));
        String json = gson.toJson(HomeCollection.date_collection_arr);

        editor.putString("trial", json);
        editor.commit();

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
                add("-1");
            }};
        } else if (command.equals("upToFive")) {
            return new ArrayList<String>() {{
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
                add("-1");
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

    private void doMood(int mood) {
        System.out.println("MOOD is " + mood);
        int happy = getSharedPreferences("bmhappy", MODE_PRIVATE).getInt("slectedbitmoji", 0);
        int ok = getSharedPreferences("bmok", MODE_PRIVATE).getInt("slectedbitmoji", 0);
        int notok = getSharedPreferences("bmnotok", MODE_PRIVATE).getInt("slectedbitmoji", 0);
        int bad = getSharedPreferences("bmbad", MODE_PRIVATE).getInt("slectedbitmoji", 0);

        SharedPreferences preferences = getSharedPreferences("MOOD", MODE_PRIVATE);
        if (mood == 1 || mood == 0) preferences.edit().putInt("moodbitmoji", happy).apply();
        if (mood == 2) preferences.edit().putInt("moodbitmoji", ok).apply();
        if (mood == 3) preferences.edit().putInt("moodbitmoji", notok).apply();
        if (mood == 4 || mood == 5) preferences.edit().putInt("moodbitmoji", bad).apply();

        getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0);
        getSharedPreferences("MOOD", MODE_PRIVATE).edit().putInt("mood", mood).apply();

    }
/*
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }*/
}

