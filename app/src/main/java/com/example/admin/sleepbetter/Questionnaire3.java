package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Questionnaire3 extends Fragment {

    private SeekBarWithIntervals sadBar = null;
    private SeekBarWithIntervals sleepyBar = null;
    private SeekBarWithIntervals tiredBar = null;
    private SeekBarWithIntervals stressedBar = null;
    private SeekBarWithIntervals irritableBar = null;

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View questionnaireView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        questionnaireView = inflater.inflate(R.layout.questionnaire_three, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("bmok", MODE_PRIVATE);
        ImageView imageView = questionnaireView.findViewById(R.id.imageView8);
        imageView.setImageResource(preferences.getInt("selectedbitmoji", 0));



        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });


        List<String> listOne = getIntervals("upToFive", "sad");

        getSeekbarWithIntervals("sad").setIntervals(listOne);

        List<String> listOne2 = getIntervals("upToFive", "sleepy");

        getSeekbarWithIntervals("sleepy").setIntervals(listOne2);

        List<String> listOne3 = getIntervals("upToFive", "tired");

        getSeekbarWithIntervals("tired").setIntervals(listOne3);


        List<String> listOne4 = getIntervals("upToFive", "stressed");

        getSeekbarWithIntervals("stressed").setIntervals(listOne4);


        List<String> listOne5 = getIntervals("upToFive", "irritable");

        getSeekbarWithIntervals("irritable").setIntervals(listOne5);





        return questionnaireView;
    }

    private List<String> getIntervals(String command, final String question) {
        final int prebiousSad = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("sad", 0);
        final int previousSleeppy = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("sleepy", 0);
        final int previousTired = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("tired", 0);
        final int previousStressed = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("stressed", 0);
        final int previousIrritable = getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("irritable", 0);


        if (command.equals("upToFive")) {
            return new ArrayList<String>() {{
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
                if(question.equals("sad")){
                    add(String.valueOf(prebiousSad));
                } else  if(question.equals("sleepy")){
                    add(String.valueOf(previousSleeppy));
                } else  if(question.equals("tired")){
                    add(String.valueOf(previousTired));
                } else  if(question.equals("stressed")){
                    add(String.valueOf(previousStressed));
                } else  if(question.equals("irritable")){
                    add(String.valueOf(previousIrritable));
                }

            }};
        }
        return null;
    }

    private SeekBarWithIntervals getSeekbarWithIntervals(String name) {

        if (name.equals("sad")) {
            if (sadBar == null) {
                sadBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.sadBar);
            }

            return sadBar;
        } else if (name.equals("sleepy")) {
            if (sleepyBar == null) {
                sleepyBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.sleepyBar);
            }

            return sleepyBar;
        } else if (name.equals("tired")) {
            if (tiredBar == null) {
                tiredBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.tiredBar);
            }

            return tiredBar;
        } else if (name.equals("stressed")) {
            if (stressedBar == null) {
                stressedBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.stressedBar);
            }

            return stressedBar;
        } else if (name.equals("irritable")) {
            if (irritableBar == null) {
                irritableBar = (SeekBarWithIntervals) questionnaireView.findViewById(R.id.irritableBar);
            }

            return irritableBar;
        }
        return null;
    }


    public void goToThirdActivity(){


        final int sad = sadBar.getProgress();
        final int sleepy = sleepyBar.getProgress();
        final int tired = tiredBar.getProgress();
        final int stressed = stressedBar.getProgress();
        final int irritable = irritableBar.getProgress();

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("sad", sad + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("sleepy", sleepy + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("tired", tired + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("stressed", stressed + 1).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("irritable", irritable + 1).apply();


        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, new Questionnaire4()).commit();
    }


}
