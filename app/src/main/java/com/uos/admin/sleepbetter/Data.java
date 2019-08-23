package com.uos.admin.sleepbetter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import android.support.v4.app.DialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class Data extends Fragment implements AdapterView.OnItemSelectedListener {
    private UserDatabase userDatabase;
    private static final String DATABASE_NAME = "user_db";
    private Spinner spinner;
    View dataView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataView = inflater.inflate(R.layout.act_data, container, false);
        ImageView imageView1 = (ImageView) dataView.findViewById(R.id.imageView28);

        imageView1.setImageResource(R.drawable.data);

        Button info1 = (Button) dataView.findViewById(R.id.infoRatings);
        info1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InfoOneDialog dia = new InfoOneDialog();

                dia.show(getFragmentManager(), "dd");
            }
        });
        Button info2 = (Button) dataView.findViewById(R.id.infoImprovements);
        info2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InfoTwoDialog dia = new InfoTwoDialog();
                dia.show(getFragmentManager(), "dialog");
            }
        });
        Button info3 = (Button) dataView.findViewById(R.id.infoTimelines);
        info3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InfoThreeDialog dia = new InfoThreeDialog();
                dia.show(getFragmentManager(), "dialog");
            }
        });
        Button info4 = (Button) dataView.findViewById(R.id.infoThirdTimeline);
        info4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InfoFourDialog dia = new InfoFourDialog();
                dia.show(getFragmentManager(), "dialog");
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {

                GraphView graph = (GraphView) dataView.findViewById(R.id.totalMood);
                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                ArrayList<Double> numberOfGoodMoods = (ArrayList<Double>) userDatabase.daoAccess().fetchMoods();
                int sizeMoods = sizeOfMoods(numberOfGoodMoods);

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

                int sizeToGraph = 0;

                for (int j=0; j<numberOfGoodMoods.size(); j++){
                    if (numberOfGoodMoods.get(j) != -1){
                        sizeToGraph++;
                    }
                }

                DataPoint[] dp = new DataPoint[sizeToGraph];

                int i=0;
                for (int k=0; k<numberOfGoodMoods.size(); k++) {

                    if (numberOfGoodMoods.get(k) != -1){
                        dp[i] = new DataPoint(k, numberOfGoodMoods.get(k));
                        i++;
                    }
                }

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                graph.addSeries(series);

                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScalableY(true);
                graph.getViewport().setScrollableY(true);

                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setThickness(8);

                int blackValue = Color.BLACK;
                graph.setTitle("Mood over time - the lower the better");
                graph.setTitleColor(blackValue);
                // graph.setTitleTextSize(13);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(15);
                graph.getViewport().setMaxXAxisSize(15);
                graph.getViewport().setMinY(1);
                graph.getViewport().setMaxY(5);

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setXAxisBoundsManual(true);

                graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
                graph.getGridLabelRenderer().setHorizontalAxisTitleColor(blackValue);


                GraphView graph2 = (GraphView) dataView.findViewById(R.id.shortMood);
                DataPoint[] dp2;

                //diferit pt ca luam doar de la structuri de 5



                if (userDatabase.daoAccess().fetchMoods().size() > 1) {

                    int sizeOfSecondGraph = 0;

                    System.out.println("nr" + numberOfGoodMoods.size());
                    int beginning;
                    if ((numberOfGoodMoods.size() -1) % 5 == 0){
                        beginning = numberOfGoodMoods.size() - 5;
                    } else {
                        beginning = ((numberOfGoodMoods.size() - 1) / 5) * 5 + 1;
                    }


                    for (int g = beginning; g < numberOfGoodMoods.size(); g++){
                        if (numberOfGoodMoods.get(g) != -1){
                            sizeOfSecondGraph++;
                        }
                    }

                    dp2 = new DataPoint[sizeOfSecondGraph];

                    //cum schimb

                    i=0;
                    int k=0;
                    Iterator it = numberOfGoodMoods.iterator();
                    if ((numberOfGoodMoods.size() - 1) % 5 == 0){
                        while (it.hasNext() && k < 5) {
                            it.next();
                            Double moodToSee = userDatabase.daoAccess().fetchMoods().get(((userDatabase.daoAccess().fetchMoods().size() - 1) / 5) * 5 - 5 + k + 1);

                            if (moodToSee != -1) {
                                dp2[i] = new DataPoint(k + 1, moodToSee);
                                i++;
                            }
                            k++;
                        }
                    } else {
                        while (it.hasNext() && k < (numberOfGoodMoods.size() - 1) % 5) {
                            it.next();
                            Double moodToSee = userDatabase.daoAccess().fetchMoods().get(((userDatabase.daoAccess().fetchMoods().size() - 1) / 5) * 5 + k + 1);

                            if (moodToSee != -1) {

                                dp2[i] = new DataPoint(k + 1, moodToSee);
                                i++;
                            }
                            k++;
                        }
                    }
                } else {
                    dp2 = new DataPoint[0];
                }



                LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(dp2);
                graph2.addSeries(series2);

                graph2.getViewport().setScalable(true);
                graph2.getViewport().setScrollable(true);
                graph2.getViewport().setScalableY(true);
                graph2.getViewport().setScrollableY(true);

                series2.setDrawDataPoints(true);
                series2.setDataPointsRadius(10);
                series2.setThickness(8);

                graph2.setTitle("Current experiment mood");
                graph2.setTitleColor(blackValue);
                //   graph2.setTitleTextSize(13);

                graph2.getViewport().setMinX(1);
                graph2.getViewport().setMaxX(5);
                graph2.getViewport().setMaxXAxisSize(5);
                graph2.getViewport().setMinY(1);
                graph2.getViewport().setMaxY(5);

                graph2.getViewport().setYAxisBoundsManual(true);
                graph2.getViewport().setXAxisBoundsManual(true);

                graph2.getGridLabelRenderer().setHorizontalAxisTitle("Mood over time - the lower the better");
                graph2.getGridLabelRenderer().setHorizontalAxisTitleColor(blackValue);



            }



        }).start();

        updateAllTexts();

        spinner = (Spinner) dataView.findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.targetGraph, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        return dataView;
    }


    public static class InfoOneDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("This section shows your state as reported in yesterday's questionnaire. The overall mood value was computed based on all of your answers. If the values show -1, it means you forgot to fill in the questionnaire.");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }
    public static class InfoTwoDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("This section aims to recommend the fields that could be improved since yesterday's questionnaire. As higher values are associated with worse feelings, this section highlights all the attributes rated 3+. Bear in mind that if you haven't completed yesterday's questionnaire, this section will be empty.");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }
    public static class InfoThreeDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("This section displays 3 graphs based on your progress while using the app. First graph shows your overall progress in time while using SleepBetter. Second graph shows the same progress but for only a duration of 5 days- the current running experiment. For the third graph you are able to choose which attribute's progress you want to view during the time you used SleepBetter. \"Lower is better\" means a lower value is desirable, as it shows a better, improved state.");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }
    public static class InfoFourDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("For this graph you are able to choose which attribute's progress you want to view during the time you used SleepBetter. \\\"Lower is better\\\" means a lower value is desirable, as it shows a better, improved state.");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            return builder.create();
        }
    }

    private int sizeOfMoods(ArrayList<Double> integers) {

        int size = 0;

        for (int i=0; i< integers.size(); i++){
            if (integers.get(i) != -1){
                size++;
            }
        }
        return size;
    }

    private void updateAllTexts() {
        TextView editWorking = (TextView) dataView.findViewById(R.id.workEdit);
        String result = "Nothing to work on";
        String change = "";

        TextView experiment = (TextView) dataView.findViewById(R.id.experimentName);
        experiment.setText("Experiment: " + getActivity().getApplicationContext().getSharedPreferences("name", Context.MODE_PRIVATE).getString("experiment", " "));

        TextView mood = (TextView) dataView.findViewById(R.id.overallMood);
        mood.setText("Overall mood: " + getActivity().getApplicationContext().getSharedPreferences("MOOD", Context.MODE_PRIVATE).getFloat("mood", 0) + "/5");

        TextView times = (TextView) dataView.findViewById(R.id.howLong);
        times.setText("How log it takes to fall asleep: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("howLong", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("howLong", 0) >= 3){
            change = change + "/ " + "hard to fall asleep";
        }

        TextView nightTerrors = (TextView) dataView.findViewById(R.id.awake);
        nightTerrors.setText("How long you are awake during the night: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("awake", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("awake", 0) >= 3){
            change = change + "/ " + "awake during the nights";
        }

        TextView waking = (TextView) dataView.findViewById(R.id.earlier);
        waking.setText("How much earlier you wake up than your normal time: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("earlier", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("earlier", 0) >= 3){
            change = change + "/ " + "waking up too early";
        }

        TextView fall = (TextView) dataView.findViewById(R.id.quality);
        fall.setText("Sleep Quality: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("quality", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("quality", 0) >= 3){
            change = change + "/ " + "sleep quality";
        }

        TextView fresh = (TextView) dataView.findViewById(R.id.impactMood);
        fresh.setText("Affecting mood, energy, or relationships: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("impactMood", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("impactMood", 0) >= 3){
            change = change + "/ " + "affecting mood, energy, relationships";
        }

        TextView sad = (TextView) dataView.findViewById(R.id.impactActivities);
        sad.setText("Affecting concentration, productivity, or ability to stay awake: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("impactActivities", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("impactActivities", 0) >= 3){
            change = change + "/ " + "affecting concentration, productivity";
        }

        TextView sleepy = (TextView) dataView.findViewById(R.id.impactGeneral);
        sleepy.setText("Affecting life in general: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("impactGeneral", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("impactLife", 0) >= 3){
            change = change + "/ " + "affecting life in general";
        }

        if (change.equals("")){
            editWorking.setText(result);
        } else {
            editWorking.setText(change);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                GraphView graph = (GraphView) dataView.findViewById(R.id.specificMood);
                graph.removeAllSeries();

                ArrayList<Double> numberOfGoodMoods = (ArrayList<Double>) userDatabase.daoAccess().fetchMoods();
                int sizeMoods = sizeOfMoods(numberOfGoodMoods);


                System.out.println("DATAAAAAAAAAAAAAAAAAAAAa");
                System.out.println(sizeMoods);

                int sizeToGraph = 0;

                for (int j=0; j<numberOfGoodMoods.size(); j++){
                    if (numberOfGoodMoods.get(j) != -1){
                        sizeToGraph++;
                    }
                }

                DataPoint[] dp = new DataPoint[sizeToGraph];

                String text = spinner.getSelectedItem().toString();
                List<Integer> listInNeed = getListFromSpinner(text);

                int i=0;
                for (int k=0; k<numberOfGoodMoods.size(); k++) {

                    if (numberOfGoodMoods.get(k) != -1){
                        dp[i] = new DataPoint(k, listInNeed.get(k));
                        i++;
                    }
                }

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                graph.addSeries(series);

                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScalableY(true);
                graph.getViewport().setScrollableY(true);

                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setThickness(8);

                int blackValue = Color.BLACK;
                graph.setTitle("Mood over time - the lower the better");
                graph.setTitleColor(blackValue);
                // graph.setTitleTextSize(13);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(15);
                graph.getViewport().setMaxXAxisSize(15);
                graph.getViewport().setMinY(1);
                graph.getViewport().setMaxY(5);

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setXAxisBoundsManual(true);

                graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
                graph.getGridLabelRenderer().setHorizontalAxisTitleColor(blackValue);



            }

            private List<Integer> getListFromSpinner(String text) {

                if (text.equals("Falling asleep")){
                    return userDatabase.daoAccess().fetchHowLong();
                } else  if (text.equals("Awake at night")){
                    return userDatabase.daoAccess().fetchAwake();
                } else if (text.equals("Waking up earlier")){
                    return userDatabase.daoAccess().fetchEarlier();
                } else if (text.equals("Sleep quality")){
                    return userDatabase.daoAccess().fetchQuality();
                } else if (text.equals("Impacting feelings")){
                    return userDatabase.daoAccess().fetchImpactMood();
                } else if (text.equals("Impacting activities")){
                    return userDatabase.daoAccess().fetchImpactActivities();
                } else if (text.equals("Impacting life")){
                    return userDatabase.daoAccess().fetchImpactGeneral();
                }
                return null;

            }
        }).start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}