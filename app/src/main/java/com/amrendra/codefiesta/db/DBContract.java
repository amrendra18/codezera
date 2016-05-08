package com.amrendra.codefiesta.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Amrendra Kumar on 09/04/16.
 */
public class DBContract {

    public static final String CONTENT_AUTHORITY = "com.amrendra.codefiesta";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RESOURCE = "resource";
    public static final String PATH_CONTEST = "contests";
    public static final String PATH_NOTIFICATION = "notifications";
    public static final String PATH_CALENDAR = "calendar";
    public static final String PATH_LIVE = "live";
    public static final String PATH_FUTURE = "future";
    public static final String PATH_PAST = "past";


    public static final class ResourceEntry implements BaseColumns {
        public static final String TABLE_NAME = "resources_table";

        public static final Uri CONTENT_URI_ALL =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESOURCE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESOURCE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESOURCE;

        public static final String RESOURCE_ID_COL = "rid";
        public static final String RESOURCE_NAME_COL = "rname";
        public static final String RESOURCE_SHOW_COL = "show";

        public static final String[] RESOURCE_PROJECTION = new String[]{
                _ID,
                RESOURCE_ID_COL,
                RESOURCE_NAME_COL,
                RESOURCE_SHOW_COL
        };
    }

    public static final class ContestEntry implements BaseColumns {
        public static final String TABLE_NAME = "contests_table";

        public static final Uri CONTENT_URI_ALL =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTEST).build();

        public static final Uri CONTENT_URI_LIVE =
                CONTENT_URI_ALL.buildUpon().appendPath(PATH_LIVE).build();

        public static Uri buildLiveContestUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_LIVE, id);
        }

        public static final Uri CONTENT_URI_FUTURE =
                CONTENT_URI_ALL.buildUpon().appendPath(PATH_FUTURE).build();

        public static Uri buildFutureContestUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_FUTURE, id);
        }

        public static final Uri CONTENT_URI_PAST =
                CONTENT_URI_ALL.buildUpon().appendPath(PATH_PAST).build();

        public static Uri buildPastContestUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_PAST, id);
        }

        public static long getTimeFromContestUri(Uri uri) {
            return ContentUris.parseId(uri);
        }

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTEST;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTEST;

        public static final String CONTEST_ID_COL = "cid";
        public static final String CONTEST_NAME_COL = "name";
        public static final String CONTEST_URL_COL = "url";
        public static final String CONTEST_RESOURCE_ID_COL = "resource_id";
        public static final String CONTEST_START_COL = "start";
        public static final String CONTEST_END_COL = "end";
        public static final String CONTEST_DURATION_COL = "duration";


        public static final String[] CONTEST_PROJECTION = new String[]{
                _ID,
                CONTEST_ID_COL,
                CONTEST_NAME_COL,
                CONTEST_URL_COL,
                CONTEST_RESOURCE_ID_COL,
                CONTEST_START_COL,
                CONTEST_END_COL,
                CONTEST_DURATION_COL
        };

    }

    public static final class NotificationEntry implements BaseColumns {
        public static final String TABLE_NAME = "notifications_table";

        public static final Uri CONTENT_URI_ALL_NOTIFICATIONS =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOTIFICATION).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTIFICATION;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTIFICATION;

        public static final String NOTIFICATION_CONTEST_ID_COL = "contest_id";
        public static final String NOTIFICATION_TIME_COL = "notify_time";
        public static final String NOTIFICATION_CONTEST_START_TIME_COL = "start_time";

        public static final String[] NOTIFICATION_PROJECTION = new String[]{
                _ID,
                NOTIFICATION_CONTEST_ID_COL,
                NOTIFICATION_TIME_COL,
                NOTIFICATION_CONTEST_START_TIME_COL
        };
    }
}
