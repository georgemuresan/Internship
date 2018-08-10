package com.example.admin.sleepbetter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        System.out.println("INPUT IS :" + nameBox.getText().toString());
        participantID = (EditText) findViewById(R.id.participantName);
/*
        getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("bmhappy", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("bmok", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("bmnotok", MODE_PRIVATE).edit().clear().commit();
        getApplicationContext().getSharedPreferences("bmbad", MODE_PRIVATE).edit().clear().commit();
*/
        getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
        getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

        final CheckBox consent = (CheckBox) findViewById(R.id.consentCheckbox);
        final CheckBox bitmoji = (CheckBox) findViewById(R.id.linkedCheckbox);

        String completed = getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing");
        int completedBitmoji = getSharedPreferences("bmhappy", MODE_PRIVATE).getInt("slectedbitmoji", -1);
        getSharedPreferences("bmok", MODE_PRIVATE).getInt("slectedbitmoji", 0);
        getSharedPreferences("bmnotok", MODE_PRIVATE).getInt("slectedbitmoji", 0);
        getSharedPreferences("bmbad", MODE_PRIVATE).getInt("slectedbitmoji", 0);


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

                String name = nameBox.getText().toString();
                String participant = participantID.getText().toString();

                if (!name.equals("")){
                    getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", name).apply();
                }
                if (!participant.equals("")){
                    getSharedPreferences("name", MODE_PRIVATE).edit().putString("participantID", participant).apply();
                }

                startActivity(intent);


            }
        });

//// PENTRU BITMOJI
//        if (completedBitmoji != -1){
//            bitmoji.setChecked(true);
//        } else {
//            bitmoji.setChecked(false);
//        }
//        bitmoji.setClickable(false);
//        TextView cform2 = (TextView) findViewById(R.id.linkedBitmoji);
//        cform2.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent(getApplicationContext(), FirstPageLinkBitmoji.class);
//
//                String name = nameBox.getText().toString();
//                String participant = participantID.getText().toString();
//
//                if (!name.equals("")){
//                    getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", name).apply();
//                }
//                if (!participant.equals("")){
//                    getSharedPreferences("name", MODE_PRIVATE).edit().putString("participantID", participant).apply();
//                }
//
//                startActivity(intent);
//
//
//            }
//        });
//



        String name = getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
        String participant = getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

        if (!name.equals("nothing")){
            nameBox.setText(name);
        }
        if (!participant.equals("nothing")){
            participantID.setText(participant);
        }

        Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (consent.isChecked() ) {
                    goToSecondActivity();
                } else {
                Toast.makeText(getApplicationContext(), "You need to choose a Bitmoji and read the consent page.", Toast.LENGTH_SHORT).show();
                }
            }

        });





        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun && consent.isChecked() && bitmoji.isChecked() && !nameBox.getText().toString().equals("") && !participantID.getText().toString().equals("")) {
            //show start activity
            startActivity(new Intent(this, MainMenu.class));

        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();


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
