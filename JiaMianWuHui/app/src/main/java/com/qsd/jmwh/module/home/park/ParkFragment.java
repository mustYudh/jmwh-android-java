package com.qsd.jmwh.module.home.park;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.park.activity.SearchActivity;
import com.qsd.jmwh.module.home.park.adapter.HomeParkPageAdapter;
import com.qsd.jmwh.module.home.park.adapter.PackCityRvAdapter;
import com.qsd.jmwh.module.home.park.presenter.ParkPresenter;
import com.qsd.jmwh.module.home.park.presenter.ParkViewer;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.utils.DialogUtils;
import com.qsd.jmwh.view.ProxyDrawable;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.Res;
import com.yu.common.utils.DensityUtil;

import java.util.List;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class ParkFragment extends BaseBarFragment
        implements ParkViewer, TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    @PresenterLifeCycle
    ParkPresenter mPresenter = new ParkPresenter(this);
    private TextView rightMenu;
    private TextView back;
    private HomeParkPageAdapter adapter;
    private DialogUtils cityDialog;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private LinearLayout mLlEdit;
    private int currentSelectListType = UserProfile.getInstance().getSex() == 1 ? 0 : 1;

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

    @Override
    protected void loadData() {
        initView();
        initTabLayout();
        initListener();
    }

    private void initView() {
        EditText edit = bindView(R.id.edit);
        edit.setInputType(InputType.TYPE_NULL);
        rightMenu = bindView(R.id.right_menu);
        back = bindView(R.id.back);
        mLlEdit = bindView(R.id.ll_edit);
        mTabLayout = bindView(R.id.tab_layout);
        mPager = bindView(R.id.view_pager);
        if (currentSelectListType == 1) {
            rightMenu.setText("男士列表");
        } else {
            rightMenu.setText("女士列表");
        }
    }

    private void initTabLayout() {
        boolean isGirl = currentSelectListType == 0;
        View tabStrip = mTabLayout.getChildAt(0);
        if (tabStrip != null) {
            ProxyDrawable proxyDrawable = new ProxyDrawable(tabStrip, 7);
            proxyDrawable.setIndicatorColor(Res.color(R.color.app_main_bg_color));
            int dividerWith = isGirl ? 25 : 44;
            proxyDrawable.setIndicatorPaddingLeft(DensityUtil.dip2px(getActivity(), dividerWith));
            proxyDrawable.setIndicatorPaddingRight(DensityUtil.dip2px(getActivity(), dividerWith));
            proxyDrawable.setRadius(DensityUtil.dip2px(2));
            proxyDrawable.setIndicatorPaddingTop(DensityUtil.dip2px(getActivity(), 7));
            tabStrip.setBackground(proxyDrawable);
        }
        mTabLayout.addOnTabSelectedListener(this);
        adapter = new HomeParkPageAdapter(getChildFragmentManager(), isGirl ? 3 : 2);
        mPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mPager);
        for (int i = 0; i < (isGirl ? 3 : 2); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                View view = View.inflate(getActivity(), R.layout.item_home_tab, null);
                TextView textView = view.findViewById(R.id.title);
                if (i == 0) {
                    textView.setText("附近");
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else if (i == 1) {
                    textView.setText(currentSelectListType == 1 ? "会员" : "注册");
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
        mPager.setOffscreenPageLimit(isGirl ? 3 : 2);
    }

    private void initListener() {
        mLlEdit.setOnClickListener(
                view -> startActivity(new Intent(getActivity(), SearchActivity.class)));
        back.setOnClickListener(
                view -> mPresenter.getRangeData(1, 0, UserProfile.getInstance().getUserId(),
                        UserProfile.getInstance().getAppToken(), 0));
        rightMenu.setOnClickListener(view -> {
            if (currentSelectListType == 0) {
                currentSelectListType = 1;
                rightMenu.setText("男士列表");
            } else {
                currentSelectListType = 0;
                rightMenu.setText("女士列表");
            }
            initTabLayout();
        });
        mPager.addOnPageChangeListener(this);
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

    /**
     * 城市弹窗
     */
    private void showCityDialog(List<RangeData.Range> provinces, int type) {
        View.OnClickListener listener = v -> {
            if (v.getId() == R.id.tv_fujin) {
                if (cityDialog.isShowing()) {
                    cityDialog.dismiss();
                }
                UserProfile.getInstance().setHomeCityName("");
                adapter.getFragment(mPager.getCurrentItem()).mPresenter.initPersonListData(
                        UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(),
                        adapter.getFragment(mPager.getCurrentItem()).home_type + "", "",
                        adapter.getFragment(mPager.getCurrentItem()).pageIndex + "",
                        currentSelectListType + "",
                        UserProfile.getInstance().getHomeCityName());

            }
        };

        cityDialog = new DialogUtils.Builder(getActivity()).view(R.layout.dialog_city)
                .gravity(Gravity.BOTTOM)
                .style(R.style.Dialog)
                .cancelTouchout(false)
                .addViewOnclick(R.id.tv_fujin, listener)
                .build();
        cityDialog.show();

        RecyclerView rv_city = cityDialog.findViewById(R.id.rv_city);
        rv_city.setLayoutManager(new LinearLayoutManager(getActivity()));
        PackCityRvAdapter adapter_city =
                new PackCityRvAdapter(R.layout.item_pack_city, provinces, getActivity());
        rv_city.setAdapter(adapter_city);

        adapter_city.setOnItemCityClickListener((id, name) -> {
            cityDialog.dismiss();
            if (type == 1) {
                UserProfile.getInstance().setHomeCityName(name);
                adapter.getFragment(mPager.getCurrentItem()).mPresenter.initPersonListData(
                        UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(),
                        adapter.getFragment(mPager.getCurrentItem()).home_type + "", "",
                        adapter.getFragment(mPager.getCurrentItem()).pageIndex + "",
                        currentSelectListType + "",
                        UserProfile.getInstance().getHomeCityName());
            } else {
                mPresenter.getRangeData(1, id, UserProfile.getInstance().getUserId(),
                        UserProfile.getInstance().getAppToken(), 1);
            }
        });
    }

    @Override
    public void setCity(List<RangeData.Range> provinces, int type) {
        showCityDialog(provinces, type);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i != 0) {
            adapter.getFragment(i).tv_top_num.setVisibility(View.GONE);
        } else {
            adapter.getFragment(i).tv_top_num.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
