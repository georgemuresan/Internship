package com.uos.admin.sleepbetter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class QFinal extends AppCompatActivity {

   private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    private static boolean goToMenu = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ques_final);

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                loopForSending();

            }

        });

        TextView cons8 = findViewById(R.id.firstSet);
        cons8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        TextView sec = findViewById(R.id.secondSet);
        sec.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        TextView th = findViewById(R.id.thirdSet);
        th.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }

    private void loopForSending(){
        if (isConnected()){
            goToMenu();
        } else {
            InternetDialog dial = new InternetDialog();
            new InternetDialog().show(getSupportFragmentManager(), "dialog");
        }
    }

    public static class InternetDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Make sure you have internet connection. When ready, press - OK.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("I can't access the internet", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    goToMenu = true;
                }
            });
            return builder.create();
        }
    }

    public void goToMenu(){

        int answered = 0;
        double mood =0;

        RadioGroup qGroup = findViewById(R.id.q1Group);
        int qID = qGroup.getCheckedRadioButtonId();
        View radioButton = qGroup.findViewById(qID);
        final int howLong = qGroup.indexOfChild(radioButton) +1;
        mood += howLong;
        if (howLong == 0) answered++;

        qGroup = findViewById(R.id.q2Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int awake = qGroup.indexOfChild(radioButton) +1;
        mood += awake;
        if (awake == 0) answered++;

        qGroup = findViewById(R.id.q3Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int earlier = qGroup.indexOfChild(radioButton) +1;
        mood += earlier;
        if (earlier == 0) answered++;

        qGroup = findViewById(R.id.q5Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int quality = qGroup.indexOfChild(radioButton) +1;
        mood += quality;
        if (quality == 0) answered++;

        qGroup = findViewById(R.id.q6Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactMood = qGroup.indexOfChild(radioButton) +1;
        mood += impactMood;
        if (impactMood == 0) answered++;

        qGroup = findViewById(R.id.q7Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactActivities = qGroup.indexOfChild(radioButton) +1;
        mood += impactActivities;
        if (impactActivities == 0) answered++;

        qGroup = findViewById(R.id.q8Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactGeneral = qGroup.indexOfChild(radioButton) +1;
        mood += impactGeneral;
        if (impactGeneral == 0) answered++;

        if (answered > 0){
            Toast.makeText(getApplicationContext(), "In order to proceed, please answer ALL questions", Toast.LENGTH_LONG).show();
        } else {
            mood = mood / 7;

            DecimalFormat formatting = new DecimalFormat("#.##");
            mood = Double.parseDouble(formatting.format(mood));

            startActivity(new Intent(getApplicationContext(), AllPages.class));


            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            final String quesDate = df.format(c);


            final double finalMood = mood;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                    UserQuestionnaire user = new UserQuestionnaire();
                    String username = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");
                    user.setUsername(username);
                    user.setDate(quesDate);
                    user.setHowLong(howLong);
                    user.setAwake(awake);
                    user.setEarlier(earlier);
                    user.setNightsAWeek(-1);
                    user.setQuality(quality);
                    user.setImpactMood(impactMood);
                    user.setImpactActivities(impactActivities);
                    user.setImpactGeneral(impactGeneral);
                    user.setProblem(-1);
                    user.setMood(finalMood);

                    getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("howLong", howLong).apply();
                    getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("awake", awake).apply();
                    getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("earlier", earlier).apply();
                    getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("quality", quality).apply();
                    getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactMood", impactMood).apply();
                    getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactActivities", impactActivities).apply();
                    getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactGeneral", impactGeneral).apply();
                    getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).edit().putFloat("mood", (float) finalMood).apply();

                    userDatabase.daoAccess().insertSingleUserQuestionnaire(user);

                    Report rep = new Report(userDatabase, getApplicationContext());
                    rep.save(username, false, getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
                }
            }).start();


            String moods_string = getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).getString("moods", "");

            String[] moods = moods_string.split("gcm");

            ArrayList<String> moodsArrayList = new ArrayList<String>(Arrays.asList(moods));

            moodsArrayList.add(String.valueOf(mood));

            moods = moodsArrayList.toArray(moods);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < moods.length; i++) {
                sb.append(moods[i]).append("gcm");
            }
            getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).edit().putString("moods", sb.toString()).apply();

            String experiments = getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

            String[] experimentsArray = experiments.split("gcm");
            String currentExperiment = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");


            ArrayList<String> experimentsArrayList = new ArrayList<String>(Arrays.asList(experimentsArray));
            experimentsArrayList.add(currentExperiment + ".");


            experimentsArray = experimentsArrayList.toArray(experimentsArray);

            sb = new StringBuilder();
            for (int i = 0; i < experimentsArray.length; i++) {
                sb.append(experimentsArray[i]).append("gcm");
            }
            getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).edit().putString("experiments", sb.toString()).apply();

            getSharedPreferences("exp", MODE_PRIVATE).getString("picked", "picked");
            getSharedPreferences("exp", MODE_PRIVATE).edit().putString("picked", "picked").apply();

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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "I'm sorry, you can't go back to the previous Pre-Questionnaire section.", Toast.LENGTH_LONG).show();

    }
}