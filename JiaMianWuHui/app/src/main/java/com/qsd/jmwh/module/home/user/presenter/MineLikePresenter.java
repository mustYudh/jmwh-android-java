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
    public void initAddLoveUser(String lLoveUserId, String nType, boolean is_love, int position, DelayClickImageView iv_love) {
        XHttpProxy.proxy(ApiServices.class)
                .addLoveUser(lLoveUserId, nType, is_love)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addLoveUserSuccess(is_love, position, iv_love);
                    }
                });
    }


    @SuppressLint("CheckResult")
    public void initAddBlackUser(String lLoveUserId, String nType, boolean is_black, int position, DelayClickTextView tv_black) {
        XHttpProxy.proxy(ApiServices.class)
                .addLoveUser(lLoveUserId, nType, is_black)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addBlackUserSuccess(is_black, position, tv_black);
                    }
                });
    }
}