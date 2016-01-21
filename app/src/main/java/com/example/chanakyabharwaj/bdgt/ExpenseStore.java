package com.example.chanakyabharwaj.bdgt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chanakya.bharwaj on 18/01/16.
 */
public class ExpenseStore {
    static ArrayList<Expense> expenses = new ArrayList<Expense>();

    public static void init() {
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(0), new BigDecimal(15), Calendar.getInstance().getTimeInMillis(), 1, "Kiez klein"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(1), new BigDecimal(50), Calendar.getInstance().getTimeInMillis(), 1, "new jacket"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(2), new BigDecimal(6), Calendar.getInstance().getTimeInMillis(), 2, "morning Coffee"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(3), new BigDecimal(10), Calendar.getInstance().getTimeInMillis(), 1, "BioMarket"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(4), new BigDecimal(25), Calendar.getInstance().getTimeInMillis(), 3, "Pizza"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(5), new BigDecimal(10), Calendar.getInstance().getTimeInMillis(), 2, "impala coffee"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(6), new BigDecimal(15), Calendar.getInstance().getTimeInMillis(), 1, "Saturday groceries"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(7), new BigDecimal(5.5), Calendar.getInstance().getTimeInMillis(), 1, "Pasta bar"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(8), new BigDecimal(3), Calendar.getInstance().getTimeInMillis(), 2, "bio market"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(0), new BigDecimal(9), Calendar.getInstance().getTimeInMillis(), 2, "U bahn"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(1), new BigDecimal(5), Calendar.getInstance().getTimeInMillis(), 2, "Beer with friends"));
    }

    public static void add(Expense e) {
        ExpenseStore.expenses.add(e);
    }

    public static void delete(Expense e) {
        ExpenseStore.expenses.remove(e);
    }
}
