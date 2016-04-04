package com.amrendra.codefiesta.ui.activities;

import android.os.Bundle;

import com.amrendra.codefiesta.R;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
