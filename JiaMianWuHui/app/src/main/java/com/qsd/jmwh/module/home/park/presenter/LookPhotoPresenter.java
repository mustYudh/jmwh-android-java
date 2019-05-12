package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class LookPhotoPresenter extends BaseViewPresenter<LookPhotoViewer> {

    public LookPhotoPresenter(LookPhotoViewer viewer) {
        super(viewer);
    }


    public void pay(int lBuyImgId, int nPayVal) {
        XHttpProxy.proxy(OtherApiServices.class).getBuyImgPaySign(lBuyImgId,nPayVal,5).subscribeWith(new TipRequestSubscriber<Object>() {
            @Override
            protected void onSuccess(Object o) {
                assert getViewer() != null;
                getViewer().paySuccess();
            }
        });
    }


    public void addBrowsingHis(int lUserId,int lBrowseInfoId,int nPayType,int nBrowseInfType) {
        XHttpProxy.proxy(OtherApiServices.class).addBrowsingHis(lUserId,lBrowseInfoId,nPayType,nBrowseInfType).subscribeWith(new TipRequestSubscriber<Object>() {
            @Override
            protected void onSuccess(Object o) {
                assert getViewer() != null;
                getViewer().startLookPhoto();
            }
        });
    }
}
