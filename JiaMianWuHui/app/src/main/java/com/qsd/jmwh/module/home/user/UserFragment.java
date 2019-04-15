package com.qsd.jmwh.module.home.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.user.presenter.UserPresenter;
import com.qsd.jmwh.module.home.user.presenter.UserViewer;
import com.qsd.jmwh.base.BaseFragment;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class UserFragment extends BaseFragment implements UserViewer {

    @PresenterLifeCycle
    UserPresenter mPresenter = new UserPresenter(this);



    @Override
    protected int getContentViewId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
}
