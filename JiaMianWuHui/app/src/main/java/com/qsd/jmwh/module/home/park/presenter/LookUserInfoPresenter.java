package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class LookUserInfoPresenter extends BaseViewPresenter<LookUserInfoViewer> {

    public LookUserInfoPresenter(LookUserInfoViewer viewer) {
        super(viewer);
    }


    public void getUserInfo(int lUserId, double nLat, double nLng) {
        XHttpProxy.proxy(ApiServices.class)
                .getOtherUserInfo(lUserId,nLat,nLng)
                .subscribeWith(new TipRequestSubscriber<OtherUserInfoBean>() {
                    @Override
                    protected void onSuccess(OtherUserInfoBean userCenterInfo) {
                        assert getViewer() != null;
                        getViewer().setUserInfo(userCenterInfo);
                    }
                });
    }
}
