package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.VipInfoBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

public class ToByVipPresenter extends BaseViewPresenter<ToByVipViewer> {

    public ToByVipPresenter(ToByVipViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void getVipInfo(int lUserId,String token) {
        XHttpProxy.proxy(ApiServices.class)
                .getVipInfoList(lUserId,token)
                .subscribeWith(new TipRequestSubscriber<VipInfoBean>() {
                    @Override
                    protected void onSuccess(VipInfoBean vipInfoBean) {
                        assert getViewer() != null;
                        if (vipInfoBean != null) {
                            getViewer().getVipInfo(vipInfoBean);
                        }
                    }
                });
    }
}
