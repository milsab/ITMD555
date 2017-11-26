package expenses.android.com.expenses;


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

public class BarChartFragment  extends Fragment {

    BarChart barChart;
    View theView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BarChart","onCreateView()");
        theView = inflater.inflate(R.layout.activity_bar_chart, container, false);
        barChart = (BarChart)theView.findViewById(R.id.bar_chart);
        setupBarChart();
        return theView;
    }

    private void setupBarChart(){
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        set.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
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
