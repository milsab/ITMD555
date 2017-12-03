package expenses.android.com.expenses;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.NumberFormat;

import expenses.android.com.expenses.data.ExpenseContract;


public class ExpenseAdapter extends CursorAdapter {

    public ExpenseAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleTextView = (TextView) view.findViewById(R.id.title_text_view);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description_text_view);
        TextView amountTextView = (TextView) view.findViewById(R.id.amount_text_view);
        TextView idTextViewHidden = (TextView) view.findViewById(R.id.id_text_view);

        int id = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_TITLE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_DESCRIPTION));
        double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT));

        String formattedAmount = NumberFormat.getInstance().format(amount);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        amountTextView.setText("$" + formattedAmount);
        idTextViewHidden.setText(String.valueOf(id));


    }


}
