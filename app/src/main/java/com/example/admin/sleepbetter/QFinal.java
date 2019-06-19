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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        questionnaireView = inflater.inflate(R.layout.act_QuesIFinal, container, false);


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

        qGroup = questionnaireView.findViewById(R.id.q4Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int nightsAWeek = qGroup.indexOfChild(radioButton) +1;
        mood += nightsAWeek;

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

        qGroup = questionnaireView.findViewById(R.id.q9Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int problem = qGroup.indexOfChild(radioButton) +1;
        mood += problem;

        mood = mood/9;

        Intent intent = new Intent(getActivity().getApplicationContext(), MainMenu.class);

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("apetite", 0);
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("apetite", 1).apply();


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String quesDate = df.format(c);

        getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).getString("currentDate", "");
        getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).edit().putString("currentDate", quesDate).apply();


        startActivity(intent);

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
                user.setNightsAWeek(nightsAWeek);
                user.setQuality(quality);
                user.setImpactMood(impactMood);
                user.setImpactActivities(impactActivities);
                user.setImpactGeneral(impactGeneral);
                user.setProblem(problem);
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

        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", Context.MODE_PRIVATE).getString("experiment", " ");

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate2 = df2.format(c);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String json = sharedPrefs.getString("trial", "");
        Gson gson = new Gson();

        Type type = new TypeToken<List<HomeCollection>>() {}.getType();
        List<HomeCollection> arrayList = gson.fromJson(json, type);

        HomeCollection.date_collection_arr = (ArrayList<HomeCollection>) arrayList;
        HomeCollection coll = HomeCollection.date_collection_arr.get(HomeCollection.date_collection_arr.size()-1);
        String date = coll.date;

        if (date.equals(formattedDate2)){
            String commentt = coll.comment;
            commentt = commentt + " / " + "";

            HomeCollection.date_collection_arr.remove(HomeCollection.date_collection_arr.size()-1);

            String proof = getActivity().getApplicationContext().getSharedPreferences("proof", MODE_PRIVATE).getString("proof", "No proof logged in yet.");

            HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, experiment, String.valueOf(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), proof, commentt));

            SharedPreferences.Editor editor = sharedPrefs.edit();

            json = gson.toJson(HomeCollection.date_collection_arr);

            editor.putString("trial", json);
            editor.commit();
        } else {
            String proof = getActivity().getApplicationContext().getSharedPreferences("proof", MODE_PRIVATE).getString("proof", "No proof logged in yet.");

            HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, experiment,  String.valueOf(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), proof, ""));

            SharedPreferences.Editor editor = sharedPrefs.edit();

            json = gson.toJson(HomeCollection.date_collection_arr);

            editor.putString("trial", json);
            editor.commit();
        }

        int days =  getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("days", 0);
        Boolean wasRightAfterChangeOfExperiment = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getBoolean("afterExperiment", true);;

        if (days % 5 == 1 && wasRightAfterChangeOfExperiment){
            getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("days", days).apply();
            getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putBoolean("afterExperiment", false).apply();

        } else {
            getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("days", days + 1).apply();
        }


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