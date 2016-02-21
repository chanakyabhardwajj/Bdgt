package com.chanakyabhardwaj.bdgt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ExpensesAdapter extends ArrayAdapter<Expense> implements StickyListHeadersAdapter {
    public ExpensesAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, R.layout.listview_expense_item, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Expense expense = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_expense_item, parent, false);
            viewHolder.category = (TextView) convertView.findViewById(R.id.listview_expense_item_category);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.listview_expense_item_amount);
            viewHolder.description = (TextView) convertView.findViewById(R.id.listview_expense_item_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (expense.category.length() > 0) {
            viewHolder.category.setText(expense.category);
        } else {
            viewHolder.category.setText("uncategorized");
        }

        viewHolder.amount.setText(expense.amount.toString() + "€");
        viewHolder.description.setText(expense.description);

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        final Expense expense = getItem(position);
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_expense_header, parent, false);
            holder.date = (TextView) convertView.findViewById(R.id.listview_expense_header_date);
            holder.total = (TextView) convertView.findViewById(R.id.listview_expense_header_total);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        Calendar current = Calendar.getInstance();
        if (current.get(Calendar.DAY_OF_YEAR) == expense.date.get(Calendar.DAY_OF_YEAR)) {
            holder.date.setText("Today");
        } else {
            holder.date.setText(new SimpleDateFormat("dd MMM").format(expense.date.getTime()));
        }

//        holder.total.setText(Integer.parseInt(holder.total.getText().toString()) + 1);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        final Expense expense = getItem(position);
        return Integer.parseInt(new SimpleDateFormat("Dyyyy").format(expense.date.getTime()));
    }

    private static class ViewHolder {
        TextView category;
        TextView amount;
        TextView description;
    }

    private static class HeaderViewHolder {
        TextView date;
        TextView total;
    }
}
