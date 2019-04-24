package com.qsd.jmwh.module.home.user.presenter;


import android.annotation.SuppressLint;

import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
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


    public void setHeader(String path, String objectName, String userId) {
        PersistenceResponse response = UploadImage.uploadImage(getActivity(), objectName, path);
        if (response.success) {
            XHttpProxy.proxy(ApiServices.class)
                    .uploadHeader(userId, UserProfile.getInstance().getAppToken(), response.cloudUrl)
                    .safeSubscribe(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            assert getViewer() != null;
                            getViewer().setUserHeaderSuccess(response.cloudUrl);
                        }
                    });

        }

    }


    public void getMyInfo() {
        XHttpProxy.proxy(ApiServices.class)
                .getUserCenterInfo()
                .subscribeWith(new TipRequestSubscriber<UserCenterMyInfo>() {
                    @Override
                    protected void onSuccess(UserCenterMyInfo userCenterMyInfo) {
                        assert getViewer() != null;
                        getViewer().setUserInfo(userCenterMyInfo);
                    }
                });
    }
}