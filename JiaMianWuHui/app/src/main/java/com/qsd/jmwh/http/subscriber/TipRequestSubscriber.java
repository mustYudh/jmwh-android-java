
package com.qsd.jmwh.http.subscriber;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;
import com.yu.common.loading.LoadingDialog;
import com.yu.common.toast.ToastUtils;

public abstract class TipRequestSubscriber<T> extends BaseSubscriber<T> {

    private Activity activity;

    public TipRequestSubscriber() {

    }

    public TipRequestSubscriber(FragmentActivity activity,boolean show) {
        if (show) {
            this.activity = activity;
        }
    }


    public TipRequestSubscriber(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onStart() {
        if (activity != null) {
            LoadingDialog.showNormalLoading(activity,false);
            LoadingDialog.startLoading( "正在加载");
        }
        super.onStart();
    }

    @Override
    protected void onError(ApiException apiException) {
        if (activity != null) {
            LoadingDialog.loadingFail(apiException.getDisplayMessage());
            new Handler().postDelayed(() -> LoadingDialog.dismissLoading(), 1000);
        } else {
            ToastUtils.show(apiException.getDisplayMessage());
        }

    }


    @Override
    public void onComplete() {
        if (activity != null) {
            LoadingDialog.loadingSuccess("加载成功");
        }
        new Handler().postDelayed(() -> LoadingDialog.dismissLoading(), 1000);
    }
}
