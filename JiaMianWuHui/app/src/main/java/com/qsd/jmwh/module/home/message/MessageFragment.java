package com.qsd.jmwh.module.home.message;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.module.home.message.adapter.MessageAdapter;
import com.qsd.jmwh.module.home.message.presenter.MessagePresenter;
import com.qsd.jmwh.module.home.message.presenter.MessageViewer;
import com.qsd.jmwh.module.home.user.activity.PushSettingActivity;
import com.qsd.jmwh.view.ProxyDrawable;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.ui.Res;
import com.yu.common.utils.DensityUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class MessageFragment extends BaseBarFragment
    implements MessageViewer, TabLayout.OnTabSelectedListener {

  @PresenterLifeCycle private MessagePresenter mPresenter = new MessagePresenter(this);

  @Override protected int getContentViewId() {
    return R.layout.message_fragment;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {

  }

  @Override protected void loadData() {
    setTitle("消息中心");
    showBack(false);
    setRightMenu("推送设置", v -> getLaunchHelper().startActivity(PushSettingActivity.class));

    TabLayout tabLayout = bindView(R.id.tab_layout);
    ViewPager viewPager = bindView(R.id.view_pager);
    tabLayout.addOnTabSelectedListener(this);
    tabLayout.setupWithViewPager(viewPager);
    List<Fragment> fragments = new ArrayList<>();
    fragments.add(new RecentContactsFragment());
    fragments.add(new SystemMessageFragment());
    MessageAdapter adapter = new MessageAdapter(getChildFragmentManager(),fragments);
    viewPager.setAdapter(adapter);

    for (int i = 0; i < 2; i++) {
      TabLayout.Tab tab = tabLayout.getTabAt(i);
      if (tab != null) {
        View view = View.inflate(getActivity(), R.layout.item_home_tab, null);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(i == 0 ? "聊天" :"系统消息");
        textView.setTextColor(Res.color(R.color.color_666666));
        textView.setTextSize(15);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tab.setCustomView(view);
      }
    }

    View tabStrip = tabLayout.getChildAt(0);

    if (tabStrip != null) {
      ProxyDrawable proxyDrawable = new ProxyDrawable(tabStrip, 7);
      proxyDrawable.setIndicatorColor(Res.color(R.color.app_main_bg_color));
      proxyDrawable.setIndicatorPaddingLeft(DensityUtil.dip2px(getActivity(), 45));
      proxyDrawable.setIndicatorPaddingRight(DensityUtil.dip2px(getActivity(), 45));
      proxyDrawable.setRadius(DensityUtil.dip2px(2));
      proxyDrawable.setIndicatorPaddingTop(DensityUtil.dip2px(getActivity(), 7));
      tabStrip.setBackground(proxyDrawable);
    }

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int i, float v, int i1) {

      }

      @Override public void onPageSelected(int position) {
          if (position == 1) {
            SystemMessageFragment fragment = (SystemMessageFragment)adapter.getListFragmentMap().get(1);
            fragment.updateMessage();
          }
      }

      @Override public void onPageScrollStateChanged(int i) {

      }
    });
  }

  @Override public void onTabSelected(TabLayout.Tab tab) {
    View view = tab.getCustomView();
    if (view != null) {
      TextView textView = view.findViewById(R.id.title);
      textView.setTextColor(Res.color(R.color.color_222222));
      textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
      textView.setTextSize(20);
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
}
