package com.example.admin.sleepbetter;


import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.KeyEvent;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;

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


        HomeCollection.date_collection_arr = new ArrayList<HomeCollection>();
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-08", "Diwali", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-08", "Holi", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-08", "Statehood Day", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-08-08", "Republic Unian", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-09", "ABC", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-06-15", "demo", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-09-26", "weekly off", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2018-01-08", "Events", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2018-01-16", "Dasahara", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2018-02-09", "Christmas", "Holiday", "this is holiday"));


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(this, cal_month, HomeCollection.date_collection_arr);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));


        ImageButton previous = (ImageButton) findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 4 && cal_month.get(GregorianCalendar.YEAR) == 2017) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(MainMenu.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 5 && cal_month.get(GregorianCalendar.YEAR) == 2018) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(MainMenu.this, "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, MainMenu.this);
            }

        });

        this.createNotificationChannel();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "13")
                .setSmallIcon(R.drawable.ic_drawer)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                //     .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(13, mBuilder.build());

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

        } else if (id == R.id.nav_help) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Help()).commit();
        } else if (id == R.id.nav_bot) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TestBot()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
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
}
