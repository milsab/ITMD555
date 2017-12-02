package expenses.android.com.expenses.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alex Wang on 12/1/17.
 */

public class Prefs {



    public static double getRemaining(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        double remaining = prefs.getFloat("REMAINING", prefs.getFloat("LIMIT", 100));
        return remaining;
    }

    public static void setRemaining(Context context,float amount){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("REMAINING", amount);
        editor.apply();
    }

    public static float getLimit(Context  context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        float limit = prefs.getFloat("LIMIT", 0);
        return limit;
    }

    public static float getTotal(Context context){
        SharedPreferences prefs =PreferenceManager.getDefaultSharedPreferences(context);
        float total = prefs.getFloat("TOTAL",0);
        return total;
    }

}
