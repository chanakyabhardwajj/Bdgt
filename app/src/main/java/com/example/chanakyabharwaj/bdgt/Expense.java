package com.example.chanakyabharwaj.bdgt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chanakya.bhardwaj on 17/01/16.
 */
public class Expense {
    public ExpenseCategory category;
    public BigDecimal amount;
    public Date date;
    public int people;
    public String description;

    public Expense(ExpenseCategory category, BigDecimal amount, Date date, int people, String description) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.people = people;
        this.description = description;
    }

    public static ArrayList<Expense> getFakeData() {
        ArrayList<Expense> fakeExpenses = new ArrayList<Expense>();

        fakeExpenses.add(new Expense(ExpenseCategory.BREAKFAST, new BigDecimal(15), new Date(), 1, "Kiez klein"));
        fakeExpenses.add(new Expense(ExpenseCategory.CLOTHES, new BigDecimal(50), new Date(), 1, "new jacket"));
        fakeExpenses.add(new Expense(ExpenseCategory.COFFEE, new BigDecimal(6), new Date(), 2, "morning Coffee"));
        fakeExpenses.add(new Expense(ExpenseCategory.BREAKFAST, new BigDecimal(10), new Date(), 1, "BioMarket"));
        fakeExpenses.add(new Expense(ExpenseCategory.DINNER, new BigDecimal(25), new Date(), 3, "Pizza"));
        fakeExpenses.add(new Expense(ExpenseCategory.COFFEE, new BigDecimal(10), new Date(), 2, "impala coffee"));
        fakeExpenses.add(new Expense(ExpenseCategory.GROCERIES, new BigDecimal(15), new Date(), 1, "Saturday groceries"));
        fakeExpenses.add(new Expense(ExpenseCategory.LUNCH, new BigDecimal(5.5), new Date(), 1, "Pasta bar"));
        fakeExpenses.add(new Expense(ExpenseCategory.COFFEE, new BigDecimal(3), new Date(), 2, "bio market"));
        fakeExpenses.add(new Expense(ExpenseCategory.TRAINTICKETS, new BigDecimal(9), new Date(), 2, "U bahn"));
        fakeExpenses.add(new Expense(ExpenseCategory.BEER, new BigDecimal(5), new Date(), 2, "Beer with friends"));
        return fakeExpenses;
    }
}
