package com.qsd.jmwh.module.home.user.presenter;


import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.MineLikeBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

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
}