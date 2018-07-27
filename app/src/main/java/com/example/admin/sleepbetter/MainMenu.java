package com.example.admin.sleepbetter;


import android.app.AlarmManager;
import android.app.FragmentManager;
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
///END NOTIFICATION

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
            super.onBackPressed();
        }

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();

            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();


        if (id == R.id.nav_factors) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Factors()).commit();
        } else if (id == R.id.nav_goal_diary) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new GoalDiary()).commit();
        } else if (id == R.id.nav_data) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Data()).commit();
        } else if (id == R.id.nav_questionnaire) {

            String experiment = getSharedPreferences("name", MODE_PRIVATE).getString("experiment", "nothing");

            if (experiment.equals(getString(R.string.firstLight))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Light_Bright()).commit();
            } else if (experiment.equals(getString(R.string.secondLight))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Light_Glasses()).commit();
            } else if (experiment.equals(getString(R.string.thirdLight))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Light_TurnOffBright()).commit();
            } else if (experiment.equals(getString(R.string.firstCaffeine))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Caffeine_6hours()).commit();
            } else if (experiment.equals(getString(R.string.secondCaffeine))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Caffeine_limit()).commit();
            } else if (experiment.equals(getString(R.string.thirdCaffeine))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Caffeine_Empty()).commit();
            } else if (experiment.equals(getString(R.string.firstSchedule))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Schedule_SameTime()).commit();
            } else if (experiment.equals(getString(R.string.secondSchedule))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Schedule_7hours()).commit();
            } else if (experiment.equals(getString(R.string.thirdSchedule))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Schedule_Relax()).commit();
            } else if (experiment.equals(getString(R.string.fourthSchedule))) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Schedule_Midnight()).commit();
            } else {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update()).commit();
            }

        } else if (id == R.id.nav_calendar) {
            //   fragmentManager.beginTransaction().replace(R.id.content_frame, new Calendar()).commit();
        } else if (id == R.id.nav_bot) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Help()).commit();
        }

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

    private void setAlarmManager(int hour, int minute, final String title, final String message) {
        System.out.println("FMM");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        class Broadcast extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("HERE I AM!!");

                long when = System.currentTimeMillis();
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);

                Intent notificationIntent = new Intent(context, MainMenu.class);
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


        Intent intent1 = new Intent(MainMenu.this, Broadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this, 0, intent1, 0);
        AlarmManager am = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

    }

    private void setAlarmManager(int hour, int minute, final String title, final String message, final Class nextclass) {
        System.out.println("FMM");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        class Broadcast extends BroadcastReceiver {
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


        Intent intent1 = new Intent(MainMenu.this, Broadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainMenu.this, 0, intent1, 0);
        AlarmManager am = (AlarmManager) MainMenu.this.getSystemService(MainMenu.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

    }

//produce the required notifications
    private void setNotifications() {
        int experiment = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);
        switch (experiment) {
            case 1: //increase bright light exposure
                setAlarmManager(12, 0, "Remember:", "Stay out in the sun at least half an hour todday!");
                break;
            case 2: //wear glasses that block blue light during the night
                setAlarmManager(8, 30, "Remember:", "Turn on your \"use the f.lux\" app");
                break;
            case 3: // turn off any bright lights 2 hours before going to bed
                setAlarmManager(19, 30, "Going to bed soon?", "Dont forget to turn off yur light");
                break;
            case 5: // Do not drink caffeine within 6 hours
                setAlarmManager(15, 0, "Remember:", "Do not drink caffeine with 6 hours before going to sleep");
                break;
            case 6: // Limit yourself to 4 cups of coffees per day; 10 canss of
                setAlarmManager(12, 15, "Remember:", "Limit yourself to 4 cups of coffees per day");
                break;
            case 7: //Do not drink empty stomach
                setAlarmManager(8, 30, "Remember:", "Try not to drink on an empty stomach");
                break;
            case 9://Usually get up at the same time everyday, even on weekends
                setAlarmManager(18, 30, "Remember:", "Set your alarm at....");
                break;
            case 10: // Sleep no lesss than 7 hours per night
                setAlarmManager(19, 00, "Remember:", "Sleep no less than 7 hours per night");
                break;
            case 11: //DO not go to bed unless you are tired. If you are not
                setAlarmManager(20, 00, "Do not go to bed unless you are tired", "You can do some of the next activities to relax");
                break;
            case 12: //Go to sleep at 22:30 PM the latest
                setAlarmManager(21, 00, "Remember:", "Go to sleep at 10:30 PM the latest");
                break;
        }

        setAlarmManager(20, 20, "Questionaire", "Pam: Ask me something if you are courious", SecondPage.class);
        setAlarmManager(16, 18, "Do you have any question?", "Pam: Ask me something if you are courious ", Help.class);
    }
    class Broadcast extends BroadcastReceiver {
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

}
