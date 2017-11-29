package expenses.android.com.expenses;

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


public class PieChartFragment extends Fragment {


    View theView;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("PieChart","onCreateView()");
        theView = inflater.inflate(R.layout.activity_pie_chart, container, false);
        pieChart = (PieChart)theView.findViewById(R.id.pie_chart);
        setupPieChart();
        return theView;
    }

    private void setupPieChart(){
        Log.wtf("PieChart","setupPieChart");
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(32.4f, "Groceries"));
        pieEntries.add(new PieEntry(38.4f, "Gas"));
        pieEntries.add(new PieEntry(21.4f, "Bank"));

        PieDataSet dataSet = new PieDataSet(pieEntries,"");

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);


        data.setValueTextColor(Color.YELLOW);
        data.setValueTextSize(20f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(43f);

        pieChart.setData(data);
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        pieChart.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupPieChart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("PieChart","onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("PieChart","onDetach()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("PieChart","onPause()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("PieChart","onDestroyView()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("PieChart","onStop()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("PieChart","onStart()");
    }

}
