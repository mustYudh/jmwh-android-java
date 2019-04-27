package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.DateProjectBean;
import com.qsd.jmwh.module.register.bean.UploadUserInfoParams;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class EditUserInfoPresenter extends BaseViewPresenter<EditUserInfoViewer> {

    public EditUserInfoPresenter(EditUserInfoViewer viewer) {
        super(viewer);
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


    public void uploadUserInfo(UploadUserInfoParams params) {
        XHttpProxy.proxy(ApiServices.class)
                .modifyUserInfo(params.sNickName, params.sDateRange, params.sAge, params.sJob,
                        params.sDatePro, params.sHeight, params.sWeight, params.lUserId,
                        params.sIntroduce, params.token,params.sUserHeadPic,params.sBust,
                        params.QQ,params.WX,params.bHiddenQQandWX)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        getActivity().finish();
                    }
                })
        ;
    }

}
