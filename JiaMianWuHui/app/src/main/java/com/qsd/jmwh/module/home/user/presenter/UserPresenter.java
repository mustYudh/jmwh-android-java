package com.qsd.jmwh.module.home.user.presenter;


import android.annotation.SuppressLint;

import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.UserCenterInfo;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

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
                .subscribeWith(new TipRequestSubscriber<UserCenterInfo>() {
                    @Override
                    protected void onSuccess(UserCenterInfo userCenterMyInfo) {
                        assert getViewer() != null;
                        getViewer().setUserInfo(userCenterMyInfo);
                    }
                });
    }

    public void destroyImgBrowsingHis() {
        XHttpProxy.proxy(OtherApiServices.class)
                .destroyImgBrowsingHis(UserProfile.getInstance().getLat(),
                        UserProfile.getInstance().getLng())
        .subscribeWith(new TipRequestSubscriber<Object>() {
            @Override
            protected void onSuccess(Object o) {
                ToastUtils.show("恢复成功");
                assert getViewer() != null;
                getViewer().refreshData();
            }
        });
    }
}