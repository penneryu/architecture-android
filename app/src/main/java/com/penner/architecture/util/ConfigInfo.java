package com.penner.architecture.util;

import com.penner.architecture.BuildConfig;

/**
 * Created by penneryu on 16/9/12.
 */
public class ConfigInfo {
    /**
     * internal version
     */
    public static final boolean isPreview = false;

    /**
     * 0 : develop
     * 6 : release
     * */
    public static final int LOG_LEVEL = BuildConfig.DEBUG ? 0 : 6;

    /**
     * Channel number
     */
    public static final int sChannel = 0;
}
