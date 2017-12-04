package expenses.android.com.expenses;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import expenses.android.com.expenses.data.ExpenseDBHelper;
import expenses.android.com.expenses.util.Utils;

public class BarChartFragment  extends Fragment {

    BarChart barChart;
    View theView;
    private long mDateFrom;
    private long mDateTo;
    ExpenseDBHelper mExpenseDbHelper;


    @Override
    public void setArguments(Bundle args) {
        Log.d("BarChartFragment", "setArguments()");
        if(args != null){
            Log.d("ListViewFragment","args search");
            mDateFrom = args.getLong("dateFrom");
            mDateTo = args.getLong("dateTo");
            Log.d("PieChartFragment","mDateFrom: " + mDateFrom);
            Log.d("PieChartFragment","mDateTo: " + mDateTo);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BarChart","onCreateView()");
        theView = inflater.inflate(R.layout.activity_bar_chart, container, false);
        barChart = (BarChart)theView.findViewById(R.id.bar_chart);
        mExpenseDbHelper = new ExpenseDBHelper(theView.getContext());
        setupBarChart();
        return theView;
    }

    private void setupBarChart(){

        Cursor cursor = mExpenseDbHelper.getExpensesCategoryDate(mDateFrom,mDateTo);
        BarData barData = new BarData();

        float categoryPosition = 1f;
        while(cursor.moveToNext()){
            List<BarEntry> category = new ArrayList<>();
            category.add(new BarEntry(categoryPosition, cursor.getFloat(1)));
            BarDataSet barDataSetCategory = new BarDataSet(category,cursor.getString(0));
            barDataSetCategory.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSetCategory.setColor(Utils.generateRandomColor());
//              barDataSetCategory.setColor(ColorTemplate)
//            barDataSetCategory.setColor(2);
            barData.addDataSet(barDataSetCategory);
            categoryPosition++;
        }

        barData.setBarWidth(0.9f); // set custom bar width
        barChart.setData(barData);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate();

    }



    @Override
    public void onResume() {
        super.onResume();
        setupBarChart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BarChar","onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("BarChar","onDetach()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("BarChar","onPause()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("BarChar","onDestroyView()");
    }
}
