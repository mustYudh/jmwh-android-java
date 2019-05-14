package com.qsd.jmwh.module.home.park;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.adapter.HomeParkPageAdapter;
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
    private boolean sex = false;
    @PresenterLifeCycle
    ParkPresenter mPresenter = new ParkPresenter(this);
    private TextView right_menu;


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
        right_menu = bindView(R.id.right_menu);
        TabLayout tabLayout = bindView(R.id.tab_layout);
        ViewPager viewPager = bindView(R.id.view_pager);
        initTabLayout(tabLayout, viewPager);


    }

    private void initTabLayout(TabLayout tabLayout, ViewPager viewPager) {
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
        tabLayout.addOnTabSelectedListener(this);
        HomeParkPageAdapter adapter = new HomeParkPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View view = View.inflate(getActivity(), R.layout.item_home_tab, null);
                TextView textView = view.findViewById(R.id.title);
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

        right_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = !sex;
                adapter.getFragment(viewPager.getCurrentItem()).mPresenter.initPersonListData(UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(), viewPager.getCurrentItem() + "", "", "0", !sex ? "0" : "1");
                right_menu.setText(!sex ? "女士列表" : "男士列表");
            }
        });

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
        right_menu.setText("女士列表");
        sex = false;
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
