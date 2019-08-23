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
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
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

public class MainMenu extends AppCompatActivity {
    static Class nextclass = MainMenu.class;
    private static final String DATABASE_NAME = "user_db";

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_main_menu);


        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewPager_id);

        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());

        adapter.addSection(new Menu(), "Menu");
        adapter.addSection(new Factors(), "Experiments");
        adapter.addSection(new Data(), "Data");
        adapter.addSection(new GoalDiary(), "Goal Diary");
        adapter.addSection(new CalendarPage(), "Calendar");
        adapter.addSection(new Update(), "Questionnaire");

        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.imageedit_32_4626940004);
        tabLayout.getTabAt(1).setIcon(R.drawable.experiments);
        tabLayout.getTabAt(2).setIcon(R.drawable.data);
        tabLayout.getTabAt(3).setIcon(R.drawable.diaryic);
        tabLayout.getTabAt(4).setIcon(R.drawable.calendar);
        tabLayout.getTabAt(5).setIcon(R.drawable.pen);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }*/
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
*/


}