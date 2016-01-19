package com.example.chanakyabharwaj.bdgt;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class ExpenseListFragment extends Fragment {
    ExpensesAdapter expensesAdapter;

    public ExpenseListFragment() {
    }

    void setExpenseItemFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ExpenseItemFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void populateExpenses(ListView listView) {
        expensesAdapter = new ExpensesAdapter(getContext(), ExpenseStore.expenses);
        listView.setAdapter(expensesAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(ExpenseListFragment.class.getSimpleName(), "on createView");
        View rootView = inflater.inflate(R.layout.fragment_expense_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setExpenseItemFragment();
            }
        });

        ListView listView_expenses = (ListView) rootView.findViewById(R.id.listview_expenses);
        listView_expenses.addHeaderView(inflater.inflate(R.layout.listview_expense_header, null, false));
        this.populateExpenses(listView_expenses);
        return rootView;
    }
}
