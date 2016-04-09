package com.amrendra.codefiesta.db;

import android.content.ContentResolver;
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
    public static final String PATH_TIME = "time";


    public static final class ResourceEntry implements BaseColumns {
        public static final String TABLE_NAME = "resources_table";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESOURCE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESOURCE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESOURCE;

        public static final String RESOURCE_ID_COL = "id";
        public static final String RESOURCE_NAME_COL = "name";
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

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTEST).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTEST;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CONTEST;

        public static final String CONTEST_ID_COL = "id";
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

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOTIFICATION).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTIFICATION;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTIFICATION;

        public static final String NOTIFICATION_CONTEST_ID_COL = "contest_id";
        public static final String NOTIFICATION_TIME_COL = "time";

        public static final String[] NOTIFICATION_PROJECTION = new String[]{
                _ID,
                NOTIFICATION_CONTEST_ID_COL,
                NOTIFICATION_TIME_COL
        };
    }
}
