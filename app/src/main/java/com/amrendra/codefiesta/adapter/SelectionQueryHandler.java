package com.amrendra.codefiesta.adapter;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.lang.ref.WeakReference;

/**
 * Created by Amrendra Kumar on 11/04/16.
 */
public class SelectionQueryHandler extends AsyncQueryHandler {

    private WeakReference<OnQueryCompleteListener> mListener;

    public SelectionQueryHandler(ContentResolver cr, OnQueryCompleteListener listener) {
        super(cr);
        this.mListener = new WeakReference<OnQueryCompleteListener>(listener);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onQueryComplete(cursor);
        } else {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onInsertComplete(uri);
        }
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onUpdateComplete(result);
        }
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onDeleteComplete(result);
        }
    }

    public interface OnQueryCompleteListener {
        void onQueryComplete(Cursor cursor);

        void onInsertComplete(Uri uri);

        void onDeleteComplete(int result);

        void onUpdateComplete(int result);
    }
}

