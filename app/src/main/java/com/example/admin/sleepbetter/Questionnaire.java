package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        Button button = (Button) questionnaireView.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });

        List<String> seekbarIntervals = getIntervals("upToFour");
        getSeekbarWithIntervals("times").setIntervals(seekbarIntervals);

        List<String> listOne = getIntervals("upToFive");
        getSeekbarWithIntervals("nightTerrors").setIntervals(listOne);




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


        Intent intent = new Intent(getActivity().getApplicationContext(), Questionnaire2.class);

        final int timesPerNight = timesPerNightBar.getProgress();
        final int nightTerrors = nightTerrorsBar.getProgress();

        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("timesPerNight", timesPerNight).apply();
        getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("nightTerrors", nightTerrors).apply();

        startActivity(intent);
    }


}
