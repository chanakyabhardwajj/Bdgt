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

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_expense_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView category = (TextView) view.findViewById(R.id.listview_expense_item_category);
        TextView amount = (TextView) view.findViewById(R.id.listview_expense_item_amount);

        // Extract properties from cursor
        String expenseCategory = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY));
        String expenseAmount = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT));

        amount.setText(expenseAmount + "€");
        if (expenseCategory.length() > 0) {
            category.setText(expenseCategory);
        } else {
            category.setText("uncategorized");
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

        Calendar expenseDate = Calendar.getInstance();
        String dt = mCursor.getString(mCursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE));
        expenseDate.setTimeInMillis(Long.parseLong(dt));
        int expenseDayOfYear = expenseDate.get(Calendar.DAY_OF_YEAR);
        int todayDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

        if (todayDayOfYear == expenseDayOfYear) {
            holder.date.setText("Today");
        } else {
            holder.date.setText(new SimpleDateFormat("dd MMM").format(expenseDate.getTime()));
        }


        BigDecimal expenseByDay = new BigDecimal(0);

        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Calendar expDate = Calendar.getInstance();
            String dtStr = mCursor.getString(mCursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE));
            String amtStr = mCursor.getString(mCursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT));
            expDate.setTimeInMillis(Long.parseLong(dtStr));

            if (expDate.get(Calendar.DAY_OF_YEAR) == expenseDayOfYear) {
                expenseByDay = expenseByDay.add(new BigDecimal(amtStr));
            }
        }

        holder.total.setText(expenseByDay.toString() + "€");
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        mCursor.moveToPosition(position);

        String dt = mCursor.getString(mCursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE));
        Calendar expenseDate = Calendar.getInstance();
        expenseDate.setTimeInMillis(Long.parseLong(dt));
        return Integer.parseInt(new SimpleDateFormat("Dyyyy").format(expenseDate.getTime()));
    }

    private static class ExpenseHeaderHolder {
        TextView date;
        TextView total;
    }
}
