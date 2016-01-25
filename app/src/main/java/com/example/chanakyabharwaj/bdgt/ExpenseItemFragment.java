package com.example.chanakyabharwaj.bdgt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
    private Expense activeExpense;

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
        MainActivity.activeExpenseId = -1;
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
        activeExpense.date.set(Calendar.YEAR, year);
        activeExpense.date.set(Calendar.MONTH, monthOfYear);
        activeExpense.date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        expenseDateView.setText(new SimpleDateFormat("dd MMM yyyy").format(activeExpense.date.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        activeExpense.date.set(Calendar.HOUR, hourOfDay);
        activeExpense.date.set(Calendar.MINUTE, minute);
        expenseTimeView.setText(new SimpleDateFormat("hh:mm a").format(activeExpense.date.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void initializeDateView() {
        expenseDateView.setText(new SimpleDateFormat("dd MMM yyyy").format(activeExpense.date.getTime()));
        expenseDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    public void initializeTimeView() {
        expenseTimeView.setText(new SimpleDateFormat("hh:mm a").format(activeExpense.date.getTime()));
        expenseTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
    }

    public void initializeAmountView() {
        if (activeExpense.amount != null) {
            expenseAmountView.setText(activeExpense.amount.toString());
        }
    }

    public void initializeDescriptionView() {
        if (activeExpense.description != null) {
            expenseDescriptionView.setText(activeExpense.description);
        }
    }

    public void initializeCategorySpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ExpenseCategory.categories);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategorySpinner.setAdapter(spinnerArrayAdapter);

        if (activeExpense.category != null) {
            expenseCategorySpinner.setSelection(ExpenseCategory.categories.indexOf(activeExpense.category));
        }
    }

    public void initializeCreateButtonView() {
        if (MainActivity.activeExpenseId != -1) {
            expenseCreateButtonView.setText("Update");
        } else {
            expenseCreateButtonView.setText("Create");
        }

        expenseCreateButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    activeExpense.setAmount(expenseAmountView.getText().toString());
                    activeExpense.setCategory(expenseCategorySpinner.getSelectedItem().toString());
                    activeExpense.setDescription(expenseDescriptionView.getText().toString());

                    if (MainActivity.activeExpenseId != -1) {
                        ExpenseDBHelper.getInstance(getContext()).updateExpense(activeExpense);
                    } else {
                        ExpenseDBHelper.getInstance(getContext()).addExpense(activeExpense);
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
                MainActivity.activeExpenseId = -1;
                activeExpense = null;
                setExpenseListFragment();
            }
        });
    }

    public boolean validateForm() {
        try {
            new BigDecimal(expenseAmountView.getText().toString());
            expenseAmountView.setError(null);
            return true;
        } catch (Exception e) {
            expenseAmountView.setError("Please enter a valid amount.");
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense_item, container, false);

        activeExpense = new Expense();
        if (MainActivity.activeExpenseId != -1) {
            Expense actualExpense = ExpenseDBHelper.getInstance(getContext()).getExpenseById(MainActivity.activeExpenseId);
            activeExpense.setId(actualExpense._id);
            activeExpense.setAmount(actualExpense.amount.toString());
            activeExpense.setDescription(actualExpense.description);
            activeExpense.setCategory(actualExpense.category);
            activeExpense.setDate(actualExpense.date.getTimeInMillis());
        } else {
            activeExpense.setDate(Calendar.getInstance().getTimeInMillis());
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
