package com.uos.admin.sleepbetter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Update_Schedule_SameTime extends Fragment {
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;
    View updateView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        updateView = inflater.inflate(R.layout.act_update_schedule_sametime, container, false);

        Button button = (Button) updateView.findViewById(R.id.submitUpdate);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goToQuestionnaire();

            }

        });

        /*
        let date = Date()
        let calendar = NSCalendar.current
        let hour = calendar.component(.hour, from: date)



        let formatter = DateFormatter()
        formatter.dateFormat = "dd"
        let current = formatter.string(from: date)
        let oldDate:String = UserDefaults.standard.string(forKey: "dateStartingExperiment")!
                let day = Int(oldDate.prefix(2))
        if (Int(current)! -  day! == 0){
            let alert = UIAlertController(title: "Notice", message: "You are not allowed to fill in today's questionnaire as you just changed the experiment. Come back tomorrow.", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: returnBack))
                self.present(alert, animated: true, completion: nil)

                self.view.isUserInteractionEnabled = false

        }
        */


        return updateView;
    }


    public void goToQuestionnaire() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        RadioGroup radioButtonGroup0 = updateView.findViewById(R.id.radioGroup2);
        int radioButtonID0 = radioButtonGroup0.getCheckedRadioButtonId();
        View radioButton0 = radioButtonGroup0.findViewById(radioButtonID0);
        int idx0 = radioButtonGroup0.indexOfChild(radioButton0);
        RadioButton r0 = (RadioButton)  radioButtonGroup0.getChildAt(idx0);
        final String testCompared = r0.getText().toString();

        final TimePicker tpLastdrink = (TimePicker) updateView.findViewById(R.id.lastDrink);
        final String textSleep = tpLastdrink.getCurrentHour() + ":" + tpLastdrink.getCurrentMinute();

        final TimePicker tpWhenSleep = (TimePicker) updateView.findViewById(R.id.whenSleep);
        final String textWake = tpWhenSleep.getCurrentHour() + ":" + tpWhenSleep.getCurrentMinute();

     //   final int dayReview = dayReviewBar.getProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                UserExperiment user = new UserExperiment();
                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("username", "nothing");
                user.setUsername(username);
                user.setDate(formattedDate);
                user.setExperiment("S1");
                user.setScheduleOneWhenSleep(textSleep);
                user.setScheduleOneWhenWake(textWake);

                user.setOverallBetter(testCompared);

                userDatabase.daoAccess().insertSingleUserExperiment(user);


            }
        }).start();


        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content_frame, new QFinal()).commit();
    }
}

