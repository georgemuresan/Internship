package com.example.admin.sleepbetter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import android.support.v4.app.NotificationCompat;

public class NotifyService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("HERE I AM!!");

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainMenu.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context,"13")
                .setSmallIcon(R.drawable.ic_menu_manage)
                .setContentTitle("Alarm Fired")
                .setContentText("Events to be Performed").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent);
        notificationManager.notify(13, mNotifyBuilder.build());
//
    }
//
//    public void setAlarm(Context context) {
//        Log.d("Carbon", "Alrm SET !!");
//
//        // get a Calendar object with current time
//        Calendar cal = Calendar.getInstance();
//        // add 30 seconds to the calendar object
//        cal.add(Calendar.SECOND, 30);
//        Intent intent = new Intent(context, NotifyService.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Get the AlarmManager service
//        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
//    }


}