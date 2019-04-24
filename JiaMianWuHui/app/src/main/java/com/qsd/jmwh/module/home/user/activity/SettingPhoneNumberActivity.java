package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.module.home.user.presenter.SettingPhoneNumberPresenter;
import com.qsd.jmwh.module.home.user.presenter.SettingPhoneNumberViewer;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.qsd.jmwh.utils.countdown.RxCountDownAdapter;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class SettingPhoneNumberActivity extends BaseBarActivity implements SettingPhoneNumberViewer {
    @PresenterLifeCycle
    SettingPhoneNumberPresenter mPresenter = new SettingPhoneNumberPresenter(this);
    private RxCountDown countDown;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.setting_phone_number_layout);
    }

    @Override
    protected void loadData() {
        setTitle("绑定手机号");
        NormaFormItemVIew mSendVerCode = bindView(R.id.get_ver_code);
        NormaFormItemVIew phoneNum = bindView(R.id.phone);
        mSendVerCode.setRightButtonListener(v -> {
            if (TextUtils.isEmpty(phoneNum.getText())) {
                ToastUtils.show("手机号输入不能为空");
            } else if (!phoneNum.getText().startsWith("1") || phoneNum.getText().length() != 11) {
                ToastUtils.show("检查手机号输入是否正确");
            } else {
                countDown = new RxCountDown(60);
                mPresenter.sendVerCode(phoneNum.getText(), countDown);
                countDown.setCountDownTimeListener(new RxCountDownAdapter() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mSendVerCode.setRightButtonEnable(false);
                        mSendVerCode.setRightHint("60S");
                    }

                    @Override
                    public void onNext(Integer time) {
                        super.onNext(time);
                        if (time == 0) {
                            mSendVerCode.setRightButtonEnable(true);
                            mSendVerCode.setRightHint("发送验证码");
                        } else {
                            mSendVerCode.setRightHint(time + "S");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mSendVerCode.setRightHint("发送验证码");
                        mSendVerCode.setRightButtonEnable(true);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });

            }
        });
        bindView(R.id.next, v -> mPresenter.bindPhone(phoneNum.getText(), mSendVerCode.getText()));
    }

    @Override
    public void onSuccess() {
        ToastUtils.show("绑定成功");
        finish();
    }
}
