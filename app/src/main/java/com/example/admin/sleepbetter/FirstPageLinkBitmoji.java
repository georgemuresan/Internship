package com.example.admin.sleepbetter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstPageLinkBitmoji extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_bitmoji);

        Button button = (Button) findViewById(R.id.submitButton);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToSecondActivity();

            }

        });

    }
    private void goToSecondActivity() {

        Intent intent = new Intent(this, FirstPage.class);


        startActivity(intent);

    }

}
