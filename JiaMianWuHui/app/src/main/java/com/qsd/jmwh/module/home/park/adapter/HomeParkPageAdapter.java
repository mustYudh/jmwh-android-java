package com.qsd.jmwh.module.home.park.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qsd.jmwh.module.home.park.PersonFragment;

public class HomeParkPageAdapter extends FragmentStatePagerAdapter {
    public HomeParkPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return new PersonFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
