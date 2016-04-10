package com.amrendra.codefiesta.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.adapter.SelectionAdapter;
import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.utils.Debug;

import butterknife.Bind;

/**
 * Created by Amrendra Kumar on 11/04/16.
 */
public class SelectionFragment extends BaseFragment {

    private static final int RESOURCE_SELECTION_LOADER = 1;

    @Bind(R.id.contest_list)
    ListView mListView;

    SelectionAdapter mAdapter;

    public SelectionFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
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
        mAdapter = new SelectionAdapter(getActivity(), null, 0);
        mListView.setAdapter(mAdapter);
        restartLoader(null);

    }

    private void restartLoader(Bundle bundle) {
        LoaderManager mLoaderManager = getActivity().getSupportLoaderManager();
        if (mLoaderManager.getLoader(RESOURCE_SELECTION_LOADER) == null) {
            mLoaderManager.initLoader(RESOURCE_SELECTION_LOADER, bundle, currentContestLoaderCallbacks);
        } else {
            mLoaderManager.restartLoader(RESOURCE_SELECTION_LOADER, bundle, currentContestLoaderCallbacks);
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> currentContestLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Debug.c();
            return new CursorLoader(
                    getActivity(),
                    DBContract.ResourceEntry.CONTENT_URI_ALL,
                    null,
                    null,
                    null,
                    DBContract.ResourceEntry.RESOURCE_NAME_COL + " ASC"
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            Debug.c();
            mAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Debug.c();
            mAdapter.swapCursor(null);
        }
    };


}

