package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.utils.MD5Utils;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

public class EditPasswordPresenter extends BaseViewPresenter<EditPasswordViewer> {

    public EditPasswordPresenter(EditPasswordViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void modifyPassword(String oldPwd, String newPwd,String againPwd) {
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
                    .modifyPassword(oldPwd, MD5Utils.string2MD5(againPwd))
                    .subscribeWith(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            assert getViewer() != null;
                            getViewer().onSuccess();
                        }
                    });
    }
}
