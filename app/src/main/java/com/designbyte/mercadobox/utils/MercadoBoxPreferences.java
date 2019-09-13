package com.designbyte.mercadobox.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MercadoBoxPreferences {
    Context context;
    private static final String PREFERENCES_FILE = "mb_pref";


    public MercadoBoxPreferences(Context context) {
        this.context = context;
    }

    public void saveSharedSetting(String settingName, String settingValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public void saveSharedSetting(String settingName, int settingValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(settingName, settingValue);
        editor.apply();
    }

    public void saveSharedSetting(String settingName, float settingValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(settingName, settingValue);
        editor.apply();
    }

    public void saveSharedSetting(String settingName, boolean settingValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(settingName, settingValue);
        editor.apply();
    }


    public String readSharedSetting(String settingName, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public int readSharedSetting(String settingName, int defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getInt(settingName, defaultValue);
    }

    public float readSharedSetting(String settingName, float defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getFloat(settingName, defaultValue);
    }

    public Boolean readSharedSetting(String settingName, boolean defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(settingName, defaultValue);
    }

    public String getUser(String settingName, String defaultValue){
        return String.valueOf(context.getSharedPreferences(PREFERENCES_FILE, 0).getString(settingName, defaultValue));
    }

    public void setUser(String settingName, String settingValue){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES_FILE, 0).edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }


}
