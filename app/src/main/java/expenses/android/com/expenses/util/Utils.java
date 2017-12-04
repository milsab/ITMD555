package expenses.android.com.expenses.util;


import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Expense Group
 *
 *         Utils class is used for general util methods needed throughout the application.
 */
public class Utils {


    /**
     * Get the date in Long format
     *
     * @param date
     *            String to be formatted
     * @return long date  from String
     */
    public static long getDateLong(String date){

        SimpleDateFormat formater = new SimpleDateFormat("dd/mm/yyyy");
        try{
            Date d = formater.parse(date);
            long milliseconds = d.getTime();
            return milliseconds;
        }catch (ParseException e){
            e.printStackTrace();
        }

        return -1;

    }

    /**
     * Get the date in String format
     *
     * @param date
     *            date to be formatted
     * @return String dateString from long
     */
    public static String getDateString(long date){
        SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy");
        Date d = new Date(date);
        String dateString = dt.format(d);
        return dateString;
    }

    /**
     * Get the day in String format
     *
     * @param date
     *            date to be formatted
     * @return Integer to be returned from String
     */
    public static int extractDay(String date){
        return Integer.parseInt(date.split("/")[0]);
    }

    /**
     * Get the month in String format
     *
     * @param date
     *            date to be formatted
     * @return Integer to be returned from String
     */
    public static int extractMonth(String date){
        return Integer.parseInt(date.split("/")[1]);
    }

    /**
     * Get the year in String format
     *
     * @param date
     *            date to be formatted
     * @return Integer to be returned from String
     */
    public static int extractYear(String date){
        return Integer.parseInt(date.split("/")[2]);
    }


    /**
     * Get the amount in formatted way
     *
     * @param amount
     *            amount to be formatted
     * @param currency
     *            currency used for formatting
     * @return String to be returned
     */
    public static String formatAmount(double amount,String currency){
        String afterDecimalString = String.valueOf(amount);
        String beforeDecimalPoint = afterDecimalString.substring(0,afterDecimalString.indexOf("."));
        double numAfterDecimal = amount - Math.floor(amount);

        if(numAfterDecimal <= 0 ){
            return new StringBuilder().append(beforeDecimalPoint).append(currency).toString();
        }
        return new StringBuilder().append(amount).append(currency).toString();

    }

    /**
     * Generate random color for the Charts
     *
     * @return int position of a random color within ColorTemplate[] array
     */
    public static int generateRandomColor(){
        Random r = new Random();
        int i = r.nextInt(ColorTemplate.COLORFUL_COLORS.length - 1) + 1;
        return ColorTemplate.COLORFUL_COLORS[i];
    }
}
