package com.qsd.jmwh.http;

import com.qsd.jmwh.module.register.bean.UserInfo;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.xuexiang.xhttp2.annotation.NetMethod;

import io.reactivex.Observable;

public interface ApiServices {
        @NetMethod(ParameterNames = {"sMobile"}, Url = "/SystemService/sendSMS")
        Observable<SendVerCodeBean> send(String sMobile);

        @NetMethod(ParameterNames =  {"sAuthCode","sLoginName","sLoginMode","sPwd"}, Url = "/UserService/registUser")
        Observable<UserInfo> register(String sAuthCode, String sLoginName, String sLoginMode, String sPwd);


        @NetMethod(ParameterNames = {"nSex","lUserId"},Url = "/UserService/modifySex")
        Observable<Object> selectGender(int nSex,int lUserId);

}
