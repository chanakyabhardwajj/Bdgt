package com.example.chanakyabharwaj.bdgt;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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


public class ExpenseItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    //Data
    private Calendar expenseDateTime;

    //Views
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

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    void setExpenseListFragment() {
        closeKeyboard(getActivity(), expenseAmountView.getWindowToken());
        MainActivity.activeExpense = null;
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
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    public void initializeAmountView() {
        if (MainActivity.activeExpense != null) {
            expenseAmountView.setText(MainActivity.activeExpense.amount.toString());
        }

    }

    public void initializeDescriptionView() {
        if (MainActivity.activeExpense != null) {
            expenseDescriptionView.setText(MainActivity.activeExpense.description);
        }
    }

    public void initializeCategorySpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ExpenseCategory.categories);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategorySpinner.setAdapter(spinnerArrayAdapter);

        if (MainActivity.activeExpense != null) {
            expenseCategorySpinner.setSelection(ExpenseCategory.categories.indexOf(MainActivity.activeExpense.category));
        }
    }

    public void initializeCreateButtonView() {
        if (MainActivity.activeExpense != null) {
            expenseCreateButtonView.setText("Update");
        } else {
            expenseCreateButtonView.setText("Create");
        }
        expenseCreateButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    if (MainActivity.activeExpense != null) {
                        MainActivity.activeExpense.amount = new BigDecimal(expenseAmountView.getText().toString());
                        MainActivity.activeExpense.category = expenseCategorySpinner.getSelectedItem().toString();
                        MainActivity.activeExpense.date = expenseDateTime.getTimeInMillis();
                        MainActivity.activeExpense.description = expenseDescriptionView.getText().toString();
                    } else {
                        ExpenseStore.add(new Expense(expenseCategorySpinner.getSelectedItem().toString(),
                                        new BigDecimal(expenseAmountView.getText().toString()),
                                        expenseDateTime.getTimeInMillis(),
                                        expenseDescriptionView.getText().toString())
                        );
                    }
                    setExpenseListFragment();
                }
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

    public boolean validateForm() {
        boolean valid = true;
        if (expenseAmountView.getText().toString().length() == 0) {
            valid = false;
            expenseAmountView.setError("Please enter the amount");
        } else {
            expenseAmountView.setError(null);
        }
        return valid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense_item, container, false);

        expenseDateTime = Calendar.getInstance();
        if (MainActivity.activeExpense != null) {
            expenseDateTime.setTimeInMillis(MainActivity.activeExpense.date);
        }

        expenseDateView = (TextView) rootView.findViewById(R.id.expense_date);
        expenseTimeView = (TextView) rootView.findViewById(R.id.expense_time);
        expenseAmountView = (EditText) rootView.findViewById(R.id.expense_amount);
        expenseCategorySpinner = (Spinner) rootView.findViewById(R.id.expense_category);
        expenseDescriptionView = (EditText) rootView.findViewById(R.id.expense_description);
        expenseCreateButtonView = (Button) rootView.findViewById(R.id.create_expense);
        expenseCancelButtonView = (Button) rootView.findViewById(R.id.cancel_expense);

        initializeDateView();
        initializeTimeView();
        initializeAmountView();
        initializeCategorySpinner();
        initializeDescriptionView();
        initializeCreateButtonView();
        initializeCancelButtonView();

        return rootView;
    }
}
