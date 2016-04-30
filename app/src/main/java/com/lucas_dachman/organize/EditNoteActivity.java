package com.lucas_dachman.organize;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class EditNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText title_edit;
    EditText text_edit;
    Note note;
    boolean isNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title_edit = (EditText) findViewById(R.id.editText_title);
        text_edit = (EditText) findViewById(R.id.editText_text);

        isNewNote = getIntent().getBooleanExtra("isNewNote", true);
        if(isNewNote)
            note = new Note();
        else {
            long note_id = getIntent().getLongExtra("note_id", 0);
            note = Note.getNote(note_id);
            title_edit.setText(note.getTitle());
            text_edit.setText(note.getText());
        }

        if(note.getInitAlarmTime() != 0L) {
            TextView alarmDateText = (TextView) findViewById(R.id.textView_alarmDate);
            long noteAlarmDate = note.getInitAlarmTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy \nHH:mm a", Locale.getDefault());
            String str = sdf.format(new Date(noteAlarmDate));
            alarmDateText.setText(str);
        }

    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this, "Date Picked", Toast.LENGTH_SHORT).show();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(note.getInitAlarmTime());
        cal.set(year, monthOfYear, dayOfMonth);
        long date = cal.getTimeInMillis();
        note.setInitAlarmTime(date);

        showTimePickerDialog();

        // TODO: remove ringtone button when date or time cancel button is presses

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(this, "Time Picked", Toast.LENGTH_SHORT).show();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(note.getInitAlarmTime());
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        long date = cal.getTimeInMillis();
        note.setInitAlarmTime(date);

        note.setAlarm(getApplicationContext());

        // Add ringtone button
        RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.edit_note_main_layout);
        Button ringtoneButton = new Button(this);
        ringtoneButton.setText("Change Ringtone");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT );
        layoutParams.addRule(RelativeLayout.BELOW, R.id.button_addReminder);
        ringtoneButton.setLayoutParams(layoutParams);
        ringtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Todo: Add code to set ringtone for this note alarm
            }
        });
        mLayout.addView(ringtoneButton);
    }

    // TODO: look for onDismissListener http://stackoverflow.com/questions/4724781/timepickerdialog-cancel-button

    public void onAddReminderClick(View view) {
        showDatePickerDialog();


    }

    // onClick for "Save"  button
    public void saveNote(View view) {

        note.setText(text_edit.getText().toString());
        note.setTitle(title_edit.getText().toString());
        note.setDateCreated(System.currentTimeMillis());

//        if(isNewNote) {
//            // Clear EditTexts
//            title_edit.setText("");
//            text_edit.setText("");
//        }



    }

}
