package com.qsd.jmwh.http.interceptor;

import com.qsd.jmwh.utils.JSONUtils;
import com.qsd.jmwh.utils.Result;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.xuexiang.xhttp2.utils.HttpUtils;

import okhttp3.Response;

public class CustomExpiredInterceptor extends BaseExpiredInterceptor {

    @Override
    protected ExpiredInfo isResponseExpired(Response oldResponse, String bodyString) {
        int code = JSONUtils.getInt(bodyString, Result.CODE, 0);
        ExpiredInfo expiredInfo = new ExpiredInfo(code);
        switch (code) {
            case 20001:
                expiredInfo.setExpiredType(20001);
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
            case 20001:
                response = HttpUtils.getErrorResponse(oldResponse, expiredInfo.getCode(), oldResponse.message());
                break;
            default:
        }
        return response;
    }
}
