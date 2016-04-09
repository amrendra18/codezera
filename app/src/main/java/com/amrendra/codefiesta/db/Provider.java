package com.amrendra.codefiesta.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by Amrendra Kumar on 09/04/16.
 */
public class Provider extends ContentProvider {

    private static DBHelper mDbHelper;

    private static final int CONTEST = 100;
    private static final int CONTEST_WITH_ID = 101;
    private static final int CONTEST_WITH_RESOURCE_ID = 102;
    private static final int CONTEST_WITH_START_DATE = 103;

    private static final int RESOURCE = 200;
    private static final int RESOURCE_WITH_ID = 201;

    private static final int NOTIFICATION = 300;
    private static final int NOTIFICATION_WITH_ID = 301;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DBContract.CONTENT_AUTHORITY;

        // Contest Table
        //  contests/
        matcher.addURI(authority, DBContract.PATH_CONTEST, CONTEST);
        // contests/23
        matcher.addURI(authority, DBContract.PATH_CONTEST + "/#", CONTEST_WITH_ID);
        // contests/resource/12
        matcher.addURI(authority, DBContract.PATH_CONTEST + "/" + DBContract.PATH_RESOURCE + "/#",
                CONTEST_WITH_RESOURCE_ID);
        // contest/time/12233213
        matcher.addURI(authority, DBContract.PATH_CONTEST + "/" + DBContract.PATH_TIME + "/#",
                CONTEST_WITH_START_DATE);

        // Resource Table
        //  resources/
        matcher.addURI(authority, DBContract.PATH_RESOURCE, RESOURCE);
        // resources/34
        matcher.addURI(authority,
                DBContract.PATH_RESOURCE + "/#", RESOURCE_WITH_ID);


        // Notification Table
        //  notification/
        matcher.addURI(authority, DBContract.PATH_NOTIFICATION, NOTIFICATION);
        // notification/34
        matcher.addURI(authority,
                DBContract.PATH_NOTIFICATION + "/#", NOTIFICATION_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Debug.i("uri : " + uri);
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CONTEST:
                return DBContract.ContestEntry.CONTENT_DIR_TYPE;
            case CONTEST_WITH_ID:
                return DBContract.ContestEntry.CONTENT_ITEM_TYPE;
            case CONTEST_WITH_RESOURCE_ID:
                return DBContract.ContestEntry.CONTENT_DIR_TYPE;
            case CONTEST_WITH_START_DATE:
                return DBContract.ContestEntry.CONTENT_DIR_TYPE;
            case RESOURCE:
                return DBContract.ResourceEntry.CONTENT_DIR_TYPE;
            case RESOURCE_WITH_ID:
                return DBContract.ResourceEntry.CONTENT_ITEM_TYPE;
            case NOTIFICATION:
                return DBContract.NotificationEntry.CONTENT_DIR_TYPE;
            case NOTIFICATION_WITH_ID:
                return DBContract.NotificationEntry.CONTENT_ITEM_TYPE;
            default:
                Debug.e("ERROR : " + uri, false);
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Debug.i("uri : " + uri);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int deleted = 0;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) {
            selection = "1";
        }
        switch (match) {
            case CONTEST:
                deleted = db.delete(DBContract.ContestEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RESOURCE:
                deleted = db.delete(DBContract.ResourceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case NOTIFICATION:
                deleted = db.delete(DBContract.NotificationEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Delete : Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (deleted != 0) {
            notify(uri);
        }
        Debug.e("CP delete : " + uri + " match : " + match + " deleted : " + deleted, false);
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Debug.i("uri : " + uri);
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final int match = sUriMatcher.match(uri);
        String tableName = null;
        switch (match) {
            case CONTEST:
                tableName = DBContract.ContestEntry.TABLE_NAME;
                break;
            case RESOURCE:
                tableName = DBContract.ResourceEntry.TABLE_NAME;
                break;
            case NOTIFICATION:
                tableName = DBContract.NotificationEntry.TABLE_NAME;
            default:
                break;
        }
        int inserted = 0;
        if (tableName != null) {
            final SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.beginTransaction();
            try {
                for (ContentValues value : values) {
                    long _id = -1;
                    if (tableName.equals(DBContract.ResourceEntry.TABLE_NAME)) {
                        _id = db.insertWithOnConflict(tableName, null, value, SQLiteDatabase
                                .CONFLICT_IGNORE);
                    } else {
                        _id = db.insertWithOnConflict(tableName, null, value, SQLiteDatabase
                                .CONFLICT_REPLACE);
                    }
                    if (_id != -1) {
                        inserted++;
                    }
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            notify(uri);
        }
        Debug.e("[Bulk Insert] uri: " + uri + " match : " + match + " table : " + tableName + " " +
                        "inserted : " + inserted,
                false);
        return inserted;
    }


    private void notify(Uri uri) {
        try {
            getContext().getContentResolver().notifyChange(uri, null);
        } catch (NullPointerException e) {
            Debug.e(e.getMessage(), false);
        }
    }
}
