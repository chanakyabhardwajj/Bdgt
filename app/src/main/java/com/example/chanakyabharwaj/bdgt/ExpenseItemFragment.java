package com.example.chanakyabharwaj.bdgt;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ExpenseItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Calendar expenseDateTime;
    private TextView expenseDateView;
    private TextView expenseTimeView;
    private EditText expenseAmountView;
    private EditText expenseDescriptionView;

    public ExpenseItemFragment() {
        // Required empty public constructor
    }

    void setExpenseListFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ExpenseListFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFrag = new DatePickerFragment();
        datePickerFrag.setTargetFragment(ExpenseItemFragment.this, 0);
        datePickerFrag.show(getFragmentManager(), "Datepicker");
    }

    public void showTimePickerDialog() {
        TimePickerFragment timePickerFrag = new TimePickerFragment();
        timePickerFrag.setTargetFragment(ExpenseItemFragment.this, 0);
        timePickerFrag.show(getFragmentManager(), "Timepicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.expenseDateTime.set(Calendar.YEAR, year);
        this.expenseDateTime.set(Calendar.MONTH, monthOfYear);
        this.expenseDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        expenseDateView.setText(new SimpleDateFormat("dd MMM yyyy").format(expenseDateTime.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.expenseDateTime.set(Calendar.HOUR, hourOfDay);
        this.expenseDateTime.set(Calendar.MINUTE, minute);
        expenseTimeView.setText(new SimpleDateFormat("hh:mm a").format(expenseDateTime.getTime()));
    }

    public void initializeDateView() {
        expenseDateView.setText(new SimpleDateFormat("dd MMM yyyy").format(expenseDateTime.getTime()));
        expenseDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    public void initializeTimeView() {
        expenseTimeView.setText(new SimpleDateFormat("hh:mm a").format(expenseDateTime.getTime()));
        expenseTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense_item, container, false);

        expenseDateTime = Calendar.getInstance();
        expenseDateView = (TextView) rootView.findViewById(R.id.expense_date);
        expenseTimeView = (TextView) rootView.findViewById(R.id.expense_time);
        expenseAmountView = (EditText) rootView.findViewById(R.id.expense_amount);
        expenseDescriptionView = (EditText) rootView.findViewById(R.id.expense_description);

        this.initializeDateView();
        this.initializeTimeView();

        Button createExpenseButton = (Button) rootView.findViewById(R.id.create_expense);
        createExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseStore.add(new Expense(ExpenseCategory.BREAKFAST,
                        new BigDecimal(expenseAmountView.getText().toString()),
                        expenseDateTime.getTimeInMillis(), 1, expenseDescriptionView.getText().toString()));
                setExpenseListFragment();
            }
        });

        Button cancelExpenseButton = (Button) rootView.findViewById(R.id.cancel_expense);
        cancelExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setExpenseListFragment();
            }
        });

        return rootView;
    }
}
