package com.qsd.jmwh.module.home.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.module.home.user.bean.PrivacySettingStatusBean;
import com.qsd.jmwh.module.home.user.dialog.SetPhotoMoneyPop;
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
    private boolean publicInfoStatus;
    private boolean payInfoStatus;
    private final static String USER_NAME = "user_name";
    private final static String HEADER = "header";

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.privacy_setting_layout);
    }

    public static Intent getIntent(Context context, String userName, String header) {
        Intent intent = new Intent(context, PrivacySettingActivity.class);
        intent.putExtra(USER_NAME, userName);
        intent.putExtra(HEADER, header);
        return intent;
    }


    @Override
    protected void loadData() {
        setTitle("隐私设置");
        publicInfo = bindView(R.id.public_info);
        payInfo = bindView(R.id.pay_info);
        showLocation = bindView(R.id.show_location);
        showAccount = bindView(R.id.show_account);
        mPresenter.getUserConfig();
        publicInfo.setSelectListener(v -> mPresenter.setUserPrivacy(0, publicInfoStatus ? 1 : 0, 0));
        payInfo.setSelectListener(v -> {
            SelectHintPop selectHintPop = new SelectHintPop(getActivity());
            selectHintPop
                    .setTitle("提示")
                    .setMessage("所有" + (UserProfile.getInstance().getSex() == 0 ? "男士" : "女士") + "必须付费才能查看你的相册，费用由你定，这可能降低你的访问量。")
                    .setSingleButton("确定", v1 -> {
                        SetPhotoMoneyPop setPhotoMoneyPop = new SetPhotoMoneyPop(getActivity(),
                                getIntent().getStringExtra(HEADER), getIntent().getStringExtra(USER_NAME), (money) -> {
                            mPresenter.setUserPrivacy(1, payInfoStatus ? 0 : 1, money);
                        });
                        setPhotoMoneyPop.showPopupWindow();
                        selectHintPop.dismiss();
                    }).setBottomButton("取消", v12 -> selectHintPop.dismiss());
            selectHintPop.showPopupWindow();
        });

        showLocation.setSwichlistener(switchStatus -> {
            mPresenter.setUserPrivacy(2, switchStatus ? 1 : 0, 0);
        });

        showAccount.setSwichlistener(switchStatus -> {
            mPresenter.setUserPrivacy(3, switchStatus ? 1 : 0, 0);
        });
    }


    @Override
    public void getPrivacySettingStatus(PrivacySettingStatusBean statusBean) {
        publicInfoStatus = statusBean.cdoData.nInfoSet == 0;
        payInfoStatus = statusBean.cdoData.nInfoSet == 1;
        publicInfo.setSelected(publicInfoStatus);
        payInfo.setSelected(payInfoStatus);
        showLocation.setSwichButton(statusBean.cdoData.bHiddenRang);
        showAccount.setSwichButton(statusBean.cdoData.bHiddenQQandWX);
    }

    @Override
    public void setSuccess(int type, int status) {
        loadData();
    }
}
