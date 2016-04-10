package com.amrendra.codefiesta.utils;

import android.content.Context;
import android.database.Cursor;

import com.amrendra.codefiesta.db.DBContract;

import java.util.HashMap;

/**
 * Created by Amrendra Kumar on 11/04/16.
 */
public class AppUtils {

    public enum CONTEST_STATUS {
        ENDED,
        RUNNING,
        FUTURE
    }

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
}
