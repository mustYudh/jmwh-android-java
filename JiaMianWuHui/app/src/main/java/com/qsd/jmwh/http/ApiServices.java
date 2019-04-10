package com.qsd.jmwh.http;

import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.xuexiang.xhttp2.annotation.NetMethod;

import io.reactivex.Observable;

public interface ApiServices {
//        ParameterNames = {"sMobile"}
        @NetMethod( Url = "/gateway/rest/v3/SystemService/verifySignature", AccessToken = false)
        Observable<SendVerCodeBean> send();
}
