package com.example.admin.sleepbetter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FirstPage extends AppCompatActivity {

    private SeekBarWithIntervals SeekbarWithIntervals = null;
    private EditText nameBox = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Button button = (Button) findViewById(R.id.startButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToSecondActivity();

            }

        });
        nameBox = (EditText) findViewById(R.id.yourName);

        String text = getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
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

        Intent intent = new Intent(this, SecondPage.class);

        String name = nameBox.getText().toString();

        getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", name).apply();

        startActivity(intent);

    }


}
