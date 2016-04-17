package com.amrendra.codefiesta.loaders;

import android.content.Context;
import android.database.Cursor;

import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.model.Website;
import com.amrendra.codefiesta.utils.Debug;

import java.util.List;

/**
 * Created by Amrendra Kumar on 17/04/16.
 */
public class ResourceSettingLoader extends BaseLoader<List<Website>> {

    public ResourceSettingLoader(Context context) {
        super(context);
    }

    @Override
    public List<Website> loadInBackground() {
        Debug.c();
        Cursor cursor = getContext().getContentResolver().query(
                DBContract.ResourceEntry.CONTENT_URI_ALL,
                DBContract.ResourceEntry.RESOURCE_PROJECTION,
                null,
                null,
                null
        );

        List<Website> list = Website.cursorToList(cursor);
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
}
