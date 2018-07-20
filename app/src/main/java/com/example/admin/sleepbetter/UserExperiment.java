package com.example.admin.sleepbetter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class UserExperiment {
    @NonNull
    @PrimaryKey
    private String date;

    private String username;
    private String experiment;
    private String lightOneSunlightExposure;
    private String lightOneHalfAnHour;
    private String lightOneCapturesSunlight;
    private String lightTwoApp;
    private String lightTwoGlasses;
    private String lightThreeBright;
    private String lightThreeTV;
    private String caffeineOneWhenDrink;
    private String caffeineOneWhenSleep;
    private int caffeineTwoCups;
    private int caffeineTwoCans;
    private int caffeineTwoEnergy;
    private String caffeineThreeDrink;
    private String caffeineThreeEmpty;
    private String scheduleOneWhenSleep;
    private String scheduleOneWhenWake;
    private String scheduleTwoWhenSleep;
    private String scheduleTwoWhenWake;
    private String scheduleThreeRelaxed;
    private String scheduleThreeActivity;
    private String scheduleFourWhenSleep;
    private int overallBetter;

    public UserExperiment() {
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


    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }


    public String getLightOneSunlightExposure() {
        return lightOneSunlightExposure;
    }

    public void setLightOneSunlightExposure(String lightOneSunlightExposure) { this.lightOneSunlightExposure = lightOneSunlightExposure; }


    public String getLightOneHalfAnHour() {
        return lightOneHalfAnHour;
    }

    public void setLightOneHalfAnHour(String lightOneHalfAnHour) { this.lightOneHalfAnHour = lightOneHalfAnHour; }

    public String getLightOneCapturesSunlight() {
        return lightOneCapturesSunlight;
    }

    public void setLightOneCapturesSunlight(String lightOneCapturesSunlight) { this.lightOneCapturesSunlight = lightOneCapturesSunlight; }

    public String getLightTwoApp() {
        return lightTwoApp;
    }

    public void setLightTwoApp(String lightTwoApp) {
        this.lightTwoApp = lightTwoApp;
    }

    public String getLightTwoGlasses() {
        return lightTwoGlasses;
    }

    public void setLightTwoGlasses(String lightTwoGlasses) {  this.lightTwoGlasses = lightTwoGlasses; }

    public String getLightThreeBright() {
        return lightThreeBright;
    }

    public void setLightThreeBright(String lightThreeBright) { this.lightThreeBright = lightThreeBright;  }

    public String getLightThreeTV() {
        return lightThreeTV;
    }

    public void setLightThreeTV(String lightThreeTV) {
        this.lightThreeTV = lightThreeTV;
    }

    public String getCaffeineOneWhenDrink() {
        return caffeineOneWhenDrink;
    }

    public void setCaffeineOneWhenDrink(String caffeineOneWhenDrink) { this.caffeineOneWhenDrink = caffeineOneWhenDrink; }

    public String getCaffeineOneWhenSleep() {
        return caffeineOneWhenSleep;
    }

    public void setCaffeineOneWhenSleep(String caffeineOneWhenSleep) { this.caffeineOneWhenSleep = caffeineOneWhenSleep; }

    public int getCaffeineTwoCups() {
        return caffeineTwoCups;
    }

    public void setCaffeineTwoCups(int caffeineTwoCups) {
        this.caffeineTwoCups = caffeineTwoCups;
    }


    public int getCaffeineTwoCans() {
        return caffeineTwoCans;
    }

    public void setCaffeineTwoCans(int caffeineTwoCans) {
        this.caffeineTwoCans = caffeineTwoCans;
    }


    public int getCaffeineTwoEnergy() {
        return caffeineTwoEnergy;
    }

    public void setCaffeineTwoEnergy(int caffeineTwoEnergy) { this.caffeineTwoEnergy = caffeineTwoEnergy; }


    public String getCaffeineThreeDrink() {
        return caffeineThreeDrink;
    }

    public void setCaffeineThreeDrink(String caffeineThreeDrink) {  this.caffeineThreeDrink = caffeineThreeDrink; }

    public String getCaffeineThreeEmpty() {
        return caffeineThreeEmpty;
    }

    public void setCaffeineThreeEmpty(String caffeineThreeEmpty) {  this.caffeineThreeEmpty = caffeineThreeEmpty; }

    public String getScheduleOneWhenSleep() {
        return scheduleOneWhenSleep;
    }

    public void setScheduleOneWhenSleep(String scheduleOneWhenSleep) { this.scheduleOneWhenSleep = scheduleOneWhenSleep; }

    public String getScheduleOneWhenWake() {
        return scheduleOneWhenWake;
    }

    public void setScheduleOneWhenWake(String scheduleOneWhenWake) { this.scheduleOneWhenWake = scheduleOneWhenWake; }

    public String getScheduleTwoWhenSleep() {
        return scheduleTwoWhenSleep;
    }

    public void setScheduleTwoWhenSleep(String scheduleTwoWhenSleep) { this.scheduleTwoWhenSleep = scheduleTwoWhenSleep; }

    public String getScheduleTwoWhenWake() {
        return scheduleTwoWhenWake;
    }

    public void setScheduleTwoWhenWake(String scheduleTwoWhenWake) {  this.scheduleTwoWhenWake = scheduleTwoWhenWake; }

    public String getScheduleThreeRelaxed() { return scheduleThreeRelaxed; }

    public void setScheduleThreeRelaxed(String scheduleThreeRelaxed) {  this.scheduleThreeRelaxed = scheduleThreeRelaxed; }

    public String getScheduleThreeActivity() {
        return scheduleThreeActivity;
    }

    public void setScheduleThreeActivity(String scheduleThreeActivity) { this.scheduleThreeActivity = scheduleThreeActivity; }

    public String getScheduleFourWhenSleep() {
        return scheduleFourWhenSleep;
    }

    public void setScheduleFourWhenSleep(String scheduleFourWhenSleep) { this.scheduleFourWhenSleep = scheduleFourWhenSleep; }

    public int getOverallBetter() { return overallBetter; }

    public void setOverallBetter(int overallBetter) { this.overallBetter = overallBetter; }
}