package com.uos.admin.sleepbetter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        stats += "User ID: " + uq.get(0).getUsername() + "\n " +
                "Date: " + date + "\n  " +
                "How long: " + how_long + "\n  " +
                "Awake: " + awake + "\n  " +
                "Earlier: " + earlier + "\n " +
                "Nights A Week: " + nightsAWeek + "\n " +
                "Quality: " + quality + "\n  " +
                "Impact mood: " + impact_mood + "\n " +
                "Impact activities: " + impact_activities + "\n  " +
                "Impact general: " + impact_general + "\n  " +
                "Problem: " + problem + "\n  " +
                "Mood: " + mood + "\n  " +
                "Demographics: " + demographics + "\n  ";


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

            experimentsList += "Username: " + uq.get(0).getUsername() + "\n " +
                    "Date: " + date2 + "\n  " +
                    "Experiment: " + experiment2 + "\n  " +
                    "L1 sunlight exposure: " + l1_1 + "\n  " +
                    "L1 half an hour: " + l1_2 + "\n  " +
                    "L1 captures sunlight: " + l1_3 + "\n  " +
                    "L2 app: " + l2_1 + "\n  " +
                    "L2 glasses: " + l2_2 + "\n  " +
                    "L3 bright: " + l3_1 + "\n  " +
                    "L3 TV: " + l3_2 + "\n  " +
                    "C1 when drink: " + c1_1 + "\n  " +
                    "C1 when sleep: " + c1_2 + "\n  " +
                    "C2 cups: " + c2_1 + "\n  " +
                    "C2 cans: " + c2_2 + "\n  " +
                    "C2 energy: " + c2_3 + "\n  " +
                    "C3 drink: " + c3_1 + "\n  " +
                    "C3 empty: " + c3_2 + "\n  " +
                    "S1 when sleep: " + s1_1 + "\n  " +
                    "S1 when wake: " + s1_2 + "\n  " +
                    "S2 when sleep: " + s2_1 + "\n  " +
                    "S2 when wake: " + s2_2 + "\n  " +
                    "S3 relaxed: " + s3_1 + "\n  " +
                    "S3 activity: " + s3_2 + "\n  " +
                    "S4 when sleep: " + s4_1 + "\n  " +
                    "Overall better: " + overall_better + "\n  ";


        }

        stats += experimentsList;
        List<UserDiary> ud = database.daoAccess().fetchDiary();


        String date3 = "";
        String comment = "";
        for (int i = 0; i < ud.size(); i++) {
            date3 += String.valueOf(ud.get(i).getDate()) + ", ";
            comment += String.valueOf(ud.get(i).getComment()) + ", ";

        }
        stats += "Date: " + date3 + "\n  " +
                "Comment: " + comment;

        /*
        // Instantiate the RequestQueue.
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        String server_urlpost = "https://hooks.slack.com/services/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"; //Points to target which is obtained from IPV4 Address from IP Config

        final String finalStats = stats;
        StringRequest stringRequestpost = new StringRequest(Request.Method.POST, server_urlpost,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {   //Server Response Handler
                        System.out.println(response);
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {    //On Error Response Handler
                System.out.println("Something went wrong...");
                error.printStackTrace();
                requestQueue.stop();
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                byte[] body = new byte[0];

                String bodyToSend = "{\"text\": \" "+ finalStats +"\"}";
                try {
                    body = bodyToSend.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return body;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<String,String>();
                headers.put("Accept", "application/json");

                return headers;
            }
            @Override
            public String getBodyContentType() {    //Sets type to json
                return "application/json";
            }
        };

        //Starts Request
        requestQueue.add(stringRequestpost);
*/
    }

}
