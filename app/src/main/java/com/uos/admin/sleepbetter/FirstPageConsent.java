package com.uos.admin.sleepbetter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class FirstPageConsent extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_consent);

        Button button = (Button) findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goBack();

            }

        });

        TextView descr = findViewById(R.id.factorsIntro);
        descr.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        TextView cons1 = findViewById(R.id.p1);
        cons1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView cons2 = findViewById(R.id.p2);
        cons2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView cons3 = findViewById(R.id.p3);
        cons3.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView cons4 = findViewById(R.id.p4);
        cons4.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView cons5 = findViewById(R.id.p5);
        cons5.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView cons6 = findViewById(R.id.p6);
        cons6.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView cons7 = findViewById(R.id.p7);
        cons7.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        TextView cons8 = findViewById(R.id.p8);
        cons8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }
    private void goBack() {

        Intent intent = new Intent(this, FirstPage.class);


        CheckBox p1 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox p2 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox p3 = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox p4 = (CheckBox) findViewById(R.id.checkBox5);
        CheckBox p5 = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox p6 = (CheckBox) findViewById(R.id.checkBox7);
        CheckBox p7 = (CheckBox) findViewById(R.id.checkBox8);

        RadioGroup rg = (RadioGroup) findViewById(R.id.group);

        if (p1.isChecked() && p2.isChecked() && p3.isChecked() && p4.isChecked() && p5.isChecked() && p6.isChecked() && p7.isChecked() && rg.getCheckedRadioButtonId() != -1){

            int selectedId = rg.getCheckedRadioButtonId();


            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String answer = (String) radioButton.getText();

            System.out.println(answer);

            getSharedPreferences("consent", MODE_PRIVATE).edit().putString("consent", answer).apply();

            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "In order to run the experiment all 7 boxes need to be ticked and you need to answer the question", Toast.LENGTH_SHORT).show();
        }



    }


}
