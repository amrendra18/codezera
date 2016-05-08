package com.amrendra.codefiesta.handler;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.lang.ref.WeakReference;

/**
 * Created by Amrendra Kumar on 25/04/16.
 */
public class DBQueryHandler extends AsyncQueryHandler {

    private WeakReference<OnQueryCompleteListener> mListener;

    public DBQueryHandler(ContentResolver cr, OnQueryCompleteListener listener) {
        super(cr);
        this.mListener = new WeakReference<>(listener);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onQueryComplete(token, cursor);
        } else {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onInsertComplete(int token, Object cookie, Uri uri) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onInsertComplete(token, uri);
        }
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onUpdateComplete(token, result);
        }
    }

    @Override
    protected void onDeleteComplete(int token, Object cookie, int result) {
        if (mListener != null && mListener.get() != null) {
            mListener.get().onDeleteComplete(token, result);
        }
    }

    public interface OnQueryCompleteListener {
        void onQueryComplete(int token, Cursor cursor);

        void onInsertComplete(int token, Uri uri);

        void onDeleteComplete(int token, int result);

        void onUpdateComplete(int token, int result);
    }
}

