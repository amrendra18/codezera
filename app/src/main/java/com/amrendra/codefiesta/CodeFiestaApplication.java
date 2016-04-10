package com.amrendra.codefiesta;

import android.app.Application;
import android.content.Context;

import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.Debug;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public class CodeFiestaApplication extends Application {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = installLeakCanary();
        Stetho.initializeWithDefaults(this);
        AppUtils.cacheResources(this);
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
}
