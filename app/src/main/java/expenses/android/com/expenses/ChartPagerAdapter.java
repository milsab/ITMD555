package expenses.android.com.expenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

public class ChartPagerAdapter extends FragmentPagerAdapter {


    private String tabTitles[] = new String[]{"List View", "Pie Chart", "Bar Chart"};

    /*private long dateFrom;
    private long dateTo;
    private String category;
    private boolean mDefault;*/


    private Bundle bundle = new Bundle();


    public ChartPagerAdapter(FragmentManager fm) {
        super(fm);
       /* Log.d("ChartPagerAdapter","Calling constructor ChartPagerAdapter()");
        Log.d("ChartPagerAdapter","dateFrom" + dateFrom);
        Log.d("ChartPagerAdapter","dateTo" + dateTo);
        Log.d("ChartPagerAdapter","category" + category);
        Log.d("ChartPagerAdapter","mDefault" + mDefault);

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.category = category;
        this.mDefault = pDefault;*/
    }


    /*@Override
    public Fragment getItem(int position) {
        Log.d("ChartPagerAdapter","getItem()");
        switch(position){
            case 0:
                return new ListViewFragment();
            case 1:
                return new PieChartFragment();
            case 2:
                return new BarChartFragment();
        }

        return new ListViewFragment();
    }*/

    @Override
    public Fragment getItem(int position) {
        Log.d("ChartPagerAdapter", "getItem()");
        Log.d("ChartPagerAdapter", "getItem: " + position);
        switch (position) {
            case 0:
                ListViewFragment listViewFragment = new ListViewFragment();
                listViewFragment.setArguments(getBundle());
                return listViewFragment;
            case 1:
                Log.d("Case 1", "getItem()");
                PieChartFragment pieChartFragment = new PieChartFragment();
                pieChartFragment.setArguments(getBundle());
                return pieChartFragment;
            case 2:
                Log.d("Case 2", "getItem()");
                BarChartFragment barChartFragment = new BarChartFragment();
                barChartFragment.setArguments(getBundle());
                return barChartFragment;
        }

        return null;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle b) {
        bundle.putLong("dateFrom", b.getLong("dateFrom"));
        bundle.putLong("dateTo", b.getLong("dateTo"));
        bundle.putString("category", b.getString("category"));
        bundle.putBoolean("default", b.getBoolean("default"));

        Log.d("ChartPagerAdapter", "category" + bundle.getString("category"));
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
