package expenses.android.com.expenses.data;


import android.provider.BaseColumns;

public class ExpenseContract {

    private ExpenseContract(){}

    public static class ExpenseEntry implements BaseColumns{
        public static final String TABLE_NAME = "expense";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CATEGORY = "category";

    }
}
