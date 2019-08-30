package com.uos.admin.sleepbetter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Update_Caffeine_limit extends AppCompatActivity {
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_update_caffeine_limit);

        Button button = (Button) findViewById(R.id.submitUpdate);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToQuestionnaire();

            }

        });

    }


    public void goToQuestionnaire() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        RadioGroup radioButtonGroup0 = findViewById(R.id.radioGroup2);
        int radioButtonID0 = radioButtonGroup0.getCheckedRadioButtonId();
        View radioButton0 = radioButtonGroup0.findViewById(radioButtonID0);
        int idx0 = radioButtonGroup0.indexOfChild(radioButton0);
        RadioButton r0 = (RadioButton)  radioButtonGroup0.getChildAt(idx0);
        final String testCompared = r0.getText().toString();


        RadioGroup radioButtonGroup = findViewById(R.id.coffeeGroup);
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int idx = radioButtonGroup.indexOfChild(radioButton);
        RadioButton r = (RadioButton) radioButtonGroup.getChildAt(idx);
        final String coffee = r.getText().toString();

        RadioGroup radioButtonGroup2 = findViewById(R.id.sodaGroup);
        int radioButtonID2 = radioButtonGroup2.getCheckedRadioButtonId();
        View radioButton2 = radioButtonGroup2.findViewById(radioButtonID2);
        int idx2 = radioButtonGroup2.indexOfChild(radioButton2);
        RadioButton r2 = (RadioButton) radioButtonGroup2.getChildAt(idx2);
        final String soda = r2.getText().toString();

        RadioGroup radioButtonGroup3 = findViewById(R.id.energyGroup);
        int radioButtonID3 = radioButtonGroup3.getCheckedRadioButtonId();
        View radioButton3 = radioButtonGroup3.findViewById(radioButtonID3);
        int idx3 = radioButtonGroup3.indexOfChild(radioButton3);
        RadioButton r3 = (RadioButton) radioButtonGroup3.getChildAt(idx3);
        final String energy = r3.getText().toString();



        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                UserExperiment user = new UserExperiment();
                String username = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                user.setUsername(username);
                user.setDate(formattedDate);
                user.setExperiment("C2");

                user.setCaffeineTwoCups(coffee);
                user.setCaffeineTwoCans(soda);
                user.setCaffeineTwoEnergy(energy);
//
                user.setOverallBetter(testCompared);

                userDatabase.daoAccess().insertSingleUserExperiment(user);


            }
        }).start();

        startActivity(new Intent(this, QFinal.class));
    }

}
