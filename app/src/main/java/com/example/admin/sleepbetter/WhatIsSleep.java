package com.example.admin.sleepbetter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WhatIsSleep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.what_is_sleep);

        SharedPreferences preferences = getSharedPreferences("bmjump", MODE_PRIVATE);
        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setImageResource(preferences.getInt("slectedbitmoji", 0));

        preferences = getSharedPreferences("bmhappy", MODE_PRIVATE);
        imageView = findViewById(R.id.imageView5);
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
