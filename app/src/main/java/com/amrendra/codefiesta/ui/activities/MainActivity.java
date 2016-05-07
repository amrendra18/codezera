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
import com.amrendra.codefiesta.bus.events.ContestClickEvent;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.sync.CodeFiestaSyncAdapter;
import com.amrendra.codefiesta.ui.fragments.CurrentFragment;
import com.amrendra.codefiesta.ui.fragments.DetailFragment;
import com.amrendra.codefiesta.ui.fragments.PastFragment;
import com.amrendra.codefiesta.ui.fragments.SelectionFragment;
import com.amrendra.codefiesta.ui.fragments.UpcomingFragment;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.CalendarUtils;
import com.amrendra.codefiesta.utils.Debug;
import com.amrendra.codefiesta.utils.TrackingConstants;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final long DRAWER_CLOSE_DELAY_MS = 150;
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

        if (null == savedInstanceState) {
            // select the correct nav menu item
            mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
            navigate(mNavigationView.getMenu().findItem(mNavItemId));
        }
    }

    private void navigate(final MenuItem menuItem) {
        // perform the actual navigation logic, updating the main content fragment etc
        int itemId = menuItem.getItemId();
        Class fragmentClass = null;
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_current_menu:
                fragmentClass = CurrentFragment.class;
                mTitle = "Current Comp";
                break;
            case R.id.nav_upcoming_menu:
                CalendarUtils.getInstance(this).getCalendarId();
                fragmentClass = UpcomingFragment.class;
                mTitle = "Future Comp";
                break;
            case R.id.nav_past_menu:
                fragmentClass = PastFragment.class;
                mTitle = "Past Comp";
                break;
            case R.id.nav_settings_menu:
                fragmentClass = SelectionFragment.class;
                mTitle = "Selection";
                break;
            case R.id.nav_rate_menu:
                //CodeFiestaSyncAdapter.syncImmediately(this);
                break;
            case R.id.nav_feedback_menu:
                break;
            case R.id.nav_open_source_menu:
                CodeFiestaSyncAdapter.syncImmediately(this);
                fragmentClass = CurrentFragment.class;
                mTitle = "Current Comp";
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

        changeFragment(fragment, mTitle);
    }

    private void changeFragment(Fragment fragment, String title) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(title)
                .commit();

        setUpTitle(title);
    }

    public void setUpTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Debug.c();
    }

    @Override
    protected void onResume() {
        super.onResume();
        trackActivity(TrackingConstants.LIST_ACTIVITY_SCREEN_NAME);
        Debug.c();
    }


    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        if (menuItem.getItemId() != mNavItemId) {
            // update highlighted item in the navigation menu
            menuItem.setChecked(true);
            // allow some time after closing the drawer before performing real navigation
            // so the user can see what is happening
            mNavItemId = menuItem.getItemId();
            mDrawerActionHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigate(menuItem);
                }
            }, DRAWER_CLOSE_DELAY_MS);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
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
            int pos = getSupportFragmentManager().getBackStackEntryCount();
            if (pos < 2) {
                finish();
            } else {
                String tr = getSupportFragmentManager().getBackStackEntryAt(pos - 2).getName();
                setUpTitle(tr);
            }
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
        outState.putString(TITLE, mTitle);
    }

    @Subscribe
    public void onContestListItemClick(ContestClickEvent event) {
        Contest contest = event.getContest();
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppUtils.CONTEST_ID_KEY, contest);
        detailFragment.setArguments(bundle);
        changeFragment(detailFragment, contest.getEvent());
    }
}

