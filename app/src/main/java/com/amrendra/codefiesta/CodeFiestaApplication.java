package com.amrendra.codefiesta;

import android.app.Application;
import android.content.Context;

import com.amrendra.codefiesta.sync.CodeFiestaSyncAdapter;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.Debug;
import com.amrendra.codefiesta.utils.UserPreferences;
import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public class CodeFiestaApplication extends Application {

    private Tracker mTracker;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = installLeakCanary();
        Stetho.initializeWithDefaults(this);
        AppUtils.cacheResources(this);
        getDefaultTracker();
        checkAndSync();
    }

    private void checkAndSync() {
        long lastSyncTime = UserPreferences.getInstance(this)
                .readValue(AppUtils.LAST_SYNC_PERFORMED, AppUtils
                        .LAST_SYNC_PERFORMED_DEFAULT_VALUE);

        long currTime = System.currentTimeMillis() / 1000;
        if (currTime - lastSyncTime > AppUtils.SIX_HOURS) {
            CodeFiestaSyncAdapter.syncImmediately(this);
        }
    }


    public static void shouldNotHappen(String msg) {
        Debug.e(msg);
    }

    protected RefWatcher installLeakCanary() {
        return LeakCanary.install(this);
        //return RefWatcher.DISABLED;
    }

    public static RefWatcher getRefWatcher(Context context) {
        CodeFiestaApplication application = (CodeFiestaApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}
