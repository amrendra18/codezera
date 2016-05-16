package com.amrendra.codefiesta.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by amrendrk on 5/16/16.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
