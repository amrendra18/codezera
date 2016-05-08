package com.amrendra.codefiesta.utils;

import android.content.Context;
import android.database.Cursor;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.db.DBContract;

import java.util.HashMap;

/**
 * Created by Amrendra Kumar on 11/04/16.
 */
public class AppUtils {
    public static final long SIX_HOURS = 6 * 60 * 60;
    public static final long ONE_DAY = SIX_HOURS * 4;
    public static final long ONE_WEEK = ONE_DAY * 7;
    public static final long ONE_MONTH = ONE_WEEK * 4;

    public static final String MAX_CONTEST_DURATION = "MAX_CONTEST_DURATION";
    public static final long MAX_DEFAULT_CONTEST_DURATION = 3 * ONE_MONTH;
    public static final String LAST_SYNC_PERFORMED = "LAST_SYNC_PERFORMED";
    public static final long LAST_SYNC_PERFORMED_DEFAULT_VALUE = -1;


    public static final int STATUS_CONTEST_ENDED = -1;
    public static final int STATUS_CONTEST_LIVE = 0;
    public static final int STATUS_CONTEST_FUTURE = 1;

    public static final int STATUS_CALENDAR_EVENT_SUCCESS = 0;
    public static final int STATUS_CALENDAR_PERMISSION_ERROR = -1;
    public static final int STATUS_CALENDAR_NO_ACCOUNT = -2;
    public static final int STATUS_CALENDAR_EVENT_ALREADY_ADDED = -3;
    public static final int STATUS_CALENDAR_EVENT_NOT_PRESENT = -4;

    public static final int STATUS_CALENDAR_EVENT_NOT_PRESENT_SO_ADDED = 101;
    public static final int STATUS_CALENDAR_EVENT_PRESENT_SO_REMOVED = 102;

    public static final String CONTEST_ID_KEY = "CONTEST_KEY";


    public static final String UNKNOWN_RESOURCE = "Unknown Website";

    static HashMap<Integer, String> resourceHashMap;

    public static void cacheResources(Context context) {
        if (resourceHashMap == null) {
            resourceHashMap = new HashMap<>();
        }
        Debug.c();
        Cursor cursor = context.getContentResolver().query(
                DBContract.ResourceEntry.CONTENT_URI_ALL,
                DBContract.ResourceEntry.RESOURCE_PROJECTION,
                null,
                null,
                null
        );
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(DBContract.ResourceEntry
                            .RESOURCE_ID_COL));
                    String name = cursor.getString(cursor.getColumnIndex(DBContract.ResourceEntry
                            .RESOURCE_NAME_COL));
                    resourceHashMap.put(id, name);
                }
            } finally {
                cursor.close();
            }
        }
    }

    public static String getResourceName(Context context, int key) {
        if (resourceHashMap == null
                || resourceHashMap.size() == 0
                || (!resourceHashMap.containsKey(key))) {
            cacheResources(context);
        }
        if (resourceHashMap.containsKey(key)) {
            return resourceHashMap.get(key);
        }
        return UNKNOWN_RESOURCE;
    }

    public static String getGoodResourceName(String resource) {
        String ret = resource;
        int slash = resource.indexOf("/");
        if (slash != -1) {
            ret = resource.substring(slash + 1);
        } else {
            int lastDot = resource.lastIndexOf(".");
            if (lastDot != -1) {
                ret = resource.substring(0, lastDot);
            }
        }
        ret = ret.replaceAll("\\.", " ").toUpperCase();
        return ret;
    }

    public static int getImageForResource(String resource) {
        if (resource != null) {
            String res = resource.toLowerCase();
            if (res.contains("codechef")) {
                return R.drawable.codechef;
            } else if (res.contains("hackerrank")) {
                return R.drawable.hackerrank;
            } else if (res.contains("hackerearth")) {
                return R.drawable.hackerearth;
            } else if (res.contains("codeforces")) {
                return R.drawable.codeforces;
            } else if (res.contains("topcoder")) {
                return R.drawable.topcoder;
            }
        }
        return R.mipmap.ic_launcher;
    }
}
