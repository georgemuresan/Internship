package com.example.admin.sleepbetter;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;

public class Seek extends Activity {
    private SeekBarWithIntervals SeekbarWithIntervals = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekbar);

        List<String> seekbarIntervals = getIntervals();
        getSeekbarWithIntervals().setIntervals(seekbarIntervals);
    }

    private List<String> getIntervals() {
        return new ArrayList<String>() {{
            add("1");
            add("aaa");
            add("3");
            add("bbb");
            add("5");
            add("ccc");
            add("7");
            add("ddd");
            add("9");
        }};
    }

    private SeekBarWithIntervals getSeekbarWithIntervals() {
        if (SeekbarWithIntervals == null) {
            SeekbarWithIntervals = (SeekBarWithIntervals) findViewById(R.id.seekbarWithIntervals);
        }

        return SeekbarWithIntervals;
    }
}
