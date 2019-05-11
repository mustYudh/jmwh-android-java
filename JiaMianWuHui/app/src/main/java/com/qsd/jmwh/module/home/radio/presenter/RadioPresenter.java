package com.qsd.jmwh.module.home.radio.presenter;


import android.annotation.SuppressLint;
import android.view.View;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */


public class RadioPresenter extends BaseViewPresenter<RadioViewer> {


    public RadioPresenter(RadioViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void initRadioData(String lUserId, double nLat, double nLng, String sDatingRange, String nTab, String pageindex, String nSex) {
        XHttpProxy.proxy(ApiServices.class)
                .getRadioDate(lUserId, nLat, nLng, sDatingRange, nTab, pageindex, nSex)
                .subscribeWith(new TipRequestSubscriber<HomeRadioListBean>() {
                    @Override
                    protected void onSuccess(HomeRadioListBean homeRadioListBean) {
                        assert getViewer() != null;
                        getViewer().getDataSuccess(homeRadioListBean);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void initRadioDataAll(String lUserId, double nLat, double nLng, String sDatingRange, String nTab, String pageindex) {
        XHttpProxy.proxy(ApiServices.class)
                .getRadioDateAll(lUserId, nLat, nLng, sDatingRange, nTab, pageindex)
                .subscribeWith(new TipRequestSubscriber<HomeRadioListBean>() {
                    @Override
                    protected void onSuccess(HomeRadioListBean homeRadioListBean) {
                        assert getViewer() != null;
                        getViewer().getDataSuccess(homeRadioListBean);
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void initRadioConfigData(String nType) {
        XHttpProxy.proxy(ApiServices.class)
                .getConfigList(nType)
                .subscribeWith(new TipRequestSubscriber<GetRadioConfigListBean>() {
                    @Override
                    protected void onSuccess(GetRadioConfigListBean configListBean) {
                        assert getViewer() != null;
                        getViewer().getConfigDataSuccess(configListBean);
                    }
                });
    }

    @Override
    public void createdView(View view) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void willDestroy() {

    }
}