package com.example.admin.sleepbetter;

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
    private int timesPerNight;
    private int nightTerrors;
    private int fallAsleep;
    private int wakeUp;
    private int fresh;
    private int sad;
    private int sleepy;
    private int tired;
    private int stressed;
    private int apetite;
    private int concentrate;
    private int coordinate;
    private int irritable;

    @ColumnInfo(name="mood_all")
    private int mood;

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

    public int getTimesPerNight() {
        return timesPerNight;
    }

    public void setTimesPerNight(int timesPerNight) {
        this.timesPerNight = timesPerNight;
    }


    public int getNightTerrors() {
        return nightTerrors;
    }

    public void setNightTerrors(int nightTerrors) {
        this.nightTerrors = nightTerrors;
    }


    public int getFallAsleep() {
        return fallAsleep;
    }

    public void setFallAsleep(int fallAsleep) {
        this.fallAsleep = fallAsleep;
    }

    public int getWakeUp() {
        return wakeUp;
    }

    public void setWakeUp(int wakeUp) {
        this.wakeUp = wakeUp;
    }

    public int getFresh() {
        return fresh;
    }

    public void setFresh(int fresh) {
        this.fresh = fresh;
    }

    public int getSad() {
        return sad;
    }

    public void setSad(int sad) {
        this.sad = sad;
    }

    public int getSleepy() {
        return sleepy;
    }

    public void setSleepy(int sleepy) {
        this.sleepy = sleepy;
    }

    public int getTired() {
        return tired;
    }

    public void setTired(int tired) {
        this.tired = tired;
    }

    public int getStressed() {
        return stressed;
    }

    public void setStressed(int stressed) {
        this.stressed = stressed;
    }

    public int getApetite() {
        return apetite;
    }

    public void setApetite(int apetite) {
        this.apetite = apetite;
    }


    public int getConcentrate() {
        return concentrate;
    }

    public void setConcentrate(int concentrate) {
        this.concentrate = concentrate;
    }


    public int getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }


    public int getIrritable() {
        return irritable;
    }

    public void setIrritable(int irritable) {
        this.irritable = irritable;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getNumberID() {
        return numberID;
    }

    public void setNumberID(int numberID) {
        this.numberID = numberID;
    }
}