package com.amrendra.codefiesta.loaders;

import android.content.Context;

import com.amrendra.codefiesta.model.Contest;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public class ContestsLoader extends BaseLoader<Contest.Response> {


    public ContestsLoader(Context context) {
        super(context);
    }

    @Override
    public Contest.Response loadInBackground() {
        return null;
    }
}
