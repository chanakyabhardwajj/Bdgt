package com.chanakyabhardwaj.bdgt;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c;

        if (ExpenseItemFragment.activeExpense != null) {
            c = ExpenseItemFragment.activeExpense.date;
        } else {
            c = Calendar.getInstance();
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = (DatePickerDialog.OnDateSetListener) getTargetFragment();
        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }
}

