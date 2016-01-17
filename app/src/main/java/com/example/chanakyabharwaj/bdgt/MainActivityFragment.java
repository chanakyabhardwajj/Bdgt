package com.example.chanakyabharwaj.bdgt;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;


class ExpensesAdapter extends ArrayAdapter<Expense> {
    int[] expenseCategoryColors = getContext().getResources().getIntArray(R.array.expenseCategoryColors);

    // View lookup cache
    private static class ViewHolder {
        TextView category;
        TextView amount;
        TextView date;
        TextView people;
        TextView description;
    }

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
            viewHolder.people = (TextView) convertView.findViewById(R.id.listview_expense_item_people);
            viewHolder.description = (TextView) convertView.findViewById(R.id.listview_expense_item_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.category.setText(expense.category.toString());
        viewHolder.category.setBackgroundColor(expenseCategoryColors[expense.category.ordinal()]);
        viewHolder.amount.setText(expense.amount.toString());
        viewHolder.date.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(expense.date));
        viewHolder.people.setText(expense.people + "people");
        viewHolder.description.setText(expense.description);

        // Return the completed view to render on screen
        return convertView;
    }
}

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    ExpensesAdapter expensesAdapter;

    public MainActivityFragment() {
    }

    void populateExpenses(ListView listView) {
        ArrayList<Expense> expenses = Expense.getFakeData();
        expensesAdapter = new ExpensesAdapter(getContext(), expenses);
        listView.setAdapter(expensesAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView_expenses = (ListView) rootView.findViewById(R.id.listview_expenses);
        listView_expenses.addHeaderView(inflater.inflate(R.layout.listview_expense_header, container, false));
        this.populateExpenses(listView_expenses);

        return rootView;
    }
}
