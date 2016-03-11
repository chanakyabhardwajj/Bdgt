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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ExpenseListFragment extends Fragment {
    TextView emptyMessageView;
    StickyListHeadersListView stickyListView;
    LinearLayout expenseSummaryView;
    TextView expenseSummaryViewTitle;
    LinearLayout expenseSummaryViewWeekBox;
    LinearLayout expenseSummaryViewMonthBox;
    TextView expenseSummaryViewToday;
    TextView expenseSummaryViewWeek;
    TextView expenseSummaryViewMonth;

    public ExpenseListFragment() {
    }

    void setExpenseItemFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, new ExpenseItemFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void populateSummary(Cursor cursor) {
        BigDecimal[] totals = ExpensesAdapter.calculateExpenseSummary(cursor, Calendar.getInstance());

        BigDecimal totalDay = totals[0];
        BigDecimal totalWeek = totals[1];
        BigDecimal totalMonth = totals[2];

        String titleMsg = getResources().getString(R.string.expense_summary_view_title);
        expenseSummaryViewTitle.setText(titleMsg);
        expenseSummaryViewToday.setText(totalDay.toString() + "€");
        expenseSummaryViewWeek.setText(totalWeek.toString() + "€");
        expenseSummaryViewMonth.setText(totalMonth.toString() + "€");

        if (totalDay.equals(totalWeek)) {
            expenseSummaryViewWeekBox.setVisibility(View.GONE);
        } else {
            expenseSummaryViewWeekBox.setVisibility(View.VISIBLE);
        }

        if (totalWeek.equals(totalMonth)) {
            expenseSummaryViewMonthBox.setVisibility(View.GONE);
        } else {
            expenseSummaryViewMonthBox.setVisibility(View.VISIBLE);
        }
    }

    void populateExpenses() {
        SQLiteDatabase db = ExpenseDBHelper.getInstance(getContext()).getWritableDatabase();
        Cursor expCursor = db.rawQuery("SELECT  * FROM " + ExpenseContract.ExpenseEntry.TABLE_NAME +
                " ORDER BY " + ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE +
                " DESC", null);

        if (expCursor.getCount() == 0) {
            emptyMessageView.setVisibility(View.VISIBLE);
            expenseSummaryView.setVisibility(View.GONE);
            stickyListView.setVisibility(View.GONE);
        } else {
            emptyMessageView.setVisibility(View.GONE);
            expenseSummaryView.setVisibility(View.VISIBLE);
            stickyListView.setVisibility(View.VISIBLE);
        }

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

        populateSummary(expCursor);
    }

    void addFakeData() {
        ExpenseDBHelper db = ExpenseDBHelper.getInstance(getContext());
        Calendar c = Calendar.getInstance();

        db.addExpense(new Expense(1, "Breakfast", new BigDecimal(15), c, "Kiez klein"));
        db.addExpense(new Expense(2, "Clothes", new BigDecimal(50), c, "new jacket"));

        c.set(2016, Calendar.FEBRUARY, 12);
        db.addExpense(new Expense(3, "Coffee", new BigDecimal(6), c, "morning Coffee"));
        db.addExpense(new Expense(4, "Groceries", new BigDecimal(10), c, "BioMarket"));
        db.addExpense(new Expense(5, "Dinner", new BigDecimal(25), c, "Pizza"));

        c.set(2016, Calendar.FEBRUARY, 13);
        db.addExpense(new Expense(6, "Coffee", new BigDecimal(10), c, "impala coffee"));
        db.addExpense(new Expense(7, "Groceries", new BigDecimal(15), c, "Saturday groceries"));

        c.set(2016, Calendar.FEBRUARY, 14);
        db.addExpense(new Expense(8, "Lunch", new BigDecimal(5.5), c, "Pasta bar"));

        c.set(2016, Calendar.FEBRUARY, 17);
        db.addExpense(new Expense(9, "Groceries", new BigDecimal(3), c, "bio market"));
        db.addExpense(new Expense(10, "Train tix", new BigDecimal(9), c, "U bahn"));

        c.set(2016, Calendar.FEBRUARY, 18);
        db.addExpense(new Expense(11, "Beer", new BigDecimal(5), c, "Beer with friends"));
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

        expenseSummaryView = (LinearLayout) rootView.findViewById(R.id.expense_summary_view);
        expenseSummaryViewTitle = (TextView) rootView.findViewById(R.id.expense_summary_view_title);
        expenseSummaryViewToday = (TextView) rootView.findViewById(R.id.expense_summary_view_today);
        expenseSummaryViewWeek = (TextView) rootView.findViewById(R.id.expense_summary_view_week);
        expenseSummaryViewMonth = (TextView) rootView.findViewById(R.id.expense_summary_view_month);
        expenseSummaryViewWeekBox = (LinearLayout) rootView.findViewById(R.id.expense_summary_view_week_box);
        expenseSummaryViewMonthBox = (LinearLayout) rootView.findViewById(R.id.expense_summary_view_month_box);

        populateExpenses();
        return rootView;
    }
}
