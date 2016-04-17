package com.amrendra.codefiesta.ui.activities;

import android.os.Bundle;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.utils.TrackingConstants;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        trackActivity(TrackingConstants.DETAIL_ACTIVITY_SCREEN_NAME);
    }
}
