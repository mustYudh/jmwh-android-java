package com.qsd.jmwh.http;

import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.qsd.jmwh.module.home.radio.bean.HomeRadioListBean;
import com.qsd.jmwh.module.home.user.bean.UserCenterMyInfo;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.bean.DateProjectBean;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.qsd.jmwh.module.register.bean.UserAuthCodeBean;
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

    @NetMethod(ParameterNames = {"sAuthCode", "sLoginName", "sLoginMode", "sPwd"}, Url = "/UserService/registUser")
    Observable<UserInfo> register(String sAuthCode, String sLoginName, String sLoginMode, String sPwd);

    @NetMethod(ParameterNames = {"nSex", "lUserId"}, Url = "/UserService/modifySex")
    Observable<Object> selectGender(int nSex, int lUserId);

    @POST("/gateway/rest/v3/UserService/login")
    Observable<ApiResult<LoginInfo>> login(@Body RequestBody requestBody);

    @NetMethod(ParameterNames = {"nLevel", "lParentId", "lUserId", "token"}, Url = "/TwosomeService/getTwosomeList")
    Observable<RangeData> getDateRange(int nLevel, int lParentId, int lUserId, String token);

    @NetMethod(Url = "/SystemService/getUserConfigInfo")
    Observable<DateProjectBean> getDateProject();

    @NetMethod(ParameterNames = {"nLat", "nLng", "sDatingRange", "nTab", "pageindex", "nSex"}, Url = "/DatingService/getDatingList")
    Observable<HomeRadioListBean> getRadioDate(String nLat, String nLng, String sDatingRange, String nTab, String pageindex, String nSex);

    @NetMethod(ParameterNames = {"lUserId", "token"}, Url = "/GoodsService/getVIPPayInfo")
    Observable<VipInfoBean> getVipInfoList(int lUserId, String token);

    @NetMethod(ParameterNames = {"nLat", "nLng", "nTab", "sNickName", "pageindex", "nSex"}, Url = "/UserService/getUserList")
    Observable<HomePersonListBean> getPersonListDate(String nLat, String nLng, String nTab, String sNickName, String pageindex, String nSex);

    @NetMethod(ParameterNames = {"lUserId", "token", "sUserHeadPic"}, Url = "/UserService/modifyHeadPic")
    Observable<Object> uploadHeader(String lUserId, String token, String sUserHeadPic);

    @NetMethod(ParameterNames = {"nType"}, Url = "/DatingService/getDatingConfigList")
    Observable<GetRadioConfigListBean> getConfigList(String nType);

    @NetMethod(ParameterNames = {"sNickName", "sDateRange", "sAge", "sJob", "sDatePro", "sHeight",
            "sWeight", "lUserId", "sIntroduce", "token", "sUserHeadPic", "sBust", "QQ", "WX", "bHiddenQQandWX"}, Url = "/UserService/modifyUserInfo")
    Observable<Object> modifyUserInfo(String sNickName, String sDateRange, String sAge, String sJob,
                                      String sDatePro, String sHeight, String sWeight, String lUserId,
                                      String sIntroduce, String token, String sUserHeadPic, String sBust, String QQ, String WX, Boolean bHiddenQQandWX);

    @NetMethod(ParameterNames = {"sNickName", "sDateRange", "sAge", "sJob", "sDatePro", "sHeight",
            "sWeight", "lUserId", "sIntroduce", "token", "sUserHeadPic", "sBust", "QQ", "WX", "bHiddenQQandWX"}, Url = "/UserService/modifyUser")
    Observable<Object> modifyUserCenter(String sNickName, String sDateRange, String sAge, String sJob,
                                      String sDatePro, String sHeight, String sWeight, String lUserId,
                                      String sIntroduce, String token, String sUserHeadPic, String sBust, String QQ, String WX, Boolean bHiddenQQandWX);

    @NetMethod(ParameterNames = {"lUserId", "token"}, Url = "/UserService/getUserRegistAuthCode")
    Observable<UserAuthCodeBean> getCod(int lUserId, String token);

    @NetMethod(ParameterNames = {"lUserId", "token", "sAuthCode"}, Url = "/UserService/userAuthByCode")
    Observable<Object> getUserAuthByCode(int lUserId, String token, String code);

    @NetMethod(Url = "/UserService/getMyUserCenterInfo")
    Observable<UserCenterMyInfo> getUserCenterInfo();

    @POST("/gateway/rest/v3/UserService/loginByWxAndQQ")
    Observable<ApiResult<LoginInfo>> authLogin(@Body RequestBody requestBody);

    @NetMethod(ParameterNames = {"sLoginName", "sPwd", "sAuthCode"}, Url = "/v3/UserService/modifyPwd")
    Observable<Object> bindPhone(String sLoginName, String sPwd, String sAuthCode);


    @NetMethod(ParameterNames = {"sLoginName", "sPwd", "sAuthCode"}, Url = "/UserService/modifyPwd")
    Observable<Object> modifyPassword(String sLoginName, String sPwd, String sAuthCode);


    @NetMethod(ParameterNames = {"token", "sSource", "sReferrer", "WX", "sCity"}, Url = "/UserService/addUserReq")
    Observable<Object> getRegisterCode(String token, String sSource, String sReferrer, String WX, String sCity);


    @NetMethod(ParameterNames = {"sFileUrl","nAttribute","nInfoType","nFileType","nFileFee"},Url = "/MaskballService/addFile")
    Observable<Object> addFile(String sFileUrl,int nAttribute,int nInfoType,int nFileType,int nFileFee);

    @NetMethod(ParameterNames = {"lFileId","nInfoType"},Url = "/MaskballService/delFile")
    Observable<Object> deleteFile(int lFileId,int nInfoType);
}
