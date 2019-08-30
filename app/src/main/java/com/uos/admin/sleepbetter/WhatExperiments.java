package com.uos.admin.sleepbetter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class WhatExperiments extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_what_experiments);

        Button button = (Button) findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToThirdActivity();

            }

        });
        TextView fo = findViewById(R.id.textView6);
        fo.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        TextView fo2 = findViewById(R.id.textView9);
        fo2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        TextView fo3 = findViewById(R.id.textView7);
        fo3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView fo4 = findViewById(R.id.textView5);
        fo4.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }

    private void goToThirdActivity() {


        Intent intent = new Intent(this, AllPages.class);

        startActivity(intent);

    }
}
