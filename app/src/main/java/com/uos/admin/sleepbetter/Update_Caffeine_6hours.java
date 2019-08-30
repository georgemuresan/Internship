package com.uos.admin.sleepbetter;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Update_Caffeine_6hours extends AppCompatActivity {
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_caffeine_six);

        Button button = (Button) findViewById(R.id.submitUpdate);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToQuestionnaire();

            }

        });

    }

    public void goToQuestionnaire(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        RadioGroup radioButtonGroup0 = findViewById(R.id.radioGroup2);
        int radioButtonID0 = radioButtonGroup0.getCheckedRadioButtonId();
        View radioButton0 = radioButtonGroup0.findViewById(radioButtonID0);
        int idx0 = radioButtonGroup0.indexOfChild(radioButton0);
        RadioButton r0 = (RadioButton)  radioButtonGroup0.getChildAt(idx0);
        final String testCompared = r0.getText().toString();

        final TimePicker tpLastdrink = (TimePicker) findViewById(R.id.lastDrink);
        final String lastDrink = tpLastdrink.getCurrentHour() + ":" + tpLastdrink.getCurrentMinute();

        final TimePicker tpWhenSleep = (TimePicker) findViewById(R.id.whenSleep);
        final String whenSleep = tpWhenSleep.getCurrentHour() + ":" + tpWhenSleep.getCurrentMinute();


        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                UserExperiment user = new UserExperiment();
                String username = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                user.setUsername(username);
                user.setDate(formattedDate);
                user.setExperiment("C1");
                user.setCaffeineOneWhenDrink(lastDrink);
                user.setCaffeineOneWhenSleep(whenSleep);
                user.setOverallBetter(testCompared);

                userDatabase.daoAccess().insertSingleUserExperiment(user);


            }
        }).start();

        startActivity(new Intent(this, QFinal.class));
    }


    }
