package expenses.android.com.expenses;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

public class ChartPagerAdapter extends FragmentPagerAdapter {


    private String tabTitles[] = new String[] {"List View","Pie Chart", "Bar Chart"};


    public ChartPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
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
