package expenses.android.com.expenses;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import expenses.android.com.expenses.data.ExpenseDBHelper;


public class ListViewFragment extends Fragment {

    View theView;
    ListView listView;
    TextView mTotalTextView;
    ExpenseAdapter mExpenseAdapter;
    ExpenseDBHelper mExpenseDbHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.activity_list_view,container,false);
        mExpenseDbHelper = new ExpenseDBHelper(theView.getContext());
        mExpenseAdapter = new ExpenseAdapter(theView.getContext(),null);
        listView = (ListView)theView.findViewById(R.id.list_view_report);
        listView.setAdapter(mExpenseAdapter);
        return theView;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayExpenses();
    }

    private void displayExpenses(){

        Cursor cursor = mExpenseDbHelper.getLatestExpenses();
        if(cursor != null){
            mExpenseAdapter.swapCursor(cursor);
        }else{
            TextView textView = (TextView)theView.findViewById(R.id.list_empty_message);
            textView.setVisibility(View.VISIBLE);
        }
    }


}
