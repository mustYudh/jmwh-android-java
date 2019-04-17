package com.qsd.jmwh.module.home.radio.presenter;


import android.annotation.SuppressLint;
import android.view.View;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
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
    public void initRadioData(float nLat, float nLng, int nTab, int pageindex, int nSex) {
        XHttpProxy.proxy(ApiServices.class)
                .getRadioData(nLat, nLng, nTab, pageindex, nSex)
                .subscribeWith(new TipRequestSubscriber<HomeRadioListBean>() {
                    @Override
                    protected void onSuccess(HomeRadioListBean homeRadioListBean) {
                        assert getViewer() != null;

                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);

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