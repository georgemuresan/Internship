package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        goalDiaryView = inflater.inflate(R.layout.goal_diary, container, false);

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

    public void updateDiary(String notee){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

      final String note = notee;

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

        String experiment = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("experiment", " ");

        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate2 = df2.format(c);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String json = sharedPrefs.getString("trial", "");
        Gson gson = new Gson();

        Type type = new TypeToken<List<HomeCollection>>() {}.getType();
        List<HomeCollection> arrayList = gson.fromJson(json, type);

        HomeCollection.date_collection_arr = (ArrayList<HomeCollection>) arrayList;
        HomeCollection coll = HomeCollection.date_collection_arr.get(HomeCollection.date_collection_arr.size()-1);
        String date = coll.date;

        if (date.equals(formattedDate2)){
            String comment = coll.comment;
            comment = comment + " / " + note;

            String lastExp = coll.experiment;
            HomeCollection.date_collection_arr.remove(HomeCollection.date_collection_arr.size()-1);
            if (lastExp.equals(experiment)){

                HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, experiment, String.valueOf(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), "No experiment started yet", comment));
            } else {
                HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, lastExp, String.valueOf(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), "No experiment started yet", comment));
            }

            SharedPreferences.Editor editor = sharedPrefs.edit();

            json = gson.toJson(HomeCollection.date_collection_arr);

            editor.putString("trial", json);
            editor.commit();

        } else {
            HomeCollection.date_collection_arr.add(new HomeCollection(formattedDate2, "(yesterday) " + experiment, "(yesterday) " + String.valueOf(getActivity().getApplicationContext().getSharedPreferences("MOOD", MODE_PRIVATE).getInt("mood", 0)), "(yesterday) " + "No experiment started yet", note));

            SharedPreferences.Editor editor = sharedPrefs.edit();

            json = gson.toJson(HomeCollection.date_collection_arr);

            editor.putString("trial", json);
            editor.commit();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                String username = getActivity().getApplicationContext().getSharedPreferences("name", MODE_PRIVATE).getString("participantID", "nothing");

                UserDiary userDiary = new UserDiary();
                userDiary.setUsername(username);
                userDiary.setDate(formattedDate);
                userDiary.setComment(note);

                userDatabase.daoAccess().insertSingleUserDiary(userDiary);

                Report rep = new Report(userDatabase, getActivity().getApplicationContext());
                rep.save(username, false, getActivity().getApplicationContext().getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing"));
            }
        }).start();

    }
}
