package com.example.chanakyabharwaj.bdgt;

import java.math.BigDecimal;

public class Expense {
    public String category;
    public BigDecimal amount;
    public long date;
    public String description;

    public Expense(String category, BigDecimal amount, long date, String description) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }
}
