
package com.qsd.jmwh.http.subscriber;

import com.qsd.jmwh.APP;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.BaseSubscriber;
import com.yu.common.toast.ToastUtils;

/**
 * 网络请求的订阅，只存储错误的日志
 *
 * @author xuexiang
 * @since 2018/8/2 下午3:37
 */
public abstract class NoTipRequestSubscriber<T> extends BaseSubscriber<T> {



    @Override
    public void onError(ApiException e) {
        ToastUtils.show(APP.getInstance(),e.getDisplayMessage());
    }
}
