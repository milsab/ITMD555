package expenses.android.com.expenses;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import expenses.android.com.expenses.data.ExpenseDBHelper;

/**
 * @author Expense Group
 *
 *         PieChartFragment class extends Fragment.Contains the methods
 *         to populate the piechart based on the user search criteria
 */
public class PieChartFragment extends Fragment {


    View theView;
    ExpenseDBHelper mExpenseDbHelper;
    private PieChart pieChart;
    private long mDateFrom;
    private long mDateTo;

    /**
     * Set the search criteria values
     *
     * @param args bundle containing the key/value pairs for the search criteria
     *
     */
    @Override
    public void setArguments(Bundle args) {
        Log.d("PieChartFragment", "setArguments()");
        if (args != null) {
            Log.d("ListViewFragment", "args search");
            mDateFrom = args.getLong("dateFrom");
            mDateTo = args.getLong("dateTo");
            Log.d("PieChartFragment", "mDateFrom: " + mDateFrom);
            Log.d("PieChartFragment", "mDateTo: " + mDateTo);

        }

    }

    /**
     * Inflate the fragment within the activity being used
     *
     * @return theView containing the fragment
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("PieChart", "onCreateView()");
        theView = inflater.inflate(R.layout.activity_pie_chart, container, false);
        mExpenseDbHelper = new ExpenseDBHelper(theView.getContext());
        pieChart = (PieChart) theView.findViewById(R.id.pie_chart);
        setupPieChart();
        return theView;
    }

    /**
     * Populate the pie chart with data for the search criteria
     *
     * @return none
     */
    private void setupPieChart() {

        Cursor c = mExpenseDbHelper.getExpensesCategoryDate(mDateFrom, mDateTo);
        List<PieEntry> pieEntries = new ArrayList<>();
        Log.wtf("PieChart", "setupPieChart()");

        while (c.moveToNext()) {
            String category = c.getString(0);
            float amount = c.getFloat(1);
            pieEntries.add(new PieEntry(amount, category));

        }
       /* pieEntries.add(new PieEntry(32.4f, "Groceries"));
        pieEntries.add(new PieEntry(38.4f, "Gas"));
        pieEntries.add(new PieEntry(21.4f, "Bank"));*/

        PieDataSet dataSet = new PieDataSet(pieEntries, "");

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);

        data.setValueTextColor(Color.YELLOW);
        data.setValueTextSize(20f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(43f);

        pieChart.setData(data);
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        pieChart.invalidate();
    }

    /**
     * Populate the pie chart with data on fragment resume
     *
     * @return none
     */
    @Override
    public void onResume() {
        super.onResume();
        setupPieChart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("PieChart", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("PieChart", "onDetach()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("PieChart", "onPause()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("PieChart", "onDestroyView()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("PieChart", "onStop()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("PieChart", "onStart()");
    }

}
