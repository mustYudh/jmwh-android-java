package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.user.presenter.EditPasswordPresenter;
import com.qsd.jmwh.module.home.user.presenter.EditPasswordViewer;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.qsd.jmwh.utils.countdown.RxCountDownAdapter;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class EditPasswordActivity extends BaseBarActivity implements EditPasswordViewer {
    @PresenterLifeCycle
    EditPasswordPresenter mPresenter = new EditPasswordPresenter(this);
    private NormaFormItemVIew mSendVerCode;
    private RxCountDown countDown;
    private NormaFormItemVIew phoneNum;
    private NormaFormItemVIew againPwdEdit;


    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.edit_password_layout);
    }

    @Override
    protected void loadData() {
        setTitle("重置密码");
        againPwdEdit = bindView(R.id.again_pwd);
        mSendVerCode = bindView(R.id.get_ver_code);
        phoneNum = bindView(R.id.phone);
        phoneNum.setEditText(UserProfile.getInstance().getPhoneNo());

        sendVerCode();
        bindView(R.id.next, v -> mPresenter.modifyPassword(phoneNum,againPwdEdit.getText(), mSendVerCode.getText()));
    }

    private void sendVerCode() {
        mSendVerCode.setRightButtonListener(v -> {
            if (TextUtils.isEmpty(phoneNum.getText())) {
                ToastUtils.show("手机号输入不能为空");
            } else if (!phoneNum.getText().startsWith("1") || phoneNum.getText().length() != 11) {
                ToastUtils.show("检查手机号输入是否正确");
            } else {
                countDown = new RxCountDown();
                countDown.start(60);
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
    }

    @Override
    public void onSuccess() {
        ToastUtils.show("修改成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDown != null) {
            countDown.stop();
        }
    }

}
