package expenses.android.com.expenses.data;


import android.provider.BaseColumns;

/**
 * @author Expense Group
 *
 *         ExpenseContract class is used to define the contract of the database tables.
 */
public class ExpenseContract {

    /**
     * ExpenseContract default constructor
     *
     */
    private ExpenseContract(){}

    /**
     * @author Expense Group
     *
     *         ExpenseEntry class implements the BaseColumns interface, in order to inherit all
     *         the default column _id to be added to the database. It contains the table name and table
     *         columns
     */
    public static class ExpenseEntry implements BaseColumns{
        public static final String TABLE_NAME = "expense";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CATEGORY = "category";

    }
}
