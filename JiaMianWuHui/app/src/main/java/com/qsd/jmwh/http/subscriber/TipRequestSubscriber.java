
package com.qsd.jmwh.http.subscriber;

import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;

public abstract class TipRequestSubscriber<T> extends BaseSubscriber<T> {



    @Override
    public void onError(ApiException e) {
    }
}
