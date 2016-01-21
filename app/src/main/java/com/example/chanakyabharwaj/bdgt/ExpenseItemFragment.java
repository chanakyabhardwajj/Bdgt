package com.example.chanakyabharwaj.bdgt;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ExpenseItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    private Calendar expenseDateTime;
    private TextView expenseDateView;
    private TextView expenseTimeView;
    private EditText expenseAmountView;
    private Spinner expenseCategorySpinner;
    private EditText expenseDescriptionView;
    private Button expenseCreateButtonView;
    private Button expenseCancelButtonView;

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
        expenseDateTime.set(Calendar.YEAR, year);
        expenseDateTime.set(Calendar.MONTH, monthOfYear);
        expenseDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        expenseDateView.setText(new SimpleDateFormat("dd MMM yyyy").format(expenseDateTime.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        expenseDateTime.set(Calendar.HOUR, hourOfDay);
        expenseDateTime.set(Calendar.MINUTE, minute);
        expenseTimeView.setText(new SimpleDateFormat("hh:mm a").format(expenseDateTime.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //
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

    public void initializeCategorySpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ExpenseCategory.categories); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategorySpinner.setAdapter(spinnerArrayAdapter);
    }

    public void initializeCreateButtonView() {
        expenseCreateButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseStore.add(new Expense(expenseCategorySpinner.getSelectedItem().toString(),
                        new BigDecimal(expenseAmountView.getText().toString()),
                        expenseDateTime.getTimeInMillis(), 1, expenseDescriptionView.getText().toString()));
                setExpenseListFragment();
            }
        });
    }

    public void initializeCancelButtonView() {
        expenseCancelButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setExpenseListFragment();
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
        expenseCategorySpinner = (Spinner) rootView.findViewById(R.id.expense_category);
        expenseDescriptionView = (EditText) rootView.findViewById(R.id.expense_description);
        expenseCreateButtonView = (Button) rootView.findViewById(R.id.create_expense);
        expenseCancelButtonView = (Button) rootView.findViewById(R.id.cancel_expense);

        initializeDateView();
        initializeTimeView();
        initializeCategorySpinner();
        initializeCreateButtonView();
        initializeCancelButtonView();

        return rootView;
    }
}
