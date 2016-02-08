package com.chanakyabhardwaj.bdgt;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by chanakya.bharwaj on 18/01/16.
 */
public class DatePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = (DatePickerDialog.OnDateSetListener)getTargetFragment();
        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }
}

