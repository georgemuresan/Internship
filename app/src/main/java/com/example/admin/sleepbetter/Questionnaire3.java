package com.example.admin.sleepbetter;

import android.app.Fragment;
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

        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });


        List<String> listOne = getIntervals("upToFive");

        getSeekbarWithIntervals("sad").setIntervals(listOne);

        getSeekbarWithIntervals("sleepy").setIntervals(listOne);

        getSeekbarWithIntervals("tired").setIntervals(listOne);

        getSeekbarWithIntervals("stressed").setIntervals(listOne);

        getSeekbarWithIntervals("irritable").setIntervals(listOne);





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


        Intent intent = new Intent(getActivity().getApplicationContext(), Questionnaire4.class);

        final int sad = sadBar.getProgress();
        final int sleepy = sleepyBar.getProgress();
        final int tired = tiredBar.getProgress();
        final int stressed = stressedBar.getProgress();
        final int irritable = irritableBar.getProgress();

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("sad", sad).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("sleepy", sleepy).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("tired", tired).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("stressed", stressed).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("irritable", irritable).apply();

        startActivity(intent);
    }


}
