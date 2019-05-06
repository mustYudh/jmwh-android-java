package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

public class PersonPresenter extends BaseViewPresenter<PersonViewer> {
    public PersonPresenter(PersonViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void initPersonListData(double nLat, double nLng, String nTab, String nickName, String pageindex, String nSex) {
        XHttpProxy.proxy(ApiServices.class)
                .getPersonListDate(nLat, nLng, nTab, nickName, pageindex, nSex)
                .subscribeWith(new TipRequestSubscriber<HomePersonListBean>() {
                    @Override
                    protected void onSuccess(HomePersonListBean homePersonListBean) {
                        assert getViewer() != null;
                        getViewer().getDataSuccess(homePersonListBean);
                    }
                });
    }
}
