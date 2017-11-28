package expenses.android.com.expenses.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import expenses.android.com.expenses.data.ExpenseContract.ExpenseEntry;
import expenses.android.com.expenses.domain.Expense;

public class ExpenseDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "expenses.db";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " (" +
                    ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ExpenseEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ExpenseEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    ExpenseEntry.COLUMN_NAME_AMOUNT + " REAL," +
                    ExpenseEntry.COLUMN_NAME_DATE + " INTEGER," +
                    ExpenseEntry.COLUMN_NAME_CATEGORY + " TEXT);";


    private static final String DROP_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + ExpenseEntry.TABLE_NAME;


    public ExpenseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXISTS);
        onCreate(db);
    }


    public Cursor getLatestExpenses() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM expense ORDER BY date DESC LIMIT 10", null);

        return cursor != null ? cursor : null;

    }


    public void insertExpense(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert(ExpenseEntry.TABLE_NAME, null, contentValues);
    }


    public Cursor getExpense(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense WHERE _id =" + id, null);
        return cursor;
    }

    public void deletePet(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM expense WHERE _id = " + id);
    }

    public void updateExpense(ContentValues contentValues, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String where = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.update(ExpenseEntry.TABLE_NAME, contentValues, where, whereArgs);
    }

    //Get expenses grouped by category(i.e groceries) for a date between x and y
    public Cursor getExpensesCategoryDate(int dateFrom, int dateTo) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT category, SUM(amount) amount " +
                "FROM expense " +
                "WHERE date BETWEEN " + dateFrom +
                "AND " + dateTo +
                "GROUP BY category);", null);

        return cursor;
    }
}