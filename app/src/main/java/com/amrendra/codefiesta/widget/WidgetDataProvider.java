package com.amrendra.codefiesta.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.CustomDate;
import com.amrendra.codefiesta.utils.DateUtils;
import com.amrendra.codefiesta.utils.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amrendrk on 5/16/16.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<Contest> contests = null;
    Context mContext = null;
    ContentResolver mContentResolver = null;

    public WidgetDataProvider(Context context, Intent intent) {
        Debug.c();
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_list_item);
        Contest c = contests.get(position);
        final int resourceId = c.getWebsite().getId();
        String resourceName = AppUtils.getResourceName(mContext, resourceId);
        final String shortResourceName = AppUtils.getGoodResourceName(resourceName);
        view.setTextViewText(R.id.contest_title_tv, c.getEvent());
        view.setTextViewText(R.id.contest_website_tv, shortResourceName);
        view.setImageViewResource(R.id.resource_logo, AppUtils.getImageForResource(resourceName));
        final long starTime = DateUtils.getEpochTime(c.getStart());
        final long endTime = DateUtils.getEpochTime(c.getEnd());
        final CustomDate startDate = new CustomDate(DateUtils.epochToDateTimeLocalShow(starTime));
        final CustomDate endDate = new CustomDate(DateUtils.epochToDateTimeLocalShow(endTime));

        view.setTextViewText(R.id.start_time, startDate.getFullTime());
        view.setTextViewText(R.id.end_time, endDate.getFullTime());
        return view;
    }

    private void initData() {
        if (contests != null) {
            contests.clear();
        }
        contests = new ArrayList<>();
        Debug.c();
        Uri uri = DBContract.ContestEntry.buildLiveContestUri(DateUtils.getCurrentEpochTime());
        Cursor cursor = mContentResolver.query(
                uri,
                null,
                null,
                null,
                DBContract.ContestEntry.CONTEST_END_COL + " ASC"
        );
        if (cursor != null && cursor.getCount() > 0) {
            Debug.e("Fetch for widget : " + cursor.getCount(), false);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contests.add(Contest.cursorToContest(mContext, cursor));
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public void onCreate() {
        Debug.c();
    }

    @Override
    public void onDataSetChanged() {
        Debug.c();
        initData();
    }

    @Override
    public void onDestroy() {
        contests.clear();
    }

    @Override
    public int getCount() {
        return contests.size();
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
