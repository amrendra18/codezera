package com.amrendra.codefiesta.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.amrendra.codefiesta.CodeFiestaApplication;
import com.amrendra.codefiesta.bus.BusProvider;
import com.amrendra.codefiesta.bus.events.ContestClickEvent;
import com.amrendra.codefiesta.model.Contest;
import com.amrendra.codefiesta.ui.activities.MainActivity;
import com.amrendra.codefiesta.utils.Debug;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Amrendra Kumar on 05/04/16.
 */
public abstract class BaseFragment extends Fragment {

    private Toast mToast;
    Tracker mTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CodeFiestaApplication application = (CodeFiestaApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusProvider.getInstance().unregister(this);
    }

    protected void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    protected void trackFragment(String screenName) {
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected void showToast(int resId) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
        mToast.show();
    }

    protected void checkForNewLoad(Cursor cursor) {
        boolean twoPane = ((MainActivity) getActivity()).isTwoPane();
        Debug.e(" twoPane: " + twoPane, false);
        if (twoPane && cursor != null && cursor.moveToFirst()) {
            final Contest contest = Contest.cursorToContest(getActivity(), cursor);
            if (contest != null) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        BusProvider.getInstance().post(new ContestClickEvent(contest));
                    }
                });
            }
        }
    }
}
