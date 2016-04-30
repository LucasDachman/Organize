package com.lucas_dachman.organize;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;


/**
 * Created by Lucas on 3/14/2016.
 */

public class Note extends RealmObject {

    private String title;
    private String text;
    @PrimaryKey
    private long dateCreated;       // TODO: Date is depricated and retarded. Change to long milis
    private long initAlarmTime;
    private int alarmType;
    private boolean repeatsSunday = false;
    private boolean repeatsMonday = false;
    private boolean repeatsTuesday = false;
    private boolean repeatsWednesday = false;
    private boolean repeatsThursday = false;
    private boolean repeatsFriday = false;
    private boolean repeatsSaturday = false;

    @Ignore
    public static final int ALARM_TYPE_NONE = -1;

    public Note(String _title, String _text) {
        title = _title;
        text = _text;
        dateCreated = System.currentTimeMillis();
        alarmType = ALARM_TYPE_NONE;
    }

    public Note(){
        title = "title";
        text = "text";
        dateCreated = System.currentTimeMillis();
        alarmType = ALARM_TYPE_NONE;
    }

    public long getDateCreated() { return dateCreated; }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String text) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.text = text;
        realm.commitTransaction();
    }

    public void setTitle(String title) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.title = title;
        realm.commitTransaction();
    }

    public void setDateCreated(long t) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.dateCreated = t;
        realm.commitTransaction();
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.alarmType = alarmType;
        realm.commitTransaction();
    }

    public long getInitAlarmTime() {
        return initAlarmTime;
    }

    public void setInitAlarmTime(long initAlarmTime) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        this.initAlarmTime = initAlarmTime;
        realm.commitTransaction();
    }

    public void createNewAlarm(long _initAlarmTime, int _alarmType, ArrayList<String> daysOfWeekRepeated ) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        initAlarmTime = _initAlarmTime;
        alarmType = _alarmType;
        if(daysOfWeekRepeated.contains("Sunday")) {
            repeatsSunday = true;
        }
        if(daysOfWeekRepeated.contains("Monday")) {
            repeatsMonday = true;
        }
        if(daysOfWeekRepeated.contains("Tuesday")) {
            repeatsTuesday = true;
        }
        if(daysOfWeekRepeated.contains("Wednesday")) {
            repeatsWednesday = true;
        }
        if(daysOfWeekRepeated.contains("Thursday")) {
            repeatsThursday = true;
        }
        if(daysOfWeekRepeated.contains("Friday")) {
            repeatsFriday = true;
        }
        if(daysOfWeekRepeated.contains("Saturday")) {
            repeatsSaturday = true;
        }
        realm.commitTransaction();
    }

    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, initAlarmTime, alarmIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, initAlarmTime, alarmIntent);
        }
    }

    public String toString() {

        String str = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateCreated);

        str = title + ", " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.DAY_OF_MONTH)
                + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" +
                calendar.get(Calendar.SECOND);
        return str;
    }

    // Static Methods

    public static void saveNote(Context context, Note note) {

        Realm realm = Realm.getDefaultInstance();
        // begin transaction
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(note);
        // commit transaction
        realm.commitTransaction();
        realm.close();

        Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show();
    }

    public static Note getNote(long _note_id) {
        // TODO: Handle when note is not found
        Note n;
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Note> query = realm.where(Note.class);
        query.equalTo("dateCreated", _note_id);
        n = query.findAll().get(0);

        return n;

    }

}
