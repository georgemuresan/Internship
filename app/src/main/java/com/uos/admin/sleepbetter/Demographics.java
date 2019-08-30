package com.uos.admin.sleepbetter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Demographics extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_demograpics);

        Button button = (Button) this.findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToMenu();

            }

        });

        TextView cons8 = findViewById(R.id.firstSet);
        cons8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

    }


    public void goToMenu(){

        String demographics = "";

        RadioGroup qGroup = this.findViewById(R.id.q1Group);
        int qID = qGroup.getCheckedRadioButtonId();
        View radioButton = qGroup.findViewById(qID);
        final int howLong = qGroup.indexOfChild(radioButton) +1;

        if (howLong != 0) demographics += "1)" + String.valueOf(howLong) + ".";

        qGroup = this.findViewById(R.id.q2Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int awake = qGroup.indexOfChild(radioButton) +1;

        if (awake != 0) demographics += "2)" + String.valueOf(awake) + ".";

        qGroup = this.findViewById(R.id.q3Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int earlier = qGroup.indexOfChild(radioButton) +1;

        if (earlier != 0) demographics += "3)" + String.valueOf(earlier) + ".";

        qGroup = this.findViewById(R.id.q4Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int quality = qGroup.indexOfChild(radioButton) +1;

        if (quality != 0) demographics += "4)" + String.valueOf(quality) + ".";

        qGroup = this.findViewById(R.id.q5Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactMood = qGroup.indexOfChild(radioButton) +1;

        if (impactMood != 0) demographics += "5)" + String.valueOf(impactMood) + ".";

        qGroup = this.findViewById(R.id.q6Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactActivities = qGroup.indexOfChild(radioButton) +1;

        if (impactActivities != 0) demographics += "6)" + String.valueOf(impactActivities) + ".";

        qGroup = this.findViewById(R.id.q7Group);
        qID = qGroup.getCheckedRadioButtonId();
        radioButton = qGroup.findViewById(qID);
        final int impactGeneral = qGroup.indexOfChild(radioButton) +1;

        if (impactGeneral != 0) demographics += "7)" + String.valueOf(impactGeneral) + ".";


      getSharedPreferences("demographics", MODE_PRIVATE).edit().putString("demographics", demographics).apply();

        startActivity(new Intent(this, Notice.class));

    }

}