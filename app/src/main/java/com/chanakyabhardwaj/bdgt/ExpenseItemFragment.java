package com.chanakyabhardwaj.bdgt;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;


public class ExpenseItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    public static Expense activeExpense;

    //Views
    private TextView expenseDateView;
    private TextView expenseTimeView;
    private EditText expenseAmountView;
    private TagContainerLayout expenseCategoryTags;
    private EditText expenseCategoryView;
    private EditText expenseDescriptionView;
    private TextView expenseCreateButtonView;
    private TextView expenseCancelButtonView;
    private TextView expenseDeleteButtonView;

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
        activeExpense = null;

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, new ExpenseListFragment());
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
        activeExpense.date.set(Calendar.HOUR_OF_DAY, hourOfDay);
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
        expenseDateView.setText(new SimpleDateFormat("dd MMM").format(activeExpense.date.getTime()));
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

    public void initializeCategoryView() {
        ArrayList<String> categories = ExpenseDBHelper.getInstance(getContext()).getAllCategories();
        if (activeExpense.category != null) {
            expenseCategoryView.setText(activeExpense.category);
        }

        expenseCategoryTags.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                expenseCategoryView.setText(text);
                expenseCategoryView.setSelection(expenseCategoryView.getText().length());
            }

            @Override
            public void onTagLongClick(final int position, String text) {
            }
        });

        expenseCategoryTags.setTags(categories);
    }


    public void initializeCreateButtonView() {
        if (MainActivity.activeExpenseId != -1) {
            expenseCreateButtonView.setText(R.string.update_expense_button);
        } else {
            expenseCreateButtonView.setText(R.string.create_expense_button);
        }

        expenseCreateButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrUpdateExpense();
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

    public void initializeDeleteButtonView() {
        if (MainActivity.activeExpenseId != -1) {
            expenseDeleteButtonView.setVisibility(View.VISIBLE);

            expenseDeleteButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle(R.string.ask_expense_delete);
                    builder.setPositiveButton(R.string.yes_expense_delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ExpenseDBHelper.getInstance(getContext()).deleteExpenseById(MainActivity.activeExpenseId);
                            setExpenseListFragment();
                        }
                    });
                    builder.create().show();
                }
            });
        } else {
            expenseDeleteButtonView.setVisibility(View.GONE);
        }

    }

    public boolean validateForm() {
        try {
            BigDecimal amount = new BigDecimal(expenseAmountView.getText().toString());
            if (amount.equals(BigDecimal.ZERO)) {
                expenseAmountView.setError(getText(R.string.expense_form_amount_error));
                return false;
            }
            expenseAmountView.setError(null);
            return true;
        } catch (Exception e) {
            expenseAmountView.setError(getText(R.string.expense_form_amount_error));
            return false;
        }
    }

    public void createOrUpdateExpense() {
        if (validateForm()) {
            activeExpense.setAmount(expenseAmountView.getText().toString());
            activeExpense.setCategory(expenseCategoryView.getText().toString());
            activeExpense.setDescription(expenseDescriptionView.getText().toString());

            if (MainActivity.activeExpenseId != -1) {
                ExpenseDBHelper.getInstance(getContext()).updateExpense(activeExpense);
            } else {
                ExpenseDBHelper.getInstance(getContext()).addExpense(activeExpense);
            }
            setExpenseListFragment();
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
        expenseCategoryView = (EditText) rootView.findViewById(R.id.expense_category);
        expenseCategoryTags = (TagContainerLayout) rootView.findViewById(R.id.expense_category_tags);
        expenseDescriptionView = (EditText) rootView.findViewById(R.id.expense_description);
        expenseCreateButtonView = (TextView) rootView.findViewById(R.id.create_expense);
        expenseCancelButtonView = (TextView) rootView.findViewById(R.id.cancel_expense);
        expenseDeleteButtonView = (TextView) rootView.findViewById(R.id.delete_expense);

        initializeDateView();
        initializeTimeView();
        initializeAmountView();
        initializeCategoryView();
        initializeDescriptionView();
        initializeCreateButtonView();
        initializeCancelButtonView();
        initializeDeleteButtonView();

        return rootView;
    }
}
