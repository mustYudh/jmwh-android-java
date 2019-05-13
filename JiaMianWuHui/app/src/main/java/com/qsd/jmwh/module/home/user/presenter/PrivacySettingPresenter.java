package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.PrivacySettingStatusBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class PrivacySettingPresenter extends BaseViewPresenter<PrivacySettingViewer> {

    public PrivacySettingPresenter(PrivacySettingViewer viewer) {
        super(viewer);
    }


    public void getUserConfig() {
        XHttpProxy.proxy(OtherApiServices.class).getUserPrivacy().subscribeWith(new TipRequestSubscriber<PrivacySettingStatusBean>() {
            @Override
            protected void onSuccess(PrivacySettingStatusBean statusBean) {
                assert getViewer() != null;
                getViewer().getPrivacySettingStatus(statusBean);
            }
        });
    }

    public void setUserPrivacy(int nType,int nStatus,int dGalaryVal) {
        XHttpProxy.proxy(OtherApiServices.class).setUserPrivacy(nType,nStatus,dGalaryVal).subscribeWith(new TipRequestSubscriber<Object>() {
            @Override
            protected void onSuccess(Object o) {
                assert getViewer() != null;
                getViewer().setSuccess(nType,nStatus);
            }
        });
    }
}
