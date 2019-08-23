package com.uos.admin.sleepbetter;


import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class B_MainMenu extends AppCompatActivity {
    static Class nextclass = B_MainMenu.class;
    private static final String DATABASE_NAME = "user_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_main_menu);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String currentDate = df.format(c);

        String startingDate = getSharedPreferences("date", MODE_PRIVATE).getString("startingDate", "");

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

        String expStartDate = getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");

        if (shouldBe == 0 && expStartDate.equals("")) {
            Toast.makeText(getApplicationContext(), "Please choose an experiment.", Toast.LENGTH_LONG).show();
        }

        //update experiments


        String experiments = getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        //+1 to include dy 0
        String[] experimentsArray = experiments.split("gcm");

        if (shouldBe > experimentsArray.length){
            String currentExperiment = getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

            ArrayList<String> experimentsArrayList = new ArrayList<String>(Arrays.asList(experimentsArray));
            for (int i=0; i< (shouldBe - experimentsArray.length); i++){
                experimentsArrayList.add(currentExperiment + ".");
            }

            experimentsArray = experimentsArrayList.toArray(experimentsArray);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < experimentsArray.length; i++) {
                sb.append(experimentsArray[i]).append("gcm");
            }
            getSharedPreferences("experiments", MODE_PRIVATE).edit().putString("experiments", sb.toString()).apply();

        }

        //context.getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putBoolean("completed", false).apply();
        //context.getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putBoolean("locked", true).apply();

            //scoatem variabila days si verificam: daca se imparte la 5, si nu e locked,
       ImageView imageView = findViewById(R.id.imageView2);
        /*
        imageView.setImageResource(R.drawable.sleep);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nameOfUser);

        String name = getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
        navUsername.setText(name);
*/
        //NOTIFICATION DEMO
        this.createNotificationChannel();
        this.setNotifications();
///END NOTIFICATIONs

        Button button1 = (Button) findViewById(R.id.whatSleep);

        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToWhatIsSleep();

            }

        });

        Button button3 = (Button) findViewById(R.id.WhatExperiments);

        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToWhatExperiments();

            }

        });


        TextView remainedDaysText = (TextView) findViewById(R.id.youHave);

        if (expStartDate.equals("")){
            remainedDaysText.setText("Please choose your experiment in the Experiments section.");
        } else {
            Date date3 = null;

            //Setting dates
            try {
                date3 = dates.parse(expStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar c3 = Calendar.getInstance();
            c3.setTime(date3);

            int experimentDaysDifference = c1.get(Calendar.DAY_OF_YEAR) - c3.get(Calendar.DAY_OF_YEAR);

            int difference = 5 - experimentDaysDifference;


            remainedDaysText.setText("You have " + difference + " days left of the current experiment.");


            if (expStartDate.equals(currentDate)) {
                remainedDaysText.setText("You have 5 days left of the current experiment.");
            } else if (difference < 5 && difference != 0){
                remainedDaysText.setText(difference + " days left of the current experiment.");
            } else {
                remainedDaysText.setText(difference + " days left of the current experiment. When available, change your experiment in the Experiments section.");
            }

        }


        String previousExperimentStartDate = getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");

        Date date3 = null;
        Date date4 = null;

        if (previousExperimentStartDate.equals("")){
            previousExperimentStartDate = currentDate;
        }
        //Setting dates
        try {
            date3 = dates.parse(currentDate);
            date4 = dates.parse(previousExperimentStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c3 = Calendar.getInstance();
        c3.setTime(date3);

        Calendar c4 = Calendar.getInstance();
        c4.setTime(date4);

        int differenceBetweenOldExperimentAndCurrent = c3.get(Calendar.DAY_OF_YEAR) - c4.get(Calendar.DAY_OF_YEAR);

        if (differenceBetweenOldExperimentAndCurrent != 0){
           getSharedPreferences("expB", MODE_PRIVATE).edit().putString("pickedB", "pickedB").apply();
        }

    }

    private void goToWhatIsSleep() {
        Intent intent = new Intent(this, WhatIsSleep.class);

        startActivity(intent);

    }

    private void goToWhatExperiments() {
        Intent intent = new Intent(this, B_WhatExperiments.class);

        startActivity(intent);

    }
/*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                System.out.println("returns");
                moveTaskToBack(true);
            } else {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
/*
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (id == R.id.nav_factors) {
            fragmentTransaction.replace(R.id.content_frame, new Factors());
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("13", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setAlarmManager(int hour, int minute, final String title, final String message) {

        getSharedPreferences("notification", MODE_PRIVATE).getString("title", "nothing");
        getSharedPreferences("notification", MODE_PRIVATE).edit().putString("title", title).apply();
        getSharedPreferences("notification", MODE_PRIVATE).getString("message", "nothing");
        getSharedPreferences("notification", MODE_PRIVATE).edit().putString("message", message).apply();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            if (Calendar.getInstance().after(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            Intent intent1 = new Intent(B_MainMenu.this, Broadcast1.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(B_MainMenu.this, 0, intent1, 0);
            AlarmManager am = (AlarmManager) B_MainMenu.this.getSystemService(B_MainMenu.this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    //produce the required notifications
    private void setNotifications() {
        int experiment = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);

        switch (experiment) {
            case 1: //increase bright light exposure
                setAlarmManager(12, 0, "Remember:", "Stay out in the sun at least half an hour today!");
                break;
            case 2: //wear glasses that block blue light during the night
                setAlarmManager(12, 30, "Remember:", "Use the \"f.lux\" app!");
                break;
            case 3: // turn off any bright lights 2 hours before going to bed
                setAlarmManager(19, 30, "Going to bed soon?", "Do not forget to turn off your light with 2 hours before bed!");
                break;
            case 5: // Do not drink caffeine within 6 hours
                setAlarmManager(15, 0, "Remember:", "Do not drink caffeine with 6 hours before going to sleep!");
                break;
            case 6: // Limit yourself to 4 cups of coffees per day; 10 canss of
                setAlarmManager(16, 24, "Remember:", "Limit yourself to 4 cups of coffee per day/10 cans of soda or 2 energy drinks!");
                break;
            case 7: //Do not drink empty stomach
                setAlarmManager(8, 0, "Remember:", "Try not to drink caffeine on an empty stomach!");
                break;
            case 9://Usually get up at the same time everyday, even on weekends
                setAlarmManager(18, 30, "Remember:", "Do not forget! Go to bed and wake up at the same time as yesterday!");
                break;
            case 10: // Sleep no lesss than 7 hours per night
                System.out.println("should be");
                setAlarmManager(19, 50, "Remember:", "Sleep no less than 7 hours per night!");
                break;
            case 11: //DO not go to bed unless you are tired. If you are not
                setAlarmManager(20, 00, "Remember", "Do not go to bed unless you are tired. Read a book, take a bath, stretch or drink tea to relax!");
                break;
            case 12: //Go to sleep at 22:30 PM the latest
                setAlarmManager(21, 00, "Remember:", "Go to sleep at 10:30 PM the latest!");
                break;
        }

    }

    public static class Broadcast1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String titleM = context.getSharedPreferences("notification", MODE_PRIVATE).getString("title", "nothing");
            String messageM = context.getSharedPreferences("notification", MODE_PRIVATE).getString("message", "nothing");


            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, nextclass);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            // Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "13")
                    .setSmallIcon(R.drawable.data)
                    .setContentTitle(titleM)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(messageM))
                    .setContentText(messageM)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(13, mNotifyBuilder.build());
//
        }
    }

}