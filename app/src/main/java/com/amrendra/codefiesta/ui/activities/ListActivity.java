package com.amrendra.codefiesta.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.ui.fragments.CurrentFragment;
import com.amrendra.codefiesta.ui.fragments.PastFragment;
import com.amrendra.codefiesta.ui.fragments.UpcomingFragment;
import com.amrendra.codefiesta.utils.Debug;

import butterknife.Bind;

public class ListActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private static final String TITLE = "title";

    private final Handler mDrawerActionHandler = new Handler();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;

    private String mTitle;
    private int mNavItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        setContentView(R.layout.activity_list);

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.nav_current_menu;
            mTitle = "Current Competition";//getString(R.string.app_name);
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
            mTitle = savedInstanceState.getString(TITLE);
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setLogo(R.mipmap.ic_launcher);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setTitle(mTitle);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R
                .string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // getSupportActionBar().setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
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
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        // select the correct nav menu item
        mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
        navigate(mNavigationView.getMenu().findItem(mNavItemId));
    }

    private void navigate(final MenuItem menuItem) {
        // perform the actual navigation logic, updating the main content fragment etc
        int itemId = menuItem.getItemId();
        Class fragmentClass = null;
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_current_menu:
                Debug.showToastShort("nav_current_menu", this);
                fragmentClass = CurrentFragment.class;
                break;
            case R.id.nav_upcoming_menu:
                Debug.showToastShort("nav_upcoming_menu", this);
                fragmentClass = UpcomingFragment.class;
                break;
            case R.id.nav_past_menu:
                Debug.showToastShort("nav_past_menu", this);
                fragmentClass = PastFragment.class;
                break;
            case R.id.nav_settings_menu:
                Debug.showToastShort("nav_settings_menu", this);
                break;
            case R.id.nav_rate_menu:
                Debug.showToastShort("nav_rate_menu", this);
                //CodeFiestaSyncAdapter.syncImmediately(this);
                break;
            case R.id.nav_feedback_menu:
                Debug.showToastShort("nav_feedback_menu", this);
                break;
            case R.id.nav_open_source_menu:
                Debug.showToastShort("nav_open_source_menu", this);
                break;
            default:
                fragmentClass = CurrentFragment.class;
                Debug.showToastShort("default", this);
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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


    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        int itemId = menuItem.getItemId();
        switch (itemId) {
            case R.id.nav_current_menu:
                mTitle = "Current Comp";
                break;
            case R.id.nav_upcoming_menu:
                mTitle = "Upcoming Comp";
                break;
            case R.id.nav_past_menu:
                mTitle = "Past Comp";
                break;
            case R.id.nav_settings_menu:
                break;
            case R.id.nav_rate_menu:
                break;
            case R.id.nav_feedback_menu:
                break;
            case R.id.nav_open_source_menu:
                break;
            default:
                mTitle = getString(R.string.app_name);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem);
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
        outState.putString(TITLE, mTitle);
    }
}

