package com.uos.admin.sleepbetter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Update extends Fragment {

    View helpView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        helpView = inflater.inflate(R.layout.act_update, container, false);

        return helpView;
    }

    private boolean checkIfAllowsQuestionnaire(){

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String currentHour = formatter1.format(calendar1.getTime());

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = formatter2.format(calendar1.getTime());


        String startingDate = getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).getString("startingDate", "");

        Date date1 = null;
        Date date2 = null;

        SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");

        //Setting dates
        try {
            date1 = dates.parse(currentDate);
            date2 = dates.parse(startingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        int shouldBe = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);


        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");

        System.out.println(experimentsArray.length);
        if (getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "").equals("No experiment for the initial day.") && shouldBe == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to fill in today's questionnaire. Choose an experiment if you haven't.", Toast.LENGTH_LONG).show();
            return false;
        } else if (currentHour.compareTo("18:59") < 0) {
            Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to fill in today's questionnaire yet. Come back at 19:00.", Toast.LENGTH_LONG).show();

            return false;
        } else if (experimentsArray.length - shouldBe >= 1) {
            Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to fill in today's questionnaire. Come back tomorrow.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }

    private boolean isViewShown = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            loadPageDataProcessing();
        } else {
            isViewShown = false;
        }
    }

    public void loadPageDataProcessing(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

        if (checkIfAllowsQuestionnaire()) {

            if (experiment.equals(getString(R.string.firstLight))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Light_Bright());
            } else if (experiment.equals(getString(R.string.secondLight))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Light_Glasses());
            } else if (experiment.equals(getString(R.string.thirdLight))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Light_TurnOffBright());
            } else if (experiment.equals(getString(R.string.firstCaffeine))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Caffeine_6hours());
            } else if (experiment.equals(getString(R.string.secondCaffeine))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Caffeine_limit());
            } else if (experiment.equals(getString(R.string.thirdCaffeine))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Caffeine_Empty());
            } else if (experiment.equals(getString(R.string.firstSchedule))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Schedule_SameTime());
            } else if (experiment.equals(getString(R.string.secondSchedule))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Schedule_7hours());
            } else if (experiment.equals(getString(R.string.thirdSchedule))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Schedule_Relax());
            } else if (experiment.equals(getString(R.string.fourthSchedule))) {
                fragmentTransaction.replace(R.id.content_frame, new Update_Schedule_Midnight());
            } else {
                fragmentTransaction.replace(R.id.content_frame, new Update());
            }

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            getFragmentManager().executePendingTransactions();
        }

    }
}
