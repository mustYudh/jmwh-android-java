package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

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


    public void buyGalleryPay(int lBuyOtherUserId,int nPayVal) {
        XHttpProxy.proxy(OtherApiServices.class)
                .buyGalleryPay(lBuyOtherUserId, 5,nPayVal).subscribeWith(new TipRequestSubscriber<Object>() {
            @Override
            protected void onSuccess(Object info) {
                ToastUtils.show("解锁成功");
                assert getViewer() != null;
                getViewer().refreshData();
            }
        });
    }


}
