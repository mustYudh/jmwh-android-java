package com.qsd.jmwh.module.home.park.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoPresenter;
import com.qsd.jmwh.module.home.park.presenter.LookUserInfoViewer;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.yu.common.mvp.PresenterLifeCycle;

public class LookUserInfoActivity extends BaseActivity implements LookUserInfoViewer {

    @PresenterLifeCycle
    private LookUserInfoPresenter mPresenter = new LookUserInfoPresenter(this);

    private final static String USER_ID = "user_id";

    public static Intent getIntent(Context context, int userId) {
        Intent starter = new Intent(context, LookUserInfoActivity.class);
        starter.putExtra(USER_ID, userId);
        return starter;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.look_user_info_layout);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setUserInfo(UserCenterInfo userCenterInfo) {

    }
}
