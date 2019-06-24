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
    implements ParkViewer, TabLayout.OnTabSelectedListener {
  @PresenterLifeCycle ParkPresenter mPresenter = new ParkPresenter(this);
  private TextView right_menu;
  private TextView back;
  private HomeParkPageAdapter adapter;
  private DialogUtils cityDialog;
  private ViewPager viewPager;

  @Override protected int getActionBarLayoutId() {
    return R.layout.hoem_bar_layout;
  }

  @Override protected int getContentViewId() {
    return R.layout.park_fragment;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {

  }

  private void initView() {
    EditText edit = bindView(R.id.edit);
    edit.setInputType(InputType.TYPE_NULL);
    right_menu = bindView(R.id.right_menu);
    back = bindView(R.id.back);
    LinearLayout ll_edit = bindView(R.id.ll_edit);
    TabLayout tabLayout = bindView(R.id.tab_layout);
    viewPager = bindView(R.id.view_pager);
    initTabLayout(tabLayout, viewPager);
    if (UserProfile.getInstance().getSex() == 0) {
      UserProfile.getInstance().setHomeSexType(1);
      right_menu.setText("男士列表");
    } else {
      UserProfile.getInstance().setHomeSexType(0);
      right_menu.setText("女士列表");
    }

    ll_edit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(new Intent(getActivity(), SearchActivity.class));
      }
    });

    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mPresenter.getRangeData(1, 0, UserProfile.getInstance().getAppAccount(),
            UserProfile.getInstance().getAppToken(), 0);
      }
    });
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
    adapter = new HomeParkPageAdapter(getChildFragmentManager());
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
      @Override public void onClick(View view) {
        if (UserProfile.getInstance().getHomeSexType() == 0) {
          UserProfile.getInstance().setHomeSexType(1);
        } else {
          UserProfile.getInstance().setHomeSexType(0);
        }
        adapter.getFragment(viewPager.getCurrentItem()).mPresenter.initPersonListData(
            UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(),
            viewPager.getCurrentItem() + "", "",
            adapter.getFragment(viewPager.getCurrentItem()).pageIndex + "",
            UserProfile.getInstance().getHomeSexType() + "",
            UserProfile.getInstance().getHomeCityName());
        right_menu.setText(UserProfile.getInstance().getHomeSexType() == 0 ? "女士列表" : "男士列表");
      }
    });

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int i, float v, int i1) {

      }

      @Override public void onPageSelected(int i) {
        if (i != 0) {
          adapter.getFragment(i).tv_top_num.setVisibility(View.GONE);
        } else {
          adapter.getFragment(i).tv_top_num.setVisibility(View.VISIBLE);
        }
        adapter.getFragment(viewPager.getCurrentItem()).mPresenter.initPersonListData(
            UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(),
            viewPager.getCurrentItem() + "", "",
            adapter.getFragment(viewPager.getCurrentItem()).pageIndex + "",
            UserProfile.getInstance().getHomeSexType() + "",
            UserProfile.getInstance().getHomeCityName());
      }

      @Override public void onPageScrollStateChanged(int i) {

      }
    });
  }

  @Override protected void loadData() {
    initView();
  }

  @Override public void onTabSelected(TabLayout.Tab tab) {
    View view = tab.getCustomView();
    if (view != null) {
      TextView textView = view.findViewById(R.id.title);
      textView.setTextColor(Res.color(R.color.color_222222));
      textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
      textView.setTextSize(22);
    }
  }

  @Override public void onTabUnselected(TabLayout.Tab tab) {
    View view = tab.getCustomView();
    if (view != null) {
      TextView textView = view.findViewById(R.id.title);
      textView.setTextColor(Res.color(R.color.color_666666));
      textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
      textView.setTextSize(15);
    }
  }

  @Override public void onTabReselected(TabLayout.Tab tab) {

  }

  /**
   * 城市弹窗
   */
  private void showCityDialog(List<RangeData.Range> provinces, int type) {
    View.OnClickListener listener = new View.OnClickListener() {
      @Override public void onClick(View v) {
        switch (v.getId()) {
          case R.id.tv_fujin:
            if (cityDialog.isShowing()) {
              cityDialog.dismiss();
            }
            UserProfile.getInstance().setHomeCityName("");
            adapter.getFragment(viewPager.getCurrentItem()).mPresenter.initPersonListData(
                UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(),
                viewPager.getCurrentItem() + "", "",
                adapter.getFragment(viewPager.getCurrentItem()).pageIndex + "",
                UserProfile.getInstance().getHomeSexType() + "",
                UserProfile.getInstance().getHomeCityName());
            break;
        }
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

    adapter_city.setOnItemCityClickListener(new PackCityRvAdapter.OnItemCityClickListener() {
      @Override public void setOnItemCityClick(int id, String name) {
        cityDialog.dismiss();
        if (type == 1) {
          UserProfile.getInstance().setHomeCityName(name);
          adapter.getFragment(viewPager.getCurrentItem()).mPresenter.initPersonListData(
              UserProfile.getInstance().getLat(), UserProfile.getInstance().getLng(),
              viewPager.getCurrentItem() + "", "",
              adapter.getFragment(viewPager.getCurrentItem()).pageIndex + "",
              UserProfile.getInstance().getHomeSexType() + "",
              UserProfile.getInstance().getHomeCityName());
        } else {
          mPresenter.getRangeData(1, id, UserProfile.getInstance().getAppAccount(),
              UserProfile.getInstance().getAppToken(), 1);
        }
      }
    });
  }

  @Override public void setCity(List<RangeData.Range> provinces, int type) {
    showCityDialog(provinces, type);
  }
}
