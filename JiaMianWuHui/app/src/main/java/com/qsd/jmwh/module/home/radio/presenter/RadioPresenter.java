package com.qsd.jmwh.module.home.radio.presenter;


import android.annotation.SuppressLint;
import android.view.View;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
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
    public void initRadioData(double nLat, double nLng,int nTab, int pageindex, int nSex) {
        XHttpProxy.proxy(ApiServices.class)
                .getRadioDate(nLat, nLng, nTab, pageindex, nSex,"")
                .subscribeWith(new TipRequestSubscriber<HomeRadioListBean>() {
                    @Override
                    protected void onSuccess(HomeRadioListBean homeRadioListBean) {
                        assert getViewer() != null;
                        getViewer().getDataSuccess(homeRadioListBean);
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