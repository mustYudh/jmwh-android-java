package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.EditRegisterCodeActivity;
import com.qsd.jmwh.module.register.bean.DateProjectBean;
import com.qsd.jmwh.module.register.bean.UploadUserInfoParams;
import com.qsd.jmwh.module.register.bean.UserAuthCodeBean;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult")
public class EditUserInfoPresenter extends BaseViewPresenter<EditUserInfoViewer> {

    public EditUserInfoPresenter(EditUserInfoViewer viewer) {
        super(viewer);
    }

    public void setHeader(String path, String objectName, String userId, String token) {
        PersistenceResponse response = UploadImage.uploadImage(getActivity(), objectName, path);
        if (response.success) {
            XHttpProxy.proxy(ApiServices.class)
                    .uploadHeader(userId, token, response.cloudUrl)
                    .safeSubscribe(new TipRequestSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            assert getViewer() != null;
                            getViewer().setUserHeaderSuccess(response.cloudUrl);
                        }
                    });

        }

    }

    public void uploadUserInfo(UploadUserInfoParams params) {
        XHttpProxy.proxy(ApiServices.class)
                .modifyUserInfo(params.sNickName, params.sDateRange, params.sAge, params.sJob,
                        params.sDatePro, params.sHeight, params.sWeight, params.lUserId,
                        params.sIntroduce, params.token,params.sUserHeadPic,params.sBust,
                        params.QQ,params.WX)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        assert getViewer() != null;
                        getViewer().commitUserInfo();
                    }
                })
        ;
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

    public void getCode(int code, String token,int sex) {
        if (sex == 0) {
            ToastUtils.show("注册成功请登录");
            getActivity().finish();
        } else {
            XHttpProxy.proxy(ApiServices.class)
                    .getCod(code,token).subscribeWith(new TipRequestSubscriber<UserAuthCodeBean>() {
                @Override
                protected void onSuccess(UserAuthCodeBean result) {
                    ToastUtils.show("邀请码获取成功");
                    Intent intent = new Intent();
                    Log.e("=========>获取成功1",result.sAuthCode);
                    intent.putExtra(EditRegisterCodeActivity.GET_AUTH_CODE_RESULT,result.sAuthCode);
                    getActivity().setResult(Activity.RESULT_OK,intent);
                    getActivity().finish();
                }
            });

        }
    }

}
