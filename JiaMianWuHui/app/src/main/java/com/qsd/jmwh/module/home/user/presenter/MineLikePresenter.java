package com.qsd.jmwh.module.home.user.presenter;


import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

/**
 * @author yudneghao
 * @date 2019/3/7
 */

@SuppressLint("CheckResult")
public class MineLikePresenter extends BaseViewPresenter<MineLikeViewer> {


    public MineLikePresenter(MineLikeViewer viewer) {
        super(viewer);
    }

    public void getMineLikeData(double nLat, double nLng, String nType) {
        XHttpProxy.proxy(ApiServices.class)
                .getMineLikeList(nLat, nLng, nType)
                .subscribeWith(new TipRequestSubscriber<MineLikeBean>() {
                    @Override
                    protected void onSuccess(MineLikeBean mineLikeBean) {
                        assert getViewer() != null;
                        getViewer().getMineLikeList(mineLikeBean);
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void initAddLoveUser(String lLoveUserId, String nType, int position, DelayClickImageView iv_love) {
        XHttpProxy.proxy(ApiServices.class)
                .addLoveUser(lLoveUserId, nType)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addLoveUserSuccess(position, iv_love);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void initdelLoveUser(String lLoveUserId, String nType, int position, DelayClickImageView iv_love) {
        XHttpProxy.proxy(ApiServices.class)
                .delLoveUser(lLoveUserId, nType)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().delLoveUserSuccess(position, iv_love);
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void initAddBlackUser(String lLoveUserId, String nType, int position, DelayClickTextView tv_black) {
        XHttpProxy.proxy(ApiServices.class)
                .addLoveUser(lLoveUserId, nType)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addBlackUserSuccess(position, tv_black);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void initDelLoveUser(String lLoveUserId, String nType, int position, DelayClickTextView tv_black) {
        XHttpProxy.proxy(ApiServices.class)
                .delLoveUser(lLoveUserId, nType)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().delBlackUserSuccess(position, tv_black);
                    }
                });
    }
}