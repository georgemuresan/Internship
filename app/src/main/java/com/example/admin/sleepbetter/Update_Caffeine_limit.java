package com.example.admin.sleepbetter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Update_Caffeine_limit extends Fragment {
    private SeekBarWithIntervals dayReviewBar = null;
    private SeekBarWithIntervals coffeeBar = null;
    private SeekBarWithIntervals cansBar = null;
    private SeekBarWithIntervals energyBar = null;
    View updateView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        updateView = inflater.inflate(R.layout.update_caffeine_limit, container, false);

        List<String> listOne = getIntervals("dayReview");
        getSeekbarWithIntervals("dayReviewBar").setIntervals(listOne);

        List<String> listTwo = getIntervals("coffee");
        getSeekbarWithIntervals("coffee").setIntervals(listTwo);

        List<String> listThree = getIntervals("soda");
        getSeekbarWithIntervals("soda").setIntervals(listThree);

        List<String> listFour = getIntervals("energy");
        getSeekbarWithIntervals("energy").setIntervals(listFour);

        return updateView;
    }

        private List<String> getIntervals(String command) {

            if (command.equals("dayReview")) {
                return new ArrayList<String>() {{
                    add("-2");
                    add("-1");
                    add("0");
                    add("1");
                    add("2");
                }};
            } else  if (command.equals("coffee")) {
                return new ArrayList<String>() {{
                    add("0");
                    add("1");
                    add("2");
                    add("3");
                    add("4");
                    add("5");
                    add("6");
                    add(">6");
                }};
            } else if (command.equals("soda")) {
                return new ArrayList<String>() {{
                    add("0");
                    add("1/2");
                    add("3/4");
                    add("5/6");
                    add("7/8");
                    add("9/10");
                    add(">10");
                }};
            } else if (command.equals("energy")) {
                return new ArrayList<String>() {{
                    add("1");
                    add("2");
                    add("3");
                    add(">3");
                }};
            }
                return null;
        }

        private SeekBarWithIntervals getSeekbarWithIntervals(String name) {

            if (name.equals("dayReviewBar")) {
                if (dayReviewBar == null) {
                    dayReviewBar = (SeekBarWithIntervals) updateView.findViewById(R.id.dayReviewBar);
                }

                return dayReviewBar;
            } else  if (name.equals("coffee")) {
                if (coffeeBar == null) {
                    coffeeBar = (SeekBarWithIntervals) updateView.findViewById(R.id.cupsBar);
                }

                return coffeeBar;
            } else if (name.equals("soda")) {
                if (cansBar == null) {
                    cansBar = (SeekBarWithIntervals) updateView.findViewById(R.id.cansBar);
                }

                return cansBar;
            } else if (name.equals("energy")) {
                if (energyBar == null) {
                    energyBar = (SeekBarWithIntervals) updateView.findViewById(R.id.energyBar);
                }

                return energyBar;
            }
                return null;
        }
    }
