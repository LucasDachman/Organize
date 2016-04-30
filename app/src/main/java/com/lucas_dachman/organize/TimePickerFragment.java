package com.lucas_dachman.organize;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Lucas on 4/16/2016.
 */
public class TimePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use current time for default values
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create new TimePickerDialog
        return new TimePickerDialog(getContext(), (TimePickerDialog.OnTimeSetListener)getActivity(), hour, minute, false);
    }
}
