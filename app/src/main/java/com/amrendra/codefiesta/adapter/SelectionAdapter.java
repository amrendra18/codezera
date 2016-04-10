package com.amrendra.codefiesta.adapter;

/**
 * Created by Amrendra Kumar on 11/04/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.db.DBContract;
import com.amrendra.codefiesta.utils.Debug;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Amrendra Kumar on 10/04/16.
 */
public class SelectionAdapter extends CursorAdapter {

    final LayoutInflater mInflator;

    public SelectionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflator = LayoutInflater.from(context);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View mItem = mInflator.inflate(R.layout.select_item_row, parent, false);
        ViewHolder mHolder = new ViewHolder(mItem);
        mItem.setTag(mHolder);
        return mItem;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final ViewHolder mHolder = (ViewHolder) view.getTag();
        int show = cursor.getInt(cursor.getColumnIndex(DBContract
                .ResourceEntry.RESOURCE_SHOW_COL));
        mHolder.checkBox.setChecked(show != 0);
        mHolder.resourceName.setText(cursor.getString(cursor.getColumnIndex(DBContract
                .ResourceEntry.RESOURCE_NAME_COL)));

        final int resourceId = cursor.getInt(cursor.getColumnIndex(DBContract
                .ResourceEntry.RESOURCE_ID_COL));

        mHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Debug.showToastShort("" + resourceId + " " + isChecked, context);
            }
        });

    }

    class ViewHolder {
        @Bind(R.id.checkBox)
        CheckBox checkBox;
        @Bind(R.id.resource_name)
        TextView resourceName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

