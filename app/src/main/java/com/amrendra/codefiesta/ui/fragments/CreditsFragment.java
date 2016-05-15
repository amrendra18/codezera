package com.amrendra.codefiesta.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrendra.codefiesta.R;

import butterknife.Bind;

/**
 * Created by amrendrk on 5/15/16.
 */
public class CreditsFragment extends BaseFragment {

    @Bind(R.id.credit_tv)
    TextView creditTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_credits, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        creditTv.setText(getString(R.string.credits_info));
    }
}
