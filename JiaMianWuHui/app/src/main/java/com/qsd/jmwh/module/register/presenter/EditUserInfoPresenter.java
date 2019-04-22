package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.DateProjectBean;
import com.qsd.jmwh.module.register.bean.EditUserInfo;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class EditUserInfoPresenter extends BaseViewPresenter<EditUserInfoViewer> {

    public EditUserInfoPresenter(EditUserInfoViewer viewer) {
        super(viewer);
    }

    public void setHeader(String path,String objectName,String userId,String token) {
        PersistenceResponse response = UploadImage.uploadImage(getActivity(),objectName,path);
        if (response.success) {
            XHttpProxy.proxy(ApiServices.class)
                    .uploadHeader(userId,token,response.cloudUrl)
                        .safeSubscribe(new TipRequestSubscriber<Object>() {
                            @Override
                            protected void onSuccess(Object o) {
                                assert getViewer() != null;
                                getViewer().setUserHeaderSuccess(response.cloudUrl);
                            }
                        });

        }

    }


    public void getDateProject() {
        XHttpProxy.proxy(ApiServices.class)
                .getDateProject().subscribeWith(new TipRequestSubscriber<DateProjectBean>() {
            @Override
            protected void onSuccess(DateProjectBean dateProjectBean) {
                assert getViewer() != null;
                getViewer().showDateProjectList(dateProjectBean.sDateProList);
            }
        });

    }

    public void editUserInfo(EditUserInfo editUserInfo) {
        assert getViewer() != null;
        getViewer().commitUserInfo();
    }
}
