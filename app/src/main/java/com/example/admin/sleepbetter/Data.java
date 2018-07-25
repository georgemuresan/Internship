package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static android.content.Context.MODE_PRIVATE;

public class Data extends Fragment {
    private UserDatabase userDatabase;
    private static final String DATABASE_NAME = "user_db";
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

                GraphView graph = (GraphView) dataView.findViewById(R.id.graph);
                userDatabase = Room.databaseBuilder(getActivity().getApplicationContext(), UserDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                userDatabase.daoAccess().deleteUserExperimentTable();
                DataPoint[] dp = new DataPoint[userDatabase.daoAccess().fetchMoods().size()];
                System.out.println(userDatabase.daoAccess().fetchUserQuestionnaires().size());
                System.out.println(userDatabase.daoAccess().fetchMoods().size());
                for(int i=0; i<userDatabase.daoAccess().fetchMoods().size(); i++){
                    System.out.println(i + "    " + userDatabase.daoAccess().fetchMoods().get(i));
                    dp[i] = new DataPoint(i, userDatabase.daoAccess().fetchMoods().get(i));
                }

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
                graph.addSeries(series);

            }
        }).start();

        return dataView;
    }
/*
    public void onBackPressed() {
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
        Intent intent = new Intent(getActivity(), MainMenu.class);
        startActivity(intent);
        getActivity().finish();

    }*/
}
