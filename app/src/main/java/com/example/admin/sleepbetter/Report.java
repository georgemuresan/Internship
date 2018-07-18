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

public class Report {

    private static final String DATABASE_NAME = "user_db";
    private UserDatabase database;
    private Context context;

    public Report(UserDatabase database, Context context) {
        this.database = database;
        this.context = context;
    }

    public void save() {
        String filePath = "";
        List<UserData> mov = database.daoAccess().fetchMovies();

        try {
            filePath = context.getFilesDir().getPath().toString() + "/coco.csv";

            File f = new File(filePath);


            BufferedWriter bw = null;
            bw = new BufferedWriter(new FileWriter(f));
            bw.write("moveID, movieName, \n");

            for (int i = 0; i < mov.size(); i++) {
                bw.append(mov.get(i).getUsername() + ", " + mov.get(i).getDate() + ", " + mov.get(i).getTimesPerNight() +
                        ", " + mov.get(i).getNightTerrors() + ", " + mov.get(i).getFallAsleep() + ", " + mov.get(i).getWakeUp() +
                        ", " + mov.get(i).getFresh() + ", " + mov.get(i).getHappy() + ", " + mov.get(i).getSad() +
                        ", " + mov.get(i).getSleepy() + ", " + mov.get(i).getTired() + ", " + mov.get(i).getStressed() + ", " +
                        mov.get(i).getApetite() + ", " + mov.get(i).getConcentrate() + ", " + mov.get(i).getCoordinate() + ", "
                        + mov.get(i).getIrritable() + "\n");
            }

            bw.close();

            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append("\n");
                }
                br.close();
            } catch (IOException e) {
            }
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String username = "internshipecs18@gmail.com";
        final String password = "ohmygoals2018";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.EnableSSL.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("internshipecs18@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("internshipecs18@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("PFA");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();

            String file = filePath;

            File ff = new File(file);
            System.out.println("EXISTS??????????? " + ff.exists());
            String fileName = "coco.csv";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            System.out.println("Sending");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
