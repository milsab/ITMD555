package expenses.android.com.expenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * @author Expense Group
 *
 *         Prefs class is used to access the data in the Application's SharedPreference.
 */
public class ChartPagerAdapter extends FragmentPagerAdapter {

    //Set the tab titles
    private String tabTitles[] = new String[] {"List View","Pie Chart", "Bar Chart"};

    private Bundle bundle = new Bundle();


    public ChartPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Get the current fragment item
     *
     * @param position
     *            The position of the current item
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                ListViewFragment  listViewFragment = new ListViewFragment();
                listViewFragment.setArguments(getBundle());
                return listViewFragment;
            case 1:
                Log.d("Case 1","getItem()");
                PieChartFragment pieChartFragment = new PieChartFragment();
                pieChartFragment.setArguments(getBundle());
                return pieChartFragment;
            case 2:
                Log.d("Case 2","getItem()");
                BarChartFragment barChartFragment = new BarChartFragment();
                barChartFragment.setArguments(getBundle());
                return barChartFragment;
        }

        return null;
    }

    //Get bundle
    public Bundle getBundle(){
        return bundle;
    }

    public void setBundle(Bundle b){
        bundle.putLong("dateFrom", b.getLong("dateFrom"));
        bundle.putLong("dateTo", b.getLong("dateTo"));
        bundle.putString("category", b.getString("category"));
        bundle.putBoolean("default", b.getBoolean("default"));

        Log.d("ChartPagerAdapter","category" + bundle.getString("category"));
    }

    //Get page title
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    //Get fragment count
    @Override
    public int getCount() {
        return 3;
    }
}
