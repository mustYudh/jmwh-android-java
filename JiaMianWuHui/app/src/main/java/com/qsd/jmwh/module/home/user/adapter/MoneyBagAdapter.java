package com.qsd.jmwh.module.home.user.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qsd.jmwh.module.home.user.fragment.JiaMianCoinFragment;

public class MoneyBagAdapter extends FragmentStatePagerAdapter {

    public MoneyBagAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
//        if (i == 0) {
//            return new MoneyFragment();
//        } else {
            return new JiaMianCoinFragment();
//        }

    }

    @Override
    public int getCount() {
        return 1;
    }
}
