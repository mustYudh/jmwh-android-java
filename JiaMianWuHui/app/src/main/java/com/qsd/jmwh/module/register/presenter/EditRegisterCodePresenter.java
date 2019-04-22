package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
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

}
