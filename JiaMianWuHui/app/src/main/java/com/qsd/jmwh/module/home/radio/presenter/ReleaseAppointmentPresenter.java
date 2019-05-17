package com.qsd.jmwh.module.home.radio.presenter;


import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */


public class ReleaseAppointmentPresenter extends BaseViewPresenter<ReleaseAppointmentViewer> {


    public ReleaseAppointmentPresenter(ReleaseAppointmentViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void addDatingData(String sDatingTitle, String sDatingRange, String sDatingTime, String sDatingTimeExt, String sContent, String nSex, double nLng, double nLat, String bCommentType, String bHiddenType, String sImg, String sDatingHope) {
        XHttpProxy.proxy(ApiServices.class)
                .addDating(sDatingTitle, sDatingRange, sDatingTime, sDatingTimeExt, sContent, nSex, nLat, nLng, bCommentType, bHiddenType, sImg, sDatingHope)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addDatingSuccess();
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
}