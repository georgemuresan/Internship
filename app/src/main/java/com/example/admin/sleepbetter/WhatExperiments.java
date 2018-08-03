package com.example.admin.sleepbetter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WhatExperiments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.what_experiments);

        SharedPreferences preferences = getSharedPreferences("bmwalking", MODE_PRIVATE);
        ImageView imageView = findViewById(R.id.imageView23);
        imageView.setImageResource(preferences.getInt("slectedbitmoji", 0));

        preferences = getSharedPreferences("bmbad", MODE_PRIVATE);
        imageView = findViewById(R.id.imageView24);
        imageView.setImageResource(preferences.getInt("slectedbitmoji", 0));

        preferences = getSharedPreferences("bmhappy", MODE_PRIVATE);
        imageView = findViewById(R.id.imageView25);
        imageView.setImageResource(preferences.getInt("slectedbitmoji", 0));

        Button button = (Button) findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });
    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, MainMenu.class);

        startActivity(intent);

    }
}
