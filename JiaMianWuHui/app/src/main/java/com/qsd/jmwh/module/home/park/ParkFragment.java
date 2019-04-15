package com.qsd.jmwh.module.home.park;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.module.home.park.presenter.ParkPresenter;
import com.qsd.jmwh.module.home.park.presenter.ParkViewer;
import com.qsd.jmwh.view.ProxyDrawable;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.Res;
import com.yu.common.utils.DensityUtil;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class ParkFragment extends BaseBarFragment implements ParkViewer, TabLayout.OnTabSelectedListener {

    @PresenterLifeCycle
    ParkPresenter mPresenter = new ParkPresenter(this);


    @Override
    protected int getActionBarLayoutId() {
        return R.layout.hoem_bar_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.park_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    private void initView() {
        TabLayout tabLayout = bindView(R.id.tab_layout);
        ViewPager viewPager = bindView(R.id.view_pager);
        initTabLayout(tabLayout,viewPager);
    }

    private void initTabLayout(TabLayout tabLayout,ViewPager viewPager) {
        tabLayout.addOnTabSelectedListener(this);
        HomeParkPageAdapter adapter = new HomeParkPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View view = View.inflate(getActivity(), R.layout.item_home_tab, null);
                TextView textView =  view.findViewById(R.id.title);
                if (i == 0) {
                    textView.setText("附近");
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else if (i == 1) {
                    textView.setText("注册");
                    textView.setTextColor(Res.color(R.color.color_666666));
                    textView.setTextSize(15);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    textView.setText("认证");
                    textView.setTextColor(Res.color(R.color.color_666666));
                    textView.setTextSize(15);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
                tab.setCustomView(view);
            }

        }
        ProxyDrawable proxyDrawable = new ProxyDrawable(tabLayout, 1);
        LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
        proxyDrawable.setIndicatorColor(Res.color(R.color.app_main_bg_color));
        proxyDrawable.setIndicatorPaddingLeft(
                DensityUtil.dip2px(getActivity(), 22f));
        proxyDrawable.setIndicatorPaddingRight(
                DensityUtil.dip2px(getActivity(), 22f));
        proxyDrawable.setIndicatorPaddingTop(
                DensityUtil.dip2px(getActivity(), 3f));
        tabStrip.setBackground(proxyDrawable);

    }

    @Override
    protected void loadData() {
        initView();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.title);
            textView.setTextColor(Res.color(R.color.color_222222));
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setTextSize(22);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.title);
            textView.setTextColor(Res.color(R.color.color_666666));
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setTextSize(15);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
