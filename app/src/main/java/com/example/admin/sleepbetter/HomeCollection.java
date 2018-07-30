package com.example.admin.sleepbetter;

import java.util.ArrayList;

class HomeCollection {
    public String date="";
    public String experiment="";
    public String overall="";
    public String proof="";
    public String comment="";


    public static ArrayList<HomeCollection> date_collection_arr;
    public HomeCollection(String date, String experiment, String overall, String proof, String comment){

        this.date=date;
        this.experiment=experiment;
        this.overall=overall;
        this.proof= proof;
        this.comment = comment;

    }
}
