package com.qsd.jmwh.http.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.utils.JSONUtils;
import com.qsd.jmwh.utils.MD5Utils;
import com.xuexiang.xhttp2.interceptor.BaseDynamicInterceptor;
import com.xuexiang.xhttp2.utils.HttpUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class CustomDynamicInterceptor extends BaseDynamicInterceptor<CustomDynamicInterceptor> {

    @Override
    protected TreeMap<String, Object> updateDynamicParams(TreeMap<String, Object> dynamicMap) {
        return dynamicMap;
    }

    //参考BaseDynamicInterceptor
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(addPostParamsSign(chain.request()));
    }

    private String bodyToString(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            Charset charset = HttpUtils.UTF8;
            MediaType contentType = copy.body().contentType();
            if (contentType != null) {
                charset = contentType.charset(HttpUtils.UTF8);
            }
            return URLDecoder.decode(buffer.readString(charset), HttpUtils.UTF8.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private Request addPostParamsSign(Request request) {
        HttpUrl mHttpUrl = HttpUrl.parse(HttpUtils.parseUrl(request.url().toString()));
        if (request.body() instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) request.body();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            List<MultipartBody.Part> oldParts = multipartBody.parts();

            //拼装新的参数
            List<MultipartBody.Part> newParts = new ArrayList<>();
            newParts.addAll(oldParts);
            TreeMap<String, Object> oldParams = new TreeMap<>();
            TreeMap<String, Object> newParams = updateDynamicParams(oldParams);
            for (Map.Entry<String, Object> paramEntry : newParams.entrySet()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(paramEntry.getKey(),
                        String.valueOf(paramEntry.getValue().toString()));
                newParts.add(part);
            }
            for (MultipartBody.Part part : newParts) {
                bodyBuilder.addPart(part);
            }
            multipartBody = bodyBuilder.build();
            request = request.newBuilder().post(multipartBody).build();
        } else if (request.body() instanceof RequestBody) {
            assert mHttpUrl != null;
            FormBody.Builder formBody = new FormBody.Builder();
            Map<String, Object> params = handleParams(request);
            if (params != null) {
                if (params.size() > 0) {
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        if (entry != null && !TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue().toString())) {
                            formBody.add(entry.getKey(), entry.getValue().toString());
                        }

                    }
                }
            }
            RequestBody body = formBody.build();
            request = request.newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "*/*")
                    .url(mHttpUrl.url())
                    .post(body)
                    .build();
        }
        return request;
    }

    private Map<String, Object> handleParams(Request request) {
        boolean isLogin = UserProfile.getInstance().isAppLogin();
        Log.e("=======>",bodyToString(request));
        Map<String, String> oldParams = JSONUtils.parseKeyAndValueToMap(bodyToString(request));
        assert oldParams != null;
        if (isLogin) {
            oldParams.put("token", UserProfile.getInstance().getAppToken());
        }
        TreeMap<String, Object> newParams = new TreeMap<>();
        newParams.put("sDeviceType", "Android");
        newParams.putAll(oldParams);
        StringBuilder sing = new StringBuilder();
        for (Map.Entry<String, Object> m : newParams.entrySet()) {
            sing.append(m.getKey()).append("=").append(m.getValue().toString()).append("&");
        }
        sing.append("secretkey=405e73b3d");
        Log.e("======>明文结果", sing.toString());
        String singResult = MD5Utils.string2MD5(sing.toString()).toUpperCase();
        Log.e("======>加密结果", singResult);
        newParams.put("sign", singResult);
        if (isLogin) {
            if (oldParams.containsKey("lUserId")) {
                oldParams.put("lUserId", UserProfile.getInstance().getAppAccount() + "");
            }
        }

        return newParams;
    }
}
