package com.qsd.jmwh.module.home.park;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.module.home.park.presenter.ParkPresenter;
import com.qsd.jmwh.module.home.park.presenter.ParkViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class ParkFragment extends BaseBarFragment implements ParkViewer {

    @PresenterLifeCycle
    ParkPresenter mPresenter = new ParkPresenter(this);


    @Override
    protected int getActionBarLayoutId() {
        return R.layout.hoem_bar_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.park_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
}
