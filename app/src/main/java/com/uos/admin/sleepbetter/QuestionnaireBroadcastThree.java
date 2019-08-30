package com.uos.admin.sleepbetter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static com.uos.admin.sleepbetter.AllPages.nextclass;

public class QuestionnaireBroadcastThree extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {


        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, nextclass);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "13")
                    .setSmallIcon(R.drawable.pillow)
                    .setContentTitle("Questionnaire")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Remember to complete the daily questionnaire if you haven't yet!"))
                    .setContentText("Remember to complete the daily questionnaire if you haven't yet!")
                    .setAutoCancel(false)
                    .setWhen(when)
                    .setOngoing(false)
                    .setContentIntent(pendingIntent);
            notificationManager.notify(22, mNotifyBuilder.build());

    }
    }