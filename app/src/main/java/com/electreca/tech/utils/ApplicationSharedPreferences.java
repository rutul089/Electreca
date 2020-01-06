package com.electreca.tech.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ApplicationSharedPreferences {
    public static final String APPLICATION_PREFERENCES_NAME = "Electreca";

    public static void deleteSharedPrefs(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();

    }

    public static boolean firstTimeRent(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public static void set(Context context, String key, int size) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, size);
        editor.commit();
        editor.apply();
    }

    public static void set(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        editor.apply();
    }

    public static boolean containsKey(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (settings.contains(key)) {
            return true;
        } else {
            return false;
        }
    }

    public static void set(String key, String value, Context context) {
        if (value != null)
            value = value.trim();
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void set(String key, int value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static void set(String key, float value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    public static void set(String key, boolean value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static void removeKeyAndValue(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorData = settings.edit();
        editorData.remove(key);
        // editorData.clear();
        editorData.apply();
    }

    public static String getStringValue(String key, String defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static int getIntValue(String key, int defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    public static float getFloatValue(String key, float defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defaultValue);
    }

    public static Boolean getBooleanValue(String key, boolean defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }


    public static void set(String key, long value, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(key, value);
        edit.apply();
    }

    public static long getLongValue(String key, long defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultValue);
    }

    /**
     * To store any Object in shared prefs.<br>
     * <b>Only use light weight object here</b>
     *
     * @param key     - shared preference key for specific value
     * @param value   - any object
     * @param context - activity context.
     */
    public static void saveObject(String key, Object value, Context context) {

        if (value == null) {
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(value);

        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, json);
        edit.apply();
    }

    /**
     * To retrieve saved object in shared preference.
     *
     * @param key           - shared preference key for specific value
     * @param default_value - default value.
     * @param className     - class name.
     * @param context       - Activity context.
     * @return -return value
     */
    public static Object getSavedObject(String key, Object default_value, Class<?> className, Context context) {

        Object retrievedObj = null;
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES_NAME, Context.MODE_PRIVATE);

        String retStr = preferences.getString(key, "");

        if (retStr == null || retStr.isEmpty()) {
            retrievedObj = default_value;
        } else {

            Gson gson = new Gson();
            try {
                retrievedObj = gson.fromJson(retStr, className);

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                retrievedObj = default_value;
            }
        }
        return retrievedObj;
    }
}
