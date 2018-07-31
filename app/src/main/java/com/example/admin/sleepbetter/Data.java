package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Data extends Fragment implements AdapterView.OnItemSelectedListener {
    private UserDatabase userDatabase;
    private static final String DATABASE_NAME = "user_db";
    private Spinner spinner;
    View dataView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataView = inflater.inflate(R.layout.data, container, false);
/*
        GraphView graph = (GraphView) dataView.findViewById(R.id.graph);
        DataPoint[] dp = new DataPoint[2];
        for(int i=0; i<1; i++){
            dp[i] = new DataPoint(1, 3);
            dp[i+1] = new DataPoint(2, 5);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        graph.addSeries(series);*/

        new Thread(new Runnable() {
            @Override
            public void run() {

                GraphView graph = (GraphView) dataView.findViewById(R.id.totalMood);
                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

                LinkedHashMap<Boolean, Integer> numberOfGoodMoods = calculateNrMoods(userDatabase.daoAccess().fetchMoods());
                int sizeMoods = sizeOfMoods(numberOfGoodMoods);

                DataPoint[] dp = new DataPoint[sizeMoods];

                int i=0;
                int k=0;
                for (Map.Entry<Boolean, Integer> entry : numberOfGoodMoods.entrySet()) {

                    if (entry.getKey()){
                        dp[i] = new DataPoint(k, entry.getValue());
                        i++;
                    }
                    k++;
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
                graph.setTitle("Overall mood over time");
                graph.setTitleColor(blackValue);
               // graph.setTitleTextSize(13);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(30);
                graph.getViewport().setMaxXAxisSize(30);
                graph.getViewport().setMinY(1);
                graph.getViewport().setMaxY(5);

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setXAxisBoundsManual(true);

                graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
                graph.getGridLabelRenderer().setHorizontalAxisTitleColor(blackValue);


                GraphView graph2 = (GraphView) dataView.findViewById(R.id.shortMood);
                DataPoint[] dp2;
                if (userDatabase.daoAccess().fetchMoods().size() > 1) {

                    dp2 = new DataPoint[sizeMoods];

                    //cum schimb
                    i=0;
                    k=0;
                    Iterator it = numberOfGoodMoods.entrySet().iterator();
                    while (it.hasNext() && i < numberOfGoodMoods.size() % 6) {
                        Map.Entry<Boolean, Integer> pair = (Map.Entry<Boolean, Integer>)it.next();
                        if (pair.getKey()){
                            dp2[i] = new DataPoint(k + 1, userDatabase.daoAccess().fetchMoods().get((userDatabase.daoAccess().fetchMoods().size() / 5) * 5 + k + 1));
                            i++;
                        }
                        k++;
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

                graph2.getGridLabelRenderer().setHorizontalAxisTitle("Overall mood over time");
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

    private LinkedHashMap<Boolean, Integer> calculateNrMoods(List<Integer> integers) {

        LinkedHashMap<Boolean, Integer> result = new LinkedHashMap<Boolean, Integer>();

        for (int i=0; i< integers.size(); i++){
            if (i != -1){
                result.put(true, integers.get(i));
            } else {
                result.put(false, -1);
            }
        }
        return result;
    }

    private int sizeOfMoods(LinkedHashMap<Boolean, Integer> integers) {

        int size = 0;

        for (Map.Entry<Boolean, Integer> entry : integers.entrySet()){
            if (entry.getKey()){
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
        mood.setText("Overall mood: " + getActivity().getApplicationContext().getSharedPreferences("MOOD", Context.MODE_PRIVATE).getInt("mood", 0) + "/5");

        TextView times = (TextView) dataView.findViewById(R.id.timesWaking);
        times.setText("Times waking up: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("timesPerNight", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("timesPerNight", 0) >= 3){
            change = change + "/ " + "times waking up";
        }

        TextView nightTerrors = (TextView) dataView.findViewById(R.id.nightTerrors);
        nightTerrors.setText("Night terrors: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("nightTerrors", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("nightTerrors", 0) >= 3){
            change = change + "/ " + "night terrors";
        }

        TextView waking = (TextView) dataView.findViewById(R.id.wakingUp);
        waking.setText("Falling asleep: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("wakeUp", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("wakeUp", 0) >= 3){
            change = change + "/ " + "waking up";
        }

        TextView fall = (TextView) dataView.findViewById(R.id.fallasleep);
        fall.setText("Waking up: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("fallAsleep", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("fallAsleep", 0) >= 3){
            change = change + "/ " + "falling sleep";
        }

        TextView fresh = (TextView) dataView.findViewById(R.id.fresh);
        fresh.setText("Fresh: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("fresh", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("fresh", 0) >= 3){
            change = change + "/ " + "freshness";
        }

        TextView sad = (TextView) dataView.findViewById(R.id.sad);
        sad.setText("Sad: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("sad", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("sad", 0) >= 3){
            change = change + "/ " + "sadness";
        }

        TextView sleepy = (TextView) dataView.findViewById(R.id.sleepy);
        sleepy.setText("Sleepy: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("sleepy", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("sleepy", 0) >= 3){
            change = change + "/ " + "sleepiness";
        }

        TextView tired = (TextView) dataView.findViewById(R.id.tired);
        tired.setText("Tired: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("tired", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("tired", 0) >= 3){
            change = change + "/ " + "tiredness";
        }

        TextView stressed = (TextView) dataView.findViewById(R.id.stressed);
        stressed.setText("Stressed: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("stressed", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("stressed", 0) >= 3){
            change = change + "/ " + "stress";
        }

        TextView irritable = (TextView) dataView.findViewById(R.id.irritable);
        irritable.setText("Irritable: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("irritable", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("irritable", 0) >= 3){
            change = change + "/ " + "irritability";
        }

        TextView concentrate = (TextView) dataView.findViewById(R.id.concentrate);
        concentrate.setText("Concentration: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("concentrate", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("concentrate", 0) >= 3){
            change = change + "/ " + "concentration";
        }

        TextView coordinate = (TextView) dataView.findViewById(R.id.coordinate);
        coordinate.setText("Coordination: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("coordinate", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("coordinate", 0) >= 3){
            change = change + "/ " + "coordination";
        }

        TextView appetite = (TextView) dataView.findViewById(R.id.appetite);
        appetite.setText("Appetite: " + getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("apetite", 0) + "/5");
        if (getActivity().getApplicationContext().getSharedPreferences("questionnaire", Context.MODE_PRIVATE).getInt("apetite", 0) >= 3){
            change = change + "/ " + "appetite";
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

                LinkedHashMap<Boolean, Integer> numberOfGoodMoods = calculateNrMoods(userDatabase.daoAccess().fetchMoods());
                int sizeMoods = sizeOfMoods(numberOfGoodMoods);


                DataPoint[] dp = new DataPoint[userDatabase.daoAccess().fetchMoods().size()];

                String text = spinner.getSelectedItem().toString();
                List<Integer> listInNeed = getListFromSpinner(text);

                int i=0;
                int k=0;
                for (Map.Entry<Boolean, Integer> entry : numberOfGoodMoods.entrySet()) {

                    if (entry.getKey()){
                        dp[i] = new DataPoint(k, listInNeed.get(k));
                        i++;
                    }
                    k++;
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
                graph.setTitle("Overall mood over time");
                graph.setTitleColor(blackValue);
               // graph.setTitleTextSize(13);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(30);
                graph.getViewport().setMaxXAxisSize(30);
                graph.getViewport().setMinY(1);
                graph.getViewport().setMaxY(5);

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setXAxisBoundsManual(true);

                graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
                graph.getGridLabelRenderer().setHorizontalAxisTitleColor(blackValue);



            }

            private List<Integer> getListFromSpinner(String text) {

                if (text.equals("Times waking up per night")){
                    return userDatabase.daoAccess().fetchTimsPerNight();
                } else  if (text.equals("Night terrors")){
                    return userDatabase.daoAccess().fetchNightTerrors();
                } else if (text.equals("Falling asleep")){
                    return userDatabase.daoAccess().fetchFallAsleep();
                } else if (text.equals("Waking up")){
                    return userDatabase.daoAccess().fetchWakeUp();
                } else if (text.equals("Fresh")){
                    return userDatabase.daoAccess().fetchFresh();
                } else if (text.equals("Sad")){
                    return userDatabase.daoAccess().fetchSad();
                } else if (text.equals("Sleepy")){
                    return userDatabase.daoAccess().fetchSleepy();
                } else if (text.equals("Tired")){
                    return userDatabase.daoAccess().fetchTired();
                } else if (text.equals("Stressed")){
                    return userDatabase.daoAccess().fetchStressed();
                } else if (text.equals("Irritable")){
                    return userDatabase.daoAccess().fetchIrritable();
                } else if (text.equals("Concentrate")){
                    return userDatabase.daoAccess().ftchConcentrate();
                } else if (text.equals("Coordinate")){
                    return userDatabase.daoAccess().fetchCoordinate();
                } else if (text.equals("Appetite")){
                    return userDatabase.daoAccess().fetchApetite();
                }
                return null;

            }
        }).start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
/*
    public void onBackPressed() {
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        Intent intent = new Intent(getActivity(), MainMenu.class);
        startActivity(intent);
        getActivity().finish();

    }*/
}
