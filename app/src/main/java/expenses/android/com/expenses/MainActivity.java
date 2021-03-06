package expenses.android.com.expenses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * @author Expense Group
 *
 *         MainActivity class extends AppCompatActivity, implements NavigationView.OnNavigationItemSelectedListener.
 *         The main activity, containing all the fragments belonging to the application.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static float totalCost = 100f;
    private static float remainingAmount =1000f;
    private static float budgetAmount;

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up the Navigation Bar and Toggle button
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Get Preferences values from setting fragment
        //-----------------------------------------------------------------------------------------

        sharedPref = getPreferences(Context.MODE_PRIVATE);
//        int f = sharedPref.getInt("TOTAL", 0);
//        String s1 = String.valueOf(f);
//        Float cost = Float.valueOf(s1);
        Float cost = sharedPref.getFloat("TOTAL", 0f);
        totalCost = cost;
        Log.wtf("MainActivity","totalCost" + totalCost);

        Float amount = sharedPref.getFloat("REMAINING", 0f);
        Log.d("MILAD", "REMAINING: " + amount);
        remainingAmount = amount;
        Log.wtf("MainActivity","remainingAmount---------------->" + remainingAmount);

        Float budget = sharedPref.getFloat("BUDGET",0f);
        budgetAmount = budget;
        Log.wtf("MainActivity","budget------------------->" + budgetAmount);
        //-----------------------------------------------------------------------------------------


        // Initiate home page on create
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , new HomeFragment())
                .commit();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //iconfy navigation bar item icons with fontawesome
        navigationView.getMenu().findItem(R.id.nav_home_layout).setIcon( R.drawable.ic_home);
        navigationView.getMenu().findItem(R.id.nav_reports_layout).setIcon(R.drawable.ic_pie_chart);
        navigationView.getMenu().findItem(R.id.nav_settings_layout).setIcon(R.drawable.ic_cogs);
        navigationView.getMenu().findItem(R.id.nav_help_layout).setIcon(R.drawable.ic_question_circle);
        navigationView.getMenu().findItem(R.id.nav_about_layout).setIcon(R.drawable.ic_info_circle);


        // Floating Add button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditExpenseActivity.class);
                i.putExtra("action", "add");
                i.putExtra("A", remainingAmount);
                i.putExtra("B", totalCost);
                Log.d("MILAD", "TOTAL: " + totalCost);
                startActivityForResult(i, 1);
                //Toast.makeText(getApplicationContext(), "MILAD SABOURI: " + remainingAmount, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Enable Navigation Toggle
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Refresh the data in MainActivity when changed.
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Float result=data.getFloatExtra("result", 0);

                totalCost = totalCost + result;
                remainingAmount = remainingAmount - result;
                this.setTotalCost(totalCost);
                this.setRemainingAmount(remainingAmount);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new HomeFragment())
                        .commit();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    /**
     * Show the current fragment based on the navigation menu
     *
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new HomeFragment())
                    .commit();
        } else if (id == R.id.nav_reports_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ReportsFragment())
                    .commit();
        } else if (id == R.id.nav_settings_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SettingsFragment())
                    .commit();
        } else if (id == R.id.nav_help_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new HelpFragment())
                    .commit();
        }else if (id == R.id.nav_about_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new AboutFragment())
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**------------------------------------------------------------
     *                      Setters and Getters                   *
     ------------------------------------------------------------*/
    public Float getTotalCost() {

        return totalCost;
    }

    public void setTotalCost(Float cost) {
        totalCost = cost;

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("TOTAL", cost);
        editor.apply();
    }

    public Float getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(float amount) {
        remainingAmount = amount;

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("REMAINING", amount);
        editor.apply();
    }

    public float getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(float budget) {
//        DecimalFormat df = new DecimalFormat("#.00");
        budgetAmount = budget;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("BUDGET", budget);
        editor.apply();
    }
}
