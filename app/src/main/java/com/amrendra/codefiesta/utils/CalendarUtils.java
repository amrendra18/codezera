package com.amrendra.codefiesta.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.model.Contest;

/**
 * Created by Amrendra Kumar on 24/04/16.
 */
public class CalendarUtils {

    Context mContext;
    static CalendarUtils mCalendarUtils = null;
    long calId = -1;

    private CalendarUtils(Context context) {
        mContext = context;
        calId = getCalendarId();
    }

    public static CalendarUtils getInstance(Context context) {
        if (mCalendarUtils == null) {
            mCalendarUtils = new CalendarUtils(context);
        }
        return mCalendarUtils;
    }

    public int addEvent(Contest contest) {
        int success = AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;
        if (calId == -1) {
            return success;
        }

        int eventIdCheck = checkEvent(contest);
        Debug.e("check : " + eventIdCheck);
        if (eventIdCheck == -1) {
            ContentValues cv = contest.toCalendarEventContentValues(calId);
            try {
                Uri uri =
                        mContext.getContentResolver().
                                insert(CalendarContract.Events.CONTENT_URI, cv);
                if (uri != null) {
                    long eventId = Long.valueOf(uri.getLastPathSegment());
                    Debug.e("uri : " + uri);
                    Debug.e("eventId : " + eventId);

                    ContentValues cvv = new ContentValues();
                    cvv.put(DBContract.CalendarEntry.CALENDAR_CONTEST_ID_COL, contest.getId());
                    cvv.put(DBContract.CalendarEntry.CALENDAR_EVENT_ID_COL, eventId);
                    cvv.put(DBContract.CalendarEntry.CALENDAR_CONTEST_START_TIME_COL, DateUtils
                            .getEpochTime(contest.getStart()));
                    cvv.put(DBContract.CalendarEntry.CALENDAR_EVENT_TIME_COL, DateUtils
                            .getEpochTime(contest.getStart()));
                    Uri uri2 = mContext.getContentResolver().insert(DBContract.CalendarEntry
                            .CONTENT_URI_ALL_CALENDAR_EVENTS, cvv);
                    Debug.e("uri2 : " + uri2);
                }
                success = AppUtils.STATUS_CALENDAR_EVENT_SUCCESS;
            } catch (SecurityException se) {
                success = AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;
                Debug.c();
                se.printStackTrace();
            }
        } else {
            Debug.c();
            success = AppUtils.STATUS_CALENDAR_EVENT_ALREADY_ADDED;
        }
        return success;
    }


    public int checkEvent(Contest contest) {
        int eventId = -1;
        if (calId == -1) {
            return eventId;
        }
        Uri uri = DBContract.CalendarEntry.buildCalendarEventUriWithContestId(contest.getId());
        Cursor cursor = mContext.getContentResolver().query(
                uri,
                DBContract.CalendarEntry.CALENDAR_PROJECTION,
                null,
                null,
                null
        );
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    eventId = cursor.getInt(cursor.getColumnIndex(DBContract.CalendarEntry
                            .CALENDAR_EVENT_ID_COL));
                }
            } finally {
                cursor.close();
            }
        }
        return eventId;
    }

    public int deleteEvent(Contest contest) {
        int success = AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;
        if (calId == -1) {
            return success;
        }

        long eventIdCheck = checkEvent(contest);
        Debug.e("check : " + eventIdCheck);
        if (eventIdCheck != -1) {
            try {
                int deleted = mContext.getContentResolver().
                        delete(
                                CalendarContract.Events.CONTENT_URI,
                                CalendarContract.Events._ID + " =? ",
                                new String[]{Long.toString(eventIdCheck)});

                Debug.e("del : " + deleted);
                mContext.getContentResolver().delete(DBContract.CalendarEntry.CONTENT_URI_ALL_CALENDAR_EVENTS,
                        DBContract.CalendarEntry.CALENDAR_CONTEST_ID_COL + " = ?",
                        new String[]{Long.toString(contest.getId())});
            } catch (SecurityException se) {
                se.printStackTrace();
                Debug.c();
            }
        }
        return success;
    }

    public long getCalendarId() {
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
                                "" + timezone);
                        return id;
                    }
                } finally {
                    cursor.close();
                }
            }
        } catch (SecurityException ex) {
            Debug.c();
            ex.printStackTrace();
        }
        Debug.c();
        return -1;
    }
}
