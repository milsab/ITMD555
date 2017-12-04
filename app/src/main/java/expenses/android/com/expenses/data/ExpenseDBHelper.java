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

/**
 * @author Expense Group
 *
 *         ExpenseDBHelper class extends SQLiteOpenHelper in order to access the database.
 *         It contains methods which read,update,delete,insert data in/from database.
 */
public class ExpenseDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;
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

    /**
     * ExpenseContract default constructor
     *
     * @param context calling the method
     *
     */
    public ExpenseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create the database and table when onCreate called
     *
     * @param db
     *            SQLiteDatabase object that will access the underlying database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Upgrade the database when table structure changes
     *
     * @param db
     *            SQLiteDatabase object that will access the underlying database
     * @param oldVersion
     *            Old version integer number of the database
     * @param newVersion
     *            New version integer number of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_IF_EXISTS);
        onCreate(db);
    }


    /**
     * Get the ten latest expenses from database
     *
     * @return Cursor containing the rows
     */
    public Cursor getLatestExpenses() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM expense ORDER BY date DESC LIMIT 10", null);

        return cursor != null ? cursor : null;

    }

    /**
     * Insert expense into the expense table
     *
     * @param contentValues
     *            Key/Value containing the column name and value to be inserted
     * @return long newRowId  from the newly inserted row
     */
    public void insertExpense(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long newRowId = db.insert(ExpenseEntry.TABLE_NAME, null, contentValues);
    }

    /**
     * Retreive an expense from table
     *
     * @param id
     *            The id used to retreive a single record from the table
     * @return cursor containing the row
     */
    public Cursor getExpense(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense WHERE _id =" + id, null);
        return cursor;
    }

    /**
     * Delete an expense from table
     *
     * @param id
     *            The id used to delete a single record from the table
     * @return none
     */
    public void deleteById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM expense WHERE _id = " + id);
    }

    /**
     * Update an expense im the table
     *
     * @param contentValues
     *            The id used to delete a single record from the table
     * @return none
     */
    public void updateExpense(ContentValues contentValues, int id) {
        SQLiteDatabase db = getWritableDatabase();
        String where = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.update(ExpenseEntry.TABLE_NAME, contentValues, where, whereArgs);
    }

    /**
     * Retreive expense rows within a period of time
     *
     * @param dateFrom
     *              the date from formatted in integer format
     * @param dateTo
     *              the date to formatted in integer format
     * @return Cursor containing the retrieved rows from table
     */
    //Get expenses grouped by category(i.e groceries) for a date between x and y
    public Cursor getExpensesCategoryDate(long dateFrom, long dateTo) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT category, SUM(amount) amount " +
                "FROM expense " +
                "WHERE date BETWEEN " + dateFrom +
                " AND " + dateTo +
                " GROUP BY category", null);

        return cursor;
    }

    /**
     * Retrieve expense rows within a period of time for a specific category
     *
     * @param dateFrom
     *              the date from formatted in integer format
     * @param dateTo
     *              the date to formatted in integer format
     * @param category
     *              the category used to retrieve the rows
     * @return Cursor containing the retrieved rows from table
     */
    //Get expenses grouped by category(i.e groceries) for a date between x and y
    public Cursor searchExpenseByCategoryDate(long dateFrom, long dateTo, String category) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM expense " +
                "WHERE category='" + category +
                "' AND date BETWEEN " + dateFrom + " AND " + dateTo,null);
        return cursor;
    }

    /**
     * Get the total expense amount within a period of time for a specific category
     *
     * @param dateFrom
     *              the date from formatted in integer format
     * @param dateTo
     *              the date to formatted in integer format
     * @param category
     *              the category used to retrieve the rows
     * @return Cursor containing the retrieved rows from table
     */
    public Cursor getTotalPeriodCategory(long dateFrom, long dateTo, String category){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM expense WHERE category='"
                + category +  "' AND date BETWEEN " + dateFrom + " AND " + dateTo,null);
        return cursor;
    }

    /**
     * Delete all rows from expense table
     *
     * @return none
     */
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM expense");
    }
}





