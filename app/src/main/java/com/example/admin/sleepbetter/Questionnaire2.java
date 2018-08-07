package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Questionnaire2 extends Fragment {

    private SeekBarWithIntervals fallAsleepBar = null;
    private SeekBarWithIntervals wakeUpBar = null;
    private SeekBarWithIntervals freshBar = null;

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View questionnaireView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        questionnaireView = inflater.inflate(R.layout.questionnaire_two, container, false);

        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });


        List<String> listOne = getIntervals("upToFive", "fall");

        getSeekbarWithIntervals("fallAsleep").setIntervals(listOne);

        List<String> listOne2 = getIntervals("upToFive", "wake");

        getSeekbarWithIntervals("wakeUp").setIntervals(listOne2);

        List<String> listOne3 = getIntervals("upToFive", "fresh");

        getSeekbarWithIntervals("fresh").setIntervals(listOne3);


        return questionnaireView;
    }

    private List<String> getIntervals(String command, final String question) {

        final int previousWake = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("wakeUp", 0);
        final int previousFall = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("fallAsleep", 0);
        final int previousFresh = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("fresh", 0);

        if (command.equals("upToFive")) {
            return new ArrayList<String>() {{
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
                if(question.equals("fall")){
                    add(String.valueOf(previousFall));
                } else  if(question.equals("wake")){
                    add(String.valueOf(previousWake));
                } else  if(question.equals("fresh")){
                    add(String.valueOf(previousFresh));
                }
            }};
        }
        return null;
    }

    private SeekBarWithIntervals getSeekbarWithIntervals(String name) {

        if (name.equals("fallAsleep")) {
            if (fallAsleepBar == null) {
                fallAsleepBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.fallAsleepBar);
            }

            return fallAsleepBar;
        } else if (name.equals("wakeUp")) {
            if (wakeUpBar == null) {
                wakeUpBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.easyWakeUpBar);
            }

            return wakeUpBar;
        } else if (name.equals("fresh")) {
            if (freshBar == null) {
                freshBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.freshBar);
            }

            return freshBar;
        }
        return null;
    }


    public void goToThirdActivity(){


        final int fallAsleep = fallAsleepBar.getProgress();
        final int wakeUp = wakeUpBar.getProgress();
        final int fresh = freshBar.getProgress();

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("fallAsleep", fallAsleep + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("wakeUp", wakeUp + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("fresh", fresh + 1).apply();


        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, new Questionnaire3()).commit();
    }


}
