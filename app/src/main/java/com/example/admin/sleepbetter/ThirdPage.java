package com.example.admin.sleepbetter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdPage extends AppCompatActivity {

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToMainMenu();

            }

        });
    }
    private void goToMainMenu() {

        Intent intent = new Intent(this, MainMenu.class);

        startActivity(intent);
/* MUST
        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c);


                UserQuestionnaire user = new UserQuestionnaire();
                user.setUsername("trial");
                user.setDate(formattedDate);
                user.setTimesPerNight(1);
                user.setNightTerrors(1);
                user.setFallAsleep(1);
                user.setWakeUp(1);
                user.setFresh(1);
                user.setSad(1);
                user.setSleepy(1);
                user.setTired(1);
                user.setStressed(1);
                user.setApetite(1);
                user.setConcentrate(1);
                user.setCoordinate(1);
                user.setIrritable(1);

                userDatabase.daoAccess().insertSingleUserQuestionnaire(user);


                Report rep = new Report(userDatabase, getApplicationContext());
                rep.save();
            }
        }).start();
*/
    }
}
