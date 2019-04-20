package com.qsd.jmwh.module.home.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseFragment;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.user.activity.EditUserInfoActivity;
import com.qsd.jmwh.module.home.user.activity.PrivacySettingActivity;
import com.qsd.jmwh.module.home.user.presenter.UserPresenter;
import com.qsd.jmwh.module.home.user.presenter.UserViewer;
import com.qsd.jmwh.module.register.ToByVipActivity;
import com.yu.common.mvp.PresenterLifeCycle;

/**
 * @author yudneghao
 * @date 2018/6/12
 */

public class UserFragment extends BaseFragment implements UserViewer, View.OnClickListener {

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
        initListener();
    }

    private void initListener() {
        bindView(R.id.vip, this);
        bindView(R.id.money_bag, this);
        bindView(R.id.edit_user_info, this);
        bindView(R.id.privacy_setting, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vip:
                getLaunchHelper().startActivity(ToByVipActivity.getIntent(getActivity(),
                        UserProfile.getInstance().getAppAccount(),
                        UserProfile.getInstance().getAppToken()));
                break;
            case R.id.money_bag:

                break;
            case R.id.edit_user_info:
                getLaunchHelper().startActivity(EditUserInfoActivity.class);
                break;
            case R.id.privacy_setting:
                getLaunchHelper().startActivity(PrivacySettingActivity.class);
                break;
            default:
        }
    }
}
