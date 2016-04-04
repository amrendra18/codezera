package com.amrendra.codefiesta.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.amrendra.codefiesta.utils.Debug;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}