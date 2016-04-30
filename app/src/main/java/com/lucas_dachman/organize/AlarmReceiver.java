package com.lucas_dachman.organize;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.provider.Settings;
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
        // TODO: Allow the user to stop the ringtone
        // TODO: Find out how to edit the ringtone properties from EditNoteActivity
    }
}
