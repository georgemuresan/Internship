package com.uos.admin.sleepbetter;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Settings extends AppCompatActivity {

    private TextView navUsername;

    @RequiresApi(api = Build.VERSION_CODES.M)

    private TimePicker tpCurrentNotification = null;
    private TimePicker toQuesNotif = null;
    private RadioGroup group;
private CheckBox disableAllCheckBox;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_settings);


        Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (toQuesNotif.getHour() < 11) {
                    saveSettings();
                } else {
                    Toast.makeText(getApplicationContext(), "I'm sorry, you can't choose the limit for your questionnaire to be before 12AM or after 10 AM.", Toast.LENGTH_LONG).show();

                }

            }

        });

        navUsername = (TextView) findViewById(R.id.yourName2);
        String name = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
        navUsername.setText(name);


        TextView currentExperimentView = (TextView) findViewById(R.id.exp);

        currentExperimentView.setText("Experiment: " + getApplicationContext().getSharedPreferences("name", Context.MODE_PRIVATE).getString("experiment", " "));

        String currentnotif = getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).getString("currentNot", "nothing");
        String quesLimit = getSharedPreferences("notif", MODE_PRIVATE).getString("limit", "0:0");
        int nrNotif = getSharedPreferences("notif", MODE_PRIVATE).getInt("nrNotif", 0);
        boolean disableAll = getSharedPreferences("notif", MODE_PRIVATE).getBoolean("disableAll", false);

        String[] currentNotifComponents = currentnotif.split(":");

        tpCurrentNotification = (TimePicker) findViewById(R.id.lastDrink2);
        tpCurrentNotification.setHour(Integer.valueOf(currentNotifComponents[0]));
        tpCurrentNotification.setMinute(Integer.valueOf(currentNotifComponents[1]));

        String[] lastQuestNotifComponents = quesLimit.split(":");

        toQuesNotif = (TimePicker) findViewById(R.id.lastDrink3);
        toQuesNotif.setHour(Integer.valueOf(lastQuestNotifComponents[0]));
        toQuesNotif.setMinute(Integer.valueOf(lastQuestNotifComponents[1]));

        group = (RadioGroup) findViewById(R.id.radioGroup);

        RadioButton savedCheckedRadioButton = (RadioButton)group.getChildAt(nrNotif - 1);
        savedCheckedRadioButton.setChecked(true);

         disableAllCheckBox = (CheckBox) findViewById(R.id.checkBox);
        if (disableAll){
            disableAllCheckBox.setChecked(true);
        } else {
            disableAllCheckBox.setChecked(false);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveSettings(){

        String newName = String.valueOf(navUsername.getText());
        getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", newName).apply();


        getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).edit().putString("currentNot", String.valueOf(tpCurrentNotification.getHour()) + ":" + String.valueOf(tpCurrentNotification.getMinute())).apply();
        getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).edit().putString("limit", String.valueOf(toQuesNotif.getHour()) + ":" + String.valueOf(toQuesNotif.getMinute())).apply();

        int qID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(qID);
        final int nr = group.indexOfChild(radioButton) +1;
        getApplicationContext().getSharedPreferences("notif", MODE_PRIVATE).edit().putInt("nrNotif", nr).apply();


        getSharedPreferences("notif", MODE_PRIVATE).edit().putBoolean("disableAll", disableAllCheckBox.isChecked()).apply();

        //now remove all notifications
/*
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent cancelServiceIntent = new Intent(getApplicationContext(), ExperimentBroadcast.class);
        PendingIntent cancelServicePendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                13, // integer constant used to identify the service
                cancelServiceIntent,
                0 //no FLAG needed for a service cancel
        );
        am.cancel(cancelServicePendingIntent);

        Intent cancelServiceIntent2 = new Intent(getApplicationContext(), QuestionnaireBroadcast.class);
        PendingIntent cancelServicePendingIntent2 = PendingIntent.getBroadcast(
                getApplicationContext(),
                20, // integer constant used to identify the service
                cancelServiceIntent2,
                0 //no FLAG needed for a service cancel
        );
        am.cancel(cancelServicePendingIntent2);

        Intent cancelServiceIntent3 = new Intent(getApplicationContext(), QuestionnaireBroadcastTwo.class);
        PendingIntent cancelServicePendingIntent3 = PendingIntent.getBroadcast(
                getApplicationContext(),
                21, // integer constant used to identify the service
                cancelServiceIntent3,
                0 //no FLAG needed for a service cancel
        );
        am.cancel(cancelServicePendingIntent3);

        Intent cancelServiceIntent4 = new Intent(getApplicationContext(), QuestionnaireBroadcastThree.class);
        PendingIntent cancelServicePendingIntent4 = PendingIntent.getBroadcast(
                getApplicationContext(),
                22, // integer constant used to identify the service
                cancelServiceIntent4,
                0 //no FLAG needed for a service cancel
        );
        am.cancel(cancelServicePendingIntent4);
*

 */
        Intent intentt = new Intent(getApplicationContext(), ExperimentBroadcast.class);
        PendingIntent pintent = PendingIntent.getBroadcast(this, 0, intentt, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        stopService(intentt);
        alarm.cancel(pintent);


        Intent intentt2 = new Intent(getApplicationContext(), QuestionnaireBroadcast.class);
        PendingIntent pintent2 = PendingIntent.getBroadcast(this, 0, intentt2, 0);
        AlarmManager alarm2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        stopService(intentt2);
        alarm2.cancel(pintent2);


        Intent intentt3 = new Intent(getApplicationContext(), QuestionnaireBroadcastTwo.class);
        PendingIntent pintent3 = PendingIntent.getBroadcast(this, 0, intentt3, 0);
        AlarmManager alarm3 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        stopService(intentt3);
        alarm3.cancel(pintent3);

        Intent intentt4 = new Intent(getApplicationContext(), QuestionnaireBroadcastThree.class);
        PendingIntent pintent4 = PendingIntent.getBroadcast(this, 0, intentt4, 0);
        AlarmManager alarm4 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        stopService(intentt4);
        alarm4.cancel(pintent4);


        if (!disableAllCheckBox.isChecked()){

            createExperimentNotification();

            createQuestionnaireOneNotification();

            int difference = 5 + (toQuesNotif.getHour());


            if (nr == 2){

                int divided = difference / 2;
                createQuestionnaireTwoNotification(divided);
            } else if (nr == 3){
                int divided = difference / 2;
                createQuestionnaireTwoNotification(divided);
                createQuestionnaireThreeNotification(divided * 2);
            }

        }


        startActivity(new Intent(getApplicationContext(), AllPages.class));
    }

    private void createQuestionnaireOneNotification() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "reminder";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("13", name, importance);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);


            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (Calendar.getInstance().after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            Intent intent1 = new Intent(getApplicationContext(), QuestionnaireBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, 0);
            AlarmManager am1 = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
            am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        }

    private void createQuestionnaireTwoNotification(int difference) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("13", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }

        int newHour = 0;
        if (difference + 19 >= 24){
            newHour = (difference + 19) - 24;
        } else {
            newHour = 19 + difference;
        }
        System.out.println("FIRST HOUR");
        System.out.println(newHour);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, newHour - 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent1 = new Intent(getApplicationContext(), QuestionnaireBroadcastTwo.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, 0);
        AlarmManager am1 = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void createQuestionnaireThreeNotification(int difference) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("13", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }

        int newHour = 0;
        if (difference + 19 >= 24){
            newHour = (difference + 19) - 24;
        } else {
            newHour = 19 + difference;
        }
        System.out.println("SECOND HOUR");
        System.out.println(newHour);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, newHour - 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent1 = new Intent(getApplicationContext(), QuestionnaireBroadcastThree.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, 0);
        AlarmManager am1 = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createExperimentNotification() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, tpCurrentNotification.getHour());
        calendar.set(Calendar.MINUTE, tpCurrentNotification.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent1 = new Intent(getApplicationContext(), ExperimentBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, 0);
        AlarmManager am = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
