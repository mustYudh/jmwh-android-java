
package com.qsd.jmwh.http.subscriber;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;
import com.yu.common.loading.LoadingDialog;

public abstract class NoTipRequestSubscriber<T> extends BaseSubscriber<T> {
    private Activity activity;


    public NoTipRequestSubscriber() {

    }

    public NoTipRequestSubscriber(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onStart() {
        if (activity != null) {
            LoadingDialog.showNormalLoading(activity, false);
            LoadingDialog.startLoading("正在加载");
        }
        super.onStart();
    }

    @Override
    protected void onError(ApiException apiException) {
        if (activity != null) {
            LoadingDialog.loadingFail(apiException.getDisplayMessage());
        }
        new Handler().postDelayed(() -> LoadingDialog.dismissLoading(), 1000);

    }


    @Override
    public void onComplete() {
        if (activity != null) {
            LoadingDialog.loadingSuccess("加载成功");
        }
    }

}
