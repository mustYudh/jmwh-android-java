package com.qsd.jmwh.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qsd.jmwh.R;
import com.yu.common.base.BaseFragment;
import com.yu.common.utils.BarUtils;

public abstract class BaseBarFragment extends BaseFragment {

    protected @LayoutRes
    int getActionBarLayoutId() {
        return R.layout.action_bar_blue;
    }


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                                       Bundle savedInstanceState) {
        View rootView;
        if ((rootView = getView()) == null) {
            rootView = onReplaceRootView(inflater, viewGroup);
        }
        mContentView = View.inflate(getActivity(), getContentViewId(),
                (ViewGroup) rootView.findViewById(R.id.content_container));
        return rootView;
    }


    @Override protected View onReplaceRootView(LayoutInflater inflater, ViewGroup viewGroup) {
        View rootView = super.onReplaceRootView(inflater, viewGroup);
        onReplaceActionBar(inflater, rootView);
        return rootView;
    }

    protected void onReplaceActionBar(LayoutInflater inflater, View rootView) {
        FrameLayout container = (FrameLayout) rootView.findViewById(R.id.action_bar_container);
        container.setVisibility(View.VISIBLE);
        View actionBar = inflater.inflate(getActionBarLayoutId(), container, false);
        container.addView(actionBar);
        BarUtils.setActionBarLayout(actionBar);
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barActionInit();
    }

    protected void barActionInit() {
        bindView(R.id.back, new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });
    }


    public void setTitle(CharSequence charSequence) {
        bindText(R.id.title, charSequence);
    }
}
