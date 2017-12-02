package expenses.android.com.expenses;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportsFragment extends Fragment{

    View theView;
    private Spinner mSpinnerCategory;
    private TextView mTotalTextView;
    private TextView mCategorySpinnerLabel;
    private String mCategory;

    private Button mDateFrom;
    private Button mDateTo;
    private int mYearFrom;
    private int mMonthFrom;
    private int mDayFrom;
    private int mYearTo;
    private int mMonthTo;
    private int mDayTo;

    private PieChart pieChart;


    ViewPager viewPager;
    ChartPagerAdapter chartPagerAdapter;
    TabLayout tabLayout;



    DatePickerDialog.OnDateSetListener mDateFromSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            mYearFrom = year;
            mMonthFrom = monthOfYear;
            mDayFrom = dayOfMonth;
        }
    };

    DatePickerDialog.OnDateSetListener mDateToSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year,
                              int monthOfYear, int dayOfMonth) {
            mYearTo = year;
            mMonthTo = monthOfYear;
            mDayTo = dayOfMonth;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ReportsFragment","onCreateView()");
        theView = inflater.inflate(R.layout.activity_reports, container, false);



        mDateFrom = (Button)theView.findViewById(R.id.set_from_date);
        mDateTo = (Button)theView.findViewById(R.id.set_to_date);

        mDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* DatePickerDialog d = new DatePickerDialog(getActivity(),
                        R.style.AppTheme, mDateFromSetListener, mYearFrom, mMonthFrom, mDayFrom);
                d.show();*/
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        mDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        mSpinnerCategory = (Spinner)theView.findViewById(R.id.spinner_category_report);

        getActivity().setTitle(getActivity().getString(R.string.reports_title));

        viewPager = (ViewPager)theView.findViewById(R.id.viewpager);
        chartPagerAdapter = new ChartPagerAdapter(getFragmentManager());
        viewPager.setAdapter(chartPagerAdapter);
        tabLayout = (TabLayout)theView.findViewById(R.id.sliding_tabs);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        mSpinnerCategory.setVisibility(View.VISIBLE);
                        mTotalTextView.setVisibility(View.VISIBLE);
                        mCategorySpinnerLabel.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mSpinnerCategory.setVisibility(View.GONE);
                        mTotalTextView.setVisibility(View.GONE);
                        mCategorySpinnerLabel.setVisibility(View.GONE);
                        break;
                    case 2:
                        mSpinnerCategory.setVisibility(View.GONE);
                        mTotalTextView.setVisibility(View.GONE);
                        mCategorySpinnerLabel.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTotalTextView = (TextView)theView.findViewById(R.id.total_view_report);
        mCategorySpinnerLabel = (TextView)theView.findViewById(R.id.category_spinner_label);

        mTotalTextView.setText("Total: " + String.valueOf(((MainActivity) getActivity()).getTotalCost()));

        setupSpinner();
        return theView;
    }


    private void setupSpinner() {

        CategoryItem categoryItem = new CategoryItem();
        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(getContext(),
                R.layout.category_spinner, categoryItem.addCategories());

        mSpinnerCategory.setAdapter(adapter);


        // Set the integer mSelected to the constant values
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem categoryItem = (CategoryItem)parent.getItemAtPosition(position);
                String selection = categoryItem.getCategory();
                Toast.makeText(getContext(),"Selected item" + selection,Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(selection)) {
                    mCategory = selection;
                    Toast.makeText(getContext(),"Category selected:" + mCategory,Toast.LENGTH_LONG).show();
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                mGender = PetContract.PetEntry.GENDER_UNKNOWN; // Unknown
            }
        });
    }

    public static  class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(c.getTime());
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ReportsTag","onDestroy()");
        viewPager = null;
        tabLayout = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(chartPagerAdapter == null){
            this.chartPagerAdapter.notifyDataSetChanged();
            this.viewPager.setAdapter(chartPagerAdapter);
        }

        Log.d("ReportsFragment","onResume()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("ReportsTag","onDetach()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("ReportsTag","onPause()");
    }

}
