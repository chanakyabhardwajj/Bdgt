package com.example.chanakyabharwaj.bdgt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

public class ExpensesAdapter extends ArrayAdapter<Expense> {
    int[] expenseCategoryColors = getContext().getResources().getIntArray(R.array.expenseCategoryColors);

    public ExpensesAdapter(Context context, ArrayList<Expense> users) {
        super(context, R.layout.listview_expense_item, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Expense expense = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_expense_item, parent, false);
            viewHolder.category = (TextView) convertView.findViewById(R.id.listview_expense_item_category);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.listview_expense_item_amount);
            viewHolder.date = (TextView) convertView.findViewById(R.id.listview_expense_item_date);
            viewHolder.description = (TextView) convertView.findViewById(R.id.listview_expense_item_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.category.setText(expense.category);
        viewHolder.category.setBackgroundColor(expenseCategoryColors[ExpenseCategory.categories.indexOf(expense.category)]);
        viewHolder.amount.setText(expense.amount.toString());
        viewHolder.date.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(expense.date.getTimeInMillis()));
        viewHolder.description.setText(expense.description);

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView category;
        TextView amount;
        TextView date;
        TextView description;
    }
}
