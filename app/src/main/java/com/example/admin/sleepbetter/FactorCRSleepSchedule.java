package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FactorCRSleepSchedule extends Fragment{

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.factors_cr_schedule, container, false);

        Button button = (Button) myView.findViewById(R.id.moreInfo);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                goHelpPage();

            }

        });

        return myView;
    }

    private void goHelpPage() {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new Help()).commit();

    }
}
