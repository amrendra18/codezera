package com.amrendra.codefiesta.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by amrendrk on 5/14/16.
 */
public class DeviceUtils {

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
