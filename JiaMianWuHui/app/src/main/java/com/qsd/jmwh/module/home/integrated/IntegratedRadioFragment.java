package com.qsd.jmwh.module.home.integrated;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarFragment;
import com.qsd.jmwh.module.home.integrated.presenter.IntegratedRadioPresenter;
import com.qsd.jmwh.module.home.integrated.presenter.IntegratedRadioViewer;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class IntegratedRadioFragment extends BaseBarFragment implements IntegratedRadioViewer {

    @PresenterLifeCycle
    IntegratedRadioPresenter mPresenter = new IntegratedRadioPresenter(this);

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.rang_bar_layout;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.integrated_radio_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
}
