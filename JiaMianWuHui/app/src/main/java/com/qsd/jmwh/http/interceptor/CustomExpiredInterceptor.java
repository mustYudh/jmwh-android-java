package com.qsd.jmwh.http.interceptor;

import com.qsd.jmwh.APP;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.splash.SplashActivity;
import com.qsd.jmwh.utils.JSONUtils;
import com.qsd.jmwh.utils.Result;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.yu.common.launche.LauncherHelper;
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
        Response response = null;
        switch (expiredInfo.getExpiredType()) {
            case 106:
                UserProfile.getInstance().clean();
                LauncherHelper.from(APP.getInstance()).startActivity(SplashActivity.class);
                break;
            default:
        }
        return response;
    }
}
