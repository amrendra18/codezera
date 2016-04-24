package com.amrendra.codefiesta.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by Amrendra Kumar on 09/04/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "codefiesta.db";

    public static final String SQL_CREATE_TABLE_RESOURCE =
            "CREATE TABLE " + DBContract.ResourceEntry.TABLE_NAME + " ("
                    + DBContract.ResourceEntry._ID + " INTEGER PRIMARY KEY,"
                    + DBContract.ResourceEntry.RESOURCE_ID_COL + " INTEGER NOT NULL,"
                    + DBContract.ResourceEntry.RESOURCE_NAME_COL + " TEXT NOT NULL,"
                    + DBContract.ResourceEntry.RESOURCE_SHOW_COL + " INTEGER NOT NULL,"
                    + " UNIQUE (" + DBContract.ResourceEntry.RESOURCE_ID_COL + ") ON CONFLICT IGNORE"
                    + " );";

    public static final String SQL_CREATE_TABLE_CONTESTS =
            "CREATE TABLE " + DBContract.ContestEntry.TABLE_NAME + " ("
                    + DBContract.ContestEntry._ID + " INTEGER PRIMARY KEY,"
                    + DBContract.ContestEntry.CONTEST_ID_COL + " INTEGER NOT NULL,"
                    + DBContract.ContestEntry.CONTEST_NAME_COL + " TEXT NOT NULL,"
                    + DBContract.ContestEntry.CONTEST_URL_COL + " TEXT NOT NULL,"
                    + DBContract.ContestEntry.CONTEST_RESOURCE_ID_COL + " TEXT NOT NULL,"
                    + DBContract.ContestEntry.CONTEST_START_COL + " INTEGER NOT NULL,"
                    + DBContract.ContestEntry.CONTEST_END_COL + " INTEGER NOT NULL,"
                    + DBContract.ContestEntry.CONTEST_DURATION_COL + " INTEGER NOT NULL,"
                    + " UNIQUE (" + DBContract.ContestEntry.CONTEST_ID_COL + ") ON CONFLICT REPLACE"
                    + " );";

    public static final String SQL_CREATE_TABLE_NOTIFICATIONS =
            "CREATE TABLE " + DBContract.NotificationEntry.TABLE_NAME + " ("
                    + DBContract.NotificationEntry._ID + " INTEGER PRIMARY KEY,"
                    + DBContract.NotificationEntry.NOTIFICATION_CONTEST_ID_COL + " INTEGER NOT NULL,"
                    + DBContract.NotificationEntry.NOTIFICATION_TIME_COL + " INTEGER NOT NULL,"
                    + DBContract.NotificationEntry.NOTIFICATION_CONTEST_START_TIME_COL + " INTEGER NOT NULL,"
                    + " UNIQUE (" + DBContract.NotificationEntry._ID + ") ON CONFLICT REPLACE"
                    + " );";

    public static final String SQL_CREATE_TABLE_CALENDARS =
            "CREATE TABLE " + DBContract.CalendarEntry.TABLE_NAME + " ("
                    + DBContract.CalendarEntry._ID + " INTEGER PRIMARY KEY,"
                    + DBContract.CalendarEntry.CALENDAR_CONTEST_ID_COL + " INTEGER NOT NULL,"
                    + DBContract.CalendarEntry.CALENDAR_EVENT_ID_COL + " INTEGER NOT NULL,"
                    + DBContract.CalendarEntry.CALENDAR_EVENT_TIME_COL + " INTEGER NOT NULL,"
                    + DBContract.CalendarEntry.CALENDAR_CONTEST_START_TIME_COL + " INTEGER NOT NULL,"
                    + " UNIQUE (" + DBContract.CalendarEntry.CALENDAR_CONTEST_ID_COL + ") ON CONFLICT REPLACE"
                    + " );";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Debug.c();
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Debug.c();
        db.execSQL(SQL_CREATE_TABLE_RESOURCE);
        db.execSQL(SQL_CREATE_TABLE_CONTESTS);
        db.execSQL(SQL_CREATE_TABLE_NOTIFICATIONS);
        db.execSQL(SQL_CREATE_TABLE_CALENDARS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Debug.c();
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.ResourceEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.ContestEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.NotificationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.CalendarEntry.TABLE_NAME);
        onCreate(db);
    }
}
