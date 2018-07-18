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

    private static final String DATABASE_NAME = "movies_db";
    private MovieDatabase movieDatabase;
    private Context context;

    public Report(MovieDatabase movieDatabase, Context context){
        this.movieDatabase = movieDatabase;
        this.context = context;
    }
    public void save() {
        String filePath = "";
       List<Movies> mov =  movieDatabase.daoAccess().fetchMovies();

        try {
            filePath = context.getFilesDir().getPath().toString() + "/coco.csv";

            File f = new File(filePath);



            BufferedWriter bw = null;
            bw = new BufferedWriter(new FileWriter(f));
            bw.write("moveID, movieName\n");

            for (int i =0; i<mov.size(); i++){
                bw.append(mov.get(i).getMovieId() + ", " + mov.get(i).getMovieName() + "\n");
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
            }
            catch (IOException e) {}
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String username = "georgecatalinmuresan@gmail.com";
        final String password = "pas";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.EnableSSL.enable","true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("georgecatalinmuresan@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("georgecatalinmuresan@gmail.com"));
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
