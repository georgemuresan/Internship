package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Update_Caffeine_limit extends Fragment {
    private SeekBarWithIntervals dayReviewBar = null;
    private SeekBarWithIntervals coffeeBar = null;
    private SeekBarWithIntervals cansBar = null;
    private SeekBarWithIntervals energyBar = null;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View updateView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        updateView = inflater.inflate(R.layout.update_caffeine_limit, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("bmsoda", MODE_PRIVATE);
        ImageView imageView = updateView.findViewById(R.id.imageView13);
        imageView.setImageResource(preferences.getInt("selectedbitmoji", 0));


        List<String> listOne = getIntervals("dayReview");
        getSeekbarWithIntervals("dayReviewBar").setIntervals(listOne);

        List<String> listTwo = getIntervals("coffee");
        getSeekbarWithIntervals("coffee").setIntervals(listTwo);

        List<String> listThree = getIntervals("soda");
        getSeekbarWithIntervals("soda").setIntervals(listThree);

        List<String> listFour = getIntervals("energy");
        getSeekbarWithIntervals("energy").setIntervals(listFour);

        Button button = (Button) updateView.findViewById(R.id.submitUpdate);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToQuestionnaire();

            }

        });


        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String currentDate = formatter1.format(calendar1.getTime());

        if (currentDate.compareTo("18:59") < 0 || getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getBoolean("completed", false)) {
            ConstraintLayout fr = (ConstraintLayout) updateView.findViewById(R.id.fr);
            disableEnableControls(false, fr);
            Toast.makeText(getActivity().getApplicationContext(), "You cannot do the questionnaire before 7:00 PM.", Toast.LENGTH_SHORT).show();

        }


        return updateView;
    }

    private void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    public void goToQuestionnaire() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        final int dayReview = dayReviewBar.getProgress();
        final int coffee = coffeeBar.getProgress();
        final int soda = cansBar.getProgress();
        final int energy = energyBar.getProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                UserExperiment user = new UserExperiment();
                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                user.setUsername(username);
                user.setDate(formattedDate);
                user.setExperiment("C2");

                user.setCaffeineTwoCups(coffee);
                user.setCaffeineTwoCans(soda);
                user.setCaffeineTwoEnergy(energy);

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
        } else if (command.equals("coffee")) {
            return new ArrayList<String>() {{
                add("0");
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
                add("6");
                add(">6");
            }};
        } else if (command.equals("soda")) {
            return new ArrayList<String>() {{
                add("0");
                add("1/2");
                add("3/4");
                add("5/6");
                add("7/8");
                add("9/10");
                add(">10");
            }};
        } else if (command.equals("energy")) {
            return new ArrayList<String>() {{
                add("1");
                add("2");
                add("3");
                add(">3");
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
        } else if (name.equals("coffee")) {
            if (coffeeBar == null) {
                coffeeBar = (SeekBarWithIntervals) updateView.findViewById(R.id.cupsBar);
            }

            return coffeeBar;
        } else if (name.equals("soda")) {
            if (cansBar == null) {
                cansBar = (SeekBarWithIntervals) updateView.findViewById(R.id.cansBar);
            }

            return cansBar;
        } else if (name.equals("energy")) {
            if (energyBar == null) {
                energyBar = (SeekBarWithIntervals) updateView.findViewById(R.id.energyBar);
            }

            return energyBar;
        }
        return null;
    }
}
