package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Questionnaire extends Fragment {

    private SeekBarWithIntervals timesPerNightBar = null;
    private SeekBarWithIntervals nightTerrorsBar = null;

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View questionnaireView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        questionnaireView = inflater.inflate(R.layout.questionnaire, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("bmsleep", MODE_PRIVATE);
        ImageView imageView = questionnaireView.findViewById(R.id.imageView6);
        imageView.setImageResource(preferences.getInt("selectedbitmoji", 0));
        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        }); 

        List<String> seekbarIntervals = getIntervals("upToFour", "times");
        getSeekbarWithIntervals("times").setIntervals(seekbarIntervals);

        List<String> seekbarIntervals2 = getIntervals("upToFour", "terrors");
        getSeekbarWithIntervals("nightTerrors").setIntervals(seekbarIntervals2);




        return questionnaireView;
    }

    private List<String> getIntervals(String command, final String question) {

        final int previousTimes = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("timesPerNight", 0);
        final int previousTerrors = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("nightTerrors", 0);

        if (command.equals("upToFour")) {

            return new ArrayList<String>() {{
                add("0");
                add("1");
                add("2");
                add("3");
                add("4/4+");
                 if(question.equals("times")){
                     if (previousTimes == 1){
                         add("0");
                     } else  if (previousTimes == 2){
                         add("1");
                     } else if (previousTimes == 3){
                         add("2");
                     } else if (previousTimes == 4){
                         add("3");
                     } else if (previousTimes == 5){
                         add("4/4+");
                     }
                 } else {
                     if (previousTerrors == 1){
                         add("0");
                     } else  if (previousTerrors == 2){
                         add("1");
                     } else if (previousTerrors == 3){
                         add("2");
                     } else if (previousTerrors == 4){
                         add("3");
                     } else if (previousTerrors == 5){
                         add("4/4+");
                     }
                 }

            }};
        }
        return null;
    }

    private SeekBarWithIntervals getSeekbarWithIntervals(String name) {

        if (name.equals("times")) {
            if (timesPerNightBar == null) {
                timesPerNightBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.timesPerNightBar);
            }

            return timesPerNightBar;
        } else if (name.equals("nightTerrors")) {
            if (nightTerrorsBar == null) {
                nightTerrorsBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.nightTerrorsBar);
            }

            return nightTerrorsBar;
        }
        return null;
    }


    public void goToThirdActivity(){


        final int timesPerNight = timesPerNightBar.getProgress();
        final int nightTerrors = nightTerrorsBar.getProgress();

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("timesPerNight", timesPerNight + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("nightTerrors", nightTerrors + 1).apply();


        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, new Questionnaire2()).commit();
    }


}
