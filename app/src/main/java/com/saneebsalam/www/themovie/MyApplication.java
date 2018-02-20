package com.saneebsalam.www.themovie;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Saneeb Salam
 * on 2/18/2018.
 */

public class MyApplication extends Application {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        preferences = getSharedPreferences("TheMovie", 0);
    }

    // Shared preferances
    public static void setsharedprefString(String key, String value) {
        editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setsharedprefBoolean(String key, boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getsharedprefString(String key) {
        return preferences.getString(key, "");
    }

    public static Boolean getsharedprefBoolean(String key) {
        return preferences.getBoolean(key, false);
    }
}

