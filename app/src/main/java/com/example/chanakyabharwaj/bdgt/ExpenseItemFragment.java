package com.example.chanakyabharwaj.bdgt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExpenseItemFragment extends Fragment {


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
        new DatePickerFragment()
                .show(getActivity().getFragmentManager(), "DatePicker");
    }

    public void showTimePickerDialog() {
        new TimePickerFragment()
                .show(getActivity().getFragmentManager(), "TimePicker");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense_item, container, false);

        TextView expenseTime = (TextView) rootView.findViewById(R.id.expense_time);
        expenseTime.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
        expenseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        Button createExpenseButton = (Button) rootView.findViewById(R.id.create_expense);
        createExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseStore.add(new Expense(ExpenseCategory.BREAKFAST, new BigDecimal(-15), new Date(), 1, "kaalluuu"));
                setExpenseListFragment();
            }
        });

        return rootView;
    }

}
