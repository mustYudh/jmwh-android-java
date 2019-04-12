
package com.qsd.jmwh.http.subscriber;

import com.qsd.jmwh.APP;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;
import com.yu.common.toast.ToastUtils;

public abstract class TipRequestSubscriber<T> extends BaseSubscriber<T> {



    @Override
    public void onError(ApiException e) {
        ToastUtils.show(APP.getInstance(),e.getDisplayMessage());
    }
}
