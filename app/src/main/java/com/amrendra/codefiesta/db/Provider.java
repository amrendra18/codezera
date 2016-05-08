package com.amrendra.codefiesta.db;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.Debug;
import com.amrendra.codefiesta.utils.UserPreferences;

import java.util.ArrayList;

/**
 * Created by Amrendra Kumar on 09/04/16.
 */
public class Provider extends ContentProvider {

    private static DBHelper mDbHelper;

    private static final int CONTEST = 100;
    private static final int CONTEST_WITH_ID = 101;
    private static final int CONTEST_WITH_RESOURCE_ID = 102;
    private static final int CONTEST_FUTURE = 103;
    private static final int CONTEST_PAST = 104;
    private static final int CONTEST_LIVE = 105;

    private static final int RESOURCE = 200;
    private static final int RESOURCE_WITH_ID = 201;

    private static final int NOTIFICATION = 300;
    private static final int NOTIFICATION_WITH_ID = 301;


    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private static final SQLiteQueryBuilder mContestByShowSettingsQueryBuilder;

    static {
        mContestByShowSettingsQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        mContestByShowSettingsQueryBuilder.setTables(
                DBContract.ContestEntry.TABLE_NAME + " JOIN " +
                        DBContract.ResourceEntry.TABLE_NAME +
                        " ON " + DBContract.ContestEntry.TABLE_NAME +
                        "." + DBContract.ContestEntry.CONTEST_RESOURCE_ID_COL +
                        " = " + DBContract.ResourceEntry.TABLE_NAME +
                        "." + DBContract.ResourceEntry.RESOURCE_ID_COL);
    }

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
        // contest/start/12233213
        matcher.addURI(authority, DBContract.PATH_CONTEST + "/" + DBContract.PATH_FUTURE + "/#",
                CONTEST_FUTURE);
        // contest/end/12233213
        matcher.addURI(authority, DBContract.PATH_CONTEST + "/" + DBContract.PATH_PAST + "/#",
                CONTEST_PAST);
        // contest/live/12233213
        matcher.addURI(authority, DBContract.PATH_CONTEST + "/" + DBContract.PATH_LIVE + "/#",
                CONTEST_LIVE);

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
        final int match = sUriMatcher.match(uri);
        Debug.e("Query : " + uri + " match : " + match, false);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor retCursor;
        long maxDuration = UserPreferences.getInstance(getContext())
                .readValue(AppUtils.MAX_CONTEST_DURATION, AppUtils.MAX_DEFAULT_CONTEST_DURATION);
        switch (match) {
            case CONTEST: {
                retCursor = mContestByShowSettingsQueryBuilder.query(
                        db,
                        projection, //projection
                        DBContract.ResourceEntry.TABLE_NAME +
                                "." + DBContract.ResourceEntry.RESOURCE_SHOW_COL +
                                " = 1 and " +
                                DBContract.ContestEntry.CONTEST_DURATION_COL + " <= " +
                                Long.toString(maxDuration),
                        null, //selectionArgs
                        null,
                        null,
                        sortOrder
                );
            }
            break;
            case CONTEST_WITH_ID: {
                long id = DBContract.ContestEntry.getIdFromContestUri(uri);
                retCursor = mContestByShowSettingsQueryBuilder.query(
                        db,
                        projection, //projection
                        DBContract.ResourceEntry.TABLE_NAME +
                                "." + DBContract.ResourceEntry.RESOURCE_SHOW_COL +
                                " = 1 and " +
                                DBContract.ContestEntry.CONTEST_ID_COL + " = ?",
                        new String[]{Long.toString(id)},
                        null,
                        null,
                        sortOrder
                );
            }
            break;
            case CONTEST_LIVE: {
                long currentTime = DBContract.ContestEntry.getTimeFromContestUri(uri);
                retCursor = mContestByShowSettingsQueryBuilder.query(
                        db,
                        projection, //projection
                        DBContract.ResourceEntry.TABLE_NAME +
                                "." + DBContract.ResourceEntry.RESOURCE_SHOW_COL +
                                " = 1 and " +
                                DBContract.ContestEntry.CONTEST_START_COL + " < ? and " + DBContract
                                .ContestEntry.CONTEST_END_COL + " > ? and " +
                                DBContract.ContestEntry.CONTEST_DURATION_COL + " <= " +
                                Long.toString(maxDuration),
                        new String[]{Long.toString(currentTime), Long.toString(currentTime)},
                        null,
                        null,
                        sortOrder
                );
            }
            break;
            case CONTEST_PAST: {
                long endTime = DBContract.ContestEntry.getTimeFromContestUri(uri);
                retCursor = mContestByShowSettingsQueryBuilder.query(
                        db,
                        projection, //projection
                        DBContract.ResourceEntry.TABLE_NAME +
                                "." + DBContract.ResourceEntry.RESOURCE_SHOW_COL +
                                " = 1 and " +
                                DBContract.ContestEntry.CONTEST_END_COL + " < ? and " +
                                DBContract.ContestEntry.CONTEST_DURATION_COL + " <= " +
                                Long.toString(maxDuration),
                        new String[]{Long.toString(endTime)},
                        null,
                        null,
                        sortOrder
                );
            }
            break;
            case CONTEST_FUTURE: {
                long startTime = DBContract.ContestEntry.getTimeFromContestUri(uri);
                retCursor = mContestByShowSettingsQueryBuilder.query(
                        db,
                        projection, //projection
                        DBContract.ResourceEntry.TABLE_NAME +
                                "." + DBContract.ResourceEntry.RESOURCE_SHOW_COL +
                                " = 1 and " +
                                DBContract.ContestEntry.CONTEST_START_COL + " > ? and " +
                                DBContract.ContestEntry.CONTEST_DURATION_COL + " <= " +
                                Long.toString(maxDuration),
                        new String[]{Long.toString(startTime)},
                        null,
                        null,
                        sortOrder
                );
            }
            break;
            case RESOURCE: {
                retCursor = db.query(
                        DBContract.ResourceEntry.TABLE_NAME, //table name
                        projection, //projection
                        null, //selection
                        null, //selectionArgs
                        null,
                        null,
                        sortOrder
                );
            }
            break;
            case NOTIFICATION: {
                retCursor = db.query(
                        DBContract.NotificationEntry.TABLE_NAME, //table name
                        projection, //projection
                        null, //selection
                        null, //selectionArgs
                        null,
                        null,
                        sortOrder
                );
            }
            break;

            case NOTIFICATION_WITH_ID: {
                long id = DBContract.NotificationEntry.getContestIdFromNotificationEventUri(uri);
                retCursor = db.query(
                        DBContract.NotificationEntry.TABLE_NAME, //table name
                        projection, //projection
                        DBContract.NotificationEntry.NOTIFICATION_CONTEST_ID_COL + " = ?", //selection
                        new String[]{Long.toString(id)}, //selectionArgs
                        null,
                        null,
                        sortOrder
                );
            }
            break;
            /*case CONTEST_WITH_ID:
                return DBContract.ContestEntry.CONTENT_ITEM_TYPE;
            case CONTEST_WITH_RESOURCE_ID:
                return DBContract.ContestEntry.CONTENT_DIR_TYPE;
            case CONTEST_FUTURE:
                return DBContract.ContestEntry.CONTENT_DIR_TYPE;
            case RESOURCE:
                return DBContract.ResourceEntry.CONTENT_DIR_TYPE;
            case RESOURCE_WITH_ID:
                return DBContract.ResourceEntry.CONTENT_ITEM_TYPE;
            case NOTIFICATION:
                return DBContract.NotificationEntry.CONTENT_DIR_TYPE;
            case NOTIFICATION_WITH_ID:
                return DBContract.NotificationEntry.CONTENT_ITEM_TYPE;*/
            default:
                Debug.e("ERROR : " + uri, false);
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        notifyChange(retCursor, getContext(), uri);
        return retCursor;
    }

    private void notifyChange(Cursor retCursor, Context context, Uri uri) {
        if (retCursor != null) {
            retCursor.setNotificationUri(context.getContentResolver(), uri);
        }
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
            case CONTEST_FUTURE:
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
        Debug.i("uri : " + uri, false);
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnUri = null;
        switch (match) {
            case CONTEST: {

            }
            break;
            case RESOURCE: {

            }
            break;
            case NOTIFICATION: {
                long _id = db.insert(DBContract.NotificationEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = DBContract.NotificationEntry.buildNotificationEventUriWithContestId(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
            }
            break;
            default:
                Debug.e("ERROR : " + uri, false);
                throw new UnsupportedOperationException("Update : Unknown uri: " + uri);
        }
        Debug.e("CP insert : " + uri + " match : " + match + " returnUri : " + returnUri, false);
        return returnUri;
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
        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsUpdated;
        switch (match) {
            case CONTEST: {
                rowsUpdated = db.update(DBContract.ContestEntry.TABLE_NAME, values, selection,
                        selectionArgs);
            }
            break;
            case RESOURCE: {
                rowsUpdated = db.update(DBContract.ResourceEntry.TABLE_NAME, values, selection,
                        selectionArgs);
            }
            break;
            case NOTIFICATION: {
                rowsUpdated = db.update(DBContract.NotificationEntry.TABLE_NAME, values, selection,
                        selectionArgs);
            }
            break;
            default:
                Debug.e("ERROR : " + uri, false);
                throw new UnsupportedOperationException("Update : Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            notify(uri);
        }
        Debug.e("CP update : " + uri + " match : " + match + " updated : " + rowsUpdated, false);
        return rowsUpdated;
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
                break;
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


    /**
     * Apply the given set of {@link ContentProviderOperation}, executing inside
     * a {@link SQLiteDatabase} transaction. All changes will be rolled back if
     * any single one fails.
     */
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        Debug.c();
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}
