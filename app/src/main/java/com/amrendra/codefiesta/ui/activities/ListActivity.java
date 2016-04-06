package com.amrendra.codefiesta.ui.activities;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.amrendra.codefiesta.BuildConfig;
import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.loaders.WebsiteLoader;
import com.amrendra.codefiesta.model.ApiResponse;
import com.amrendra.codefiesta.model.Website;
import com.amrendra.codefiesta.utils.Debug;

public class ListActivity extends BaseActivity {

    private static final int RESOURCE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        setContentView(R.layout.activity_list);

        Debug.showToastShort(BuildConfig.API_KEY, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Debug.c();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Debug.c();
        initResourceLoader(null);
    }

    private void initResourceLoader(Bundle bundle) {
        if (getSupportLoaderManager().getLoader(RESOURCE_LOADER) == null) {
            getSupportLoaderManager().initLoader(RESOURCE_LOADER, bundle, resourceLoaderCallbacks);
        } else {
            getSupportLoaderManager().restartLoader(RESOURCE_LOADER, bundle, resourceLoaderCallbacks);
        }
    }

    private LoaderManager.LoaderCallbacks<ApiResponse<Website.Response>> resourceLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<ApiResponse<Website.Response>>() {

        @Override
        public Loader<ApiResponse<Website.Response>> onCreateLoader(int id, Bundle args) {
            Debug.c();
            return new WebsiteLoader(ListActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<ApiResponse<Website.Response>> loader, ApiResponse<Website.Response> data) {
            Debug.c();
            Debug.i(data.toString());
            Website.Response response = data.getContent();
            for (Website website : response.websites) {
                Debug.d(website.toString());
            }
        }

        @Override
        public void onLoaderReset(Loader<ApiResponse<Website.Response>> loader) {
            Debug.c();
        }
    };
}

