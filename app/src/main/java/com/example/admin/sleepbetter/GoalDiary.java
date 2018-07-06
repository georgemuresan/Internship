package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GoalDiary extends Fragment {

    View goalDiaryView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        goalDiaryView = inflater.inflate(R.layout.goal_diary, container, false);

        return goalDiaryView;
    }
}
