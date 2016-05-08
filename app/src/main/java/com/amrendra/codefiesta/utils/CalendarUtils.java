package com.amrendra.codefiesta.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;

/**
 * Created by Amrendra Kumar on 24/04/16.
 */
public class CalendarUtils {
    public static long getCalendarId(Context mContext) {
        Debug.c();
        String[] projection = new String[]{CalendarContract.Calendars._ID, CalendarContract
                .Calendars.NAME, CalendarContract.Calendars.ACCOUNT_NAME, CalendarContract
                .Calendars.CALENDAR_TIME_ZONE};

        try {
            Cursor cursor = mContext.getContentResolver().query(
                    CalendarContract.Calendars.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
            if (cursor != null) {
                try {
                    Debug.c();
                    if (cursor.moveToFirst()) {
                        int id = cursor.getInt(cursor.getColumnIndex(CalendarContract.Calendars
                                ._ID));
                        String name = cursor.getString(cursor.getColumnIndex(CalendarContract
                                .Calendars
                                .NAME));
                        String accountName = cursor.getString(cursor.getColumnIndex(CalendarContract
                                .Calendars
                                .ACCOUNT_NAME));
                        String timezone = cursor.getString(cursor.getColumnIndex(CalendarContract
                                .Calendars
                                .CALENDAR_TIME_ZONE));
                        Debug.i("id: " + id + " name: " + name + " accName: " + accountName + " zone: " +
                                "" + timezone, false);
                        return id;
                    } else {
                        return AppUtils.STATUS_CALENDAR_NO_ACCOUNT;
                    }
                } finally {
                    cursor.close();
                }
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        Debug.c();
        return AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;
    }
}
