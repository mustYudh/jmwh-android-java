package com.qsd.jmwh.http.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.utils.JSONUtils;
import com.xuexiang.xhttp2.interceptor.BaseDynamicInterceptor;
import com.xuexiang.xhttp2.logs.HttpLog;
import com.xuexiang.xhttp2.utils.HttpUtils;
import com.xuexiang.xhttp2.utils.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        Request request = addPostParamsSign(chain.request());
        // TODO: 2019/3/22 判断登录添加TOKEN
        StringBuilder sing = new StringBuilder();
        if (!TextUtils.isEmpty(bodyToString(request))) {
            TreeMap<String, String> params = JSONUtils.parseKeyAndValueToMap(bodyToString(request));
            assert params != null;
            params.put("sDeviceType", "Android");
//            params.put("secretkey", "405e73b3d");
            if (UserProfile.getInstance().isLogin()) {
                params.put("token", UserProfile.getInstance().getToken());
                params.put("IUserId", UserProfile.getInstance().getAccount());
            }
        /*    int i = 0;
            for (Map.Entry<String, String> m : params.entrySet()) {
                if (params.entrySet().size() > 0) {
                    i++;
                    sing.append(m.getKey()).append("=").append(m.getValue()).append(i < params.size() ? "&" : "");
                }
            }
            if (!TextUtils.isEmpty(sing.toString())) {
                MD5Utils.string2MD5(sing.toString()).toUpperCase();
            }*/
            params.put("sign", "845A8E8A3732FF41E0F8E78759826D74");
        }
        Log.e("===>需要签名", bodyToString(request) + "===code===");
        return chain.proceed(request);
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

    private Request addPostParamsSign(Request request) throws UnsupportedEncodingException {
        HttpUrl mHttpUrl = HttpUrl.parse(HttpUtils.parseUrl(request.url().toString()));
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //原有的参数
            TreeMap<String, Object> oldParams = new TreeMap<>();
            for (int i = 0; i < formBody.size(); i++) {
                oldParams.put(formBody.encodedName(i), formBody.encodedValue(i));
            }
            //拼装新的参数
            TreeMap<String, Object> newParams = updateDynamicParams(oldParams);
            Utils.checkNotNull(newParams, "newParams == null");
            String sDeviceType = URLDecoder.decode("Android", HttpUtils.UTF8.name());
            bodyBuilder.addEncoded("sDeviceType",sDeviceType);
            for (Map.Entry<String, Object> entry : newParams.entrySet()) {
                String value = URLDecoder.decode(String.valueOf(entry.getValue()), HttpUtils.UTF8.name());
                bodyBuilder.addEncoded(entry.getKey(), value);
            }
            String url = HttpUtils.createUrlFromParams(HttpUtils.parseUrl(mHttpUrl.url().toString()), newParams);
            HttpLog.i(url);
            formBody = bodyBuilder.build();
            request = request.newBuilder().post(formBody).build();
        } else if (request.body() instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) request.body();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            List<MultipartBody.Part> oldParts = multipartBody.parts();

            //拼装新的参数
            List<MultipartBody.Part> newParts = new ArrayList<>();
            newParts.addAll(oldParts);
            TreeMap<String, Object> oldParams = new TreeMap<>();
            TreeMap<String, Object> newParams = updateDynamicParams(oldParams);
            for (Map.Entry<String, Object> paramEntry : newParams.entrySet()) {
                MultipartBody.Part part = MultipartBody.Part.createFormData(paramEntry.getKey(), String.valueOf(paramEntry.getValue()));
                newParts.add(part);
            }
            for (MultipartBody.Part part : newParts) {
                bodyBuilder.addPart(part);
            }
            multipartBody = bodyBuilder.build();
            request = request.newBuilder().post(multipartBody).build();
        } else if (request.body() instanceof RequestBody) {
            TreeMap<String, Object> params = updateDynamicParams(new TreeMap<String, Object>());
            String url = HttpUtils.createUrlFromParams(HttpUtils.parseUrl(mHttpUrl.url().toString()), params);
            request = request.newBuilder().url(url).build();
        }
        return request;
    }




}
