package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.register.presenter.ToByVipPresenter;
import com.qsd.jmwh.module.register.presenter.ToByVipViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class ToByVipActivity extends BaseBarActivity implements ToByVipViewer {

    @PresenterLifeCycle
    private ToByVipPresenter mPresenter = new ToByVipPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.to_by_vip_activity);
    }

    @Override
    protected void loadData() {
        setTitle("会员中心");
    }

}
