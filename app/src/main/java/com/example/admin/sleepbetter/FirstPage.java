package com.example.admin.sleepbetter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class FirstPage extends AppCompatActivity {

    private SeekBarWithIntervals SeekbarWithIntervals = null;
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

    }
    private void goToSecondActivity() {

        Intent intent = new Intent(this, SecondPage.class);

        startActivity(intent);

    }

}
