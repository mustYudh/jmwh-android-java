package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.bean.UserInfo;
import com.qsd.jmwh.module.register.presenter.RegisterPresenter;
import com.qsd.jmwh.module.register.presenter.RegisterViewer;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.qsd.jmwh.utils.countdown.RxCountDownAdapter;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

public class RegisterActivity extends BaseBarActivity implements RegisterViewer, View.OnClickListener {

    @PresenterLifeCycle
    RegisterPresenter mPresenter = new RegisterPresenter(this);
    private NormaFormItemVIew mSendVerCode;
    private NormaFormItemVIew phoneNum;
    private NormaFormItemVIew password;
    private RxCountDown countDown;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.register_activity_layout);
    }

    @Override
    protected void loadData() {
        setTitle("手机号码注册");
        initView();
        initListener();

    }

    private void initView() {
        mSendVerCode = bindView(R.id.get_ver_code);
        phoneNum = bindView(R.id.phone);
        password = bindView(R.id.password);
    }

    private void initListener() {
        bindView(R.id.next, this);
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
    protected void onDestroy() {
        super.onDestroy();
        if (countDown != null) {
            countDown.stop();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                mPresenter.register(phoneNum.getText(), password.getText(), mSendVerCode.getText());
                break;
        }
    }


    @Override
    public void registerSuccess(UserInfo registerBean) {
        LoginInfo info = new LoginInfo();
        info.sIMID = registerBean.sIMID;
        info.lUserId = registerBean.lUserId;
        info.sIMToken = registerBean.sIMToken;
        info.token = registerBean.token;
        UserProfile.getInstance().appLogin(info);
        getLaunchHelper().startActivity(SelectGenderActivity.getIntent(getActivity(),registerBean.lUserId,registerBean.token));
        finish();
    }
}
