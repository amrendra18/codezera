package com.amrendra.codefiesta;

import android.app.Application;

import com.amrendra.codefiesta.utils.Debug;
import com.facebook.stetho.Stetho;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public class CodeFiestaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }


    public static void shouldNotHappen(String msg) {
        Debug.e(msg);
    }
}
