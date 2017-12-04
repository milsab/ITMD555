package expenses.android.com.expenses.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Expense Group
 *
 *         Prefs class is used to access the data in the Application's SharedPreference.
 */
public class Prefs {
    /**
     * Get the key remaining from the SharedPreference
     *
     * @param context
     *            Context that is calling the method
     * @return double remaining from the SharedPreference
     */
    public static double getRemaining(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        double remaining = prefs.getFloat("REMAINING", prefs.getFloat("LIMIT", 100));
        return remaining;
    }

    /**
     * Set the key remaining in the SharedPreference
     *
     * @param context
     *            Context that is calling the method
     * @param amount The amount that we are setting
     * @return nothing
     */
    public static void setRemaining(Context context,float amount){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("REMAINING", amount);
        editor.apply();
    }

    /**
     * Get the key remaining from the SharedPreference
     *
     * @param context
     *            Context that is calling the method
     * @return float limit from the SharedPreference
     */
    public static float getLimit(Context  context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        float limit = prefs.getFloat("LIMIT", 0);
        return limit;
    }

    /**
     * Get the key remaining from the SharedPreference
     *
     * @param context
     *            Context that is calling the method
     * @return float total from the SharedPreference
     */
    public static float getTotal(Context context){
        SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(context);
        float total = prefs.getFloat("TOTAL",0);
        return total;
    }

}
