package com.qsd.jmwh.http.interceptor;

import android.content.Intent;
import com.qsd.jmwh.APP;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.splash.SplashActivity;
import com.qsd.jmwh.utils.JSONUtils;
import com.qsd.jmwh.utils.Result;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.xuexiang.xhttp2.utils.HttpUtils;
import com.yu.common.launche.LauncherHelper;
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
                            UserProfile.getInstance().clean();
                            Intent intent = new Intent(APP.getInstance(), SplashActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            LauncherHelper.from(APP.getInstance()).startActivity(intent);
                            response[0] = HttpUtils.getErrorResponse(oldResponse, expiredInfo.getCode(), "需要重新登录");
                        }
                    });
                break;

            default:
        }
        return response[0];
    }
}
