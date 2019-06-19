package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CalendarPage extends Fragment {

    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;
    View helpView;
    private static final String DATABASE_NAME = "user_db";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        helpView = inflater.inflate(R.layout.act_Calendar, container, false);

/*
        HomeCollection.date_collection_arr = new ArrayList<HomeCollection>();
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-08", "Diwali", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-08", "Holi", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-08", "Statehood Day", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-08-08", "Republic Unian", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-07-09", "ABC", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-06-15", "demo", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2017-09-26", "weekly off", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2018-01-08", "Events", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2018-01-16", "Dasahara", "Holiday", "this is holiday"));
        HomeCollection.date_collection_arr.add(new HomeCollection("2018-02-09", "Christmas", "Holiday", "this is holiday"));

*/
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    final String currentDate = df.format(c);

                    String startingDate = getActivity().getApplicationContext().getSharedPreferences("date", MODE_PRIVATE).getString("startingDate", "");

                    Date date1 = null;
                    Date date2 = null;


                    //Setting dates
                    try {
                        date1 = df.parse(currentDate);
                        date2 = df.parse(startingDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(date1);

                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(date2);

                    int shouldBe = c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

                    HomeCollection.date_collection_arr = new ArrayList<HomeCollection>();

                    UserDatabase uDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                    ArrayList<Double> moods = (ArrayList<Double>) uDatabase.daoAccess().fetchMoods();

                    String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");
                    String[] experimentsArray = experiments.split("$^");

                    String diary_comments = getActivity().getApplicationContext().getSharedPreferences("diary", MODE_PRIVATE).getString("diary", "");
                    String[] diaries = diary_comments.split("$^");

                    String dayForLoop = startingDate;
                    for (int i=0; i<shouldBe; i++) {

                        HomeCollection.date_collection_arr.add(new HomeCollection(dayForLoop, experimentsArray[i], String.valueOf(moods.get(i)), diaries[i]));

                        Calendar cal = Calendar.getInstance();
                        try {
                            cal.setTime(df.parse(startingDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        cal.add(Calendar.DATE, 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                        dayForLoop = sdf1.format(cal.getTime());

                    }
                }

                //scoatem variabila days si verificam: daca se imparte la 5, si nu e locked,
            }).start();


        cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_month_copy = (GregorianCalendar) cal_month.clone();
        hwAdapter = new HwAdapter(getActivity(), cal_month, HomeCollection.date_collection_arr);

        tv_month = (TextView) helpView.findViewById(R.id.tv_month);
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));


        ImageButton previous = (ImageButton) helpView.findViewById(R.id.ib_prev);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 4 && cal_month.get(GregorianCalendar.YEAR) == 2019) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(getActivity().getApplicationContext(), "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setPreviousMonth();
                    refreshCalendar();
                }


            }
        });
        ImageButton next = (ImageButton) helpView.findViewById(R.id.Ib_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cal_month.get(GregorianCalendar.MONTH) == 10 && cal_month.get(GregorianCalendar.YEAR) == 2019) {
                    //cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
                    Toast.makeText(getActivity().getApplicationContext(), "Event Detail is available for current session only.", Toast.LENGTH_SHORT).show();
                } else {
                    setNextMonth();
                    refreshCalendar();
                }
            }
        });
        GridView gridview = (GridView) helpView.findViewById(R.id.gv_calendar);
        gridview.setAdapter(hwAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedGridDate = HwAdapter.day_string.get(position);
                ((HwAdapter) parent.getAdapter()).getPositionList(selectedGridDate, getActivity());
            }

        });



        return helpView;
    }

    protected void setNextMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) + 1), cal_month.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH,
                    cal_month.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_month.get(GregorianCalendar.MONTH) == cal_month.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_month.set((cal_month.get(GregorianCalendar.YEAR) - 1), cal_month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            cal_month.set(GregorianCalendar.MONTH, cal_month.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        hwAdapter.refreshDays();
        hwAdapter.notifyDataSetChanged();
        tv_month.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_month));
    }
}