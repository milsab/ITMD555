package expenses.android.com.expenses;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import expenses.android.com.expenses.data.ExpenseDBHelper;

/**
 * @author Expense Group
 *
 *         ListViewFragment class extends Fragment.Contains the methods
 *         to populate the list view based on the user search criteria
 */
public class ListViewFragment extends Fragment {

    View theView;
    ListView listView;
    TextView mTotalTextView;
    ExpenseAdapter mExpenseAdapter;
    ExpenseDBHelper mExpenseDbHelper;

    private long mDateFrom;
    private long mDateTo;
    private String mCategory;
    private boolean mDefault;

    /**
     * Set the search criteria values
     *
     * @param args bundle containing the key/value pairs for the search criteria
     *
     */
    @Override
    public void setArguments(Bundle args) {
        Log.d("ListViewFragment", "setArguments()");
        if (args != null) {
            Log.d("ListViewFragment", "args search");
            mDateFrom = args.getLong("dateFrom");
            mDateTo = args.getLong("dateTo");
            mCategory = args.getString("category");
            mDefault = args.getBoolean("default");
        }
    }

    /**
     * Inflate the fragment within the activity being used
     *
     * @return theView containing the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.activity_list_view, container, false);
        Log.d("ListViewFragment", "onCreateView()");
        mExpenseDbHelper = new ExpenseDBHelper(theView.getContext());
        mExpenseAdapter = new ExpenseAdapter(theView.getContext(), null);
        listView = (ListView) theView.findViewById(R.id.list_view_report);
        listView.setAdapter(mExpenseAdapter);


        Log.d("ListViewFragment", "dateFrom: " + mDateFrom);
        Log.d("ListViewFragment", "dateTo: " + mDateTo);
        Log.d("ListViewFragment", "Category: " + mCategory);


        return theView;
    }

    /**
     * Populate the bar chart with data on fragment start
     *
     * @return none
     */
    @Override
    public void onStart() {
        super.onStart();

        //Display expenses default for the first time
//        if(mDefault){
//            Log.d("ListViewFragment","Default list view");
//            displayExpenses();
//        }else if(!mDefault){
        displayPeriodExpenses(mDateFrom, mDateTo, mCategory);

//        }
    }

    private void displayExpenses() {
        Cursor cursor = mExpenseDbHelper.getLatestExpenses();
        if (cursor != null) {
            mExpenseAdapter.swapCursor(cursor);
        } else {
            TextView textView = (TextView) theView.findViewById(R.id.list_empty_message);
            textView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Populate the list view chart with data for the search criteria
     *
     * @param from
     *              Date period from used to search data
     *@param to
     *              Date period to used to search data
     * @param category
     *              Category used to search data
     * @return none
     */
    private void displayPeriodExpenses(long from, long to, String category) {
        Cursor cursor = mExpenseDbHelper.searchExpenseByCategoryDate(from, to, category);
        if (cursor != null) {
            mExpenseAdapter.swapCursor(cursor);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("ListViewFragment", "onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ListViewFragment", "onDestroy()");
    }

}
