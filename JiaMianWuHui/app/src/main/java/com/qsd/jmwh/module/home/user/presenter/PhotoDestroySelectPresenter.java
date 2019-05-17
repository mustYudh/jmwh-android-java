package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult")
public class PhotoDestroySelectPresenter extends BaseViewPresenter<PhotoDestroySelectViewer> {

    public PhotoDestroySelectPresenter(PhotoDestroySelectViewer viewer) {
        super(viewer);
    }



    public void uploadFile(String sFileUrl,int nAttribute,int nInfoType,int nFileType,int nFileFee) {
        XHttpProxy.proxy(ApiServices.class)
                    .addFile(sFileUrl,nAttribute,nInfoType,nFileType,nFileFee,"")
                    .subscribeWith(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            getActivity().finish();
                        }
                    });
    }

    public void deleteFile(int lFileId,int nInfoType) {
            XHttpProxy.proxy(ApiServices.class)
                    .deleteFile(lFileId,nInfoType).subscribeWith(new TipRequestSubscriber<Object>() {
                @Override
                protected void onSuccess(Object o) {
                    ToastUtils.show("删除成功");
                    getActivity().finish();
                }
            });

    }

    public void modifyFile(int nFileType,int nFileFee,int lFileId) {
        XHttpProxy.proxy(OtherApiServices.class)
                .modifyFile(nFileType,nFileFee,lFileId).subscribeWith(new TipRequestSubscriber<Object>() {
            @Override
            protected void onSuccess(Object o) {
                assert getViewer() != null;
                getViewer().modifySuccess(nFileType);
            }
        });

    }
}
