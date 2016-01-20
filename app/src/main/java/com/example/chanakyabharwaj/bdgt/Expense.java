package com.example.chanakyabharwaj.bdgt;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chanakya.bhardwaj on 17/01/16.
 */
public class Expense {
    public ExpenseCategory category;
    public BigDecimal amount;
    public long date;
    public int people;
    public String description;

    public Expense(ExpenseCategory category, BigDecimal amount, long date, int people, String description) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.people = people;
        this.description = description;
    }
}
