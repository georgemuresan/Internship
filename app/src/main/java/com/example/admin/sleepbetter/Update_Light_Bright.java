package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Update_Light_Bright extends Fragment {

    private SeekBarWithIntervals dayReviewBar = null;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    View updateView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        updateView = inflater.inflate(R.layout.update_light_bright, container, false);

        List<String> listOne = getIntervals("dayReview");
        getSeekbarWithIntervals("dayReviewBar").setIntervals(listOne);

        Button button = (Button) updateView.findViewById(R.id.submitUpdate);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToQuestionnaire();

            }

        });


        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("h:mm");
        String currentDate = formatter1.format(calendar1.getTime());

        if(currentDate.compareTo("18:59")<0 || getActivity().getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).getBoolean("completed", false)) {   ConstraintLayout fr = (ConstraintLayout) updateView.findViewById(R.id.fr);
            disableEnableControls(false, fr);
            Toast.makeText(getActivity().getApplicationContext(), "You cannot do the questionnaire before 7:00 PM.", Toast.LENGTH_SHORT).show();

        }



        return updateView;
    }

    private void disableEnableControls(boolean enable, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
        }
    }
    public void goToQuestionnaire(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        RadioGroup radioButtonGroup = updateView.findViewById(R.id.sunlightGroup);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int idx = radioButtonGroup.indexOfChild(radioButton);
        RadioButton r = (RadioButton)  radioButtonGroup.getChildAt(idx);
        final String textSunlight = r.getText().toString();

        RadioGroup radioButtonGroup2 = updateView.findViewById(R.id.glassesGroup);
        int radioButtonID2 = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton2 = radioButtonGroup.findViewById(radioButtonID2);
        int idx2 = radioButtonGroup.indexOfChild(radioButton2);
        RadioButton r2 = (RadioButton)  radioButtonGroup.getChildAt(idx2);
        final String textHalf = r2.getText().toString();

        RadioGroup radioButtonGroup3 = updateView.findViewById(R.id.appGroup);
        int radioButtonID3 = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton3 = radioButtonGroup.findViewById(radioButtonID3);
        int idx3 = radioButtonGroup.indexOfChild(radioButton3);
        RadioButton r3 = (RadioButton)  radioButtonGroup.getChildAt(idx3);
        final String textRoom = r3.getText().toString();

        final int dayReview = dayReviewBar.getProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                UserExperiment user = new UserExperiment();
                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                user.setUsername(username);
                user.setDate(formattedDate);
                user.setExperiment("L1");

                user.setLightOneSunlightExposure(textSunlight);
                user.setLightOneHalfAnHour(textHalf);
                user.setLightOneCapturesSunlight(textRoom);

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
