package com.qsd.jmwh.module.home.park.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.qsd.jmwh.module.home.park.PersonFragment;
import java.util.HashMap;
import java.util.Map;

public class HomeParkPageAdapter extends FragmentStatePagerAdapter {
  private Map<Integer, PersonFragment> mListFragmentMap = new HashMap<>();

  public PersonFragment getFragment(Integer key) {
    return mListFragmentMap.get(key);
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    super.destroyItem(container, position, object);
    mListFragmentMap.remove(position);
  }

  @NonNull @Override
  public PersonFragment instantiateItem(@NonNull ViewGroup container, int position) {
    PersonFragment personFragment = (PersonFragment) super.instantiateItem(container, position);
    mListFragmentMap.put(position, personFragment);
    return personFragment;
  }

  private int count;

  public HomeParkPageAdapter(FragmentManager fm, int count) {
    super(fm);
    this.count = count;
  }

  @Override public Fragment getItem(int position) {
    Fragment ft = null;
    switch (position) {
      case 0:
        ft = PersonFragment.newInstance(getCount() == 3 ? "0" : "3");
        break;
      case 1:
        ft = PersonFragment.newInstance(getCount() == 3 ? "1" : "4");
        break;
      case 2:
        ft = PersonFragment.newInstance("2");
        break;
      default:
    }
    return ft;
  }

  @Override public int getCount() {
    return count;
  }
}
