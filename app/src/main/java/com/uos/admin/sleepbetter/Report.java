package com.uos.admin.sleepbetter;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static android.content.Context.MODE_PRIVATE;

public class Report {

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase database;
    private Context context;

    public Report(UserDatabase database, Context context) {
        this.database = database;
        this.context = context;
    }

    public void save(String user, Boolean isFirstTime, String consent) {

        List<UserQuestionnaire> uq = database.daoAccess().fetchUserQuestionnaires();


        String stats = "";

        String date = "";
        String how_long = "";
        String awake = "";
        String earlier = "";
        String nightsAWeek = String.valueOf(uq.get(0).getNightsAWeek());
        String quality = "";
        String impact_mood = "";
        String impact_activities = "";
        String impact_general = "";
        String problem = String.valueOf(uq.get(0).getProblem());
        String mood = "";
        String demographics = context.getSharedPreferences("demographics", MODE_PRIVATE).getString("demographics", "");
        for (int i = 0; i < uq.size(); i++) {
            date += String.valueOf(uq.get(i).getDate()) + ", ";
            how_long += String.valueOf(uq.get(i).getHowLong()) + ", ";
            awake += String.valueOf(uq.get(i).getAwake()) + ", ";
            earlier += String.valueOf(uq.get(i).getEarlier()) + ", ";
            quality += String.valueOf(uq.get(i).getQuality()) + ", ";
            impact_mood += String.valueOf(uq.get(i).getImpactMood()) + ", ";
            impact_activities += String.valueOf(uq.get(i).getImpactActivities()) + ", ";
            impact_general += String.valueOf(uq.get(i).getImpactGeneral()) + ", ";
            mood += String.valueOf(uq.get(i).getMood()) + ", ";
        }
        stats += "Username: " + uq.get(0).getUsername() + "/ " +
                "Date: " + date + "/ " +
                "How long: " + how_long + "/ " +
                "Awake: " + awake + "/ " +
                "Earlier: " + earlier + "/ " +
                "Nights A Week: " + nightsAWeek + "/ " +
                "Quality: " + quality + "/ " +
                "Impact mood: " + impact_mood + "/ " +
                "Impact activities: " + impact_activities + "/ " +
                "Impact general: " + impact_general + "/ " +
                "Problem: " + problem + "/ " +
                "Mood: " + mood + "/ " +
                "Demographics: " + demographics + "/ ";


        String experimentsList = "";

        if (!isFirstTime) {

            List<UserExperiment> ue = database.daoAccess().fetchUserExperiments();


            String date2 = "";
            String experiment2 = "";
            String l1_1 = "";
            String l1_2 = "";
            String l1_3 = "";
            String l2_1 = "";
            String l2_2 = "";
            String l3_1 = "";
            String l3_2 = "";
            String c1_1 = "";
            String c1_2 = "";
            String c2_1 = "";
            String c2_2 = "";
            String c2_3 = "";
            String c3_1 = "";
            String c3_2 = "";
            String s1_1 = "";
            String s1_2 = "";
            String s2_1 = "";
            String s2_2 = "";
            String s3_1 = "";
            String s3_2 = "";
            String s4_1 = "";
            String overall_better = "";

            for (int i = 0; i < ue.size(); i++) {

                date2 += String.valueOf(ue.get(i).getDate()) + ", ";
                experiment2 += String.valueOf(ue.get(i).getExperiment()) + ", ";
                l1_1 += String.valueOf(ue.get(i).getLightOneSunlightExposure()) + ", ";
                l1_2 += String.valueOf(ue.get(i).getLightOneHalfAnHour()) + ", ";
                l1_3 += String.valueOf(ue.get(i).getLightOneCapturesSunlight()) + ", ";
                l2_1 += String.valueOf(ue.get(i).getLightTwoApp()) + ", ";
                l2_2 += String.valueOf(ue.get(i).getLightTwoGlasses()) + ", ";
                l3_1 += String.valueOf(ue.get(i).getLightThreeBright()) + ", ";
                l3_2 += String.valueOf(ue.get(i).getLightThreeTV()) + ", ";
                c1_1 += String.valueOf(ue.get(i).getCaffeineOneWhenDrink()) + ", ";
                c1_2 += String.valueOf(ue.get(i).getCaffeineOneWhenSleep()) + ", ";
                c2_1 += String.valueOf(ue.get(i).getCaffeineTwoCups()) + ", ";
                c2_2 += String.valueOf(ue.get(i).getCaffeineTwoCans()) + ", ";
                c2_3 += String.valueOf(ue.get(i).getCaffeineTwoEnergy()) + ", ";
                c3_1 += String.valueOf(ue.get(i).getCaffeineThreeDrink()) + ", ";
                c3_2 += String.valueOf(ue.get(i).getCaffeineThreeEmpty()) + ", ";
                s1_1 += String.valueOf(ue.get(i).getScheduleOneWhenSleep()) + ", ";
                s1_2 += String.valueOf(ue.get(i).getScheduleOneWhenWake()) + ", ";
                s2_1 += String.valueOf(ue.get(i).getScheduleTwoWhenSleep()) + ", ";
                s2_2 += String.valueOf(ue.get(i).getScheduleTwoWhenWake()) + ", ";
                s3_1 += String.valueOf(ue.get(i).getScheduleThreeRelaxed()) + ", ";
                s3_2 += String.valueOf(ue.get(i).getScheduleThreeActivity()) + ", ";
                s4_1 += String.valueOf(ue.get(i).getScheduleFourWhenSleep()) + ", ";
                overall_better += String.valueOf(ue.get(i).getOverallBetter()) + ", ";
            }

            experimentsList += "Username: " + uq.get(0).getUsername() + "/ " +
                    "Date: " + date2 + "/ " +
                    "Experiment: " + experiment2 + "/ " +
                    "L1 sunlight exposure: " + l1_1 + "/ " +
                    "L1 half an hour: " + l1_2 + "/ " +
                    "L1 captures sunlight: " + l1_3 + "/ " +
                    "L2 app: " + l2_1 + "/ " +
                    "L2 glasses: " + l2_2 + "/ " +
                    "L3 bright: " + l3_1 + "/ " +
                    "L3 TV: " + l3_2 + "/ " +
                    "C1 when drink: " + c1_1 + "/ " +
                    "C1 when sleep: " + c1_2 + "/ " +
                    "C2 cups: " + c2_1 + "/ " +
                    "C2 cans: " + c2_2 + "/ " +
                    "C2 energy: " + c2_3 + "/ " +
                    "C3 drink: " + c3_1 + "/ " +
                    "C3 empty: " + c3_2 + "/ " +
                    "S1 when sleep: " + s1_1 + "/ " +
                    "S1 when wake: " + s1_2 + "/ " +
                    "S2 when sleep: " + s2_1 + "/ " +
                    "S2 when wake: " + s2_2 + "/ " +
                    "S3 relaxed: " + s3_1 + "/ " +
                    "S3 activity: " + s3_2 + "/ " +
                    "S4 when sleep: " + s4_1 + "/ " +
                    "Overall better: " + overall_better + "/ ";


        }

        stats += experimentsList;
        List<UserDiary> ud = database.daoAccess().fetchDiary();


        String date3 = "";
        String comment = String.valueOf(uq.get(0).getProblem());
        for (int i = 0; i < ud.size(); i++) {
            date3 += String.valueOf(ud.get(i).getDate()) + ", ";
            comment += String.valueOf(ud.get(i).getComment()) + ", ";

        }
        stats += "Date: " + date3 + "/ " +
                "Comment: " + comment;


    }

}
