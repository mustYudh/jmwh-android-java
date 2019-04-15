package com.qsd.jmwh.http.params;

import com.qsd.jmwh.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PostParams {
    private final static PostParams POST_PARAMS = new PostParams();

    private static Map<String, String> params = new HashMap<>();

    private PostParams() { }

    public static PostParams createParams() {
        params.clear();
        return POST_PARAMS;
    }

    public PostParams put(String key, String value) {
        params.put(key, value);
        return this;
    }

    public RequestBody creatBody() {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONUtils.parseMapToJson(params));
        return body;
    }

}
