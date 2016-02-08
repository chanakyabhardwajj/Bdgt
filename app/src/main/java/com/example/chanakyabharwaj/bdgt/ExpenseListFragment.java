package com.example.chanakyabharwaj.bdgt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class ExpenseListFragment extends Fragment {
    ExpensesAdapter expensesAdapter;
    ArrayList<Expense> allExpenses;

    public ExpenseListFragment() {
    }

    void setExpenseItemFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ExpenseItemFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void populateExpenses(ListView listView) {
        allExpenses = ExpenseDBHelper.getInstance(getContext()).getAllExpenses();

        if (allExpenses.size() == 0) {
            addFakeData();
            allExpenses = ExpenseDBHelper.getInstance(getContext()).getAllExpenses();
        }
        Collections.sort(allExpenses);
        expensesAdapter = new ExpensesAdapter(getContext(), allExpenses);
        listView.setAdapter(expensesAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                MainActivity.activeExpenseId = allExpenses.get(position - 1)._id;
                setExpenseItemFragment();
            }
        });
    }

    void addFakeData() {
        ExpenseDBHelper db = ExpenseDBHelper.getInstance(getContext());
        db.addExpense(new Expense(1, ExpenseCategory.categories.get(0), new BigDecimal(15), Calendar.getInstance(), "Kiez klein"));
        db.addExpense(new Expense(2, ExpenseCategory.categories.get(1), new BigDecimal(50), Calendar.getInstance(), "new jacket"));
        db.addExpense(new Expense(3, ExpenseCategory.categories.get(2), new BigDecimal(6), Calendar.getInstance(), "morning Coffee"));
        db.addExpense(new Expense(4, ExpenseCategory.categories.get(3), new BigDecimal(10), Calendar.getInstance(), "BioMarket"));
        db.addExpense(new Expense(5, ExpenseCategory.categories.get(4), new BigDecimal(25), Calendar.getInstance(), "Pizza"));
        db.addExpense(new Expense(6, ExpenseCategory.categories.get(5), new BigDecimal(10), Calendar.getInstance(), "impala coffee"));
        db.addExpense(new Expense(7, ExpenseCategory.categories.get(6), new BigDecimal(15), Calendar.getInstance(), "Saturday groceries"));
        db.addExpense(new Expense(8, ExpenseCategory.categories.get(7), new BigDecimal(5.5), Calendar.getInstance(), "Pasta bar"));
        db.addExpense(new Expense(9, ExpenseCategory.categories.get(0), new BigDecimal(3), Calendar.getInstance(), "bio market"));
        db.addExpense(new Expense(10, ExpenseCategory.categories.get(0), new BigDecimal(9), Calendar.getInstance(), "U bahn"));
        db.addExpense(new Expense(11, ExpenseCategory.categories.get(2), new BigDecimal(5), Calendar.getInstance(), "Beer with friends"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.activeExpenseId = -1;
                setExpenseItemFragment();
            }
        });

        ListView listView_expenses = (ListView) rootView.findViewById(R.id.listview_expenses);
        listView_expenses.addHeaderView(inflater.inflate(R.layout.listview_expense_header, null, false));
        populateExpenses(listView_expenses);
        return rootView;
    }
}
