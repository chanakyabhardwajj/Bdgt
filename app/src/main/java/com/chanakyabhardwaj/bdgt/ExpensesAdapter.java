package com.chanakyabhardwaj.bdgt;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExpensesAdapter extends ArrayAdapter<Expense> {
    public ExpensesAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, R.layout.listview_expense_item, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Expense expense = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_expense_item, parent, false);
            viewHolder.category = (TextView) convertView.findViewById(R.id.listview_expense_item_category);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.listview_expense_item_amount);
            viewHolder.date = (TextView) convertView.findViewById(R.id.listview_expense_item_date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.listview_expense_item_time);
            viewHolder.description = (TextView) convertView.findViewById(R.id.listview_expense_item_description);
            viewHolder.deleteIcon = (ImageView) convertView.findViewById(R.id.listview_expense_item_delete_icon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        if (expense.category.length() > 0) {
            viewHolder.category.setText(expense.category);
        } else {
            viewHolder.category.setText("uncategorized");
        }

        viewHolder.amount.setText(expense.amount.toString() + "€");
        viewHolder.date.setText(new SimpleDateFormat("dd MMM").format(expense.date.getTime()));
        viewHolder.time.setText(new SimpleDateFormat("hh:mm a").format(expense.date.getTime()));
        viewHolder.description.setText(expense.description);
        viewHolder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle(R.string.ask_expense_delete);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ExpenseDBHelper.getInstance(getContext()).deleteExpense(expense);
                        remove(expense);
                        notifyDataSetChanged();
                    }
                });
                builder.create().show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView category;
        TextView amount;
        TextView date;
        TextView time;
        TextView description;
        ImageView deleteIcon;
    }
}
