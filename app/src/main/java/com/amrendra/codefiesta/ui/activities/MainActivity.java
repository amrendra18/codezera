package com.amrendra.codefiesta.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.amrendra.codefiesta.BuildConfig;
import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.bus.BusProvider;
import com.amrendra.codefiesta.bus.events.CalendarPermissionGrantedEvent;
import com.amrendra.codefiesta.bus.events.ContestClickEvent;
import com.amrendra.codefiesta.bus.events.SettingsSaveEvent;
import com.amrendra.codefiesta.bus.events.SnackBarMessageDetailFragmentEvent;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.ui.fragments.CreditsFragment;
import com.amrendra.codefiesta.ui.fragments.CurrentFragment;
import com.amrendra.codefiesta.ui.fragments.DetailFragment;
import com.amrendra.codefiesta.ui.fragments.PastFragment;
import com.amrendra.codefiesta.ui.fragments.SelectionFragment;
import com.amrendra.codefiesta.ui.fragments.UpcomingFragment;
import com.amrendra.codefiesta.utils.AppUtils;
import com.amrendra.codefiesta.utils.Debug;
import com.amrendra.codefiesta.utils.DeviceUtils;
import com.amrendra.codefiesta.utils.TrackingConstants;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private static final long DRAWER_CLOSE_DELAY_MS = 150;
    private static final String NAV_ITEM_ID = "navItemId";
    private static final String TITLE = "title";

    boolean mTwoPane = false;

    private final Handler mDrawerActionHandler = new Handler();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;

    String mTitle;
    int mNavItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.c();
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.right_content_frame) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
        Debug.e("TABLET : " + mTwoPane + " isTablet: " + DeviceUtils.isTablet(this), false);

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.nav_current_menu;
            mTitle = getString(R.string.live_contests);
        } else {
            Debug.e("SavedInstance not null", false);
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
            mTitle = savedInstanceState.getString(TITLE);
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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
        if (savedInstanceState == null) {
            navigate(mNavigationView.getMenu().findItem(mNavItemId));
        } else {
            mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
        }
    }

    @Subscribe
    public void settingsSaveEventTriggered(SettingsSaveEvent event) {
        Debug.c();
        navigate(mNavigationView.getMenu().findItem(R.id.nav_current_menu));
    }

    private void navigate(final MenuItem menuItem) {
        // perform the actual navigation logic, updating the main content fragment etc

        int itemId = menuItem.getItemId();
        Class fragmentClass = null;
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_current_menu:
                fragmentClass = CurrentFragment.class;
                mTitle = getString(R.string.live_contests);
                break;
            case R.id.nav_upcoming_menu:
                fragmentClass = UpcomingFragment.class;
                mTitle = getString(R.string.upcoming_contests);
                break;
            case R.id.nav_past_menu:
                fragmentClass = PastFragment.class;
                mTitle = getString(R.string.past_contest);
                break;
            case R.id.nav_settings_menu:
                fragmentClass = SelectionFragment.class;
                mTitle = getString(R.string.nav_settings);
                break;
            case R.id.nav_share_menu:
                break;
            case R.id.nav_rate_menu:
                break;
            case R.id.nav_feedback_menu:
                sendEmail();
                break;
            case R.id.nav_open_source_menu:
                AppUtils.openWebsite(this, AppUtils.GIT_URL);
                break;
            case R.id.nav_credits_menu:
                fragmentClass = CreditsFragment.class;
                mTitle = getString(R.string.nav_credits);
                break;
            default:
                fragmentClass = CurrentFragment.class;
                mTitle = getString(R.string.live_contests);
        }

        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            changeFragment(fragment, mTitle, itemId);
            menuItem.setChecked(true);
            mNavItemId = itemId;
        } else {
            mNavigationView.getMenu().findItem(mNavItemId).setChecked(true);
        }

    }

    private void changeFragment(Fragment fragment, String title, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mTwoPane && fragment instanceof DetailFragment) {
            fragmentManager.beginTransaction()
                    .replace(R.id.right_content_frame, fragment)
                    .setBreadCrumbShortTitle(id)
                    .addToBackStack(mTitle)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.left_content_frame, fragment)
                    .setBreadCrumbShortTitle(id)
                    .addToBackStack(title)
                    .commit();

            setUpTitle(title);
        }
    }

    public void setUpTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        trackActivity(TrackingConstants.LIST_ACTIVITY_SCREEN_NAME);
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem);
            }
        }, DRAWER_CLOSE_DELAY_MS);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
                int id = getSupportFragmentManager().getBackStackEntryAt(pos - 2).getBreadCrumbShortTitleRes();
                mNavItemId = id;
                mNavigationView.getMenu().findItem(id).setChecked(true);
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
        bundle.putParcelable(AppUtils.CONTEST_KEY, contest);
        detailFragment.setArguments(bundle);
        changeFragment(detailFragment, mTitle, mNavItemId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case DetailFragment.MY_PERMISSIONS_REQUEST_WRITE_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    BusProvider.getInstance().post(new CalendarPermissionGrantedEvent());
                } else {
                    String text = getString(R.string.calendar_permission_denied);
                    BusProvider.getInstance().post(new SnackBarMessageDetailFragmentEvent(text));
                }
                break;
            }
        }
    }

    public boolean isTwoPane() {
        return mTwoPane;
    }

    private void sendEmail() {
        String subject = String.format(getString(R.string.email_subject), getString(R.string.app_name), BuildConfig.VERSION_NAME);
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"amrendra.nitb+appfeedback@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, "");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, getString(R.string.send_email)));
    }
}

