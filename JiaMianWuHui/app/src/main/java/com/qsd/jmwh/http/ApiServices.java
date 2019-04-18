package com.qsd.jmwh.http;

import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.bean.DateProjectBean;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.qsd.jmwh.module.register.bean.UserInfo;
import com.qsd.jmwh.module.register.bean.VipInfoBean;
import com.xuexiang.xhttp2.annotation.NetMethod;
import com.xuexiang.xhttp2.model.ApiResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {
        @NetMethod(ParameterNames = {"sMobile"}, Url = "/SystemService/sendSMS")
        Observable<SendVerCodeBean> send(String sMobile);

        @NetMethod(ParameterNames =  {"sAuthCode","sLoginName","sLoginMode","sPwd"}, Url = "/UserService/registUser")
        Observable<UserInfo> register(String sAuthCode, String sLoginName, String sLoginMode, String sPwd);

        @NetMethod(ParameterNames = {"nSex","lUserId"},Url = "/UserService/modifySex")
        Observable<Object> selectGender(int nSex,int lUserId);

        @POST("/gateway/rest/v3/UserService/login")
        Observable<ApiResult<LoginInfo>> login(@Body RequestBody requestBody);

        @NetMethod(ParameterNames = {"nLevel","lParentId","lUserId","token"},Url = "/TwosomeService/getTwosomeList")
        Observable<RangeData> getDateRange(int nLevel,int lParentId,int lUserId,String token);

        @NetMethod(Url = "/SystemService/getUserConfigInfo")
        Observable<DateProjectBean> getDateProject();

        @NetMethod(ParameterNames = {"nLat","nLng","sDatingRange","nTab","pageindex","nSex"},Url = "/DatingService/getDatingList")
        Observable<HomeRadioListBean> getRadioDate(String nLat, String nLng,String sDatingRange, String nTab, String pageindex,String nSex);

        @NetMethod(ParameterNames = {"lUserId","token"},Url = "/GoodsService/getVIPPayInfo")
        Observable<VipInfoBean> getVipInfoList(int lUserId,String token);
}
