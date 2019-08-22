package com.uos.admin.sleepbetter;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class UserQuestionnaire {
 //   @NonNull
  //  @PrimaryKey
  //  private String date;

  //  private String username;

    @PrimaryKey(autoGenerate = true)
    private int numberID;


    private String username;

    private String date;
    private int howLong;
    private int awake;
    private int earlier;
    private int nightsAWeek;
    private int quality;
    private int impactMood;
    private int impactActivities;
    private int impactGeneral;
    private int problem;

    @ColumnInfo(name="mood_all")
    private double mood;

    public UserQuestionnaire() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAwake() {
        return awake;
    }

    public void setAwake(int awake) {
        this.awake = awake;
    }


    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }


    public int getEarlier() {
        return earlier;
    }

    public void setEarlier(int earlier) {
        this.earlier = earlier;
    }

    public int getNightsAWeek() { return nightsAWeek; }

    public void setNightsAWeek(int nightsAWeek) {
        this.nightsAWeek = nightsAWeek;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getImpactMood() {
        return impactMood;
    }

    public void setImpactMood(int impactMood) {
        this.impactMood = impactMood;
    }

    public int getImpactActivities() {
        return impactActivities;
    }

    public void setImpactActivities(int impactActivities) {
        this.impactActivities = impactActivities;
    }

    public int getImpactGeneral() {
        return impactGeneral;
    }

    public void setImpactGeneral(int impactGeneral) {
        this.impactGeneral = impactGeneral;
    }

    public int getProblem() {
        return problem;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public double getMood() {
        return mood;
    }

    public void setMood(double mood) {
        this.mood = mood;
    }

    public int getNumberID() {
        return numberID;
    }

    public void setNumberID(int numberID) {
        this.numberID = numberID;
    }
}