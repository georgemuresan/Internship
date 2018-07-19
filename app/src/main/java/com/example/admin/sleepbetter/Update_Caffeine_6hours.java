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

import java.util.ArrayList;
import java.util.List;

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

       // final TimePicker tpLastdrink = (TimePicker) updateView.findViewById(R.id.lastDrink);
      //  String lastDrink = tpLastdrink.getCurrentHour() + ":" + tpLastdrink.getCurrentMinute();

      //  final TimePicker tpWhenSleep = (TimePicker) updateView.findViewById(R.id.whenSleep);
    //    String whenSleep = tpWhenSleep.getCurrentHour() + ":" + tpWhenSleep.getCurrentMinute();
//
    //    final int dayReview = dayReviewBar.getProgress();

    /*    new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                UserExperiment user = new UserExperiment();


               // userDatabase.daoAccess().insertSingleUserQuestionnaire(user);

               // Report rep = new Report(userDatabase, getActivity().getApplicationContext());
              //  rep.save();
            }
        }).start();

*/
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
