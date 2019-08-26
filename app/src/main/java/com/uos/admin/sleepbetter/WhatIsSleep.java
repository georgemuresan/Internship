package com.uos.admin.sleepbetter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WhatIsSleep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_what_is_sleep);

        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setImageResource(R.drawable.data);

        imageView = findViewById(R.id.imageView5);
        imageView.setImageResource(R.drawable.data);

        Button button = (Button) findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });
    }
    private void goToThirdActivity() {



        String participantID = getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

        Intent intent = new Intent(this, AllPages.class);

            startActivity(intent);


    }

}
