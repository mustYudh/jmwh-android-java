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
    public Fragment getItem(int position) {
        Fragment ft = null;
        switch (position) {
            case 0:
                ft = PersonFragment.newInstance("0");
                break;
            case 1:
                ft = PersonFragment.newInstance("1");
                break;
            case 2:
                ft = PersonFragment.newInstance("2");
                break;

        }
        return ft;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
