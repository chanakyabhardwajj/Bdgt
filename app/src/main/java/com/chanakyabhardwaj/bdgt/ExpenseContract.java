package com.chanakyabhardwaj.bdgt;

import android.provider.BaseColumns;

public final class ExpenseContract {
    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";
    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " (" +
                    ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ExpenseEntry.COLUMN_NAME_EXPENSE_DATE + INT_TYPE + NOT_NULL + COMMA_SEP +
                    ExpenseEntry.COLUMN_NAME_EXPENSE_DESCRIPTION + TEXT_TYPE +
                    " )";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ExpenseContract() {
    }

    public static abstract class ExpenseEntry implements BaseColumns {
        public static final String TABLE_NAME = "EXPENSES";
        public static final String COLUMN_NAME_EXPENSE_AMOUNT = "amount";
        public static final String COLUMN_NAME_EXPENSE_CATEGORY = "category";
        public static final String COLUMN_NAME_EXPENSE_DATE = "date";
        public static final String COLUMN_NAME_EXPENSE_DESCRIPTION = "description";
    }
};