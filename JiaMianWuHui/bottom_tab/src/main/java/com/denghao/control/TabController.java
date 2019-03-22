package com.denghao.control;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.denghao.control.view.utils.FieldUtils;
import com.denghao.control.view.utils.UpdataCurrentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yudenghao
 * @deprecated Created by  on 2018/6/11.
 */

public class TabController {
    private List<TabInfoBean> mBeans;
    private TabViewControl tabViewControl;
    private FragmentActivity fragmentActivity;
    private TabViewControl.TabClickListener tabClickListener;
    private Fragment currentFragment;
    private int currentPosition;

    public TabController(FragmentActivity fragmentActivity, TabViewControl tabViewControl) {
        this.tabViewControl = tabViewControl;
        this.fragmentActivity = fragmentActivity;
        tabViewControl.setOnTabClickListener(new TabViewControl.TabClickListener() {
            @Override
            public void onTabClickListener(int position, View view) {
                selectFragment(position, null);
                if (tabClickListener != null) {
                    tabClickListener.onTabClickListener(position, view);
                }
            }
        });
        mBeans = new ArrayList<>();
        fixHuaWeiBug();
    }

    public void setOnTabClickListener(TabViewControl.TabClickListener onTabClickListener) {
        this.tabClickListener = onTabClickListener;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public int getCurrentPoition() {
        return currentPosition;
    }

    public String getCurrentTag() {
        return currentPosition >= 0 && currentPosition < mBeans.size() ? mBeans.get(currentPosition).tag
                : "";
    }

    /**
     * 华为设置权限后会重新执行onCreate ,但是FragmentManager的页面不销毁，这边直接先全部销毁
     */
    private void fixHuaWeiBug() {
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //当前所有fragment
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fm.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment f : fragments) {
                if (f != null) {
                    ft.remove(f);
                }
            }
            ft.commitAllowingStateLoss();
        }
    }

    public void addItem(TabItem tabItem) {
        Fragment fragment = tabItem.getCurrentFragment();
        String tag = tabItem.getTag();
        if (fragment != null) {
            tabViewControl.addViewTabView(tabItem.getView());
            mBeans.add(new TabInfoBean(tag, fragment, tabItem));
        }
    }

    public void addItem(List<TabItem> items) {
        if (items != null) {
            for (TabItem item : items) {
                addItem(item);
            }
        }
    }

    public String getTag(int position) {
        return position >= 0 && position < mBeans.size() ? mBeans.get(position).tag : "";
    }

    public Fragment getFragment(int position) {
        return position >= 0 && position < mBeans.size() ? mBeans.get(position).mFragment : null;
    }

    public TabItem getTabItem(int position) {
        return position >= 0 && position < mBeans.size() ? mBeans.get(position).mItem : null;
    }

    // TODO: 2018/6/12 bundle 没有做支持
    public void selectFragment(int position, Bundle bundle) {
        if (tabViewControl.getTabCount() == 0) {
            return;
        }
        if (position < 0 || position > tabViewControl.getTabCount()) {
            position = 0;
        }
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        boolean hasAdded = false;
        Fragment fragment = manager.findFragmentByTag(getTag(position));
        if (fragment != null) {
            hasAdded = true;
        } else {
            fragment = getFragment(position);
        }
        if (fragment == null) {
            return;
        }
        currentPosition = position;
        currentFragment = fragment;

        @SuppressLint("CommitTransaction") FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragmentList = manager.getFragments();

        for (Fragment f : fragmentList) {
            if (f != null && f != fragment) {
                transaction.hide(f);
            }
        }
        if (!hasAdded && !fragment.isAdded()) {
            Object obj = new FieldUtils().getField(Fragment.class, fragment, "mIndex");
            int index = -1;
            if (obj != null) {
                index = (int) obj;
            }
            if (index < 0 || !fragment.isStateSaved()) {
                if (fragment.getArguments() != null) {
                    Bundle currentBundle = fragment.getArguments();
                    currentBundle.putAll(bundle);
                    fragment.setArguments(currentBundle);
                } else {
                    fragment.setArguments(bundle);
                }
            }
        } else {
            if (fragment instanceof UpdataCurrentFragment) {
                ((UpdataCurrentFragment) fragment).update(bundle);
            }
        }
        if (hasAdded || fragment.isAdded()) {
            transaction.show(fragment).commitAllowingStateLoss();
        } else {
            transaction.add(tabViewControl.getContentView().getId(), fragment, getTag(position))
                    .commitAllowingStateLoss();
        }
        tabViewControl.getCurrentPosition(position);
        if (getTabItem(position) != null) {
            getTabItem(position).setMessageHint(0);
        }
    }

    public void clearAllTab() {
        tabViewControl.removeAllTabView();
        mBeans.clear();
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isAdded()) {
                transaction.remove(fragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
