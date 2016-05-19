package com.lucas_dachman.organize;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

/**
 * Created by Lucas on 4/18/2016.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Ringtone ringtone = RingtoneManager.getRingtone(context, Settings.System.DEFAULT_RINGTONE_URI);
        ringtone.setStreamType(RingtoneManager.TYPE_NOTIFICATION);
        ringtone.play();
        Toast.makeText(context, "Alarm Received", Toast.LENGTH_SHORT).show();

        // TODO: Create a notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_alarm_white_24dp)
                .setContentTitle("Organize Notification")
                .setContentText("This is a notification");
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int requestCode = intent.getExtras().getInt("request code");
        notificationManager.notify(requestCode, mBuilder.build());


        // TODO: Allow the user to stop the ringtone
        // TODO: Find out how to edit the ringtone properties from EditNoteActivity
    }
}
