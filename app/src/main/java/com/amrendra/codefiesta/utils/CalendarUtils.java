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
    long calId = AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;

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

    public int addEventToCalendar(Contest contest) {
        if (calId < 0) {
            calId = getCalendarId();
            if(calId < 0){
                return (int) calId;
            }
        }
        int success = AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;
        int eventIdCheck = checkEvent(contest);
        Debug.e("check : " + eventIdCheck, false);
        if (eventIdCheck == AppUtils.STATUS_CALENDAR_EVENT_NOT_PRESENT) {
            Debug.e("event not found, hence adding", false);
            ContentValues cv = contest.toCalendarEventContentValues(calId);
            try {
                Uri uri =
                        mContext.getContentResolver().
                                insert(CalendarContract.Events.CONTENT_URI, cv);
                if (uri != null) {
                    long eventId = Long.valueOf(uri.getLastPathSegment());
                    Debug.e("uri : " + uri, false);
                    Debug.e("eventId : " + eventId, false);

                    ContentValues cvv = new ContentValues();
                    cvv.put(DBContract.CalendarEntry.CALENDAR_CONTEST_ID_COL, contest.getId());
                    cvv.put(DBContract.CalendarEntry.CALENDAR_EVENT_ID_COL, eventId);
                    cvv.put(DBContract.CalendarEntry.CALENDAR_CONTEST_START_TIME_COL, DateUtils
                            .getEpochTime(contest.getStart()));
                    cvv.put(DBContract.CalendarEntry.CALENDAR_EVENT_TIME_COL, DateUtils
                            .getEpochTime(contest.getStart()));
                    Uri uri2 = mContext.getContentResolver().insert(DBContract.CalendarEntry
                            .CONTENT_URI_ALL_CALENDAR_EVENTS, cvv);
                    Debug.e("uri2 : " + uri2, false);
                }
                success = AppUtils.STATUS_CALENDAR_EVENT_SUCCESS;
            } catch (SecurityException se) {
                success = AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;
                Debug.c();
                se.printStackTrace();
            }
        } else if(eventIdCheck >= 0){
            Debug.c();
            success = AppUtils.STATUS_CALENDAR_EVENT_ALREADY_ADDED;
        }
        return success;
    }


    public int checkEvent(Contest contest) {
        if (calId < 0) {
            calId = getCalendarId();
            if(calId < 0){
                return (int) calId;
            }
        }
        int eventId = AppUtils.STATUS_CALENDAR_EVENT_NOT_PRESENT;
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
        if (calId < 0) {
            calId = getCalendarId();
            if(calId < 0){
                return (int) calId;
            }
        }
        int success = AppUtils.STATUS_CALENDAR_PERMISSION_ERROR;
        long eventIdCheck = checkEvent(contest);
        Debug.e("check : " + eventIdCheck, false);
        if (eventIdCheck != -1) {
            try {
                int deleted = mContext.getContentResolver().
                        delete(
                                CalendarContract.Events.CONTENT_URI,
                                CalendarContract.Events._ID + " =? ",
                                new String[]{Long.toString(eventIdCheck)});

                Debug.e("del : " + deleted, false);
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
