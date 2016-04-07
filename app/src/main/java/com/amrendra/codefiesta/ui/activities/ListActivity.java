package com.amrendra.codefiesta.ui.activities;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.adapters.NavAdapter;
import com.amrendra.codefiesta.loaders.WebsiteLoader;
import com.amrendra.codefiesta.model.ApiResponse;
import com.amrendra.codefiesta.model.Website;
import com.amrendra.codefiesta.utils.Debug;

import butterknife.Bind;

public class ListActivity extends BaseActivity {
    private static final int RESOURCE_LOADER = 0;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ActionBarDrawerToggle mDrawerToggle;

    private String[] navMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        navMenuTitles = getResources().getStringArray(R.array.nav_menus);
        setContentView(R.layout.activity_list);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setLogo(R.mipmap.ic_launcher);
            actionBar.setDisplayUseLogoEnabled(true);
        }

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new NavAdapter(navMenuTitles);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R
                .string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
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
        //initResourceLoader(null);
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

