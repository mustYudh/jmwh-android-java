package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.ui.DelayClickImageView;

public class SearchPresenter extends BaseViewPresenter<SearchViewer> {
    public SearchPresenter(SearchViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void initPersonListData(double nLat, double nLng, String nTab, String nickName, String pageindex, String nSex,String city) {
        XHttpProxy.proxy(ApiServices.class)
                .getPersonListDate(nLat, nLng, nTab, nickName, pageindex, nSex,city)
                .subscribeWith(new TipRequestSubscriber<HomePersonListBean>() {
                    @Override
                    protected void onSuccess(HomePersonListBean homePersonListBean) {
                        assert getViewer() != null;
                        getViewer().getDataSuccess(homePersonListBean);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void initAddLoveUser(String lLoveUserId, String nType,int position, DelayClickImageView iv_love) {
        XHttpProxy.proxy(ApiServices.class)
                .addLoveUser(lLoveUserId, nType)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().addLoveUserSuccess(position,iv_love);
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
}
