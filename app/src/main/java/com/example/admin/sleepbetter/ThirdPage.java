package com.example.admin.sleepbetter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);

        Button button = (Button) findViewById(R.id.startButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToMainMenu();

            }

        });
    }
    private void goToMainMenu() {

        Intent intent = new Intent(this, MainMenu.class);

        startActivity(intent);

    }
}
