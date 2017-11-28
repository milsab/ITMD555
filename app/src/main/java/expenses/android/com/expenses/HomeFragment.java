package expenses.android.com.expenses;


import android.app.Activity;
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

    TextView txtTotal;
    TextView txtRemaining;
    int total;
    int remaining;

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

        txtTotal = (TextView) theView.findViewById(R.id.txtTotal);
        txtRemaining = (TextView) theView.findViewById(R.id.txtRemaining);

        total = ((MainActivity) getActivity()).getTotalCost();
        remaining = ((MainActivity) getActivity()).getRemainingAmount();

        txtTotal.setText(String.valueOf(total));
        txtRemaining.setText(String.valueOf(remaining));


        listView = (ListView)theView.findViewById(R.id.expense_list_view);

        expenseAdapter = new ExpenseAdapter(theView.getContext(),null);
        listView.setAdapter(expenseAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView amount = (TextView)view.findViewById(R.id.amount_text_view);
                String s = amount.getText().toString();
                total = total - Integer.parseInt(s.substring(0, s.length() - 1));
                remaining = remaining + Integer.parseInt(s.substring(0, s.length() - 1));
                Intent i = new Intent(getContext(),EditExpenseActivity.class);
                TextView textView = (TextView)view.findViewById(R.id.id_text_view);
                String idText = textView.getText().toString();
                i.putExtra("id",Integer.parseInt(idText));
                i.putExtra("action","edit");
                startActivityForResult(i, 1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("result", 0);
                total = total + result;
                remaining = remaining - result;

                ((MainActivity) getActivity()).setTotalCost(total);
                ((MainActivity) getActivity()).setRemainingAmount(remaining);

                txtTotal.setText(String.valueOf(total));
                txtRemaining.setText(String.valueOf(remaining));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
