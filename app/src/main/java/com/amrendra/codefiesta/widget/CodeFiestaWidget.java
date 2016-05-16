package com.amrendra.codefiesta.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.ui.activities.MainActivity;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by amrendrk on 5/16/16.
 */
public class CodeFiestaWidget extends AppWidgetProvider {

    public CodeFiestaWidget() {

    }

    public static void onUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_title_bar, pendingIntent);
        views.setRemoteAdapter(R.id.contest_list_widget, new Intent(context, WidgetService.class));
        views.setEmptyView(R.id.contest_list_widget, R.id.contest_list_widget_empty);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            onUpdate(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent != null) {
            Debug.e("intent : " + intent.getAction(), false);
            if (intent.getAction().equals(AppUtils.BROADCAST_DATA_UPDATED)) {
                Debug.e("Need to update widget", false);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.contest_list_widget);
            }
        }
    }
}
