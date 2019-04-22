package com.qsd.jmwh.module.home.user.presenter;


import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/3/7
 */

@SuppressLint("CheckResult")
public class UserPresenter extends BaseViewPresenter<UserViewer> {


    public UserPresenter(UserViewer viewer) {
        super(viewer);
    }


    public void getMyInfo() {
        XHttpProxy.proxy(ApiServices.class)
                .getUserCenterInfo()
                .subscribeWith(new TipRequestSubscriber<UserCenterMyInfo>() {
                    @Override
                    protected void onSuccess(UserCenterMyInfo userCenterMyInfo) {

                    }
                });
    }
}