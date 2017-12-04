package expenses.android.com.expenses;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.Calendar;

import expenses.android.com.expenses.data.ExpenseDBHelper;

public class HomeFragment extends Fragment {

    TextView txtTotal;
    TextView txtRemaining;
    TextView month;
    ArcProgress arcProgress;
    Float total;
    Float remaining;
    Float budget;
    double consumptionPercent;
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

        // Get views
        txtTotal = (TextView) theView.findViewById(R.id.txtTotal);
        txtRemaining = (TextView) theView.findViewById(R.id.txtRemaining);
        arcProgress = (ArcProgress) theView.findViewById(R.id.arc_progress);
        listView = (ListView)theView.findViewById(R.id.expense_list_view);
        month = (TextView) theView.findViewById(R.id.text_month);


        // Get sharedpreferences values
        total = ((MainActivity) getActivity()).getTotalCost();
        remaining = ((MainActivity) getActivity()).getRemainingAmount();
        budget = ((MainActivity) getActivity()).getBudgetAmount();


        //calculate progress percentage
        if(budget == 0){
            consumptionPercent = 0;
        } else{
            consumptionPercent = (double)total/budget * 100;
        }

        //Set values
        txtTotal.setText("$ " + String.format("%.2f", total));
        txtRemaining.setText("Remaining: $" + String.format("%.2f", remaining));


        Log.wtf("HomeFragment", "Current Month:" + Calendar.getInstance().get(Calendar.MONTH));
        monthTxtChanger();
        colorChanger();


        expenseAdapter = new ExpenseAdapter(theView.getContext(),null);
        listView.setAdapter(expenseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView amount = (TextView)view.findViewById(R.id.amount_text_view);
                String ss = amount.getText().toString();
                String s = ss.replace(",", "");
//                Log.d("MO", "TOTAL: " + total);

//                try {
//                    Number backtoString = NumberFormat.getInstance().parse(s);
//                    Log.wtf("HomeFrag", "amount" + backtoString);
//                } catch (java.text.ParseException e) {
//                    e.printStackTrace();
//                }


                total = total - Float.valueOf(s.substring(1, s.length() ));
                Log.d("MO", "TOTAL: " + total);
                remaining = remaining + Float.valueOf(s.substring(1, s.length() ));

                Intent i = new Intent(getContext(),EditExpenseActivity.class);
                TextView textView = (TextView)view.findViewById(R.id.id_text_view);
                String idText = textView.getText().toString();
                i.putExtra("id",Integer.parseInt(idText));
                i.putExtra("action","edit");
                i.putExtra("A", remaining);
                i.putExtra("B", total);
                startActivityForResult(i, 1);
            }
        });

        return theView;
    }

    // Change the Month TextView based on current month
    public void monthTxtChanger() {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        String[] monthlist = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};

        for (int x = 0; x < monthlist.length; x++) {
            if (currentMonth == x + 1) {
                month.setText(monthlist[x + 1]);
            }
        }
    }

    // Change Home TextViews colors based on expense value
    public void colorChanger() {
        if (consumptionPercent >= 89) {
            txtTotal.setTextColor(ColorTemplate.rgb("#ce0606"));
        } else if (consumptionPercent >= 50) {
            arcProgress.setFinishedStrokeColor(ColorTemplate.rgb("#FFC30F"));
        } else {
            txtTotal.setTextColor(Color.BLACK);
            txtRemaining.setTextColor(Color.BLACK);
            arcProgress.setFinishedStrokeColor(ColorTemplate.rgb("#04a0db"));
        }

        arcProgress.setProgress((int) consumptionPercent);
    }

    @Override
    public void onStart() {
        super.onStart();
        displayExpenses();
        total = ((MainActivity) getActivity()).getTotalCost();
        remaining = ((MainActivity) getActivity()).getRemainingAmount();
        budget = ((MainActivity) getActivity()).getBudgetAmount();


        // Calculate progress percentage
        if (budget == 0) {
            consumptionPercent = 0;
        } else {
            consumptionPercent = (double) total / budget * 100;
        }

        // Change Home TextViews colors based on expense value
        colorChanger();
    }

    private void displayExpenses(){

        Cursor cursor = mExpenseDbHelper.getLatestExpenses();

        if(cursor != null || cursor.getColumnCount() == 0){
            expenseAdapter.swapCursor(cursor);
        }else{
            TextView textView = (TextView)theView.findViewById(R.id.list_empty_message);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Expenses are empty!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Float result=data.getFloatExtra("result", 0);
                total = total + result;
                remaining = remaining - result;
                if(budget == 0){
                    consumptionPercent = 0;
                } else{
                    consumptionPercent = (double)total/budget * 100;
                }


                ((MainActivity) getActivity()).setTotalCost(total);
                ((MainActivity) getActivity()).setRemainingAmount(remaining);
//                ((MainActivity) getActivity()).setBudgetAmount((int)consumptionPercent);


//                txtTotal.setText(String.valueOf(total));
                txtTotal.setText("$ " + String.format("%.2f", total));
                txtRemaining.setText("Remaining: $" + String.format("%.2f", remaining));
                colorChanger();

            } else if(resultCode == 1039){
                if(budget == 0){
                    consumptionPercent = 0;
                } else{
                    consumptionPercent = (double)total/budget * 100;
                }

                ((MainActivity) getActivity()).setTotalCost(total);
                ((MainActivity) getActivity()).setRemainingAmount(remaining);

                txtTotal.setText("$ " + String.format("%.2f", total));
                txtRemaining.setText("Remaining: $" + String.format("%.2f", remaining));
                colorChanger();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}