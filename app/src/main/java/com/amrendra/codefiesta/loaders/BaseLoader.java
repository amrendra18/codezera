package com.amrendra.codefiesta.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    private T mData;

    public BaseLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            releaseResources(data);
            return;
        }

        T oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    private void releaseResources(T oldData) {
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }


    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }
    }

    @Override
    public void onCanceled(T data) {
        super.onCanceled(data);
        releaseResources(data);
    }
}
