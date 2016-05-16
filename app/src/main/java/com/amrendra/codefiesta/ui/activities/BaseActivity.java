package com.amrendra.codefiesta.ui.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.amrendra.codefiesta.CodeFiestaApplication;
import com.amrendra.codefiesta.bus.BusProvider;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    Tracker mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CodeFiestaApplication application = (CodeFiestaApplication) getApplication();
        mTracker = application.getDefaultTracker();
        application.checkAndSync();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    protected void trackActivity(String screenName) {
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
