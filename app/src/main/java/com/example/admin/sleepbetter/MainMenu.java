package com.example.admin.sleepbetter;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.TextView;

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

        int mood = getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0);
        ImageView imageView =  (ImageView) headerView.findViewById(R.id.imageView);
        if (mood == 1) imageView.setImageResource(R.drawable.happy);
        if (mood == 2) imageView.setImageResource(R.drawable.ok);
        if (mood == 3) imageView.setImageResource(R.drawable.notok);
        if (mood == 4 || mood == 5) imageView.setImageResource(R.drawable.bad);
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
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Questionnaire()).commit();
        } else if (id == R.id.nav_help) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Help()).commit();
        } else if (id == R.id.nav_bot) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TestBot()).commit();
        } else if (id == R.id.nav_update) {
        fragmentManager.beginTransaction().replace(R.id.content_frame, new Update()).commit();
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }*/


}
