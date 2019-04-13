package com.qsd.jmwh.module.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
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
            Log.e("=====>", phoneNum.getEditText());
            if (TextUtils.isEmpty(phoneNum.getEditText())) {
                ToastUtils.show("手机号输入不能为空");
            } else {
                RxCountDown countDown = new RxCountDown(60);
                mPresenter.sendVerCode( phoneNum.getEditText(),countDown);
                countDown.setCountDownTimeListener(new RxCountDownAdapter() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e("=====>", "start");
                        mSendVerCode.setRightButtonEnable(false);
                        mSendVerCode.setRightHint("60S");
                    }

                    @Override
                    public void onNext(Integer time) {
                        super.onNext(time);
                        Log.e("=====>", time + "onNext");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                mPresenter.register(phoneNum.getEditText(), password.getEditText(), mSendVerCode.getEditText());
                break;
        }
    }


    @Override
    public void registerSuccess() {
        getLaunchHelper().startActivity(SelectGenderActivity.class);
        finish();
    }
}
