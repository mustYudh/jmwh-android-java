package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.bean.PrivacySettingStatusBean;
import com.qsd.jmwh.module.home.user.presenter.PrivacySettingPresenter;
import com.qsd.jmwh.module.home.user.presenter.PrivacySettingViewer;
import com.qsd.jmwh.view.UserItemView;
import com.yu.common.mvp.PresenterLifeCycle;

public class PrivacySettingActivity extends BaseBarActivity implements PrivacySettingViewer {
    @PresenterLifeCycle
    private PrivacySettingPresenter mPresenter = new PrivacySettingPresenter(this);
    private UserItemView publicInfo;
    private UserItemView payInfo;
    private UserItemView showLocation;
    private UserItemView showAccount;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.privacy_setting_layout);
    }

    @Override
    protected void loadData() {
        setTitle("隐私设置");
        publicInfo = bindView(R.id.public_info);
        payInfo = bindView(R.id.pay_info);
        showLocation = bindView(R.id.show_location);
        showAccount = bindView(R.id.show_account);
        mPresenter.getUserConfig();
    }

    @Override
    public void getPrivacySettingStatus(PrivacySettingStatusBean statusBean) {
        publicInfo.setSelected(statusBean.cdoData.nInfoSet == 0);
        payInfo.setSelected(statusBean.cdoData.nInfoSet == 1);
        showLocation.setSwichButton(statusBean.cdoData.bHiddenRang);
        showAccount.setSwichButton(statusBean.cdoData.bHiddenQQandWX);
    }
}
