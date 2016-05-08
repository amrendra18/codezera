package com.amrendra.codefiesta.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.amrendra.codefiesta.CodeFiestaApplication;
import com.amrendra.codefiesta.bus.BusProvider;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;

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
        ButterKnife.bind(this, view);
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
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
}
