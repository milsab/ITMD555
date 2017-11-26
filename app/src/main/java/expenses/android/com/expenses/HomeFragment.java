package expenses.android.com.expenses;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import expenses.android.com.expenses.data.ExpenseDBHelper;
import expenses.android.com.expenses.domain.Expense;

public class HomeFragment extends Fragment {

    View theView;

    ListView listView;
    ExpenseAdapter expenseAdapter;
    ExpenseDBHelper mExpenseDbHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        theView = inflater.inflate(R.layout.activity_home, container, false);
        getActivity().setTitle(getActivity().getString(R.string.home_title));

        mExpenseDbHelper = new ExpenseDBHelper(getContext());

        listView = (ListView)theView.findViewById(R.id.expense_list_view);
        expenseAdapter = new ExpenseAdapter(theView.getContext(),null);
        listView.setAdapter(expenseAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(),EditExpenseActivity.class);
                TextView textView = (TextView)view.findViewById(R.id.id_text_view);
                String idText = textView.getText().toString();
                i.putExtra("id",Integer.parseInt(idText));
                i.putExtra("action","edit");
                startActivity(i);

            }
        });

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
            expenseAdapter.swapCursor(cursor);
        }else{
            TextView textView = (TextView)theView.findViewById(R.id.list_empty_message);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
