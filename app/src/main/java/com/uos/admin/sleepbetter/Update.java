package com.uos.admin.sleepbetter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
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

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (getView() == null && tabLayout.getSelectedTabPosition() == 5) {
            loadPageDataProcessing();
            if (getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).getBoolean("questionnaire", true) == true){

                InfoFirstDialog dia = new InfoFirstDialog();
                dia.show(getFragmentManager(), "dialog");

                getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("questionnaire", false).apply();

            }
        }

        return helpView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (getView() != null && tabLayout.getSelectedTabPosition() == 5) {
            loadPageDataProcessing();
            if (getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).getBoolean("questionnaire", true) == true){

                InfoFirstDialog dia = new InfoFirstDialog();
                dia.show(getFragmentManager(), "dialog");

                getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("questionnaire", false).apply();

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (tabLayout.getSelectedTabPosition() == 5){
            loadPageDataProcessing();
        }
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

        //adding a new day only after the ques limit - e.g. after 4 am
        Calendar calendarr = Calendar.getInstance();
        SimpleDateFormat formatterr = new SimpleDateFormat("HH:mm");
        String currentHourr = formatterr.format(calendarr.getTime());

        String quesLimit = getActivity().getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).getString("limit", "0:0");
        String[] lastQuestNotifComponents = quesLimit.split(":");

        String[] currentHourComponents = currentHourr.split(":");
        if (Integer.valueOf(currentHourComponents[0]) < Integer.valueOf(lastQuestNotifComponents[0])) {
            shouldBe--;
        }

        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");


        if (experiments.equals("No experiment for the initial day.") && shouldBe == 0) {
            System.out.println("gggG");
            Toast.makeText(getActivity().getApplicationContext(), "I'm sorry, you cannot fill in today's questionnaire as this is the first day. Please choose an experiment if you haven't yet.", Toast.LENGTH_LONG).show();
            return false;
        } else if (experimentsArray.length - shouldBe >= 1) {
            Toast.makeText(getActivity().getApplicationContext(), "I'm sorry, you cannot fill in today's questionnaire. Come back tomorrow.", Toast.LENGTH_LONG).show();
            System.out.println("gggGefw4f");
            return false;
        } else if ((Integer.valueOf(currentHourComponents[0]) < 19 && Integer.valueOf(currentHourComponents[0]) > Integer.valueOf(lastQuestNotifComponents[0])) || (Integer.valueOf(currentHourComponents[0]) == Integer.valueOf(lastQuestNotifComponents[0]) && Integer.valueOf(currentHourComponents[1]) > Integer.valueOf(lastQuestNotifComponents[1]))) {
            Toast.makeText(getActivity().getApplicationContext(), "I'm sorry, you cannot fill in today's questionnaire yet. Come back at 19:00.", Toast.LENGTH_LONG).show();
            System.out.println("gggGwww");
            return false;
        } else {
            return true;
        }

    }


    public void loadPageDataProcessing(){
        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

        System.out.println(checkIfAllowsQuestionnaire());
        if (checkIfAllowsQuestionnaire()) {

            if (experiment.equals(getString(R.string.firstLight))) {
                startActivity(new Intent(getActivity(), Update_Light_Bright.class));
            } else if (experiment.equals(getString(R.string.secondLight))) {
                startActivity(new Intent(getActivity(), Update_Light_Glasses.class));
            } else if (experiment.equals(getString(R.string.thirdLight))) {
                startActivity(new Intent(getActivity(), Update_Light_TurnOffBright.class));
            } else if (experiment.equals(getString(R.string.firstCaffeine))) {
                startActivity(new Intent(getActivity(), Update_Caffeine_6hours.class));
            } else if (experiment.equals(getString(R.string.secondCaffeine))) {
                startActivity(new Intent(getActivity(), Update_Caffeine_limit.class));
            } else if (experiment.equals(getString(R.string.thirdCaffeine))) {
                startActivity(new Intent(getActivity(), Update_Caffeine_Empty.class));
            } else if (experiment.equals(getString(R.string.firstSchedule))) {
                startActivity(new Intent(getActivity(), Update_Schedule_SameTime.class));
            } else if (experiment.equals(getString(R.string.secondSchedule))) {
                startActivity(new Intent(getActivity(), Update_Schedule_7hours.class));
            } else if (experiment.equals(getString(R.string.thirdSchedule))) {
                startActivity(new Intent(getActivity(), Update_Schedule_Relax.class));
            } else if (experiment.equals(getString(R.string.fourthSchedule))) {
                startActivity(new Intent(getActivity(), Update_Schedule_Midnight.class));
            } else {
                startActivity(new Intent(getActivity(), Update.class));
            }

        }

    }

    public static class InfoFirstDialog extends DialogFragment {

        private String message;
        private int hour, minute;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("This is the page you will have to access after 7PM everyday in order to fill in the daily questionnaire. If you try to complete it at the different time, you will get a notice saying that is not possible. The limit for completing the questionnaire (the default is 12AM) can be extended in the Settings. First, you will be asked a few (3-4) questions about your experiment, and afterwards the questionnaire (with 7 questions) will begin. You will not be able to go back after starting the questionnaire so please make sure you have 1-2 minutes for filling this in before closing the app.");

            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            return builder.create();
        }


    }

}
