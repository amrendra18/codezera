package com.amrendra.codefiesta.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Amrendra Kumar on 24/04/16.
 */
public class UserPreferences {
    private static final String SHARED_PREF_FILE_NAME = "codefiesta.pref";

    SharedPreferences mSharedPreference;
    SharedPreferences.Editor mEditor;

    static UserPreferences mSharedPreferenceMgr = null;

    private UserPreferences(Context context) {
        mSharedPreference = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreference.edit();
    }

    public static UserPreferences getInstance(Context context) {
        if (mSharedPreferenceMgr == null) {
            mSharedPreferenceMgr = new UserPreferences(context);
        }
        return mSharedPreferenceMgr;
    }

    public void removeKey(final String key) {
        mEditor.remove(key).commit();
    }

    public int readValue(final String key, final int defaultValue) {
        return mSharedPreference.getInt(key, defaultValue);
    }

    public void writeValue(final String key, final int value) {
        mEditor.putInt(key, value).commit();
    }

    public long readValue(final String key, final long defaultValue) {
        return mSharedPreference.getLong(key, defaultValue);
    }

    public void writeValue(final String key, final long value) {
        mEditor.putLong(key, value).commit();
    }

    public double readValue(final String key, final double defaultValue) {
        return Double.longBitsToDouble(mSharedPreference.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public void writeValue(final String key, final double value) {
        mEditor.putLong(key, Double.doubleToRawLongBits(value)).commit();
    }

    public boolean readValue(String key, boolean defaultValue) {
        return mSharedPreference.getBoolean(key, defaultValue);
    }

    public void writeValue(final String key, final boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    public String readValue(final String key, final String defaultValue) {
        return mSharedPreference.getString(key, defaultValue);
    }

    public void writeValue(final String key, final String value) {
        mEditor.putString(key, value).commit();
    }

    public void clear() {
        mEditor.clear().commit();
    }


    public void debug() {
        Debug.preferences(mSharedPreference);
    }
}
