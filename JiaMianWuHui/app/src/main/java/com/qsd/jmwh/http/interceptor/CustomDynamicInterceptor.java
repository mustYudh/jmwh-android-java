package com.qsd.jmwh.http.interceptor;

import com.xuexiang.xhttp2.interceptor.BaseDynamicInterceptor;

import java.io.IOException;
import java.util.TreeMap;

import okhttp3.Request;
import okhttp3.Response;

public class CustomDynamicInterceptor extends BaseDynamicInterceptor<CustomDynamicInterceptor> {

    @Override
    protected TreeMap<String, Object> updateDynamicParams(TreeMap<String, Object> dynamicMap) {
        return dynamicMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        // TODO: 2019/3/22 判断登录添加TOKEN
        return super.intercept(chain);
    }
}
