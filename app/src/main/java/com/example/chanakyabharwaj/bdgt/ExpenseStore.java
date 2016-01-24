package com.example.chanakyabharwaj.bdgt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class ExpenseStore {
    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void init() {
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(0), new BigDecimal(15), Calendar.getInstance().getTimeInMillis(), "Kiez klein"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(1), new BigDecimal(50), Calendar.getInstance().getTimeInMillis(), "new jacket"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(2), new BigDecimal(6), Calendar.getInstance().getTimeInMillis(), "morning Coffee"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(3), new BigDecimal(10), Calendar.getInstance().getTimeInMillis(), "BioMarket"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(4), new BigDecimal(25), Calendar.getInstance().getTimeInMillis(), "Pizza"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(5), new BigDecimal(10), Calendar.getInstance().getTimeInMillis(), "impala coffee"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(6), new BigDecimal(15), Calendar.getInstance().getTimeInMillis(), "Saturday groceries"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(7), new BigDecimal(5.5), Calendar.getInstance().getTimeInMillis(), "Pasta bar"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(8), new BigDecimal(3), Calendar.getInstance().getTimeInMillis(), "bio market"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(0), new BigDecimal(9), Calendar.getInstance().getTimeInMillis(), "U bahn"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.categories.get(1), new BigDecimal(5), Calendar.getInstance().getTimeInMillis(), "Beer with friends"));
    }

    public static void add(Expense e) {
        ExpenseStore.expenses.add(e);
    }

    public static void delete(Expense e) {
        ExpenseStore.expenses.remove(e);
    }
}
