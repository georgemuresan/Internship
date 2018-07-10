package com.example.admin.sleepbetter;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;

public class MainTest extends Activity {
    private SeekbarWithIntervals SeekbarWithIntervals = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);

        List<String> seekbarIntervals = getIntervals();
        getSeekbarWithIntervals().setIntervals(seekbarIntervals);
    }

    private List<String> getIntervals() {
        return new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
            add("10");
        }};
    }

    private SeekbarWithIntervals getSeekbarWithIntervals() {
        if (SeekbarWithIntervals == null) {
            SeekbarWithIntervals = (SeekbarWithIntervals) findViewById(R.id.seekbarWithIntervals);
        }

        return SeekbarWithIntervals;
    }
}
