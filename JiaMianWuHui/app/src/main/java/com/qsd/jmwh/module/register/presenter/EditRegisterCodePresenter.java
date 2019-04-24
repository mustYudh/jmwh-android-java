package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.UserAuthCodeBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class EditRegisterCodePresenter extends BaseViewPresenter<EditRegisterCodeViewer> {

    public EditRegisterCodePresenter(EditRegisterCodeViewer viewer) {
        super(viewer);
    }


    public void commitCode(int userCode,String token, String code) {
        XHttpProxy.proxy(ApiServices.class)
                .getUserAuthByCode(userCode,token,code)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().registerSuccess();
                    }
                });

    }

    public void getUserCode(int code,String token) {
        XHttpProxy.proxy(ApiServices.class)
                .getCod(code,token).subscribeWith(new NoTipRequestSubscriber<UserAuthCodeBean>() {
            @Override
            protected void onSuccess(UserAuthCodeBean result) {
                if (result != null && !TextUtils.isEmpty(result.sAuthCode)) {
                    assert getViewer() != null;
                    getViewer().getUserCode(result.sAuthCode);
                }
            }
        });
    }

}
