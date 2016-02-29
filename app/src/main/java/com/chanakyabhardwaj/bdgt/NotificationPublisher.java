package com.chanakyabhardwaj.bdgt;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.preference.PreferenceManager;

import java.util.Calendar;

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    private static Notification buildNotification(Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(context.getText(R.string.notification_title));
        builder.setContentText(context.getText(R.string.notification_text));
        builder.setSmallIcon(R.drawable.ic_launcher);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // Cancel the notification after its selected

        return notification;
    }

    public static void scheduleNotification(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean alarmSet = sharedPreferences.getBoolean(context.getString(R.string.preferences_alarm_key), false);

        if (alarmSet) {
            Notification notification = buildNotification(context);
            Intent notificationIntent = new Intent(context, NotificationPublisher.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar timeToNotify = Calendar.getInstance();
            timeToNotify.set(Calendar.HOUR_OF_DAY, 20);
            timeToNotify.set(Calendar.MINUTE, 00);

            if (timeToNotify.before(Calendar.getInstance())) {
                timeToNotify.add(Calendar.DAY_OF_MONTH, 1);
            }

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeToNotify.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            Notification notification = buildNotification(context);
            Intent notificationIntent = new Intent(context, NotificationPublisher.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
    }

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }
}