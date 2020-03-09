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

        String agreed = context.getSharedPreferences("consent", MODE_PRIVATE).getString("consent", "nothing");
        String experiments = context.getSharedPreferences("experiments", MODE_PRIVATE).getString("experiments", "N/A");


        String overall_better = "";
        String exprimentProof = "";

        if (!isFirstTime) {

            List<UserExperiment> ue = database.daoAccess().fetchUserExperiments();


            String experiment2 = "";


            for (int i = 0; i < ue.size(); i++) {

                experiment2 = String.valueOf(ue.get(i).getExperiment());

                if (experiment2.equals("L1")){
                    exprimentProof += String.valueOf(ue.get(i).getLightOneSunlightExposure()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getLightOneHalfAnHour()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getLightOneCapturesSunlight()) + ", ";
                } else  if (experiment2.equals("L2")){
                    exprimentProof += String.valueOf(ue.get(i).getLightTwoApp()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getLightTwoGlasses()) + ", ";
                } else if (experiment2.equals("L3")){
                    exprimentProof += String.valueOf(ue.get(i).getLightThreeBright()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getLightThreeTV()) + ", ";
                } else if (experiment2.equals("C1")){
                    exprimentProof += String.valueOf(ue.get(i).getCaffeineOneWhenDrink()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getCaffeineOneWhenSleep()) + ", ";
                } else if (experiment2.equals("C2")){
                    exprimentProof += String.valueOf(ue.get(i).getCaffeineTwoCups()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getCaffeineTwoCans()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getCaffeineTwoEnergy()) + ", ";
                } else if (experiment2.equals("C3")){
                    exprimentProof += String.valueOf(ue.get(i).getCaffeineThreeDrink()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getCaffeineThreeEmpty()) + ", ";
                } else if (experiment2.equals("S1")){
                    exprimentProof += String.valueOf(ue.get(i).getScheduleOneWhenSleep()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getScheduleOneWhenWake()) + ", ";
                } else if (experiment2.equals("S2")){
                    exprimentProof += String.valueOf(ue.get(i).getScheduleTwoWhenSleep()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getScheduleTwoWhenWake()) + ", ";
                } else if (experiment2.equals("S3")){
                    exprimentProof += String.valueOf(ue.get(i).getScheduleThreeRelaxed()) + "-";
                    exprimentProof += String.valueOf(ue.get(i).getScheduleThreeActivity()) + ", ";
                } else if (experiment2.equals("S4")){
                    exprimentProof += String.valueOf(ue.get(i).getScheduleFourWhenSleep()) + ", ";
                }

                overall_better += String.valueOf(ue.get(i).getOverallBetter()) + ", ";
            }


        }

        if (overall_better.equals("")){
            exprimentProof = "N/A";
            overall_better = "N/A";
        }




        List<UserDiary> ud = database.daoAccess().fetchDiary();


        String date3 = "";
        String comment = "";

        String diary = "";

        for (int i = 0; i < ud.size(); i++) {
            diary += String.valueOf(ud.get(i).getDate()) + "-";
            diary += String.valueOf(ud.get(i).getComment()) + ", ";
        }

        if (diary.equals("")){
            diary = "N/A";
        }

        stats += "ANDROID - Participant ID: " + uq.get(0).getUsername() + "\n " +
                "Demographics: " + demographics + "\n  " +
                "Agreed on being contacted: " + agreed + "\n" +
                "Experiment: " + experiments + "\n" +
                "Experiment Proof: " + exprimentProof + "\n" +
                "Feeling compared to yesterday: " + overall_better + "\n" +
                "Fall asleep rate: " + how_long + "\n  " +
                "How long awake rate: " + awake + "\n  " +
                "Earlier wake up rate: " + earlier + "\n " +
                "Nights a week rate: " + nightsAWeek + "\n " +
                "Sleep quality rate: " + quality + "\n  " +
                "Mood rate: " + impact_mood + "\n " +
                "Activities rate: " + impact_activities + "\n  " +
                "Life rate: " + impact_general + "\n  " +
                "Problem rate: " + problem + "\n  " +
                "Overall Mood: " + mood + "\n  " +
                "Goal Diary: " + diary + "\n  ";


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

    }

}
