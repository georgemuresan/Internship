package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GoalDiary extends Fragment {

    View goalDiaryView;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;
    private String note;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        goalDiaryView = inflater.inflate(R.layout.goal_diary, container, false);

        Button button = (Button) goalDiaryView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                updateDiary();

            }

        });

        new Thread(new Runnable() {
            @Override
            public void run() {



                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                List<UserDiary> diaries = userDatabase.daoAccess().fetchDiary();
                final TableLayout tableLayout = (TableLayout) goalDiaryView.findViewById(R.id.table);

                int colorBlack = Color.BLACK;
                for (int i=0; i< diaries.size(); i++){
                        // Creation row
                        final TableRow tableRow = new TableRow(getActivity().getApplicationContext());
                        tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,2));
                    tableRow.setWeightSum(2);
                        // Creation textView
                        final TextView text = new TextView(getActivity().getApplicationContext());
                        text.setText(diaries.get(i).getDate());
                        text.setTextColor(colorBlack);
                        text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1));

                    // Creation textView
                    final TextView text2 = new TextView(getActivity().getApplicationContext());
                    text2.setText(diaries.get(i).getComment());
                    text2.setTextColor(colorBlack);
                    text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

                        tableRow.addView(text);
                        tableRow.addView(text2);

                        tableLayout.addView(tableRow);
                    }

            }
        }).start();
        return goalDiaryView;
    }

    public void updateDiary(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        EditText noted = (EditText) goalDiaryView.findViewById(R.id.yourGoal);
        note = noted.getText().toString();

        final TableLayout tableLayout = (TableLayout) goalDiaryView.findViewById(R.id.table);

                int colorBlack = Color.BLACK;
                    // Creation row
                    final TableRow tableRow = new TableRow(getActivity().getApplicationContext());
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT,2));
                    tableRow.setWeightSum(2);
                    // Creation textView
                    final TextView text = new TextView(getActivity().getApplicationContext());
                    text.setText(formattedDate);
                    text.setTextColor(colorBlack);
                    text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1));

                    // Creation textView
                    final TextView text2 = new TextView(getActivity().getApplicationContext());
                    text2.setText(note);
                    text2.setTextColor(colorBlack);
                    text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));

                    tableRow.addView(text);
                    tableRow.addView(text2);

                    tableLayout.addView(tableRow);

    }
}
