package com.uos.admin.sleepbetter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> sections = new ArrayList<Fragment>();
    private final List<String> sectionsTitles = new ArrayList<String>();


    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return sections.get(position);
    }

    @Override
    public int getCount() {
        return sectionsTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return sectionsTitles.get(position);
    }

    public void addSection(Fragment section, String title){

        sections.add(section);
        sectionsTitles.add(title);

    }


}
