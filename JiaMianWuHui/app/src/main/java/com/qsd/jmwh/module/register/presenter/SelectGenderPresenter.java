package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

public class SelectGenderPresenter extends BaseViewPresenter<SelectGenderViewer> {


    public SelectGenderPresenter(SelectGenderViewer viewer) {
        super(viewer);
    }

    @SuppressLint("CheckResult")
    public void selectGender(int nSex, int account) {
        if (account != -1) {
            XHttpProxy.proxy(ApiServices.class)
                    .selectGender(nSex, account)
                    .subscribeWith(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            assert getViewer() != null;
                            getViewer().selectedSuccess(nSex);
                        }
                    });
        }
    }
}
