package com.chanakyabhardwaj.bdgt;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c;

        if (ExpenseItemFragment.activeExpense != null) {
            c = ExpenseItemFragment.activeExpense.date;
        } else {
            c = Calendar.getInstance();
        }

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = (TimePickerDialog.OnTimeSetListener) getTargetFragment();
        return new TimePickerDialog(getActivity(), timeSetListener, hour, minute, true);
    }
}
