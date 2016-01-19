package com.example.chanakyabharwaj.bdgt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chanakya.bharwaj on 18/01/16.
 */
public class ExpenseStore {
    static ArrayList<Expense> expenses = new ArrayList<Expense>();

    public static void init() {
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.BREAKFAST, new BigDecimal(15), new Date(), 1, "Kiez klein"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.CLOTHES, new BigDecimal(50), new Date(), 1, "new jacket"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.COFFEE, new BigDecimal(6), new Date(), 2, "morning Coffee"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.BREAKFAST, new BigDecimal(10), new Date(), 1, "BioMarket"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.DINNER, new BigDecimal(25), new Date(), 3, "Pizza"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.COFFEE, new BigDecimal(10), new Date(), 2, "impala coffee"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.GROCERIES, new BigDecimal(15), new Date(), 1, "Saturday groceries"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.LUNCH, new BigDecimal(5.5), new Date(), 1, "Pasta bar"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.COFFEE, new BigDecimal(3), new Date(), 2, "bio market"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.TRAINTICKETS, new BigDecimal(9), new Date(), 2, "U bahn"));
        ExpenseStore.expenses.add(new Expense(ExpenseCategory.BEER, new BigDecimal(5), new Date(), 2, "Beer with friends"));
    }

    public static void add(Expense e) {
        ExpenseStore.expenses.add(e);
    }

    public static void delete(Expense e) {
        ExpenseStore.expenses.remove(e);
    }
}
