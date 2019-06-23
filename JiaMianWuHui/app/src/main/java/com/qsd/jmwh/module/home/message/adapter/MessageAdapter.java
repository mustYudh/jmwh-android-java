package com.qsd.jmwh.module.home.message.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.qsd.jmwh.module.home.message.SystemMessageFragment;

/**
 * @author yudneghao
 * @date 2019-06-08
 */
public class MessageAdapter extends FragmentStatePagerAdapter {

  public MessageAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int i) {
    if (i == 0) {
      return new RecentContactsFragment();

    } else  {
      return new SystemMessageFragment();
    }
  }

  @Override public int getCount() {
    return 2;
  }
}
