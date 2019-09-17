package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
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
    private NormaFormItemVIew mPhoneNum;
    private NormaFormItemVIew password;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.setting_phone_number_layout);
    }


    @Override
    protected void loadData() {
        setTitle("绑定手机号");
        NormaFormItemVIew mSendVerCode = bindView(R.id.get_ver_code);
        mPhoneNum = bindView(R.id.phone);
        password = bindView(R.id.password);
        mPhoneNum.setEditText(UserProfile.getInstance().getPhoneNo());
        mSendVerCode.setRightButtonListener(v -> {
            if (TextUtils.isEmpty(mPhoneNum.getText())) {
                ToastUtils.show("手机号输入不能为空");
            } else if (!mPhoneNum.getText().startsWith("1") || mPhoneNum.getText().length() != 11) {
                ToastUtils.show("检查手机号输入是否正确");
            } else {
                countDown = new RxCountDown();
                countDown.start(60);
                mPresenter.sendVerCode(mPhoneNum.getText(), countDown);
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

        bindView(R.id.next, v -> mPresenter.bindPhone(mPhoneNum.getText(),password.getText(), mSendVerCode.getText()));
    }

    @Override
    public void onSuccess() {
        UserProfile.getInstance().setPhoneNo(mPhoneNum.getText());
        ToastUtils.show("绑定成功");
        finish();
    }
}
