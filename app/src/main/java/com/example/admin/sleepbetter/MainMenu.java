package com.example.admin.sleepbetter;


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
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static Class nextclass = MainMenu.class;
    private static final String DATABASE_NAME = "user_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_menu);

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

        final int finalShouldBe = shouldBe;

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDatabase uDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                int loggedIn = uDatabase.daoAccess().fetchUserQuestionnaires().size();


                int misses = finalShouldBe -loggedIn;


                if (misses >=1){

                    String moods_string = getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).getString("moods", "");

                    String[] moods = moods_string.split("gcm");

                    ArrayList<String> moodsArrayList = new ArrayList<String>(Arrays.asList(moods));

                    for (int i=0; i<misses; i++){
                    // adaugam -1 in tabel

                        UserQuestionnaire user = new UserQuestionnaire();
                        String username = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing"); user.setUsername(username);
                        user.setDate(currentDate);
                        user.setUsername(username);
                        user.setHowLong(-1);
                        user.setAwake(-1);
                        user.setEarlier(-1);
                        user.setNightsAWeek(-1);
                        user.setQuality(-1);
                        user.setImpactMood(-1);
                        user.setImpactActivities(-1);
                        user.setImpactGeneral(-1);
                        user.setProblem(-1);
                        user.setMood(-1);

                        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("howLong", -1).apply();
                        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("awake", -1).apply();
                        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("earlier", -1).apply();
                        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("quality", -1).apply();
                        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactMood", -1).apply();
                        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactActivities", -1).apply();
                        getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putInt("impactGeneral", -1).apply();
                        getSharedPreferences("MOOD", MODE_PRIVATE).edit().putFloat("mood", (float) -1).apply();

                        //setting mood value to -1 in shared preferences

                        uDatabase.daoAccess().insertSingleUserQuestionnaire(user);

                        moodsArrayList.add("-1");

                        Report rep = new Report(uDatabase, getApplicationContext());
                        rep.save(username, false, getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
                    }

                    moods = moodsArrayList.toArray(moods);

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < moods.length; i++) {
                        sb.append(moods[i]).append("gcm");
                    }
                    getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).edit().putString("moods", sb.toString()).apply();

                }







            }

            //scoatem variabila days si verificam: daca se imparte la 5, si nu e locked,
        }).start();

        //update experiments


        String experiments = getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");

        //+1 to include dy 0
        if (shouldBe> experimentsArray.length){
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

        //NOTIFICATION DEMO
        this.createNotificationChannel();
        this.setNotifications();
        this.setFirstSpecialNotification();
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


        String expStartDate = getSharedPreferences("date", MODE_PRIVATE).getString("startExperiment", "");

        TextView remainedDaysText = (TextView) findViewById(R.id.youHave);

        if (expStartDate.equals("")){
            remainedDaysText.setText("Please choose your experiment in the Experiments section.");
        } else {
            Date date3 = null;

            //Setting dates
            try {
                date1 = dates.parse(currentDate);
                date3 = dates.parse(expStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar c3 = Calendar.getInstance();
            c3.setTime(date3);

            int experimentDaysDifference = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

            int difference = 5 - experimentDaysDifference;


            remainedDaysText.setText("You have " + difference + " days left of the current experiment.");


            if (expStartDate.equals(startingDate)) {
                remainedDaysText.setText("You have 5 days left of the current experiment.");
            } else if (difference < 5 && difference != 0){
                remainedDaysText.setText(difference + " days left of the current experiment.");
            } else {
                remainedDaysText.setText(difference + " days left of the current experiment. When available, change your experiment in the Experiments section.");
            }

        }


    }

    private void goToWhatIsSleep() {
        Intent intent = new Intent(this, WhatIsSleep.class);

        startActivity(intent);

    }

    private void goToWhatExperiments() {
        Intent intent = new Intent(this, WhatExperiments.class);

        startActivity(intent);

    }

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (id == R.id.nav_factors) {
            fragmentTransaction.replace(R.id.content_frame, new Factors());
        } else if (id == R.id.nav_goal_diary) {
            fragmentTransaction.replace(R.id.content_frame, new GoalDiary());
        } else if (id == R.id.nav_data) {
            fragmentTransaction.replace(R.id.content_frame, new Data());
        } else if (id == R.id.nav_questionnaire) {

            String experiment = getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

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

            }
        } else if (id == R.id.nav_calendar) {

            fragmentTransaction.replace(R.id.content_frame, new CalendarPage());

        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean checkIfAllowsQuestionnaire(){

        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        String currentHour = formatter1.format(calendar1.getTime());

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = formatter2.format(calendar1.getTime());


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


        String experiments = getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");

        String[] experimentsArray = experiments.split("gcm");

        System.out.println(experimentsArray.length);
        if (getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "").equals("No experiment for the initial day.") && shouldBe == 0) {
            Toast.makeText(getApplicationContext(), "You are not allowed to fill in today's questionnaire. Choose an experiment if you haven't.", Toast.LENGTH_LONG).show();
            return false;
        } else if (currentHour.compareTo("18:59") < 0) {
            Toast.makeText(getApplicationContext(), "You are not allowed to fill in today's questionnaire yet. Come back at 19:00.", Toast.LENGTH_LONG).show();

            return false;
        } else if (experimentsArray.length - shouldBe >= 1) {
            Toast.makeText(getApplicationContext(), "You are not allowed to fill in today's questionnaire. Come back tomorrow.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
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

            Intent intent1 = new Intent(MainMenu.this, Broadcast1.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this, 0, intent1, 0);
            AlarmManager am = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void setFirstSpecialNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent1 = new Intent(MainMenu.this, Broadcast2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this, 0, intent1, 0);
        AlarmManager am1 = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

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
                    .setSmallIcon(R.drawable.pill)
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

    public static class Broadcast2 extends BroadcastReceiver {

        //intrebarea este daca, aunci cand va veni momentul a schimbe si experimentul - daca o sa ii dea si alarma veche cu complete questionnaire si cea noua cu both complete the questionnaire and the experiment
        @Override
        public void onReceive(Context context, Intent intent) {

            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
//AICIII
            Intent notificationIntent = new Intent(context, MainMenu.class);
           
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            // Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

           NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "13")
                        .setSmallIcon(R.drawable.pill)
                        .setContentTitle("Questionnaire")
                        .setContentText("Remember to complete your questionnaire")
                        .setAutoCancel(true).setWhen(when)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(20, mNotifyBuilder.build());


        }
    }

}