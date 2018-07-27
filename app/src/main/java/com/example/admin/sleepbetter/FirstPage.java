package com.example.admin.sleepbetter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

public class FirstPage extends AppCompatActivity {

    private EditText nameBox = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToSecondActivity();

            }

        });
        nameBox = (EditText) findViewById(R.id.yourName);


//        getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).edit().clear().commit();
//        getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).edit().clear().commit();
//        getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).edit().clear().commit();
//        getApplicationContext().getSharedPreferences("questionnaire", MODE_PRIVATE).edit().clear().commit();

        String text = getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
/*
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            //show start activity
            startActivity(new Intent(this, MainMenu.class));
            Toast.makeText(FirstPage.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();*/
    }
    private void goToSecondActivity() {

        Intent intent = new Intent(this, SecondPage.class);

        String name = nameBox.getText().toString();

        getSharedPreferences("name", MODE_PRIVATE).edit().putString("username", name).apply();


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        HomeCollection.date_collection_arr = new ArrayList<HomeCollection>();
        HomeCollection.date_collection_arr.add(new HomeCollection("2018-07-08", "Diwali", "Holiday", "this is holiday"));
        String json = gson.toJson(HomeCollection.date_collection_arr);

        editor.putString("trial", json);
        editor.commit();







        startActivity(intent);

    }


}
