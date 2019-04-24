package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;


@SuppressLint("CheckResult")
public class SettingPhoneNumberPresenter extends BaseViewPresenter<SettingPhoneNumberViewer> {

    public SettingPhoneNumberPresenter(SettingPhoneNumberViewer viewer) {
        super(viewer);
    }

    public void sendVerCode(String number, RxCountDown countDown) {
        XHttpProxy.proxy(ApiServices.class)
                .send(number)
                .subscribeWith(new TipRequestSubscriber<SendVerCodeBean>() {
                    @Override
                    protected void onSuccess(SendVerCodeBean sendVerCodeBeanApiResult) {
                        assert getViewer() != null;
                        ToastUtils.show("发送成功");
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        countDown.stopCoutdown();
                    }
                });
    }


    public void bindPhone(String phone, String verCode) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show("手机号输入不能为空");
            return;
        }
        if (TextUtils.isEmpty(verCode)) {
            ToastUtils.show("验证码号输入不能为空");
            return;
        }
        if (!phone.startsWith("1") || phone.length() != 11) {
            ToastUtils.show("检查手机号输入是否正确");
            return;
        }
        XHttpProxy.proxy(ApiServices.class)
                    .bindPhone(phone,"",verCode)
                    .subscribeWith(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            assert getViewer() != null;
                            getViewer().onSuccess();
                        }
                    });

    }
}
