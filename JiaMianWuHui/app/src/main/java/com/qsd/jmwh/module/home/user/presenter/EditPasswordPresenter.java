package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.qsd.jmwh.utils.MD5Utils;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult")
public class EditPasswordPresenter extends BaseViewPresenter<EditPasswordViewer> {

    public EditPasswordPresenter(EditPasswordViewer viewer) {
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


    public void modifyPassword(String oldPwd, String newPwd,String againPwd,String sAuthCode) {
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.show("原密码输入不能为空");
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.show("新码输入不能为空");
            return;
        }
        if (TextUtils.isEmpty(againPwd)) {
            ToastUtils.show("确认信密码输入不能为空");
            return;
        }
        if (!newPwd.equals(againPwd)) {
            ToastUtils.show("两次输入密码不一致");
            return;
        }
        if (oldPwd.equals(againPwd)) {
            ToastUtils.show("新密码不能与原密码相同");
            return;
        }
        XHttpProxy.proxy(ApiServices.class)
                    .modifyPassword(oldPwd, MD5Utils.string2MD5(againPwd),sAuthCode)
                    .subscribeWith(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            assert getViewer() != null;
                            getViewer().onSuccess();
                        }
                    });
    }
}
