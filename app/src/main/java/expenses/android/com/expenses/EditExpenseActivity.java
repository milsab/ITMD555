package expenses.android.com.expenses;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import expenses.android.com.expenses.data.ExpenseContract;
import expenses.android.com.expenses.data.ExpenseDBHelper;
import expenses.android.com.expenses.util.Prefs;
import expenses.android.com.expenses.util.Utils;


public class EditExpenseActivity extends AppCompatActivity {

    private EditText mEditExpenseTitle;
    private EditText mEditDescriptionExpense;
    private EditText mEditAmountExpense;
    private Spinner mSpinnerCategory;
    private Button mButtonDSetDate;
    private DatePicker datePicker;
    private TextView dateView;
    private Calendar calendar;
    private int year, month, day;
    private String mCategory;
    private Float amount;
    private int mExpenseId;
    private String mAction;
    private String mDate;

    private Float remaining;
    private Float total;
    private Button btnDelete;


    private ExpenseDBHelper mExpenseDBHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        mExpenseDBHelper = new ExpenseDBHelper(getApplicationContext());

        mEditExpenseTitle = (EditText) findViewById(R.id.edit_expense_title);
        mEditDescriptionExpense = (EditText) findViewById(R.id.edit_description_expense);
        mEditAmountExpense = (EditText) findViewById(R.id.edit_amount_expense);
        mSpinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        dateView = (EditText) findViewById(R.id.date_view);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        // Pops up keyboard when add expense activity starts
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        dateView.setText(calendar.get(Calendar.DATE) + "/"
                + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.YEAR));

        //showDate(year, month+1, day);


        setupSpinner();


        Intent i = getIntent();

        if(i.getStringExtra("action").equals("add")){
            mAction = "add";
            setTitle("Add Expense");
            btnDelete.setVisibility(View.GONE);
        }else{
            setTitle("Edit Expense");
            mAction = "edit";
            mExpenseId = i.getIntExtra("id",-1);
            populateData(mExpenseId);
            btnDelete.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(),"The id is :" + mExpenseId,Toast.LENGTH_SHORT).show();
        }

        remaining = i.getFloatExtra("A", 0);
        Log.d("MILAD", "REMAINING in Edit: " + remaining);
        total = i.getFloatExtra("B", 0);
        Log.d("MILAD", "TOTAL in Edit: " + total);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpenseDBHelper = new ExpenseDBHelper(getApplicationContext());
                mExpenseDBHelper.deleteById(mExpenseId);
                Toast.makeText(getApplicationContext(),"Expense Deleted", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","DELETE");
                setResult(1039,returnIntent);
                finish();
            }
        });
    }

    public void set_date(View view) {
//        Toast.makeText(getApplicationContext(),"text clicked", Toast.LENGTH_LONG).show();
        showDialog(999);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupSpinner() {

        CategoryItem categoryItem = new CategoryItem();

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this,
                R.layout.category_spinner, categoryItem.addCategories());

        mSpinnerCategory.setAdapter(adapter);


        // Set the integer mSelected to the constant values
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 CategoryItem categoryItem = (CategoryItem)parent.getItemAtPosition(position);
                 String selection = categoryItem.getCategory();
                if (!TextUtils.isEmpty(selection)) {
                    mCategory = selection;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                mGender = PetContract.PetEntry.GENDER_UNKNOWN; // Unknown
            }
        });
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }};

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            if(mAction.equals("edit")){
                year = Utils.extractYear(mDate);
                month = Utils.extractMonth(mDate) - 1;
                day = Utils.extractDay(mDate);
            }

            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    private void saveExpense(){
        ContentValues contentValues = new ContentValues();

        String title = mEditExpenseTitle.getText().toString().trim();
        String description = mEditDescriptionExpense.getText().toString().trim();
        amount = Float.valueOf(mEditAmountExpense.getText().toString().trim());

        //Update the remaining in SharedPreferences
        float limit = Prefs.getLimit(this);
        float total = Prefs.getTotal(this);
        Prefs.setRemaining(this,limit - total);

        long date = Utils.getDateLong(dateView.getText().toString().trim());


        contentValues.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_TITLE,title);
        contentValues.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_DESCRIPTION,description);
        contentValues.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT,amount);
        contentValues.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE,date);
        contentValues.put(ExpenseContract.ExpenseEntry.COLUMN_NAME_CATEGORY,mCategory);

        if(mAction.equals("add")){
            mExpenseDBHelper.insertExpense(contentValues);
        }else if(mAction.equals("edit")){
            mExpenseDBHelper.updateExpense(contentValues,mExpenseId);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                if(validate()){

                    saveExpense();
                    Float result = amount;
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",result);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                    return true;
                } else {
                    return false;
                }

        }

        return super.onOptionsItemSelected(item);
    }


    private void populateData(int id){
        Cursor c = mExpenseDBHelper.getExpense(id);
        if(c.moveToFirst()){
            int titleColumnIndex = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_TITLE);
            int descriptionColumnIndex = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_DESCRIPTION);
            int amountColumnIndex = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_AMOUNT);
            int dateColumnIndex = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_DATE);
            int categoryColumnIndex = c.getColumnIndex(ExpenseContract.ExpenseEntry.COLUMN_NAME_CATEGORY);

            String title = c.getString(titleColumnIndex);
            String description = c.getString(descriptionColumnIndex);
            Float amount = c.getFloat(amountColumnIndex);
            mDate = Utils.getDateString(c.getLong(dateColumnIndex));
            mCategory = c.getString(categoryColumnIndex);

            Log.d("Date EditText", "Date: " + mDate);

            mEditExpenseTitle.setText(title);
            mEditDescriptionExpense.setText(description);
            mEditAmountExpense.setText(String.format("%.2f", amount));
            dateView.setText(mDate);


            mSpinnerCategory.setSelection(getSelectionIndex(mSpinnerCategory,mCategory));


        }
    }

    private int getSelectionIndex(Spinner spinner, String category){
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            CategoryItem categoryItem = (CategoryItem)spinner.getItemAtPosition(i);
            if(categoryItem.getCategory().equals(category)){
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean validate() {
        boolean status = true;
        if (mEditExpenseTitle.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Title is Required",
                    Toast.LENGTH_SHORT).show();
            status = false;
        } else if (mEditAmountExpense.getText().toString().trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Amount is Required",
                    Toast.LENGTH_SHORT).show();
            status = false;
        } else if (
                (( Float.valueOf(mEditAmountExpense.getText().toString().trim()) ) > remaining )
                ){
            Toast.makeText(getApplicationContext(), "Cannot add expense, change limit",
                    Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;
    }
}
