package com.amrendra.codefiesta.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by amrendrk on 5/16/16.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    Context mContext = null;
    ContentResolver mContentResolver = null;

    public WidgetDataProvider(Context context, Intent intent) {
        Debug.c();
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
