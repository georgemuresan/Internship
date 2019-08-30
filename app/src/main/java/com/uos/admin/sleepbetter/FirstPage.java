package com.uos.admin.sleepbetter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FirstPage extends AppCompatActivity {

    private EditText nameBox = null;
    private TextView participantID = null;
    private String participant = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);

        nameBox = (EditText) findViewById(R.id.yourName);
        System.out.println("INPUT IS :" + nameBox.getText().toString());
        participantID = (TextView) findViewById(R.id.participantID);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //getTime() returns the current date in default time zone
        Date date = calendar.getTime();
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String hour = String.valueOf(calendar.get(Calendar.HOUR));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        String millisecond = String.valueOf(calendar.get(Calendar.MILLISECOND));

        participant = day + month + year + hour + minute + second + millisecond;
        participantID.setText("Participant ID: " + participant);

        final CheckBox consent = (CheckBox) findViewById(R.id.consentCheckbox);


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

                String name = nameBox.getText().toString();

                if (!name.equals("")){
                    getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", name).apply();
                }

                startActivity(intent);


            }
        });


        String name = getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");

        if (!name.equals("nothing")){
            nameBox.setText(name);
        }

        Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (consent.isChecked() ) {
                    goToSecondActivity();
                } else {
                Toast.makeText(getApplicationContext(), "You need read the consent page.", Toast.LENGTH_SHORT).show();
                }
            }

        });





        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun && consent.isChecked() && !nameBox.getText().toString().equals("")) {
            //show start activity

            startActivity(new Intent(this, AllPages.class));


        }



    }




    private void goToSecondActivity() {



        if (nameBox.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please input your name", Toast.LENGTH_SHORT).show();

        } else {

            Intent intent = new Intent(this, Demographics.class);

                String name = nameBox.getText().toString();

                getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", name).apply();
                getSharedPreferences("name", MODE_PRIVATE).edit().putString("participantID", participant).apply();

                startActivity(intent);

        }

    }


}
