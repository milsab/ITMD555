package expenses.android.com.expenses.util;


import android.util.Log;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Utils {



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


    public static String getDateString(long date){
        SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy");
        Date d = new Date(date);
        String dateString = dt.format(d);
        return dateString;
    }


    public static int extractDay(String date){
        return Integer.parseInt(date.split("/")[0]);
    }

    public static int extractMonth(String date){
        return Integer.parseInt(date.split("/")[1]);
    }

    public static int extractYear(String date){
        return Integer.parseInt(date.split("/")[2]);
    }


    public static String formatAmount(double amount,String currency){
        String afterDecimalString = String.valueOf(amount);
        String beforeDecimalPoint = afterDecimalString.substring(0,afterDecimalString.indexOf("."));
        double numAfterDecimal = amount - Math.floor(amount);

        if(numAfterDecimal <= 0 ){
            return new StringBuilder().append(beforeDecimalPoint).append(currency).toString();
        }
        return new StringBuilder().append(amount).append(currency).toString();

    }


    public static int generateRandomColor(){
        Random r = new Random();
        int i = r.nextInt(ColorTemplate.COLORFUL_COLORS.length - 1) + 1;
        return ColorTemplate.COLORFUL_COLORS[i];
    }
}
