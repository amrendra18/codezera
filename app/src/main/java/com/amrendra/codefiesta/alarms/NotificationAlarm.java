package com.amrendra.codefiesta.alarms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.text.Spanned;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.ui.activities.MainActivity;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.CustomDate;
import com.amrendra.codefiesta.utils.DateUtils;
import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by Amrendra Kumar on 08/05/16.
 */
public class NotificationAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Contest contest = intent.getParcelableExtra(AppUtils.CONTEST_KEY);
        Debug.e(contest.getEvent(), false);
        Intent newIntent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, newIntent, 0);

        Notification n;

        final int resourceId = contest.getWebsite().getId();
        String resourceName = AppUtils.getResourceName(context, resourceId);
        final String shortResourceName = AppUtils.getGoodResourceName(resourceName);

        final long starTime = DateUtils.getEpochTime(contest.getStart());
        final CustomDate startDate = new CustomDate(DateUtils.epochToDateTimeLocalShow(starTime));

        Spanned msg = Html.fromHtml(String.format(context.getString(R.string.notification_string),
                shortResourceName, startDate.getFullTime()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            n = new NotificationCompat.Builder(context)
                    .setTicker(context.getString(R.string.app_name))
                    .setContentTitle(contest.getEvent())
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentText(msg)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setSmallIcon(R.drawable.icon_notification)
                    .setContentIntent(pIntent)
                    .build();
        } else {
            n = new NotificationCompat.Builder(context)
                    .setTicker(context.getString(R.string.app_name))
                    .setContentTitle(contest.getEvent())
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentText(msg)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .build();
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(contest.getId(), n);


        //delete the notification entry from db
        context.getContentResolver().delete(
                DBContract.NotificationEntry.CONTENT_URI_ALL_NOTIFICATIONS,
                DBContract.NotificationEntry.NOTIFICATION_CONTEST_ID_COL + " = ?",
                new String[]{Long.toString(contest.getId())}
        );
    }
}
