package com.denghao.control;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author yudneghao
 * @date 2018/6/11
 */

public class BottomNavigationControl extends LinearLayout implements TabViewControl {

  private LinearLayout tabControlView;
  private FrameLayout frameLayout;
  private TabClickListener tabClickListener;

  public BottomNavigationControl(Context context) {
    this(context, null);
  }

  public BottomNavigationControl(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BottomNavigationControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    View.inflate(context, R.layout.bottom_navigation_control_view, this);
    tabControlView = (LinearLayout) findViewById(R.id.tab_control);
    frameLayout = (FrameLayout) findViewById(R.id.content_view);
  }

  public void setTabControlHeight(int height) {
    LinearLayout.LayoutParams params =
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    params.height = dp2PX(height);
    tabControlView.setLayoutParams(params);
  }

  @Override public FrameLayout getContentView() {
    return frameLayout;
  }

  @Override public void addViewTabView(final View tabView) {
    if (tabView != null) {
      LinearLayout.LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
      tabControlView.addView(tabView, params);
      tabView.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          int position = tabControlView.indexOfChild(tabView);
          getCurrentPosition(position);
          if (tabClickListener != null) {
            tabClickListener.onTabClickListener(position, tabView);
          }
        }
      });
    }
  }

  @Override public void removeAllTabView() {
    tabControlView.removeAllViews();
  }

  @Override public int getCurrentPosition(int currentPosition) {
    for (int i = 0; i < getTabCount(); i++) {
      View tab = tabControlView.getChildAt(i);
      if (tab != null) {
        tab.setSelected(currentPosition == i);
        tab.setEnabled(currentPosition != i);
      }
    }
    return currentPosition;
  }

  @Override public int getTabCount() {
    return tabControlView.getChildCount();
  }

  @Override public View getOtherView(View view) {
    return (View) view.findViewById(R.id.tab_control);
  }

  @Override public void setOnTabClickListener(TabClickListener onTabClickListener) {
    this.tabClickListener = onTabClickListener;
  }

  private int dp2PX(float dp) {
    float density = Resources.getSystem().getDisplayMetrics().density;
    return (int) (density * dp + 0.5);
  }
}
