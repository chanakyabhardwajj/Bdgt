package com.chanakyabhardwaj.bdgt;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ExpenseListFragment extends Fragment {
    TextView emptyMessageView;
    StickyListHeadersListView stickyListView;

    public ExpenseListFragment() {
    }

    void setExpenseItemFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, new ExpenseItemFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void populateExpenses() {
        stickyListView.setEmptyView(emptyMessageView);

        SQLiteDatabase db = ExpenseDBHelper.getInstance(getContext()).getWritableDatabase();
        Cursor expCursor = db.rawQuery("SELECT  * FROM " + ExpenseContract.ExpenseEntry.TABLE_NAME +
                " ORDER BY " + ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE +
                " DESC", null);

        final ExpensesAdapter expensesAdapter = new ExpensesAdapter(getContext(), expCursor, 0);
        stickyListView.setAdapter(expensesAdapter);

        stickyListView.setClickable(true);
        stickyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                MainActivity.activeExpenseId = (int) stickyListView.getAdapter().getItemId(position);
                setExpenseItemFragment();
            }
        });
    }

    void addFakeData() {
        ExpenseDBHelper db = ExpenseDBHelper.getInstance(getContext());
        Calendar c = Calendar.getInstance();

        c.set(2016, Calendar.FEBRUARY, 10);
        db.addExpense(new Expense(1, ExpenseCategory.categories.get(0), new BigDecimal(15), c, "Kiez klein"));
        db.addExpense(new Expense(2, ExpenseCategory.categories.get(1), new BigDecimal(50), c, "new jacket"));

        c.set(2016, Calendar.FEBRUARY, 12);
        db.addExpense(new Expense(3, ExpenseCategory.categories.get(2), new BigDecimal(6), c, "morning Coffee"));
        db.addExpense(new Expense(4, ExpenseCategory.categories.get(3), new BigDecimal(10), c, "BioMarket"));
        db.addExpense(new Expense(5, ExpenseCategory.categories.get(4), new BigDecimal(25), c, "Pizza"));

        c.set(2016, Calendar.FEBRUARY, 13);
        db.addExpense(new Expense(6, ExpenseCategory.categories.get(5), new BigDecimal(10), c, "impala coffee"));
        db.addExpense(new Expense(7, ExpenseCategory.categories.get(6), new BigDecimal(15), c, "Saturday groceries"));

        c.set(2016, Calendar.FEBRUARY, 14);
        db.addExpense(new Expense(8, ExpenseCategory.categories.get(7), new BigDecimal(5.5), c, "Pasta bar"));

        c.set(2016, Calendar.FEBRUARY, 17);
        db.addExpense(new Expense(9, ExpenseCategory.categories.get(0), new BigDecimal(3), c, "bio market"));
        db.addExpense(new Expense(10, ExpenseCategory.categories.get(0), new BigDecimal(9), c, "U bahn"));

        c.set(2016, Calendar.FEBRUARY, 18);
        db.addExpense(new Expense(11, ExpenseCategory.categories.get(2), new BigDecimal(5), c, "Beer with friends"));
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

        emptyMessageView = (TextView) rootView.findViewById(R.id.listview_empty_message);
        stickyListView = (StickyListHeadersListView) rootView.findViewById(R.id.stickylistview_expenses);
        populateExpenses();

        return rootView;
    }
}
