package com.example.admin.sleepbetter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
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
                int happy, ok, notok, bad, bedtime, calendar, diary, eat, jump, laptop, sleep, soda, sun, sunglas, walking;

                View radioButton = radioGroup.findViewById(selectedBitmoji);
                int idx = radioGroup.indexOfChild(radioButton);
                System.out.println("THIS IS IT   " + idx);

                switch (idx) {
                    case 0:
                        happy = R.drawable.happy;
                        ok = R.drawable.ok;
                        notok = R.drawable.notok;
                        bad = R.drawable.bad;
                        bedtime = R.drawable.badtime;
                        calendar = R.drawable.calendar;
                        diary = R.drawable.diary;
                        eat = R.drawable.eat;
                        jump = R.drawable.jump;
                        laptop = R.drawable.laptop;
                        sleep = R.drawable.sleep;
                        soda = R.drawable.soda;
                        sun = R.drawable.sun;
                        sunglas = R.drawable.sunglas;
                        walking = R.drawable.walking;
                        putimgs(happy, ok, notok, bad, bedtime, calendar, diary, eat, jump, laptop, sleep, soda, sun, sunglas, walking);
                        break;
                    case 1:
                        System.out.println("HERE");
                        happy = R.drawable.happy1;
                        ok = R.drawable.ok1;
                        notok = R.drawable.notok1;
                        bad = R.drawable.bad1;
                        bedtime = R.drawable.bedtime1;
                        calendar = R.drawable.calendar1;
                        diary = R.drawable.diary1;
                        eat = R.drawable.eat1;
                        jump = R.drawable.jump1;
                        laptop = R.drawable.laptop1;
                        sleep = R.drawable.sleep1;
                        soda = R.drawable.soda1;
                        sun = R.drawable.sun1;
                        sunglas = R.drawable.sunglas1;
                        walking = R.drawable.walking1;
                        putimgs(happy, ok, notok, bad, bedtime, calendar, diary, eat, jump, laptop, sleep, soda, sun, sunglas, walking);
                        break;
                    case 2:
                        happy = R.drawable.happy2;
                        ok = R.drawable.ok2;
                        notok = R.drawable.notok2;
                        bad = R.drawable.bad2;
                        bedtime = R.drawable.bedtime2;
                        calendar = R.drawable.calendar2;
                        diary = R.drawable.diary2;
                        eat = R.drawable.eat2;
                        jump = R.drawable.jump2;
                        laptop = R.drawable.laptop2;
                        sleep = R.drawable.sleep2;
                        soda = R.drawable.soda2;
                        sun = R.drawable.sun2;
                        sunglas = R.drawable.sunglas2;
                        walking = R.drawable.walking2;
                        putimgs(happy, ok, notok, bad, bedtime, calendar, diary, eat, jump, laptop, sleep, soda, sun, sunglas, walking);

                        break;
                }

                if (!(radioGroup.getCheckedRadioButtonId() == -1)) goToSecondActivity();
                else
                    Toast.makeText(getApplicationContext(), "You need to select a companion", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void putimgs(int happy, int ok, int notok, int bad, int bedtime, int calendar,
                        int diary, int eat, int jump, int laptop, int sleep, int soda, int sun, int sunglas,
                        int walking) {
        getSharedPreferences("bmhappy", MODE_PRIVATE).edit().putInt("slectedbitmoji", happy).apply();
        getSharedPreferences("bmok", MODE_PRIVATE).edit().putInt("slectedbitmoji", ok).apply();
        getSharedPreferences("bmnotok", MODE_PRIVATE).edit().putInt("slectedbitmoji", notok).apply();
        getSharedPreferences("bmbad", MODE_PRIVATE).edit().putInt("slectedbitmoji", bad).apply();
        getSharedPreferences("bmbedtime", MODE_PRIVATE).edit().putInt("slectedbitmoji", bedtime).apply();
        getSharedPreferences("bmcalendar", MODE_PRIVATE).edit().putInt("slectedbitmoji", calendar).apply();
        getSharedPreferences("bmdiary", MODE_PRIVATE).edit().putInt("slectedbitmoji", diary).apply();
        getSharedPreferences("bmeat", MODE_PRIVATE).edit().putInt("slectedbitmoji", eat).apply();
        getSharedPreferences("bmjump", MODE_PRIVATE).edit().putInt("slectedbitmoji", jump).apply();
        getSharedPreferences("bmlaptop", MODE_PRIVATE).edit().putInt("slectedbitmoji", laptop).apply();
        getSharedPreferences("bmsleep", MODE_PRIVATE).edit().putInt("slectedbitmoji", sleep).apply();
        getSharedPreferences("bmsoda", MODE_PRIVATE).edit().putInt("slectedbitmoji", soda).apply();
        getSharedPreferences("bmsun", MODE_PRIVATE).edit().putInt("slectedbitmoji", sun).apply();
        getSharedPreferences("bmsunglas", MODE_PRIVATE).edit().putInt("slectedbitmoji", sunglas).apply();
        getSharedPreferences("bmwalking", MODE_PRIVATE).edit().putInt("slectedbitmoji", walking).apply();

    }

    private void goToSecondActivity() {
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
    }
}
