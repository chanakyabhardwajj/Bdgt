package com.chanakyabhardwaj.bdgt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class ExpenseDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Expense.db";
    private static ExpenseDBHelper dbInstance;

    private ExpenseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ExpenseDBHelper getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new ExpenseDBHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    public static Calendar extractDateFromCursor(Cursor cursor) {
        Calendar expenseDate = Calendar.getInstance();
        String dateString = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE));
        expenseDate.setTimeInMillis(Long.parseLong(dateString));
        return expenseDate;
    }

    public static BigDecimal extractAmountFromCursor(Cursor cursor) {
        String amount = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT));
        return new BigDecimal(amount);
    }

    public static String extractCategoryFromCursor(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY));
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExpenseContract.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ExpenseContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT, expense.amount.toString());
            values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY, expense.category);
            values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DESCRIPTION, expense.description);
            values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE, expense.date.getTimeInMillis());

            db.insertOrThrow(ExpenseContract.ExpenseEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(ExpenseDBHelper.class.getSimpleName(), "Error while trying to add expense to database");
        } finally {
            db.endTransaction();
        }
    }

    public int updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT, expense.amount.toString());
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY, expense.category);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DESCRIPTION, expense.description);
        values.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE, expense.date.getTimeInMillis());

        return db.update(ExpenseContract.ExpenseEntry.TABLE_NAME, values, ExpenseContract.ExpenseEntry._ID + " = ?",
                new String[]{String.valueOf(expense._id)});
    }

    public Expense getExpenseById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] allColumns = {ExpenseContract.ExpenseEntry._ID,
                ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT,
                ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY,
                ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DESCRIPTION,
                ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE};

        Cursor cursor = db.query(ExpenseContract.ExpenseEntry.TABLE_NAME,
                allColumns, ExpenseContract.ExpenseEntry._ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursorToExpense(cursor);
    }

    public int deleteExpenseById(int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ExpenseContract.ExpenseEntry.TABLE_NAME, ExpenseContract.ExpenseEntry._ID + "=" + expenseId, null);
    }

    public ArrayList<String> getAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] categoryColumn = {ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY};

        ArrayList<String> categories = new ArrayList<String>();

        Cursor cursor = db.query(true, ExpenseContract.ExpenseEntry.TABLE_NAME,
                categoryColumn, null, null, ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String category = extractCategoryFromCursor(cursor);
            if (category.length() > 0) {
                categories.add(category);
            }
            cursor.moveToNext();
        }

        cursor.close();
        return categories;
    }

    private Expense cursorToExpense(Cursor cursor) {
        Expense expense = new Expense();
        expense.setId(cursor.getInt(cursor.getColumnIndex(ExpenseContract.ExpenseEntry._ID)));
        expense.setAmount(cursor.getString(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_AMOUNT)));
        expense.setDescription(cursor.getString(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DESCRIPTION)));
        expense.setCategory(cursor.getString(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_CATEGORY)));
        expense.setDate(cursor.getLong(cursor.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_EXPENSE_DATE)));
        return expense;
    }
}