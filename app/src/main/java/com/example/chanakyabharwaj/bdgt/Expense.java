package com.example.chanakyabharwaj.bdgt;

import java.math.BigDecimal;
import java.util.Calendar;

public class Expense implements Comparable<Expense> {
    public int _id;
    public String category;
    public BigDecimal amount;
    public Calendar date;
    public String description;

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
        return this.date.compareTo(another.date);
    }
}