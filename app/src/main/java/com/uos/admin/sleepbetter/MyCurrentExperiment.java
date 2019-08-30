package com.uos.admin.sleepbetter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class MyCurrentExperiment extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_current_xperiment);


        Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AllPages.class);

                startActivity(intent);
            }

        });

        TextView fo = findViewById(R.id.descriptionID);
        fo.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);


        TextView experimentTitle = (TextView) findViewById(R.id.experimentTitleID);
        String currentExp = getApplicationContext().getSharedPreferences("name", Context.MODE_PRIVATE).getString("experiment", " ");

        if (currentExp.equals(" ")){
            experimentTitle.setText("No chosen experiment");
        } else {
            experimentTitle.setText("Experiment: " + currentExp);
        }

        TextView description = (TextView) findViewById(R.id.descriptionID);

        int experiment = getApplicationContext().getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE).getInt("KEY_SAVED_RADIO_BUTTON_INDEX", 0);

        switch (experiment) {
            case 1: //increase bright light exposure
                description.setText("Exposure to sunlight should occur in the morning upon awakening, typically within the first hour after crawling out of bed. It is best to spend 30 to 45 minutes getting direct sunlight exposure into the eyes;\nSpend at least half an hour in the sun during the day as well, by exercising, walking or just any other sort of activity;\nIt is recommended that your bedroom can capture the sunlight during the day - leaving your curtains open;\nMoreover, don't worry if it's a cloudy day, because even when filtered through clouds or rain, the sunlight will continue to have its effect.");
                break;
            case 2: //wear glasses that block blue light during the night
                description.setText("Blue light - coming from LED screens or even from the sun - in the evening tricks your brain into thinking it’s daytime, which inhibits the production of melatonin and reduces both the quantity and quality of your sleep;\nWearing sleep masks or glasses during the night is recommended here;\nAlso, installing the f.lx app ( https://justgetflux.com/ ) would be highly recommended as it warms up your computer display at night, to match your indoor lighting;\nManually adjusting the brightness on your devices wuld also help with this matter.");
                break;
            case 3: // turn off any bright lights 2 hours before going to bed
                description.setText("Light makes you feel alert, which isn't what you want when you need to sleep;\nTherefore, try turning off the TV, the computer, or any other LED-light device you are using with 2 hours before going to bed.");
                break;
            case 5: // Do not drink caffeine within 6 hours
                description.setText("It is highly recommended not drinking any product containing caffeine with 6 hours before going to sleep;\nThat is either coffee, soft drinks such as Coke, Fanta etc, some tea brews - such as green tea or black tea, and of course, energy drinks.");
                break;
            case 6: // Limit yourself to 4 cups of coffees per day; 10 canss of
                description.setText("During the day, please limit yourself to drinking not more than 4 cups of coffee (about 945 ml), 10 cans of soda (about 3.3 l) such as Fanta or Coke, or 2 energy drinks - of any kind.");
                break;
            case 7: //Do not drink empty stomach
                description.setText("After you wake up, we highly recommend not drinking any products containing caffeine on empty stomach (before eating any food);\nThat is either coffee, soft drinks such as Coke, Fanta etc, some tea brews - such as green tea or black tea, and of course, energy drinks.");
                break;
            case 9://Usually get up at the same time everyday, even on weekends
                description.setText("Going to bed and waking up at the same time everyday, even during the weekend, or during holidays;\nIt's important to have a sleeping schedule as your body loves ROUTINE.");
                break;
            case 10: // Sleep no lesss than 7 hours per night
                description.setText("It can happen very often that you don't feel sleepy close to bedtime. That can be caused by a lot of other factors, like the caffeine in your body, light exposure, adrenaline etc;\n Either way, before going to sleep, we recommend trying one of the next few activities:\\nTake a warm bath to let your body relax;\\nRead a book to relax your mind;\\nTry doing a few exercises or stretching;\\nDrink a hot cup of tea, but be careful so that it doesn not containt caffeine (such as black tea and green tea);\\nAny other activity that works well for yourself and helps your mind and body relax.");
                break;
            case 11: //DO not go to bed unless you are tired. If you are not
                description.setText("Going to sleep no later than 22:30 in order to regulate you sleep cycles to an appropriate bedtime hour.");
                break;
            case 12: //Go to sleep at 22:30 PM the latest
                description.setText("Since you haven't chosen an experiment from the \"Experiment\" section yet, this section is unavailable.");
                break;
        }
    }
}
