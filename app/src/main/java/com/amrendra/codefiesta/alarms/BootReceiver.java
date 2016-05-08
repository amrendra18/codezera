package com.amrendra.codefiesta.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by Amrendra Kumar on 09/05/16.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Debug.c();
        // iterate over all notifications and set alarms
        Cursor cursor = context.getContentResolver().query(
                DBContract.NotificationEntry.CONTENT_URI_ALL_NOTIFICATIONS,
                DBContract.NotificationEntry.NOTIFICATION_PROJECTION,
                null,
                null,
                null
        );
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    long notificationId = cursor.getLong(cursor.getColumnIndex(DBContract
                            .NotificationEntry._ID));
                    long contestId = cursor.getLong(cursor.getColumnIndex(DBContract
                            .NotificationEntry.NOTIFICATION_CONTEST_ID_COL));
                    long timeNotification = cursor.getLong(cursor.getColumnIndex(DBContract
                            .NotificationEntry.NOTIFICATION_TIME_COL));
                    if (timeNotification > System.currentTimeMillis() / 1000) {
                        Uri uri = DBContract.ContestEntry.buildContestUriWithId(contestId);
                        Cursor contestCursor = context.getContentResolver().query(
                                uri,
                                null,
                                null,
                                null,
                                null
                        );
                        if (contestCursor != null) {
                            try {
                                if (contestCursor.moveToFirst()) {
                                    Contest contest = Contest.cursorToContest(context, contestCursor);
                                    addNotification(context, contest, timeNotification);
                                }
                            } finally {
                                contestCursor.close();
                            }
                        }
                    } else {
                        int deleted = context.getContentResolver().delete(
                                DBContract.NotificationEntry.CONTENT_URI_ALL_NOTIFICATIONS,
                                DBContract.NotificationEntry._ID + " = ?",
                                new String[]{Long.toString(notificationId)}
                        );
                        Debug.e("deleted : " + deleted, false);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }

    private void addNotification(Context context, Contest contest, long time) {
        Debug.e("time : " + time + " contest: " + contest.getEvent(), false);
        Intent alarmIntent = new Intent(context, NotificationAlarm.class);
        alarmIntent.putExtra(AppUtils.CONTEST_KEY, contest);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, contest.getId(),
                alarmIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService
                (Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time * 1000, pendingIntent);
    }
}
