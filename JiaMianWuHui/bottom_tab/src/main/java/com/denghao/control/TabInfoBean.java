package com.denghao.control;

import android.support.v4.app.Fragment;
import java.io.Serializable;

/**
 * Created by yudenghao on 2018/6/11.
 */

public class TabInfoBean implements Serializable{
    public String tag;
    public Fragment mFragment;
    public TabItem mItem;

    public TabInfoBean(String tag, Fragment fragment, TabItem item) {
        this.tag = tag;
        mFragment = fragment;
        mItem = item;
    }
}
