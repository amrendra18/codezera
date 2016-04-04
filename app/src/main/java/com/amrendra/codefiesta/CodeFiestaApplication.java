package com.amrendra.codefiesta;

import android.app.Application;

import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public class CodeFiestaApplication extends Application {

    @Override
    public void onCreate() {
        Debug.c();
        super.onCreate();
    }
}
