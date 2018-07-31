package com.example.admin.sleepbetter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class FirstPage extends AppCompatActivity {

    private EditText nameBox = null;
    private EditText participantID = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        nameBox = (EditText) findViewById(R.id.yourName);
        participantID = (EditText) findViewById(R.id.participantName);

        getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().clear().commit();
       // getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).edit().clear().commit();

        getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
        getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

        final CheckBox consent = (CheckBox) findViewById(R.id.consentCheckbox);
        final CheckBox bitmoji = (CheckBox) findViewById(R.id.linkedCheckbox);

        String completed = getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing");

        if (completed.equals("Yes") || completed.equals("No")){
            consent.setChecked(true);
        } else {
            consent.setChecked(false);
        }
        consent.setClickable(false);
        TextView cform = (TextView) findViewById(R.id.consentForm);
        cform.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), FirstPageConsent.class);
                startActivity(intent);


            }
        });

/* PENTRU BITMOJI
        String completed = getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing");

        if (completed.equals("Yes") || completed.equals("No")){
            consent.setChecked(true);
        } else {
            consent.setChecked(false);
        }
        consent.setClickable(false);
        TextView cform = (TextView) findViewById(R.id.consentForm);
        cform.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), FirstPageConsent.class);
                startActivity(intent);


            }
        });

        */
        Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            //    if (consent.isChecked() && bitmoji.isChecked()) {
                    goToSecondActivity();
             //   } else {
            //    Toast.makeText(getApplicationContext(), "You need to choose a Bitmoji and read the consent page.", Toast.LENGTH_SHORT).show();
            //    }
            }

        });




/*
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            //show start activity
            startActivity(new Intent(this, MainMenu.class));
            Toast.makeText(FirstPage.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();*/


    }
    private void goToSecondActivity() {

        if (nameBox.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please input your name", Toast.LENGTH_SHORT).show();

        } else if (participantID.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please input your participant ID number", Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(this, SecondPage.class);

            String name = nameBox.getText().toString();
            String participant = participantID.getText().toString();

            getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", name).apply();
            getSharedPreferences("name", MODE_PRIVATE).edit().putString("participantID", participant).apply();

            startActivity(intent);

        }

    }

}
