package com.uos.admin.sleepbetter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class GoalDiary extends Fragment {

    View goalDiaryView;
    private static final String DATABASE_NAME = "user_db";
    private UserDatabase userDatabase;
    private String note;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        goalDiaryView = inflater.inflate(R.layout.act_diary, container, false);

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (getView() == null && tabLayout.getSelectedTabPosition() == 3) {

            if (getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).getBoolean("diary", true)){

                InfoFirstDialog dia = new InfoFirstDialog();
                dia.show(getFragmentManager(), "dialog");

                getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("diary", false).apply();

            }
        }



        Button button = (Button) goalDiaryView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                EditText noted = (EditText) goalDiaryView.findViewById(R.id.yourGoal);
                note = noted.getText().toString();
                if (!note.equals("")) {
                    updateDiary(note);

                    InputMethodManager inputManager = (InputMethodManager)
                            getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    noted.setText("");
                }
            }

        });

        TextView cons8 = goalDiaryView.findViewById(R.id.pleaseInput);
        cons8.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        TabLayout tabLayout = (TabLayout) AllPages.tabLayout;
        if (getView() != null && tabLayout.getSelectedTabPosition() == 3) {

            if (getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).getBoolean("diary", true)){

                InfoFirstDialog dia = new InfoFirstDialog();
                dia.show(getFragmentManager(), "dialog");

                getActivity().getApplicationContext().getSharedPreferences("firstnotice", MODE_PRIVATE).edit().putBoolean("diary", false).apply();

            }
        }


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

    public static class InfoFirstDialog extends DialogFragment {

        private String message;
        private int hour, minute;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("This section is where you can save any notes, insights, comments or ideas about your experience - think of it as a notebook that would help you record any comments. Also, you will be able to see previous comments from the current day only. The old ones could only be seen in the Calendar section.");

            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            return builder.create();
        }


    }

}
