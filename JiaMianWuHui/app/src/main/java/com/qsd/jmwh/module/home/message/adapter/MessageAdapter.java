package com.qsd.jmwh.module.home.message.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yudneghao
 * @date 2019-06-08
 */
public class MessageAdapter extends FragmentStatePagerAdapter {

  private Map<Integer, Fragment> mListFragmentMap = new HashMap<>();

  private List<Fragment> mFragments = new ArrayList<>();

  public MessageAdapter(FragmentManager fm, List<Fragment> fragments) {
    super(fm);
    this.mFragments = fragments;
  }

  @Override public Fragment getItem(int i) {
    return mFragments.get(i);
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    super.destroyItem(container, position, object);
    mListFragmentMap.remove(position);
  }

  @Override public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    mListFragmentMap.put(position, (Fragment) super.instantiateItem(container, position));
    return super.instantiateItem(container, position);
  }

  public Map<Integer, Fragment> getListFragmentMap() {
    return mListFragmentMap;
  }

  @Override public int getCount() {
    return mFragments.size();
  }
}
