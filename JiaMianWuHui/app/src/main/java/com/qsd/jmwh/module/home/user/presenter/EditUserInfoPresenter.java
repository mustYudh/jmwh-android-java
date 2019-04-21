package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.DateProjectBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class EditUserInfoPresenter extends BaseViewPresenter<EditUserInfoViewer> {

    public EditUserInfoPresenter(EditUserInfoViewer viewer) {
        super(viewer);
    }


    public void getDateProject() {
        XHttpProxy.proxy(ApiServices.class)
                .getDateProject().subscribeWith(new TipRequestSubscriber<DateProjectBean>() {
            @Override
            protected void onSuccess(DateProjectBean dateProjectBean) {
                assert getViewer() != null;
                getViewer().showDateProjectList(dateProjectBean.sDateProList);
            }
        });

    }

}
