package com.chanakyabhardwaj.bdgt;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class ExpensesAdapter extends CursorAdapter implements StickyListHeadersAdapter {
    private LayoutInflater inflater;

    public ExpensesAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
        mCursor = cursor;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public static BigDecimal[] calculateExpenseSummary(Cursor cursor, Calendar relativeToDate) {
        BigDecimal totalDay = new BigDecimal(0);
        BigDecimal totalWeek = new BigDecimal(0);
        BigDecimal totalMonth = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);

        int day = relativeToDate.get(Calendar.DAY_OF_YEAR);
        int week = relativeToDate.get(Calendar.WEEK_OF_YEAR);
        int month = relativeToDate.get(Calendar.MONTH);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Calendar expenseDate = ExpenseDBHelper.extractDateFromCursor(cursor);
            BigDecimal amount = ExpenseDBHelper.extractAmountFromCursor(cursor);

            if (expenseDate.get(Calendar.DAY_OF_YEAR) == day) {
                totalDay = totalDay.add(amount);
            }

            if (expenseDate.get(Calendar.WEEK_OF_YEAR) == week) {
                totalWeek = totalWeek.add(amount);
            }

            if (expenseDate.get(Calendar.MONTH) == month) {
                totalMonth = totalMonth.add(amount);
            }

            total = total.add(amount);
        }

        return new BigDecimal[]{totalDay, totalWeek, totalMonth, total};
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_expense_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView category = (TextView) view.findViewById(R.id.listview_expense_item_category);
        TextView amount = (TextView) view.findViewById(R.id.listview_expense_item_amount);

        String expenseCategory = ExpenseDBHelper.extractCategoryFromCursor(cursor);
        BigDecimal expenseAmount = ExpenseDBHelper.extractAmountFromCursor(cursor);

        amount.setText(expenseAmount.toString() + "€");
        if (expenseCategory.length() > 0) {
            category.setText(expenseCategory);
        } else {
            category.setText(R.string.expense_form_uncategorized);
        }
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        ExpenseHeaderHolder holder;

        if (convertView == null) {
            holder = new ExpenseHeaderHolder();
            convertView = inflater.inflate(R.layout.listview_expense_header, parent, false);
            holder.date = (TextView) convertView.findViewById(R.id.listview_expense_header_date);
            holder.total = (TextView) convertView.findViewById(R.id.listview_expense_header_total);
            convertView.setTag(holder);
        } else {
            holder = (ExpenseHeaderHolder) convertView.getTag();
        }

        mCursor.moveToPosition(position);

        Calendar expenseDate = ExpenseDBHelper.extractDateFromCursor(mCursor);
        int expenseDayOfYear = expenseDate.get(Calendar.DAY_OF_YEAR);
        int todayDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

        if (todayDayOfYear == expenseDayOfYear) {
            holder.date.setText("Today");
        } else {
            holder.date.setText(new SimpleDateFormat("dd MMM").format(expenseDate.getTime()));
        }

        BigDecimal dayTotal = calculateExpenseSummary(mCursor, expenseDate)[0];
        holder.total.setText(dayTotal.toString() + "€");
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        mCursor.moveToPosition(position);
        Calendar expenseDate = ExpenseDBHelper.extractDateFromCursor(mCursor);
        return Integer.parseInt(new SimpleDateFormat("Dyyyy").format(expenseDate.getTime()));
    }

    private static class ExpenseHeaderHolder {
        TextView date;
        TextView total;
    }

}
