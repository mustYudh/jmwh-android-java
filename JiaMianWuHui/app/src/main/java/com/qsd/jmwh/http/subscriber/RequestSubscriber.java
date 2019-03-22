package com.qsd.jmwh.http.subscriber;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;
import com.yu.common.loading.LoadingDialog;
import com.yu.common.toast.ToastUtils;

public class RequestSubscriber<T> extends BaseSubscriber<T> {
    private Activity activity;


    public RequestSubscriber() {

    }

    public RequestSubscriber(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onStart() {
        if (activity != null) {
//            LoadingDialog.showLoading(activity,view);
        }
        super.onStart();
    }

    @Override
    protected void onError(ApiException apiException) {
        if (activity != null) {
            LoadingDialog.dismissLoading();
        }
        ToastUtils.show(activity, apiException.getDisplayMessage());
    }

    @Override
    protected void onSuccess(T t) {

    }
}
