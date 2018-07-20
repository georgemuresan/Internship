package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Update_Caffeine_6hours extends Fragment {
    private SeekBarWithIntervals dayReviewBar = null;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View updateView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        updateView = inflater.inflate(R.layout.update_caffeine_six, container, false);

        List<String> listOne = getIntervals("dayReview");
        getSeekbarWithIntervals("dayReviewBar").setIntervals(listOne);

        Button button = (Button) updateView.findViewById(R.id.submitUpdate);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToQuestionnaire();

            }

        });



        return updateView;
    }

    public void goToQuestionnaire(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        final TimePicker tpLastdrink = (TimePicker) updateView.findViewById(R.id.lastDrink);
        final String lastDrink = tpLastdrink.getCurrentHour() + ":" + tpLastdrink.getCurrentMinute();

        final TimePicker tpWhenSleep = (TimePicker) updateView.findViewById(R.id.whenSleep);
        final String whenSleep = tpWhenSleep.getCurrentHour() + ":" + tpWhenSleep.getCurrentMinute();

        final int dayReview = dayReviewBar.getProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                UserExperiment user = new UserExperiment();
                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                user.setUsername(username);
                user.setDate(formattedDate);
                user.setExperiment("C1");
                user.setCaffeineOneWhenDrink(lastDrink);
                user.setCaffeineOneWhenSleep(whenSleep);
                user.setOverallBetter(dayReview);

                userDatabase.daoAccess().insertSingleUserExperiment(user);


            }
        }).start();


        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, new Questionnaire()).commit();
    }

        private List<String> getIntervals(String command) {

            if (command.equals("dayReview")) {
                return new ArrayList<String>() {{
                    add("-2");
                    add("-1");
                    add("0");
                    add("1");
                    add("2");
                }};
            }
            return null;
        }

        private SeekBarWithIntervals getSeekbarWithIntervals(String name) {

            if (name.equals("dayReviewBar")) {
                if (dayReviewBar == null) {
                    dayReviewBar = (SeekBarWithIntervals) updateView.findViewById(R.id.dayReviewBar);
                }

                return dayReviewBar;
            }
            return null;
        }
    }
