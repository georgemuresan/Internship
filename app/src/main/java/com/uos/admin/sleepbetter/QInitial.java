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
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class QInitial extends AppCompatActivity {


    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;
    private static boolean goToMenu = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_ques_initial);

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

        TextView fo = findViewById(R.id.fourthSet);
        fo.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

    }

    private void loopForSending(){
        if (isConnected() || goToMenu){
            goToMenu();
        } else {
            InternetDialog dial = new InternetDialog();
            dial.show(getSupportFragmentManager(), "dialog");
        }
    }

    public  boolean isConnected() {
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

    private void goToMenu() {

        int answered = 0;
        double mood =0;

        RadioGroup qGroup = this.findViewById(R.id.q1Group);
        int qID = qGroup.getCheckedRadioButtonId();
        View radioButton = qGroup.findViewById(qID);
        final int howLong = qGroup.indexOfChild(radioButton) +1;
        mood += howLong;
        if (howLong == 0) answered++;
        //RadioButton r = (RadioButton) q1Group.getChildAt(idx);
        // final String howLong = r.getText().toString();

        qGroup = this.findViewById(R.id.q2Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int awake = qGroup.indexOfChild(radioButton) +1;
        mood += awake;
        if (awake == 0) answered++;

        qGroup = this.findViewById(R.id.q3Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int earlier = qGroup.indexOfChild(radioButton) +1;
        mood += earlier;
        if (earlier == 0) answered++;

        qGroup = this.findViewById(R.id.q4Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int nightsAWeek = qGroup.indexOfChild(radioButton) +1;
        mood += nightsAWeek;
        if (nightsAWeek == 0) answered++;

        qGroup = this.findViewById(R.id.q5Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int quality = qGroup.indexOfChild(radioButton) +1;
        mood += quality;
        if (quality == 0) answered++;

        qGroup = this.findViewById(R.id.q6Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactMood = qGroup.indexOfChild(radioButton) +1;
        mood += impactMood;
        if (impactMood == 0) answered++;

        qGroup = this.findViewById(R.id.q7Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactActivities = qGroup.indexOfChild(radioButton) +1;
        mood += impactActivities;
        if (impactActivities == 0) answered++;

        qGroup = this.findViewById(R.id.q8Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactGeneral = qGroup.indexOfChild(radioButton) +1;
        mood += impactGeneral;
        if (impactGeneral == 0) answered++;

        qGroup = this.findViewById(R.id.q9Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int problem = qGroup.indexOfChild(radioButton) +1;
        mood += problem;
        if (problem == 0) answered++;

        if (answered > 0){
            Toast.makeText(getApplicationContext(), "In order to proceed, please answer ALL questions", Toast.LENGTH_LONG).show();
        } else {
            mood = mood / 9;

            DecimalFormat formatting = new DecimalFormat("#.##");
            mood = Double.parseDouble(formatting.format(mood));

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            final String currentDate = df.format(c);

            getSharedPreferences("date", MODE_PRIVATE).edit().putString("startingDate", currentDate).apply();

            startActivity(new Intent(this, AllPages.class));


            final double finalMood = mood;
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
                    user.setDate(currentDate);
                    user.setHowLong(howLong);
                    user.setAwake(awake);
                    user.setEarlier(earlier);
                    user.setNightsAWeek(nightsAWeek);
                    user.setQuality(quality);
                    user.setImpactMood(impactMood);
                    user.setImpactActivities(impactActivities);
                    user.setImpactGeneral(impactGeneral);
                    user.setProblem(problem);
                    user.setMood(finalMood);

                    getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("howLong", howLong).apply();
                    getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("awake", awake).apply();
                    getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("earlier", earlier).apply();
                    getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("quality", quality).apply();
                    getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactMood", impactMood).apply();
                    getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactActivities", impactActivities).apply();
                    getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactGeneral", impactGeneral).apply();
                    getSharedPreferences("MOOD", MODE_PRIVATE).edit().putFloat("mood", (float) finalMood).apply();
                    userDatabase.daoAccess().insertSingleUserQuestionnaire(user);


                    Report rep = new Report(userDatabase, getApplicationContext());
                    rep.save(username, true, getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));

                    String diary_comments = "gcm gcm gcm gcm gcm gcm gcm gcm gcm gcm gcm gcm gcm gcm gcm gcm";
                    getSharedPreferences("diary", MODE_PRIVATE).edit().putString("diary", diary_comments).apply();
                    String experiments = "No experiment for the initial day.";
                    getSharedPreferences("experiments", MODE_PRIVATE).edit().putString("experiments", experiments).apply();
                }
            }).start();


            String[] moods = new String[0];

            ArrayList<String> moodsArrayList = new ArrayList<String>();

            moodsArrayList.add(String.valueOf(mood));

            moods = moodsArrayList.toArray(moods);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < moods.length; i++) {
                sb.append(moods[i]).append("gcm");
            }
            getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).edit().putString("moods", sb.toString()).apply();

            getApplicationContext().getSharedPreferences("exp", MODE_PRIVATE).edit().putString("picked", "picked").apply();


            //settings
            getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).edit().putString("limit", "0:0").apply();
            getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).edit().putInt("nrNotif", 1).apply();
            getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).edit().putBoolean("disableAll", false).apply();
            getApplicationContext().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();

            getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("menu", true).apply();
            getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("experiments", true).apply();
            getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("data", true).apply();
            getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("diary", true).apply();
            getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("calendar", true).apply();
            getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("questionnaire", true).apply();

        }
    }

}

