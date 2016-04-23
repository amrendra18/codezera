package com.amrendra.codefiesta.ui.fragments;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.adapter.SelectionAdapter;
import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.loaders.ResourceSettingLoader;
import com.amrendra.codefiesta.model.Website;
import com.amrendra.codefiesta.utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Amrendra Kumar on 11/04/16.
 */
public class SelectionFragment extends BaseFragment implements SelectionAdapter.CompetitionSettingsChangedListener {

    private static final int RESOURCE_SELECTION_LOADER = 1;

    @Bind(R.id.contest_list)
    RecyclerView mRecyclerView;

    @OnClick(R.id.save_button)
    void onSaveButtonClick() {
        saveSettingsList();
    }

    SelectionAdapter mAdapter;

    HashMap<Integer, Integer> settingsHashMap;

    public SelectionFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshSettingsList();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveSettingsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.selection_fragment, container, false);
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
        List<Website> list = new ArrayList<>();
        mAdapter = new SelectionAdapter(getActivity(), list, this);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        restartLoader(null);
    }

    private void restartLoader(Bundle bundle) {
        LoaderManager mLoaderManager = getActivity().getSupportLoaderManager();
        if (mLoaderManager.getLoader(RESOURCE_SELECTION_LOADER) == null) {
            mLoaderManager.initLoader(RESOURCE_SELECTION_LOADER, bundle, resourceSettingsLoader);
        } else {
            mLoaderManager.restartLoader(RESOURCE_SELECTION_LOADER, bundle, resourceSettingsLoader);
        }
    }

    private LoaderManager.LoaderCallbacks<List<Website>> resourceSettingsLoader
            = new LoaderManager.LoaderCallbacks<List<Website>>() {

        @Override
        public Loader<List<Website>> onCreateLoader(int id, Bundle args) {
            Debug.c();
            return new ResourceSettingLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<List<Website>> loader, List<Website> list) {
            Debug.c();
            if (list != null) {
                Debug.e(" size : " + list.size());
                mAdapter.resetWebsiteList(list);
            }

        }

        @Override
        public void onLoaderReset(Loader<List<Website>> loader) {
            Debug.c();
            mAdapter.clearWebsites();

        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void settingsChanged(int competitionId, int want) {
        Debug.e("Change required : " + competitionId + " change : " + want);
        settingsHashMap.put(competitionId, want);
    }

    private void refreshSettingsList() {
        Debug.c();
        if (settingsHashMap != null) {
            settingsHashMap.clear();
        } else {
            settingsHashMap = new HashMap<>();
        }
    }

    private void saveSettingsList() {
        Debug.c();
        if (settingsHashMap != null && settingsHashMap.size() > 0) {
            ArrayList<ContentProviderOperation> cpo = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : settingsHashMap.entrySet()) {
                int resourceId = entry.getKey();
                int show = entry.getValue();
                Debug.e("changing operation for : " + resourceId + " change : " + show);
                ContentValues cv = new ContentValues();
                cv.put(DBContract.ResourceEntry.RESOURCE_ID_COL, resourceId);
                cv.put(DBContract.ResourceEntry.RESOURCE_SHOW_COL, show);
                cpo.add(
                        ContentProviderOperation.newUpdate(DBContract.ResourceEntry.CONTENT_URI_ALL)
                                .withValues(cv).build()
                );
            }
            try {
                ContentProviderResult[] cpr = getActivity().getContentResolver()
                        .applyBatch(getString(R.string.contentauthority), cpo);
                Debug.showToastShort("Updated settings for " + cpr.length + " websites", getActivity());
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
                Debug.e(" error : " + e.getLocalizedMessage());
            }
            Debug.c();
            refreshSettingsList();
        }
    }
}

