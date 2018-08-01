package com.example.admin.sleepbetter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FirstPageLinkBitmoji extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_bitmoji);
        Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.linkedBit);
                int selectedBitmoji = radioGroup.getCheckedRadioButtonId();
                int happy, ok, notok, bad;
                System.out.println("THI SIS IT" + selectedBitmoji);
                switch (selectedBitmoji) {
                    case 0:
                        happy = R.drawable.happy;
                        ok = R.drawable.ok;
                        notok = R.drawable.notok;
                        bad = R.drawable.bad;
                        putimgs(happy, ok, notok, bad);
                        break;
                    case 1:
                        happy = R.drawable.happy1;
                        ok = R.drawable.ok1;
                        notok = R.drawable.notok1;
                        bad = R.drawable.bad1;
                        putimgs(happy, ok, notok, bad);
                        break;
                    case 2:
                        happy = R.drawable.happy2;
                        ok = R.drawable.ok2;
                        notok = R.drawable.notok2;
                        bad = R.drawable.bad2;
                        putimgs(happy, ok, notok, bad);
                        break;
                }
                if ((radioGroup.getCheckedRadioButtonId() == -1))
                    goToSecondActivity();
                else {
                    Toast.makeText(getApplicationContext(), "You need to select a companion", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

    public void putimgs(int happy, int ok, int notok, int bad) {
        getSharedPreferences("bmhappy", MODE_PRIVATE).edit().putInt("slectedbitmoji", happy);
        getSharedPreferences("bmok", MODE_PRIVATE).edit().putInt("slectedbitmoji", ok);
        getSharedPreferences("bmnotok", MODE_PRIVATE).edit().putInt("slectedbitmoji", notok);
        getSharedPreferences("bmbad", MODE_PRIVATE).edit().putInt("slectedbitmoji", bad);
    }


    private void goToSecondActivity() {

        Intent intent = new Intent(this, FirstPage.class);
        getSharedPreferences("bit", MODE_PRIVATE).edit().putBoolean("bit", true);
        startActivity(intent);
    }

}
