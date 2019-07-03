package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class QFinal extends Fragment {

   private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View questionnaireView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        questionnaireView = inflater.inflate(R.layout.act_ques_final, container, false);


        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                loopForSending();

            }

        });

        return questionnaireView;
    }

    private void loopForSending(){
        //     System.out.println("IS NETWORK : " + isConnected());
        if (isConnected()){
            goToMenu();
        } else {
            QInitial.InternetDialog dial = new QInitial.InternetDialog();
            dial.show(getFragmentManager(), "dialog");
        }
    }


    public void goToMenu(){

        double mood =0;

        RadioGroup qGroup = questionnaireView.findViewById(R.id.q1Group);
        int qID = qGroup.getCheckedRadioButtonId();
        View radioButton = qGroup.findViewById(qID);
        final int howLong = qGroup.indexOfChild(radioButton) +1;
        mood += howLong;

        //RadioButton r = (RadioButton) q1Group.getChildAt(idx);
        // final String howLong = r.getText().toString();

        qGroup = questionnaireView.findViewById(R.id.q2Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int awake = qGroup.indexOfChild(radioButton) +1;
        mood += awake;

        qGroup = questionnaireView.findViewById(R.id.q3Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int earlier = qGroup.indexOfChild(radioButton) +1;
        mood += earlier;

        qGroup = questionnaireView.findViewById(R.id.q5Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int quality = qGroup.indexOfChild(radioButton) +1;
        mood += quality;

        qGroup = questionnaireView.findViewById(R.id.q6Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactMood = qGroup.indexOfChild(radioButton) +1;
        mood += impactMood;

        qGroup = questionnaireView.findViewById(R.id.q7Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactActivities = qGroup.indexOfChild(radioButton) +1;
        mood += impactActivities;

        qGroup = questionnaireView.findViewById(R.id.q8Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactGeneral = qGroup.indexOfChild(radioButton) +1;
        mood += impactGeneral;


        mood = mood/7;

        DecimalFormat formatting = new DecimalFormat("#.##");
        mood = Double.parseDouble(formatting.format(mood));


        String participantID = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

        startActivity(new Intent(getActivity().getApplicationContext(), MainMenu.class));


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String quesDate = df.format(c);


        final double finalMood = mood;

        new Thread(new Runnable() {
            @Override
            public void run() {

                 userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                UserQuestionnaire user = new UserQuestionnaire();
                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");
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

                getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("howLong", howLong).apply();
                getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("awake", awake).apply();
                getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("earlier", earlier).apply();
                getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("quality", quality).apply();
                getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactMood", impactMood).apply();
                getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactActivities", impactActivities).apply();
                getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactGeneral", impactGeneral).apply();
                getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).edit().putFloat("mood", (float) finalMood).apply();

                userDatabase.daoAccess().insertSingleUserQuestionnaire(user);

                Report rep = new Report(userDatabase, getActivity().getApplicationContext());
                rep.save(username, false, getActivity().getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
            }
        }).start();



        String moods_string = getActivity().getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).getString("moods", "");

        String[] moods = moods_string.split("gcm");

        ArrayList<String> moodsArrayList = new ArrayList<String>(Arrays.asList(moods));

        moodsArrayList.add(String.valueOf(mood));

        moods = moodsArrayList.toArray(moods);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < moods.length; i++) {
            sb.append(moods[i]).append("gcm");
        }
        getActivity().getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).edit().putString("moods", sb.toString()).apply();

        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");
        String currentExperiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");


        ArrayList<String> experimentsArrayList = new ArrayList<String>(Arrays.asList(experimentsArray));
        experimentsArrayList.add(currentExperiment + ".");


        experimentsArray = experimentsArrayList.toArray(experimentsArray);

        sb = new StringBuilder();
        for (int i = 0; i < experimentsArray.length; i++) {
            sb.append(experimentsArray[i]).append("gcm");
        }
        getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).edit().putString("experiments", sb.toString()).apply();

        getActivity().getSharedPreferences("exp", MODE_PRIVATE).getString("picked", "picked");
        getActivity().getSharedPreferences("exp", MODE_PRIVATE).edit().putString("picked", "picked").apply();

    }

    public boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}