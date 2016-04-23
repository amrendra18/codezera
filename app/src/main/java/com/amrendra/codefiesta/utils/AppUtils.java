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

    public static final String MAX_CONTEST_DURATION = "MAX_CONTEST_DURATION";
    public static final int MAX_DEFAULT_CONTEST_DURATION = 3 * 30 * 24 * 60 * 60;

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
                    Debug.e("resource : " + id + " name : " + name);
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
