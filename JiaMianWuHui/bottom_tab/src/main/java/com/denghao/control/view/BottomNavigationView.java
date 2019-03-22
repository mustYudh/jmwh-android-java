package com.denghao.control.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.denghao.control.BottomNavigationControl;
import com.denghao.control.R;
import com.denghao.control.TabController;
import com.denghao.control.TabItem;
import java.util.List;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class BottomNavigationView extends LinearLayout {
  private TabController mController;
  private BottomNavigationControl mControl;

  public BottomNavigationView(Context context) {
    this(context, null);
  }

  public BottomNavigationView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }

  private void initView(Context context) {
    View.inflate(context, R.layout.bottom_navgation_view, this);
    mControl = (BottomNavigationControl) findViewById(R.id.bottom_navigation_control);
  }


  public BottomNavigationView initControl(FragmentActivity activity) {
    mController = new TabController(activity, mControl);
    mController.clearAllTab();
    return this;
  }

  public void
  setPagerView(List<TabItem> items, int normalSelect) {
    mController.addItem(items);
    mController.selectFragment(normalSelect, null);
  }


  public BottomNavigationControl getNavgation() {
    return mControl;
  }

  public TabController getControl() {
    return mController;
  }
}
