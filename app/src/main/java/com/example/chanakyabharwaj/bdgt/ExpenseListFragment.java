package com.example.chanakyabharwaj.bdgt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


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
