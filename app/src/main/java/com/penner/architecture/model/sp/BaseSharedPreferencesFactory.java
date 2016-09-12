package com.penner.architecture.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by penneryu on 16/9/12.
 */
public abstract class BaseSharedPreferencesFactory {

    private Context mContext;
    private int mMode;
    private SharedPreferences mSharedPreferences;

    public BaseSharedPreferencesFactory(Context context) {
        this(context, Context.MODE_PRIVATE);
    }

    public BaseSharedPreferencesFactory(Context context, int mode) {
        mContext = context.getApplicationContext();
        mMode = mode;
    }

    protected abstract String getKey();

    public SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(getKey(), mMode);
        }
        return mSharedPreferences;
    }

    public void setBoolValue(String keyName, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(keyName, value);
        editor.apply();
    }

    public boolean getBoolValue(String keyName, boolean defauleValue) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getBoolean(keyName, defauleValue);
    }

    public void setStringValue(String keyName, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(keyName, value);
        editor.apply();
    }

    public String getStringValue(String keyName) {
        SharedPreferences sp = getSharedPreferences();
        return sp.getString(keyName, "");
    }
}
