package com.amrendra.codefiesta.adapter;

/**
 * Created by Amrendra Kumar on 11/04/16.
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amrendra.codefiesta.R;
import com.amrendra.codefiesta.model.Website;
import com.amrendra.codefiesta.utils.Debug;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Amrendra Kumar on 10/04/16.
 */
public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {

    private List<Website> mWebsitesList = new ArrayList<>();

    CompetitionSettingsChangedListener settingsChangedListener;

    public SelectionAdapter(List<Website> wlist, CompetitionSettingsChangedListener mListner) {
        this.mWebsitesList = wlist;
        settingsChangedListener = mListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_item_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Website website = mWebsitesList.get(position);
        int show = website.getShow();
        holder.checkBox.setChecked(show != 0);
        holder.resourceName.setText(website.getName());

        final int resourceId = website.getId();

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Debug.showToastShort("" + resourceId + " " + isChecked, context);
                int toShow = (((CheckBox) v).isChecked() ? 1 : 0);
                Debug.i("resource : " + resourceId + " change : " + toShow);
                settingsChangedListener.settingsChanged(resourceId, toShow, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWebsitesList.size();
    }

    public void updateWebsiteSettingStatus(int pos, int show) {
        Website website = mWebsitesList.get(pos);
        website.setShow(show);
        //mWebsitesList.add(pos, website);
    }

    public void resetWebsiteList(@NonNull List<Website> data) {
        mWebsitesList = data;
        notifyDataSetChanged();
    }

    @NonNull
    public List<Website> getWebsiteList() {
        return mWebsitesList;
    }

    public void clearWebsites() {
        if (!mWebsitesList.isEmpty()) {
            mWebsitesList.clear();
            notifyDataSetChanged();
        }
    }

    public interface CompetitionSettingsChangedListener {
        void settingsChanged(int competitionId, int want, int pos);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.checkBox)
        CheckBox checkBox;
        @Bind(R.id.resource_name)
        TextView resourceName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

