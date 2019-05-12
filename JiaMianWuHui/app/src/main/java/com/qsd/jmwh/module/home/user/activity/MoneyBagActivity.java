package com.qsd.jmwh.module.home.user.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.adapter.MoneyBagAdapter;
import com.qsd.jmwh.module.home.user.presenter.MoneyBagPresenter;
import com.qsd.jmwh.module.home.user.presenter.MoneyBagViewer;
import com.qsd.jmwh.view.ProxyDrawable;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.Res;
import com.yu.common.utils.DensityUtil;

public class MoneyBagActivity extends BaseBarActivity implements
        TabLayout.OnTabSelectedListener, MoneyBagViewer {

    @PresenterLifeCycle
    private MoneyBagPresenter mPresenter = new MoneyBagPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.money_bag);
    }

    @Override
    protected void loadData() {
        setTitle("钱包");
        initView();
    }

    private void initView() {
        TabLayout tabLayout = bindView(R.id.tab_layout);
        ViewPager viewPager = bindView(R.id.view_pager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        MoneyBagAdapter adapter = new MoneyBagAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        for (int i = 0; i < 2; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View view = View.inflate(getActivity(), R.layout.item_home_tab, null);
                TextView textView = view.findViewById(R.id.title);
//                if (i == 0) {
//                    textView.setText("现金");
//                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                } else {
                    textView.setText("假面币");
                    textView.setTextColor(Res.color(R.color.color_666666));
                    textView.setTextSize(15);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                }
                tab.setCustomView(view);
            }
        }
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip != null) {
            ProxyDrawable proxyDrawable = new ProxyDrawable(tabStrip, 7);
            proxyDrawable.setIndicatorColor(Res.color(R.color.app_main_bg_color));
            proxyDrawable.setIndicatorPaddingLeft(DensityUtil.dip2px(getActivity(), 25));
            proxyDrawable.setIndicatorPaddingRight(DensityUtil.dip2px(getActivity(), 25));
            proxyDrawable.setRadius(DensityUtil.dip2px(2));
            proxyDrawable.setIndicatorPaddingTop(DensityUtil.dip2px(getActivity(), 7));
            tabStrip.setBackground(proxyDrawable);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.title);
            textView.setTextColor(Res.color(R.color.color_222222));
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setTextSize(20);
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
