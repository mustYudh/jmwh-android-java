package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class PhotoDestroySelectPresenter extends BaseViewPresenter<PhotoDestroySelectViewer> {

    public PhotoDestroySelectPresenter(PhotoDestroySelectViewer viewer) {
        super(viewer);
    }



    public void uploadFile(String sFileUrl, int nAttribute, int nInfoType, int nFileType, int nFileFee) {
        XHttpProxy.proxy(ApiServices.class)
                    .addFile(sFileUrl,nAttribute,nInfoType,nFileType,nFileFee)
                    .subscribeWith(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            getActivity().finish();
                        }
                    });
    }
}
