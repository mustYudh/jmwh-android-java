package com.qsd.jmwh.http;

import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.xuexiang.xhttp2.annotation.NetMethod;
import io.reactivex.Observable;

public interface ApiServices {
//        ParameterNames = {"sMobile"}
        @NetMethod(ParameterNames = {"sMobile"}, Url = "/SystemService/sendSMS")
        Observable<SendVerCodeBean> send(String sMobile);
}
