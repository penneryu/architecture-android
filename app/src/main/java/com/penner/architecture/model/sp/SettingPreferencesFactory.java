package com.penner.architecture.model.sp;

import android.content.Context;

/**
 * Created by penneryu on 16/9/12.
 */
public class SettingPreferencesFactory extends BaseSharedPreferencesFactory {

    public final static String sStringType = "stringType";
    public final static String sBoolType= "sBoolType";

    public SettingPreferencesFactory(Context context) {
        super(context);
    }

    @Override
    protected String getKey() {
        return "penner_settinginfo";
    }
}
