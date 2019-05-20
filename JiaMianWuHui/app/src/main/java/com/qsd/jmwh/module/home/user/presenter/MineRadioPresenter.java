package com.qsd.jmwh.module.home.user.presenter;


import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.MineRadioListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */

@SuppressLint("CheckResult")
public class MineRadioPresenter extends BaseViewPresenter<MineRadioViewer> {


    public MineRadioPresenter(MineRadioViewer viewer) {
        super(viewer);
    }

    public void getDatingByUserIdData(String pageindex) {
        XHttpProxy.proxy(ApiServices.class)
                .getDatingByUserId(pageindex)
                .subscribeWith(new TipRequestSubscriber<MineRadioListBean>() {
                    @Override
                    protected void onSuccess(MineRadioListBean mineRadioListBean) {
                        assert getViewer() != null;
                        getViewer().getMineRadioList(mineRadioListBean);
                    }
                });
    }


    public void modifyStatus(String nStatus, String lDatingId) {
        XHttpProxy.proxy(ApiServices.class)
                .modifyStatus(nStatus, lDatingId)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().getModifyStatus();
                    }
                });
    }
}