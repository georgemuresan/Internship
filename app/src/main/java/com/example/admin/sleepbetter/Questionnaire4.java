package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Questionnaire4 extends Fragment {

    private SeekBarWithIntervals apetiteBar = null;
    private SeekBarWithIntervals concentrateBar = null;
    private SeekBarWithIntervals coordinateBar = null;

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View questionnaireView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        questionnaireView = inflater.inflate(R.layout.questionnaire_four, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("bmjump", MODE_PRIVATE);
        ImageView imageView = questionnaireView.findViewById(R.id.imageView9);
        imageView.setImageResource(preferences.getInt("selectedbitmoji", 0));

        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });

        List<String> listOne = getIntervals("upToFive");

        getSeekbarWithIntervals("concentrate").setIntervals(listOne);

        getSeekbarWithIntervals("coordinate").setIntervals(listOne);

        getSeekbarWithIntervals("apetite").setIntervals(listOne);

        return questionnaireView;
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

        if (name.equals("concentrate")) {
            if (concentrateBar == null) {
                concentrateBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.concentrateBar);
            }

            return concentrateBar;
        } else if (name.equals("coordinate")) {
            if (coordinateBar == null) {
                coordinateBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.coordinateBar);
            }

            return coordinateBar;
        } else if (name.equals("apetite")) {
            if (apetiteBar == null) {
                apetiteBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.apetiteBar);
            }

            return apetiteBar;
        }
        return null;
    }


    public void goToThirdActivity(){

        System.out.println("test");
        Intent intent = new Intent(getActivity().getApplicationContext(), MainMenu.class);

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getBoolean("completed", false);
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putBoolean("completed", true).apply();

        final int timesPerNight = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("timesPerNight", 0);
        final int nightTerrors = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("nightTerrors", 0);

        final int fallAsleep = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("fallAsleep", 0);
        final int wakeUp = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("wakeUp", 0);
        final int fresh = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("fresh", 0);

        final int sad = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("sad", 0);
        final int sleepy = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("sleepy", 0);
        final int tired = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("tired", 0);
        final int stressed = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("stressed", 0);
        final int irritable = getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getInt("irritable", 0);

        final int apetite = apetiteBar.getProgress();
        final int concentrate = concentrateBar.getProgress();
        final int coordinate = coordinateBar.getProgress();

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("apetite", apetite + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("concentrate", concentrate + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("coordinate", coordinate + 1).apply();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).edit().putInt("mood", moodCalculator(timesPerNight, nightTerrors, sad, sleepy, tired, stressed, irritable, concentrate+1, coordinate+1)).apply();

        EditText commentBox = (EditText) questionnaireView.findViewById(R.id.yourName2);
        final String comment = commentBox.getText().toString();

        startActivity(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("entered thread");
                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                UserQuestionnaire user = new UserQuestionnaire();
                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");
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
                user.setApetite(apetite+1);
                user.setConcentrate(concentrate+1);
                user.setCoordinate(coordinate+1);
                user.setIrritable(irritable);
                user.setMood(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0));

                userDatabase.daoAccess().insertSingleUserQuestionnaire(user);

                System.out.println("mood id " + getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0));
                System.out.println(userDatabase.daoAccess().fetchUserQuestionnaires().size() + " questionnaires");
                if (!comment.equals("")) {


                    UserDiary userDiary = new UserDiary();
                    userDiary.setUsername(username);
                    userDiary.setDate(formattedDate);
                    userDiary.setComment(comment);

                    userDatabase.daoAccess().insertSingleUserDiary(userDiary);
                }


                Report rep = new Report(userDatabase, getActivity().getApplicationContext());
                rep.save(username, false, getActivity().getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
            }
        }).start();

        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", Context.MODE_PRIVATE).getString("experiment", " ");

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate2 = df2.format(c);

        System.out.println("passed thread");
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
            commentt = commentt + " / " + comment;

            HomeCollection.date_collection_arr.remove(HomeCollection.date_collection_arr.size()-1);

            HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, experiment, String.valueOf(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), "No experiment started yet", commentt));

            SharedPreferences.Editor editor = sharedPrefs.edit();

            json = gson.toJson(HomeCollection.date_collection_arr);

            editor.putString("trial", json);
            editor.commit();
        } else {
            HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, experiment,  String.valueOf(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), "No experiment started yet", comment));

            SharedPreferences.Editor editor = sharedPrefs.edit();

            json = gson.toJson(HomeCollection.date_collection_arr);

            editor.putString("trial", json);
            editor.commit();
        }

        int days =  getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("days", 0);
        if (days % 5 == 1){
            getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("days", days).apply();
        } else {
            getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("days", days + 1).apply();
        }


    }
    private int moodCalculator(int timesNight, int nightmares, int sad, int sleepy, int tired, int stressed, int irritable, int concentrate, int coordinate) {
        System.out.println( timesNight + " " + nightmares + " " +   sad + " " +   sleepy + " " +   tired + " " +   stressed + " " +   irritable + " " +   concentrate + " " +   coordinate);
        int avgMood = (sad + sleepy + tired + stressed + irritable) / 5;
        int avgNight = (timesNight + nightmares) / 2;
        int avgAction = (concentrate + coordinate) / 2;
        int result = (avgMood * 3 + avgNight + avgAction) / 5;
        return result;
    }

}