package com.chanakyabhardwaj.bdgt;

import java.math.BigDecimal;
import java.util.Calendar;

public class Expense implements Comparable<Expense> {
    public int _id;
    public String category;
    public BigDecimal amount;
    public Calendar date;
    public String description;

    public Expense(int id, String cat, BigDecimal amount, Calendar date, String description) {
        this._id = id;
        this.category = cat;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Expense() {
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setAmount(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        this.date = c;
    }

    @Override
    public int compareTo(Expense another) {
        return -1 * this.date.compareTo(another.date);
    }
}