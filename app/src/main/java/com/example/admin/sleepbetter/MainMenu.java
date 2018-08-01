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
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static Class nextclass = MainMenu.class;
    static String title = "DEFAULT";
    static String message = "DEFAULT MSG";
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;
    private static int experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
// Adding the right bitmoji
        int mood = getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
        if (mood == 1) imageView.setImageResource(R.drawable.happy);
        if (mood == 2) imageView.setImageResource(R.drawable.ok);
        if (mood == 3) imageView.setImageResource(R.drawable.notok);
        if (mood == 4 || mood == 5) imageView.setImageResource(R.drawable.bad);

        //NOTIFICATION DEMO
        this.createNotificationChannel();
        this.setNotifications();
        this.setSpecialNotification();
///END NOTIFICATIONs

        Button button1 = (Button) findViewById(R.id.whatSleep);

        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToWhatIsSleep();

            }

        });

        Button button2 = (Button) findViewById(R.id.WhatChabot);

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToWhatIsChatbot();

            }

        });

        Button button3 = (Button) findViewById(R.id.WhatExperiments);

        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToWhatExperiments();

            }

        });

    }

    private void goToWhatIsSleep() {
        Intent intent = new Intent(this, WhatIsSleep.class);

        startActivity(intent);

    }

    private void goToWhatIsChatbot() {
        Intent intent = new Intent(this, WhatChatbot.class);

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

        } else if (id == R.id.nav_calendar) {
            fragmentTransaction.replace(R.id.content_frame, new CalendarPage());
        } else if (id == R.id.nav_bot) {
            fragmentTransaction.replace(R.id.content_frame, new Help());
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    private void setAlarmManager(int hour, int minute, final String title, final String message, int experiment) {

        if (title.equals("Oups")){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);


            Intent intent1 = new Intent(MainMenu.this, Broadcast4.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this, 0, intent1, 0);
            AlarmManager am = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {

            this.title = title;
            this.message = message;
            this.experiment = experiment;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);


            Intent intent1 = new Intent(MainMenu.this, Broadcast1.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this, 0, intent1, 0);
            AlarmManager am = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }

    private void setSpecialNotification() {
        System.out.println("SPECIALK");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainMenu.this, Broadcast2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this, 0, intent1, 0);
        AlarmManager am1 = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
        am1.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 35);
        calendar.set(Calendar.SECOND, 0);
        Intent intent2 = new Intent(MainMenu.this, Broadcast3.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainMenu.this, 0, intent2, 0);
        AlarmManager am2 = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
        am2.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);

    }

    //produce the required notifications
    private void setNotifications() {
        int experiment = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);
        switch (experiment) {
            case 1: //increase bright light exposure
                setAlarmManager(12, 0, "Remember:", "Stay out in the sun at least half an hour today!", 1);
                break;
            case 2: //wear glasses that block blue light during the night
                setAlarmManager(8, 30, "Remember:", "Turn on your \"use the f.lux\" app", 2);
                break;
            case 3: // turn off any bright lights 2 hours before going to bed
                setAlarmManager(19, 30, "Going to bed soon?", "Do not forget to turn off your light", 3);
                break;
            case 5: // Do not drink caffeine within 6 hours
                setAlarmManager(15, 0, "Remember:", "Do not drink caffeine with 6 hours before going to sleep", 5);
                break;
            case 6: // Limit yourself to 4 cups of coffees per day; 10 canss of
                setAlarmManager(12, 15, "Remember:", "Limit yourself to 4 cups of coffees per day", 6);
                break;
            case 7: //Do not drink empty stomach
                setAlarmManager(8, 30, "Remember:", "Try not to drink on an empty stomach", 7);
                break;
            case 9://Usually get up at the same time everyday, even on weekends
                setAlarmManager(18, 30, "Remember:", "Set your alarm at....", 9);
                break;
            case 10: // Sleep no lesss than 7 hours per night
                setAlarmManager(19, 00, "Remember:", "Sleep no less than 7 hours per night", 10);
                break;
            case 11: //DO not go to bed unless you are tired. If you are not
                setAlarmManager(20, 00, "Do not go to bed unless you are tired", "You can do some of the next activities to relax", 11);
                break;
            case 12: //Go to sleep at 22:30 PM the latest
                setAlarmManager(21, 00, "Remember:", "Go to sleep at 10:30 PM the latest", 12);
                break;
        }

        setAlarmManager(0, 1, "Oups:", "Checking questionnaire", 0);
    }

    public static class Broadcast1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("HERE I AM!!");

            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, nextclass);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "13")
                    .setSmallIcon(R.drawable.pill)
                    .setContentTitle(title)
                    .setContentText(message).setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(13, mNotifyBuilder.build());
//
        }
    }

    public static class Broadcast2 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("HERE I AM 2!!");

            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
//AICIII
            Intent notificationIntent = null;
            switch (experiment) {
                case 1: //increase bright light exposure
                     notificationIntent = new Intent(context, Update_Light_Bright.class);
                    break;
                case 2: //wear glasses that block blue light during the night
                     notificationIntent = new Intent(context, Update_Light_Glasses.class);
                    break;
                case 3: // turn off any bright lights 2 hours before going to bed
                     notificationIntent = new Intent(context, Update_Light_TurnOffBright.class);
                    break;
                case 5: // Do not drink caffeine within 6 hours
                     notificationIntent = new Intent(context, Update_Caffeine_6hours.class);
                    break;
                case 6: // Limit yourself to 4 cups of coffees per day; 10 canss of
                     notificationIntent = new Intent(context, Update_Caffeine_limit.class);
                    break;
                case 7: //Do not drink empty stomach
                     notificationIntent = new Intent(context, Update_Caffeine_Empty.class);
                    break;
                case 9://Usually get up at the same time everyday, even on weekends
                     notificationIntent = new Intent(context, Update_Schedule_SameTime.class);
                    break;
                case 10: // Sleep no lesss than 7 hours per night
                     notificationIntent = new Intent(context, Update_Schedule_7hours.class);
                    break;
                case 11: //DO not go to bed unless you are tired. If you are not
                     notificationIntent = new Intent(context, Update_Schedule_Relax.class);
                    break;
                case 12: //Go to sleep at 22:30 PM the latest
                     notificationIntent = new Intent(context, Update_Schedule_Midnight.class);
                    break;
            }

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "13")
                    .setSmallIcon(R.drawable.pill)
                    .setContentTitle("Questionnaire")
                    .setContentText("Remember to complete your questionnaire").setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(20, mNotifyBuilder.build());

        }
    }

    public static class Broadcast3 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("HERE I AM!3!");

            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, Help.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "13")
                    .setSmallIcon(R.drawable.pill)
                    .setContentTitle("Do you have any question?")
                    .setContentText("Pam: Ask me something if you are courious").setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(21, mNotifyBuilder.build());
//
        }
    }

    public class Broadcast4 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().putBoolean("completed", false).apply();

            //updating day
            int numberOfDays = getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("numberDays", 0);
            numberOfDays++;
            getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("numberDays", numberOfDays).apply();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    final String formattedDate = df.format(c);


                    userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                    int totalQuestionnaires = userDatabase.daoAccess().fetchUserQuestionnaires().size();

                    int fiveDays = getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("days", 0);
                    int numberOfDays = getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("numberDays", 0);

                    if (numberOfDays >= totalQuestionnaires ){

                        //daca nu a facut chestionarul
                        //1 updatam ce se intampla in questionnaire 4
                        if (fiveDays % 5 == 1){
                            getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("days", fiveDays).apply();
                        } else {
                            getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putInt("days", fiveDays + 1).apply();
                        }
                        //2 adaugam -1 in tabel

                        UserQuestionnaire user = new UserQuestionnaire();
                        String username = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                        user.setUsername(username);
                        user.setDate(formattedDate);
                        user.setTimesPerNight(-1);
                        user.setNightTerrors(-1);
                        user.setFallAsleep(-1);
                        user.setWakeUp(-1);
                        user.setFresh(-1);
                        user.setSad(-1);
                        user.setSleepy(-1);
                        user.setTired(-1);
                        user.setStressed(-1);
                        user.setApetite(-1);
                        user.setConcentrate(-1);
                        user.setCoordinate(-1);
                        user.setIrritable(-1);
                        user.setMood(-1);

                        //setting mood value to -1 in shared preferences

                        userDatabase.daoAccess().insertSingleUserQuestionnaire(user);

                        Report rep = new Report(userDatabase, getApplicationContext());
                        rep.save(username, false, getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));

                    }
                }

                //scoatem variabila days si verificam: daca se imparte la 5, si nu e locked,
            }).start();

            getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().putBoolean("locked", true).apply();
//
        }
    }

}