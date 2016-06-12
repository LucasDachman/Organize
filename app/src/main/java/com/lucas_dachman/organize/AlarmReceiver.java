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
    Note note;

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: Fix the ringtone sound stuff
//        Ringtone ringtone = RingtoneManager.getRingtone(context, Settings.System.DEFAULT_RINGTONE_URI);
//        ringtone.setStreamType(RingtoneManager.TYPE_NOTIFICATION);
//        ringtone.play();
        Toast.makeText(context, "Alarm Received", Toast.LENGTH_SHORT).show();

        // Get corrisponding note
        long noteId = intent.getLongExtra("note_id", 0);
        note = Note.getNote(noteId);

        int requestCode = intent.getExtras().getInt("request code");
        // TODO: Create a notification
        Intent stopRingtoneIntent = new Intent(context, NotificationHandlerFragment.class);
        stopRingtoneIntent.putExtra("buttonAction", "stopRingtone");
        PendingIntent stopRingtonePendingIntent = PendingIntent.getActivity(context, 0, stopRingtoneIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_alarm_white_24dp)
                .setContentTitle(note.getTitle())
                .setContentText(note.getTextShort())
                .addAction(R.mipmap.ic_alarm_white_24dp, "Stop Ringtone", stopRingtonePendingIntent);
        Intent resultIntent = new Intent(context, EditNoteActivity.class);
        resultIntent.putExtra("note_id", noteId);
        resultIntent.putExtra("isNewNote", false);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(requestCode, mBuilder.build());

        String buttonAction = intent.getStringExtra("buttonAction");
        // TODO: Allow the user to stop the ringtone
        // TODO: Find out how to edit the ringtone properties from EditNoteActivity
    }
}
