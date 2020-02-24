package com.electreca.tech.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.electreca.tech.ElectrecaApplication;
import com.electreca.tech.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethods {
    public static ElectrecaApplication getAppClassInstance(Context mContext) {
        return (ElectrecaApplication) mContext.getApplicationContext();
    }

    public static String checkNullForString(Object o) {
        if (checkForValidString(o == null ? null : o.toString())) {
            return o.toString().trim();
        } else {
            return "";
        }
    }

    /**
     * To check valid email
     *
     * @param email String
     * @return - true if email is valid
     */
    public static boolean isValidEmail(String email) {
        boolean isValid = false;
//        String expression_email = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String expression_email = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(expression_email, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    /**
     * To check network availability
     *
     * @param context Context
     * @return - true if network is available
     */
    public static boolean checkNetwork(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (conMgr.getActiveNetworkInfo() != null) && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();

    }

    /**
     * To check whether string is valid or not.
     *
     * @param str = String
     * @return - true if string is valid else false
     */
    public static boolean checkForValidString(String str) {
        return str != null && !str.trim().equals("") && !str.trim().equals("null") && str.trim().length() > 0;
    }

    /**
     * To show toast
     *
     * @param context Context
     * @param title   String
     */
    public static void showToast(String title, Context context) {
        if (checkForValidString(title)) Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
    }

    /**
     * To show General Something went wrong toast
     *
     * @param context Context
     */
    public static void showGeneralSWWToast(Context context) {
        Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
    }

    /**
     * To show General No Internet Connection toast
     *
     * @param context Context
     */
    public static void showGeneralNICToast(Context context) {
        Toast.makeText(context, context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
    }

    public static String currentDateFormatted(String date, String dateFormat) {
        Date currentDate = new Date(date);
        SimpleDateFormat formattedDate = null;
        formattedDate = new SimpleDateFormat(dateFormat);
        return formattedDate.format(currentDate);
    }

    public static int dayDifference(String currentDate, String firstlyOpenDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null, date2 = null;
        try {
            if (HelperMethods.checkForValidString(firstlyOpenDate)) {
                date1 = simpleDateFormat.parse(firstlyOpenDate);
            }
            if (HelperMethods.checkForValidString(currentDate)) {
                date2 = simpleDateFormat.parse(currentDate);
            }
            long difference = date2.getTime() - date1.getTime();
            if (difference < 0) {
                return 0;
            }

            return (int) (difference / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat formattedDate = null;
        formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return formattedDate.format(currentDate);
    }

    public static String strToDate(String inputDateStr, String dateformat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date localDate = new Date();
        String monthday;
        try {
            if (checkForValidString(inputDateStr)) {
                localDate = dateFormat.parse(inputDateStr);
            } else {
                return monthday = "";
            }
            DateFormat sdfmonth = null;
            sdfmonth = new SimpleDateFormat(dateformat);
            monthday = sdfmonth.format(localDate);
            return monthday;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

}
