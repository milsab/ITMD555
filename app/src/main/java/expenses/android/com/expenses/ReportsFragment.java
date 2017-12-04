package expenses.android.com.expenses;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
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

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import expenses.android.com.expenses.data.ExpenseDBHelper;
import expenses.android.com.expenses.util.Utils;

public class ReportsFragment extends Fragment{

    View theView;
    private Spinner mSpinnerCategory;
    private TextView mTotalTextView;
    private TextView mCategorySpinnerLabel;
    private String mCategory;

    private static Button mDateFrom;
    private static  Button mDateTo;

    private long mDateFromIntFormatted;
    private long mDateToIntFormatted;


    private Button  mSearchBtn;

    private ExpenseDBHelper mExpenseDbHelper;


    private boolean firstSearch = true;



    private int mDayTo;

    private static boolean mFromClicked = false;
    private static boolean mToClicked = false;



    ViewPager viewPager;
    ChartPagerAdapter chartPagerAdapter;
    TabLayout tabLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ReportsFragment","onCreateView()");
        theView = inflater.inflate(R.layout.activity_reports, container, false);

        mDateFrom = (Button)theView.findViewById(R.id.set_from_date);
        mDateTo = (Button)theView.findViewById(R.id.set_to_date);

        mExpenseDbHelper = new ExpenseDBHelper(theView.getContext());

        mDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* DatePickerDialog d = new DatePickerDialog(getActivity(),
                        R.style.AppTheme, mDateFromSetListener, mYearFrom, mMonthFrom, mDayFrom);
                d.show();*/
                mFromClicked = true;
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        mDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToClicked = true;
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");
            }
        });

        mSpinnerCategory = (Spinner)theView.findViewById(R.id.spinner_category_report);

        getActivity().setTitle(getActivity().getString(R.string.reports_title));

        viewPager = (ViewPager)theView.findViewById(R.id.viewpager);

        mSearchBtn = (Button)theView.findViewById(R.id.search);

//        chartPagerAdapter = new ChartPagerAdapter(getFragmentManager());
//        viewPager.setAdapter(chartPagerAdapter);
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

        mTotalTextView.setVisibility(View.INVISIBLE);

//        setSearchBtn();

//        mTotalTextView.setText("Total: " + String.valueOf(((MainActivity) getActivity()).getTotalCost()));

//        mTotalTextView.setText("Total: " + String.valueOf(((MainActivity) getActivity()).getTotalCost()));


//        mTotalTextView.setText("Total: " + String.format( "%.2f", ((MainActivity) getActivity()).getTotalCost()));


        setupSpinner();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateFromIntFormatted = Utils.getDateLong(mDateFrom.getText().toString());
                mDateToIntFormatted = Utils.getDateLong(mDateTo.getText().toString());

//                destroyChartFragments();
                Log.d("RequestFragment","After destroy");
                Bundle bundle = new Bundle();
                bundle.putLong("dateFrom", mDateFromIntFormatted);
                bundle.putLong("dateTo", mDateToIntFormatted);
                bundle.putString("category", mCategory);


                if(firstSearch){
                    Log.d("ReportsFragment","Getting in search create chartAdapter");
                    bundle.putBoolean("default", true);
                    chartPagerAdapter = new ChartPagerAdapter(getFragmentManager());
                    chartPagerAdapter.setBundle(bundle);
                    viewPager.setAdapter(chartPagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                }else if(!firstSearch){
                    bundle.putBoolean("default", false);
                    Log.d("ReportsFragment","Getting in search create chartAdapter");
                    chartPagerAdapter.setBundle(bundle);
//                    chartPagerAdapter.getItem(0);
                }
//                chartPagerAdapter.setBundle(bundle);
                setUpTotal(mDateFromIntFormatted,mDateToIntFormatted,mCategory);

                firstSearch = false;

            }
        });



        return theView;
    }




    /*private void setSearchBtn(){
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateFromIntFormatted = Utils.getDateLong(mDateFrom.getText().toString());
                mDateToIntFormatted = Utils.getDateLong(mDateTo.getText().toString());
                destroyChartFragments();
                Log.d("RequestFragment","After destroy");
//                viewPager.setAdapter(null);

                chartPagerAdapter =  new ChartPagerAdapter(getFragmentManager(),mDateFromIntFormatted,mDateToIntFormatted,mCategory, false);
                viewPager.setAdapter(chartPagerAdapter);
            }
        });
    }*/


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

    public void setUpTotal(long from, long to, String category){
        Cursor c = mExpenseDbHelper.getTotalPeriodCategory(from,to,category);
        if(c.moveToNext()){
            double total = c.getDouble(0);
            mTotalTextView.setText("Total: $" + total);
            mTotalTextView.setVisibility(View.VISIBLE);

        }
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
            showDate(year, month + 1, day);
        }

        private void showDate(int year, int month, int day) {
            String date = new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).toString();
            if(mFromClicked){
                mDateFrom.setText(date);
                mFromClicked = false;
            }else if(mToClicked){
                mDateTo.setText(date);
                mToClicked = false;
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyChartFragments();
        Log.d("ReportsTag","onDestroy()");
    }


    @SuppressLint("RestrictedApi")
    private void  destroyChartFragments(){
        List<Fragment> fragments = this.getFragmentManager().getFragments();
        if(fragments != null){
            FragmentTransaction ft = this.getFragmentManager().beginTransaction();
            for(Fragment f : fragments){
                if(f instanceof PieChartFragment || f instanceof BarChartFragment || f instanceof  ListViewFragment){
                    Log.d("Fragment destroyed",  f.getClass().getSimpleName());
                    ft.remove(f);
                }
            }
            ft.commitAllowingStateLoss();

        }
    }
}
