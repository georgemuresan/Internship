package com.example.admin.sleepbetter;


import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
        this.Notidication();
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

            if (experiment.equals("firstLight")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Light_Bright()).commit();
            } else if (experiment.equals("secondLight")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Light_Glasses()).commit();
            } else if (experiment.equals("thirdLight")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Light_TurnOffBright()).commit();
            } else if (experiment.equals("firstCaffeine")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Caffeine_6hours()).commit();
            } else if (experiment.equals("secondCaffeine")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Caffeine_limit()).commit();
            } else if (experiment.equals("thirdCaffeine")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Caffeine_Empty()).commit();
            } else if (experiment.equals("firstSchedule")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Schedule_SameTime()).commit();
            } else if (experiment.equals("secondSchedule")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Schedule_7hours()).commit();
            } else if (experiment.equals("thirdSchedule")) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Update_Schedule_Relax()).commit();
            } else if (experiment.equals("fourthSchedule")) {
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

    private void Notidication() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df.format(c);
        String lastDate = getSharedPreferences("date", MODE_PRIVATE).getString("lastdate", "nothing");

        if (!currentDate.equals(lastDate)) {

            Intent intent = new Intent(this, SecondPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "13")
                    .setSmallIcon(R.drawable.ic_drawer)
                    .setContentTitle("Questionaire")
                    .setContentText("Pam: Remember to complete your questionaire :D")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(14, mBuilder.build());
        }
    }
}
