package com.amrendra.codefiesta.ui.activities;

import android.os.Bundle;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.utils.Debug;

public class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        setContentView(R.layout.activity_list);
    }

}
