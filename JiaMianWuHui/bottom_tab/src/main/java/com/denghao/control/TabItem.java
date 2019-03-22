package com.denghao.control;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public interface TabItem {

  Fragment getCurrentFragment();

  View getView();

  String getTag();

  void setMessageHint(int count);
}
