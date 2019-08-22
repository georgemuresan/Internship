package com.uos.admin.sleepbetter;

import java.util.ArrayList;

class HomeCollection {
    public String date="";
    public String experiment="";
    public String overall="";
    public String comment="";


    public static ArrayList<HomeCollection> date_collection_arr;
    public HomeCollection(String date, String experiment, String overall, String comment){

        this.date=date;
        this.experiment=experiment;
        this.overall=overall;
        this.comment = comment;

    }
}
