package com.amrendra.codefiesta.ui.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.adapter.ContestAdapter;
import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.utils.DateUtils;
import com.amrendra.codefiesta.utils.Debug;
import com.amrendra.codefiesta.utils.TrackingConstants;

import butterknife.Bind;

public class PastFragment extends BaseFragment {

    private static final int CURRENT_CONTESTS_LOADER = 0;
    @Bind(R.id.list_fragment_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.contest_list)
    ListView mListView;
    @Bind(R.id.view_error)
    RelativeLayout errorLayout;
    @Bind(R.id.view_loading)
    RelativeLayout loadingLayout;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.error_message)
    TextView errorTv;
    ContestAdapter mAdapter;

    public PastFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        trackFragment(TrackingConstants.PAST_FRAGMENT_SCREEN_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    /*
    Lifecycle of a fragment
    1. onInflate
    2. onAttach()
    3. onCreate()
    4. onCreateView()
    5. onViewCreated()
    Activity.onCreate()
    6. onActivityCreated()
    7. onStart()
    8. onResume() Fragment is visible now
    9. onPause()
    10. onStop()
    11. onDestroyView();
    12. onDestroy()
    13. onDetach
    */

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ContestAdapter(getActivity(), null, 0);
        mListView.setAdapter(mAdapter);
        restartLoader(null);
    }

    private void restartLoader(Bundle bundle) {
        progressBar.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
        errorTv.setVisibility(View.INVISIBLE);
        LoaderManager mLoaderManager = getActivity().getSupportLoaderManager();
        if (mLoaderManager.getLoader(CURRENT_CONTESTS_LOADER) == null) {
            mLoaderManager.initLoader(CURRENT_CONTESTS_LOADER, bundle, currentContestLoaderCallbacks);
        } else {
            mLoaderManager.restartLoader(CURRENT_CONTESTS_LOADER, bundle, currentContestLoaderCallbacks);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> currentContestLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Debug.c();
            Uri uri = DBContract.ContestEntry.buildPastContestUri(DateUtils.getCurrentEpochTime());
            return new CursorLoader(
                    getActivity(),
                    uri,
                    null,
                    null,
                    null,
                    DBContract.ContestEntry.CONTEST_END_COL + " ASC"
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            Debug.c();
            mAdapter.swapCursor(cursor);
            progressBar.setVisibility(View.INVISIBLE);
            if (cursor != null && cursor.getCount() > 0) {
                errorTv.setVisibility(View.INVISIBLE);
                errorLayout.setVisibility(View.INVISIBLE);
                checkForNewLoad(cursor);
            } else {
                /*if (!AppUtils.isNetworkConnected(getActivity())) {
                    Debug.showToastShort(getActivity().getString(R.string
                            .internet), getActivity(), true);
                }*/
                errorTv.setText(getActivity().getString(R.string.nomatch));
                errorTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.egg_empty, 0, 0);
                errorTv.setContentDescription(getActivity().getString(R.string.nomatch));
                errorTv.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Debug.c();
            mAdapter.swapCursor(null);
        }
    };
}
