package com.uos.admin.sleepbetter;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


public class Factors extends Fragment {

    View factorsView;

    private static RadioGroup radioGroup;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        factorsView = inflater.inflate(R.layout.act_experiments, container, false);


        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (getView() == null && tabLayout.getSelectedTabPosition() == 1) {
            if (getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).getBoolean("experiments", true) == true) {

                InfoFirstDialog dia = new InfoFirstDialog();
                dia.show(getFragmentManager(), "dialog");

                getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("experiments", false).apply();

            }

        }

       Button button2 = (Button) factorsView.findViewById(R.id.submit);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitExperiment();
            }
        });

        TextView cons8 = factorsView.findViewById(R.id.factorsIntro);
        cons8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        return factorsView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (getView() != null && tabLayout.getSelectedTabPosition() == 1) {
            loadPageDataProcessing();
            if (getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).getBoolean("experiments", true) == true) {

                InfoFirstDialog dia = new InfoFirstDialog();
                dia.show(getFragmentManager(), "dialog");

                getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("experiments", false).apply();

            }

        }


    }

    @Override
    public void onResume() {
        super.onResume();

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (tabLayout.getSelectedTabPosition() == 1) {
            loadPageDataProcessing();
        }
    }


    public void loadPageDataProcessing() {
        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");
        int savedRadioIndex = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);

        radioGroup = (RadioGroup) factorsView.findViewById(R.id.experimentsGroup);

        if (!experiment.equals("nothing")) {
            RadioButton savedCheckedRadioButton = (RadioButton) radioGroup.getChildAt(savedRadioIndex);
            savedCheckedRadioButton.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int checkedIndex;

                RadioButton rb = (RadioButton) factorsView.findViewById(checkedId);
                if (rb.getText().equals(getString(R.string.firstLight))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.firstLight)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 1).apply();
                } else if (rb.getText().equals(getString(R.string.secondLight))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.secondLight)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 2).apply();
                } else if (rb.getText().equals(getString(R.string.thirdLight))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.thirdLight)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 3).apply();
                } else if (rb.getText().equals(getString(R.string.firstCaffeine))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.firstCaffeine)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 5).apply();
                } else if (rb.getText().equals(getString(R.string.secondCaffeine))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.secondCaffeine)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 6).apply();
                } else if (rb.getText().equals(getString(R.string.thirdCaffeine))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.thirdCaffeine)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 7).apply();
                } else if (rb.getText().equals(getString(R.string.firstSchedule))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.firstSchedule)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 9).apply();
                } else if (rb.getText().equals(getString(R.string.secondSchedule))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.secondSchedule)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 10).apply();
                } else if (rb.getText().equals(getString(R.string.thirdSchedule))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.thirdSchedule)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 11).apply();
                } else if (rb.getText().equals(getString(R.string.fourthSchedule))) {
                    getActivity().getSharedPreferences("name", MODE_PRIVATE).edit().putString("experiment", getString(R.string.fourthSchedule)).apply();
                    getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("KEY_SAVED_RADIO_BUTTON_INDEX", 12).apply();
                }
            }
        });


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String currentDate = df.format(c);

        String previousExperimentStartDate = getActivity().getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH");
        String currentHour = formatter1.format(calendar1.getTime());

        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");

        int loggedIn = experimentsArray.length;
        Date date1 = null;
        Date date2 = null;

        SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");

        if (previousExperimentStartDate.equals("")) {
            previousExperimentStartDate = currentDate;
        }
        //Setting dates
        try {
            date1 = dates.parse(currentDate);
            date2 = dates.parse(previousExperimentStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        int differenceBetweenOldExperimentAndCurrent = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

        //adding a new day only after the ques limit - e.g. after 4 am
        Calendar calendarr = Calendar.getInstance();
        SimpleDateFormat formatterr = new SimpleDateFormat("HH:mm");
        String currentHourr = formatterr.format(calendarr.getTime());

        String quesLimit = getActivity().getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).getString("limit", "0:0");
        String[] lastQuestNotifComponents = quesLimit.split(":");

        String[] currentHourComponents = currentHourr.split(":");
        if (Integer.valueOf(currentHourComponents[0]) < Integer.valueOf(lastQuestNotifComponents[0])) {
            differenceBetweenOldExperimentAndCurrent--;
        }


        System.out.println(loggedIn);
        if (differenceBetweenOldExperimentAndCurrent % 5 != 0) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
            Toast.makeText(getActivity().getApplicationContext(), "I'm sorry, you can't change your experiment as 5 days have not passed yet.", Toast.LENGTH_LONG).show();


        } else if (differenceBetweenOldExperimentAndCurrent == 0 && getActivity().getSharedPreferences("exp", MODE_PRIVATE).getString("picked", "").equals("newPicked")) {

            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                radioGroup.getChildAt(i).setEnabled(false);
            }
            Toast.makeText(getActivity().getApplicationContext(), "I'm sorry, you can't change your experiment yet as you've just picked it.", Toast.LENGTH_LONG).show();

        } else if (differenceBetweenOldExperimentAndCurrent % 5 == 0 && differenceBetweenOldExperimentAndCurrent != 0) {
            if (loggedIn % 5 != 1) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(false);
                }
                Toast.makeText(getActivity().getApplicationContext(), "I'm sorry, you can't change your experiment yet. You can change it after completing today's questionnaire.", Toast.LENGTH_LONG).show();

            }
        }


    }

    private void SavePreferences(String key, int value) {
        getActivity().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    private void submitExperiment() {

        FactorsDialog dia = new FactorsDialog();
        dia.show(getFragmentManager(), "dialog");

    }


    public static class FactorsDialog extends DialogFragment {

        private String message;
        private int hour, minute;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            int experiment = getActivity().getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);

            switch (experiment) {
                case 1: //increase bright light exposure
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nGetting sunlight exposure in the morrning;\nStaying at least half an hour in the sun per day;\nYour room needs to capture sunlight.");
                    message = "Stay out in the sun at least half an hour today!";
                    hour = 12;
                    minute = 0;
                    break;
                case 2: //wear glasses that block blue light during the night
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nMaybe installing the f.lx app fromm google that warms up your computer display at night, to match your indoor lighting;\nIf needed - wearing glasses/a sleeping mask to block blue light during the night.");
                    message = "\"Use the \\\"f.lux\\\" app!\" and wear a sleeping mask/glasses when going to sleep!";
                    hour = 12;
                    minute = 30;
                    break;
                case 3: // turn off any bright lights 2 hours before going to bed
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nTurning off the TV/computer with 2 hours before going to bed;\nTurning off any other bright lights in your room with 2 hours before going to bed.");
                    message = "Going to bed soon?, Do not forget to turn off your light with 2 hours before bed!";
                    hour = 19;
                    minute = 30;
                    break;
                case 5: // Do not drink caffeine within 6 hours
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNot drinking coffee/soda/any energy drink with 6 hours before sleep.");
                    message = "Don't drink caffeine within 6 hours of going to bed!!";
                    hour = 19;
                    minute = 30;
                    break;
                case 6: // Limit yourself to 4 cups of coffees per day; 10 canss of
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nLimiting yourself to drinking not more than 4 cups of coffee per day, 10 cans of soda or 2 energy drinks.");
                    message = "Limit yourself to 4 cups of coffee per day/10 cans of soda or 2 energy drinks!";
                    hour = 14;
                    minute = 0;
                    break;
                case 7: //Do not drink empty stomach
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNever drinking caffeine (coffee, soda, energy drinks) on empty stomach.");
                    message = "Try not to drink caffeine on an empty stomach!";
                    hour = 8;
                    minute = 0;
                    break;
                case 9://Usually get up at the same time everyday, even on weekends
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nGoing to bed and waking up at the same time everyday.");
                    message = "Do not forget! Go to bed and wake up at the same time as yesterday!";
                    hour = 18;
                    minute = 30;
                    break;
                case 10: // Sleep no lesss than 7 hours per night
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nSleeping at least 7 hours per night.");
                    message = "Sleep no less than 7 hours per night";
                    hour = 20;
                    minute = 0;
                    break;
                case 11: //DO not go to bed unless you are tired. If you are not
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNot going to bed unless you are tired;\nIf you are not, you should take a bath/read a book/stretch-short exercise/drink a hot cup of tea.");
                    message = "Do not go to bed unless you are tired. Read a book, take a bath, stretch or drink tea to relax!";
                    hour = 20;
                    minute = 0;
                    break;
                case 12: //Go to sleep at 22:30 PM the latest
                    builder.setMessage("Are you sure you want to choose this experiment? You will not be able to change it for the next 5 days. This experiment presumes:\nNot going to sleep after 22:30.");
                    message = "Go to sleep at 10:30 PM the latest!!";
                    hour = 21;
                    minute = 0;
                    break;
            }
            getActivity().getApplicationContext().getSharedPreferences("notification", MODE_PRIVATE).edit().putString("message", message).apply();


            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //daca nu e blocat, atunci se updateaza experimentul, se blocheaza accesul si se incrementeaza nr de zile
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        radioGroup.getChildAt(i).setEnabled(false);
                    }




                    getActivity().getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");

                    //adding a new day only after the ques limit - e.g. after 4 am
                    Calendar calendarr = Calendar.getInstance();
                    SimpleDateFormat formatterr = new SimpleDateFormat("HH:mm");
                    String currentHourr = formatterr.format(calendarr.getTime());

                    String quesLimit = getActivity().getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).getString("limit", "0:0");
                    String[] lastQuestNotifComponents = quesLimit.split(":");

                    String[] currentHourComponents = currentHourr.split(":");

                    if (Integer.valueOf(currentHourComponents[0]) < Integer.valueOf(lastQuestNotifComponents[0])) {

                        Date dt = Calendar.getInstance().getTime();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dt);
                        cal.add(Calendar.DATE, -1);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        final String currentDate = dateFormat.format(cal.getTime());

                        getActivity().getSharedPreferences("date", MODE_PRIVATE).edit().putString("startExperiment", currentDate).apply();

                        System.out.println(currentDate);
                    } else {

                        //update starting date

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        final String currentDate = df.format(c);
                        getActivity().getSharedPreferences("date", MODE_PRIVATE).edit().putString("startExperiment", currentDate).apply();

                        System.out.println(currentDate);

                    }


                    getActivity().getSharedPreferences("exp", MODE_PRIVATE).getString("picked", "newPicked");
                    getActivity().getSharedPreferences("exp", MODE_PRIVATE).edit().putString("picked", "newPicked").apply();

                    //


                    String participantID = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

                    Intent intent = new Intent(getActivity().getApplicationContext(), AllPages.class);

                    startActivity(intent);

                    createQuestionnaireNotification();
                    createExperimentNotification();


                }
            })
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

        private void createQuestionnaireNotification() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "reminder";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("13", name, importance);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);


            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 55);
            calendar.set(Calendar.SECOND, 0);

            if (Calendar.getInstance().after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            Intent intent1 = new Intent(getActivity(), QuestionnaireBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent1, 0);
            AlarmManager am1 = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
            am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        private void createExperimentNotification() {

            getActivity().getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).edit().putString("currentNot", hour + ":" + minute).apply();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 56);
            calendar.set(Calendar.SECOND, 0);

            if (Calendar.getInstance().after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            Intent intent1 = new Intent(getActivity(), ExperimentBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent1, 0);
            AlarmManager am = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }


    public static class InfoFirstDialog extends DialogFragment {

        private String message;
        private int hour, minute;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("This is where you will be able to choose your preferred experiment to try out for the next 5 days in order to improve your sleep. Please bare in mind that once you choose an experiment, you cannot change it for the next 5 days. When clicking submit, a dialog will pop up outlining the experiment information again and asking you if you are sure about your choice. Also, once 5 days have passed, you will be able to change the experiment after completing that day's questionnaire. If you won't change the experiment, your current one will continue for another 5 days.");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            return builder.create();
        }


    }

}
