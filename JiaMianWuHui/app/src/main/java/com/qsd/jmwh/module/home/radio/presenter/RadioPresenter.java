package com.qsd.jmwh.module.home.radio.presenter;


import android.annotation.SuppressLint;
import android.view.View;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.radio.bean.GetDatingUserVipBean;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.qsd.jmwh.module.home.radio.bean.LocalHomeRadioListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

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


    @SuppressLint("CheckResult")
    public void addDatingLikeCount(String lDatingId, String lJoinerId, String lInitiatorId, DelayClickImageView iv_like, DelayClickTextView tv_like, int position, int is_like) {
        XHttpProxy.proxy(ApiServices.class)
                .addDatingLikeCount(lDatingId, lJoinerId, lInitiatorId)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addDatingLikeCountSuccess(iv_like, tv_like, position, is_like);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void addDatingCommentCount(String lDatingId, String lJoinerId, String lInitiatorId,String sContent,LocalHomeRadioListBean item) {
        XHttpProxy.proxy(ApiServices.class)
                .addDatingCommentCount(lDatingId, lJoinerId, lInitiatorId,sContent)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addDatingCommentCountSuccess(item);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getDatingUserVIP(LocalHomeRadioListBean item) {
        XHttpProxy.proxy(ApiServices.class)
                .getDatingUserVIP()
                .subscribeWith(new TipRequestSubscriber<GetDatingUserVipBean>() {
                    @Override
                    protected void onSuccess(GetDatingUserVipBean getDatingUserVipBean) {
                        assert getViewer() != null;
                        getViewer().getDatingUserVIPSuccess(getDatingUserVipBean, item);
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