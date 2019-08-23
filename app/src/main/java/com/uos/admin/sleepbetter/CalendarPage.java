package com.uos.admin.sleepbetter;

import android.app.FragmentTransaction;
import android.arch.persistence.room.Room;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.content.Context.MODE_PRIVATE;

public class CalendarPage extends Fragment {
    public GregorianCalendar cal_month, cal_month_copy;
    private HwAdapter hwAdapter;
    private TextView tv_month;
    View helpView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        helpView = inflater.inflate(R.layout.act_calendar, container, false);


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

        String moods_string = getActivity().getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).getString("moods", "");

        String[] moods = moods_string.split("gcm");

        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");
        String[] experimentsArray = experiments.split("gcm");

        String diary_comments = getActivity().getApplicationContext().getSharedPreferences("diary", MODE_PRIVATE).getString("diary", "");
        String[] diaries = diary_comments.split("gcm");

        //model date from 23-Jun-2019 too 2019-06-23
        String dayForLoop = startingDate;
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i <= shouldBe - 1; i++) {
            Date d1 = null;
             try {
                d1=new SimpleDateFormat("dd-MMM-yyyy").parse(dayForLoop);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat changer = new SimpleDateFormat("yyyy-MM-dd");

            String dateToAdd = changer.format(d1);

            System.out.println("CHECKKKKKKKKKK");
            System.out.println(experimentsArray.length);
            System.out.println(moods.length);
            System.out.println(dateToAdd);
            HomeCollection.date_collection_arr.add(new HomeCollection(dateToAdd, experimentsArray[i], moods[i], diaries[i]));


            try {
                cal.setTime(df.parse(dayForLoop));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.add(Calendar.DATE, 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
            dayForLoop = sdf1.format(cal.getTime());

        }


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

    @Override
    public void onResume() {
        super.onResume();

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

        String moods_string = getActivity().getApplicationContext().getSharedPreferences("moods", MODE_PRIVATE).getString("moods", "");

        String[] moods = moods_string.split("gcm");

        String experiments = getActivity().getApplicationContext().getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "");
        String[] experimentsArray = experiments.split("gcm");

        String diary_comments = getActivity().getApplicationContext().getSharedPreferences("diary", MODE_PRIVATE).getString("diary", "");
        String[] diaries = diary_comments.split("gcm");

        //model date from 23-Jun-2019 too 2019-06-23
        String dayForLoop = startingDate;
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i <= shouldBe - 1; i++) {
            Date d1 = null;
            try {
                d1=new SimpleDateFormat("dd-MMM-yyyy").parse(dayForLoop);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat changer = new SimpleDateFormat("yyyy-MM-dd");

            String dateToAdd = changer.format(d1);

            System.out.println("CHECKKKKKKKKKK");
            System.out.println(experimentsArray.length);
            System.out.println(moods.length);
            System.out.println(dateToAdd);
            HomeCollection.date_collection_arr.add(new HomeCollection(dateToAdd, experimentsArray[i], moods[i], diaries[i]));


            try {
                cal.setTime(df.parse(dayForLoop));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.add(Calendar.DATE, 1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
            dayForLoop = sdf1.format(cal.getTime());

        }


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