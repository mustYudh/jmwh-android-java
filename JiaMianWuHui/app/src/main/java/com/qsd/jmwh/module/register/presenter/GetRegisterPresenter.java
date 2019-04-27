package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.EditRegisterCodeActivity;
import com.qsd.jmwh.module.register.bean.UserAuthCodeBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult")
public class GetRegisterPresenter extends BaseViewPresenter<GetRegisterViewer> {

    public GetRegisterPresenter(GetRegisterViewer viewer) {
        super(viewer);
    }



    public void getRegisterCode(int userId,String token, String sSource, String sReferrer, String WX, String sCity) {
        if (TextUtils.isEmpty(sCity)) {
            ToastUtils.show("城市输入不能为空");
            return;
        }
        if (TextUtils.isEmpty(sSource)) {
            ToastUtils.show("你从哪里知道假面舞会输入不能为空");
            return;
        }
        XHttpProxy.proxy(ApiServices.class)
                .getRegisterCode(token,sSource,sReferrer,WX,sCity)
                .subscribeWith(new TipRequestSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        getCode(userId,token);
                    }
                });
    }


    private void getCode(int userId, String token) {
            XHttpProxy.proxy(ApiServices.class)
                    .getCod(userId,token).subscribeWith(new TipRequestSubscriber<UserAuthCodeBean>() {
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
