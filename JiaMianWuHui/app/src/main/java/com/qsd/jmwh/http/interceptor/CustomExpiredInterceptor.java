package com.qsd.jmwh.http.interceptor;

import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.utils.ActivityManager;
import com.qsd.jmwh.utils.JSONUtils;
import com.qsd.jmwh.utils.Result;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.xuexiang.xhttp2.utils.HttpUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Response;

public class CustomExpiredInterceptor extends BaseExpiredInterceptor {

    @Override
    protected ExpiredInfo isResponseExpired(Response oldResponse, String bodyString) {
        int code = JSONUtils.getInt(bodyString, Result.CODE, 0);
        ExpiredInfo expiredInfo = new ExpiredInfo(code);
        switch (code) {
            case 106:
                expiredInfo.setExpiredType(106);
                expiredInfo.setBodyString(bodyString);
                break;
            default:
        }
        return expiredInfo;
    }

    @Override
    protected Response responseExpired(Response oldResponse, Chain chain, ExpiredInfo expiredInfo) {
        final Response[] response = { oldResponse };
        switch (expiredInfo.getExpiredType()) {
            case 106:
                Observable.create(emitter -> {}).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    new NoTipRequestSubscriber<Object>() {
                        @Override protected void onSuccess(Object o) {
                            ActivityManager.getInstance().reLogin();
                            response[0] = HttpUtils.getErrorResponse(oldResponse, expiredInfo.getCode(), "登录已失效,请重新登录!");
                        }
                    });
                break;

            default:
        }
        return response[0];
    }
}
