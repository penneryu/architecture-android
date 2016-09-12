package com.penner.architecture.util;

/**
 * Created by penneryu on 16/7/29.
 */
public final class UriUtils {

    public static String s9Host;

    static {
        if (ConfigInfo.isPreview) {
            s9Host = "http://9.fanli.com/";
        } else {
            s9Host = "http://9.fanli.com/";
        }
    }
}
