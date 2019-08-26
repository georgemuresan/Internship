package com.uos.admin.sleepbetter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WhatExperiments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_what_experiments);

       ImageView imageView = findViewById(R.id.imageView23);
        imageView.setImageResource(R.drawable.data);

         imageView = findViewById(R.id.imageView24);
        imageView.setImageResource(R.drawable.data);

        Button button = (Button) findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });
    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, AllPages.class);

        startActivity(intent);

    }
}
