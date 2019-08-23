package com.uos.admin.sleepbetter;

import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GoalDiary extends Fragment {

    View goalDiaryView;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;
    private String note;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        goalDiaryView = inflater.inflate(R.layout.act_diary, container, false);
        ImageView imageView = (ImageView) goalDiaryView.findViewById(R.id.imageView29);
        imageView.setImageResource(R.drawable.data);
        Button button = (Button) goalDiaryView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText noted = (EditText) goalDiaryView.findViewById(R.id.yourGoal);
                note = noted.getText().toString();
                if (!note.equals("")) {
                    updateDiary(note);
                }
            }

        });

        new Thread(new Runnable() {
            @Override
            public void run() {


                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                List<UserDiary> diaries = userDatabase.daoAccess().fetchDiary();
                final TableLayout tableLayout = (TableLayout) goalDiaryView.findViewById(R.id.table);


                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String currentDate = df.format(c);

                int colorBlack = Color.BLACK;
                for (int i = 0; i < diaries.size(); i++) {

                    if (diaries.get(i).getDate().equals(currentDate)) {
                        // Creation row
                        final TableRow tableRow = new TableRow(getActivity().getApplicationContext());
                        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
                        tableRow.setWeightSum(1);

                        // Creation textView
                        final TextView text2 = new TextView(getActivity().getApplicationContext());
                        text2.setText(diaries.get(i).getComment());
                        text2.setTextColor(colorBlack);
                        text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        tableRow.addView(text2);

                        tableLayout.addView(tableRow);
                    }
                }

            }
        }).start();
        return goalDiaryView;
    }

    public void updateDiary(String notee) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String currentDate = df.format(c);

        final String note = notee;

        final TableLayout tableLayout = (TableLayout) goalDiaryView.findViewById(R.id.table);

        int colorBlack = Color.BLACK;
        // Creation row
        final TableRow tableRow = new TableRow(getActivity().getApplicationContext());
        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
        tableRow.setWeightSum(1);

        // Creation textView
        final TextView text2 = new TextView(getActivity().getApplicationContext());
        text2.setText(note);
        text2.setTextColor(colorBlack);
        text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

        tableRow.addView(text2);

        tableLayout.addView(tableRow);

        //Update array of comments

        String startingDate= getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).getString("startingDate", "");
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat dates = new SimpleDateFormat("dd-MMM-yyyy");
        //Setting dates
        try {
            date1 = dates.parse(currentDate);
            date2 = dates.parse(startingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        int differenceOfDates = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

        String diary_comments = getActivity().getApplicationContext().getSharedPreferences("diary", MODE_PRIVATE).getString("diary", "");



        String[] diaries = diary_comments.split("gcm");

        diaries[differenceOfDates] = diaries[differenceOfDates] + note + ".";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < diaries.length; i++) {
            sb.append(diaries[i]).append("gcm");
        }
        getActivity().getApplicationContext().getSharedPreferences("diary", MODE_PRIVATE).edit().putString("diary", sb.toString()).apply();

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

                UserDiary userDiary = new UserDiary();
                userDiary.setUsername(username);
                userDiary.setDate(currentDate);
                userDiary.setComment(note);

                userDatabase.daoAccess().insertSingleUserDiary(userDiary);

                Report rep = new Report(userDatabase, getActivity().getApplicationContext());
                rep.save(username, false, getActivity().getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
            }
        }).start();

    }
}
