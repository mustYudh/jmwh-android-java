package com.qsd.jmwh.module.login.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.request.CustomApiResult;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.qsd.jmwh.utils.MD5Utils;
import com.qsd.jmwh.utils.RxSchedulerUtils;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

public class LoginPresenter extends BaseViewPresenter<LoginViewer> {

    public LoginPresenter(LoginViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void login(String userName, String pwd) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtils.show("账号输入为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show("密码输入为空");
            return;
        }
        XHttpProxy.proxy(ApiServices.class)
                .login(userName, MD5Utils.string2MD5(pwd))
                .subscribeWith(new TipRequestSubscriber<LoginInfo>() {
                    @Override
                    protected void onSuccess(LoginInfo loginInfo) {

                    }


                });

        XHttp.custom(ApiServices.class)
                .login(userName, MD5Utils.string2MD5(pwd))
                .compose(RxSchedulerUtils.<CustomApiResult<SendVerCodeBean>>_io_main_o())
                .subscribeWith(new TipRequestSubscriber<SendVerCodeBean>() {
                    @Override
                    protected void onSuccess(SendVerCodeBean r) {

                    }
                });
    }
}
