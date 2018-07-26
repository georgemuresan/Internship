package com.example.admin.sleepbetter;

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

    public void save(String user, Boolean isFirstTime) {

        String filePath = "";
        String filePathTwo = "";
        String filePath2 = "";
        try {

            List<UserQuestionnaire> uq = database.daoAccess().fetchUserQuestionnaires();

            filePath = context.getFilesDir().getPath().toString() + "/" + user + "_userQuestionnaire.csv";

            File f = new File(filePath);


            BufferedWriter bw = null;
            bw = new BufferedWriter(new FileWriter(f));
            bw.write("Username, Date, times of waking up per night, nr of night terrors, fall asleep rate, wake up rate, fresh rate, sad rate, sleepy rate, tired rate, stressed rate, irritable rate, concentrate level, coordinate rate, appetite level, mood, \n");

            for (int i = 0; i < uq.size(); i++) {
                bw.append(uq.get(i).getUsername() + ", " + uq.get(i).getDate() + ", " + uq.get(i).getTimesPerNight() +
                        ", " + uq.get(i).getNightTerrors() + ", " + uq.get(i).getFallAsleep() + ", " + uq.get(i).getWakeUp() +
                        ", " + uq.get(i).getFresh() + ", " + uq.get(i).getSad() +
                        ", " + uq.get(i).getSleepy() + ", " + uq.get(i).getTired() + ", " + uq.get(i).getStressed() + ", " +
                        uq.get(i).getIrritable() + ", " + uq.get(i).getConcentrate() + ", " + uq.get(i).getCoordinate() + ", "
                        + uq.get(i).getApetite() + ", " + uq.get(i).getMood() + "\n");
            }

            bw.close();

            if (!isFirstTime){

                List<UserExperiment> ue = database.daoAccess().fetchUserExperiments();

                filePathTwo = context.getFilesDir().getPath().toString() + "/" + user + "_userExperiment.csv";

                File f2 = new File(filePathTwo);


                BufferedWriter bw2 = null;
                bw2 = new BufferedWriter(new FileWriter(f2));
                bw2.write("Username, Date, Experiment, L1 sunlight exposure, L1 half an hour, L1 captures sunlight, L2 app, L2 glasses, L3 bright, L3 TV, C1 when drink, C1 when sleep, C2 cups, C2 cans, C2 energy, C3 drink, C3 empty, S1 when sleep, S1 when wak, S2 when sleep, S2 when wake, S3 relaxed, S3 activity, S4 when sleep, Overall better, \n");

                for (int i = 0; i < ue.size(); i++) {
                    bw2.append(ue.get(i).getUsername() + ", " + ue.get(i).getDate() + ", " + ue.get(i).getExperiment() +
                            ", " + ue.get(i).getLightOneSunlightExposure() + ", " + ue.get(i).getLightOneHalfAnHour() + ", " + ue.get(i).getLightOneCapturesSunlight() +
                            ", " + ue.get(i).getLightTwoApp() + ", " + ue.get(i).getLightTwoGlasses() +
                            ", " + ue.get(i).getLightThreeBright() + ", " + ue.get(i).getLightThreeTV() + ", " + ue.get(i).getCaffeineOneWhenDrink() + ", " +
                            ue.get(i).getCaffeineOneWhenSleep() + ", " + ue.get(i).getCaffeineTwoCups() + ", " + ue.get(i).getCaffeineTwoCans() + ", "
                            + ue.get(i).getCaffeineTwoEnergy() + ", " + ue.get(i).getCaffeineThreeDrink() + ", " + ue.get(i).getCaffeineThreeEmpty() + ", "
                            + ue.get(i).getScheduleOneWhenSleep() + ", " + ue.get(i).getScheduleOneWhenWake() + ", " + ue.get(i).getScheduleTwoWhenSleep() + ", "
                            + ue.get(i).getScheduleTwoWhenWake() + ", " + ue.get(i).getScheduleThreeRelaxed() + ", " + ue.get(i).getScheduleThreeActivity() + ", "
                            + ue.get(i).getScheduleFourWhenSleep() + ", " + ue.get(i).getOverallBetter() + "\n");
                }

                bw2.close();
            }

            List<UserDiary> ud = database.daoAccess().fetchDiary();

            filePath2 = context.getFilesDir().getPath().toString() + "/" + user + "_userDiary.csv";

            File f2 = new File(filePath2);


            BufferedWriter bw2 = null;
            bw2 = new BufferedWriter(new FileWriter(f2));
            bw2.write("Username, Date, Comment, \n");

            for (int i = 0; i < uq.size(); i++) {
                bw2.append(ud.get(i).getUsername() + ", " + ud.get(i).getDate() + ", " + ud.get(i).getComment() + "\n");
            }

            bw2.close();



        } catch (IOException e) {
            e.printStackTrace();
        }

        final String username = "gcm1y18";
        final String password = "Ohmygoals2018";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.soton.ac.uk");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.EnableSSL.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("GC.Muresan@soton.ac.uk"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("GC.Muresan@soton.ac.uk"));
            message.setSubject("Username: " + user + " / First time: " + isFirstTime);
            message.setText("PFA");



            Multipart multipart = new MimeMultipart();


            MimeBodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart = new MimeBodyPart();

            String file = filePath;

            File ff = new File(file);
            String fileName = user + "_userQuestionnaire.csv";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);

            multipart.addBodyPart(messageBodyPart);

            if (!isFirstTime){
                MimeBodyPart messageBodyPartTwo = new MimeBodyPart();

                messageBodyPartTwo = new MimeBodyPart();

                String fileTwo = filePathTwo;

                File ff2 = new File(fileTwo);
                String fileNameTwo = user + "_userExperiment.csv";
                DataSource sourceTwo = new FileDataSource(fileTwo);
                messageBodyPartTwo.setDataHandler(new DataHandler(sourceTwo));
                messageBodyPartTwo.setFileName(fileNameTwo);

                multipart.addBodyPart(messageBodyPartTwo);
            }

            MimeBodyPart messageBodyPartThree = new MimeBodyPart();

            messageBodyPartThree = new MimeBodyPart();

            String fileThree = filePath2;

            File ff3 = new File(fileThree);
            String fileNameThree = user + "_userDiary.csv";
            DataSource source3 = new FileDataSource(fileThree);
            messageBodyPartThree.setDataHandler(new DataHandler(source3));
            messageBodyPartThree.setFileName(fileNameThree);

            multipart.addBodyPart(messageBodyPartThree);



            message.setContent(multipart);

            System.out.println("Sending");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
