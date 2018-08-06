package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
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

        SharedPreferences preferences = this.getActivity().getSharedPreferences("bmwalking", MODE_PRIVATE);
        ImageView imageView = questionnaireView.findViewById(R.id.imageView7);
        imageView.setImageResource(preferences.getInt("selectedbitmoji", 0));

        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });


        List<String> listOne = getIntervals("upToFive");

        getSeekbarWithIntervals("fallAsleep").setIntervals(listOne);

        getSeekbarWithIntervals("wakeUp").setIntervals(listOne);

        getSeekbarWithIntervals("fresh").setIntervals(listOne);


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
        } else if (command.equals("upToFive")) {
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


    public void goToThirdActivity() {


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
