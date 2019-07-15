package com.qsd.jmwh.data;

import android.content.Context;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import com.netease.nim.uikit.api.NimUIKit;
import com.qsd.jmwh.APP;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import java.io.Serializable;
import java.util.UUID;

public class UserProfile implements Serializable {

  private static UserProfile instance;

  private static final String SHARE_PREFERENCES_NAME = ".public_profile";

  private static final String APP_USER_ID = "account";
  private static final String APP_TOKEN = "token";
  private static final String PHONE_NO = "phone_no";
  private static final String SEX = "sex";
  private static final String LAT = "lat";
  private static final String LNG = "lng";
  private static final String CITY_NAME = "city_name";
  private static final String HOME_CITY_NAME = "home_city_name";
  private static final String HOME_SEX_TYPE = "home_sex_type";
  private static final String USER_PIC = "user_pic";
  private static final String USER_NAUTHTYPE = "user_nauthtype";

  private static final String SIM_USERID = "sim_userid";
  private static final String SIM_TOKEN = "sim_token";

  private SharedPreferencesHelper spHelper;

  private UserProfile() {
    spHelper = SharedPreferencesHelper.create(
        APP.getInstance().getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE));
  }

  public synchronized static UserProfile getInstance() {
    if (instance == null) {
      synchronized (UserProfile.class) {
        if (instance == null) {
          instance = new UserProfile();
        }
      }
    }
    return instance;
  }

  public void appLogin(LoginInfo userInfo) {
    setAppAccount(userInfo.lUserId);
    setAppToken(userInfo.token);
    setUserPic(userInfo.sUserHeadPic);
    setSimToken(userInfo.sIMToken);
    setSimUserId(userInfo.sIMID);
    setSex(userInfo.nSex);
    JPushInterface.setAlias(APP.getInstance().getApplicationContext(), 1, "jg_" + userInfo.lUserId);
  }

  public void setUserNauthtype(String user_nauthtype) {
    spHelper.putString(USER_NAUTHTYPE, user_nauthtype);
  }

  public String getUserNauthtype() {
    return spHelper.getString(USER_NAUTHTYPE, "0");
  }

  public void setHomeSexType(int homeSexType) {
    spHelper.putInt(HOME_SEX_TYPE, homeSexType);
  }

  public int getHomeSexType() {
    return spHelper.getInt(HOME_SEX_TYPE, 0);
  }

  public void setHomeCityName(String homeCityName) {
    spHelper.putString(HOME_CITY_NAME, homeCityName);
  }

  public String getHomeCityName() {
    return spHelper.getString(HOME_CITY_NAME, "");
  }

  public void setSex(int sex) {
    spHelper.putInt(SEX, sex);
  }

  public int getSex() {
    return spHelper.getInt(SEX, -1);
  }

  public int getUserId() {
    return spHelper.getInt(APP_USER_ID, -1);
  }

  private void setAppAccount(int account) {
    spHelper.putInt(APP_USER_ID, account);
  }

  private void setUserPic(String user_pic) {
    spHelper.putString(USER_PIC, user_pic);
  }

  public String getUserPic() {
    return spHelper.getString(USER_PIC, "");
  }

  public String getAppToken() {
    return spHelper.getString(APP_TOKEN, "");
  }

  private void setAppToken(String token) {
    spHelper.putString(APP_TOKEN, token);
  }

  private void setSimToken(String token) {
    spHelper.putString(SIM_TOKEN, token);
  }

  private void setSimUserId(String id) {
    spHelper.putString(SIM_USERID, id);
  }

  public String getSimUserId() {
    return spHelper.getString(SIM_USERID, "");
  }

  public String getSimToken() {
    return spHelper.getString(SIM_TOKEN, "");
  }

  public void setPhoneNo(String phoneNo) {
    if (!TextUtils.isEmpty(phoneNo)) {
      spHelper.putString(PHONE_NO, phoneNo);
    }
  }

  public String getPhoneNo() {
    return spHelper.getString(PHONE_NO, "");
  }

  public boolean isAppLogin() {
    return !TextUtils.isEmpty(getAppToken()) && getUserId() != -1;
  }

  public void setCityName(String cityName) {
    spHelper.putString(CITY_NAME, cityName);
  }

  public String getCityName() {
    return spHelper.getString(CITY_NAME, "");
  }

  public void setLat(float lat) {
    spHelper.putFloat(LAT, lat);
  }

  public float getLat() {
    return spHelper.getFloat(LAT, 0);
  }

  public void setLng(float lng) {
    spHelper.putFloat(LNG, lng);
  }

  public float getLng() {
    return spHelper.getFloat(LNG, 0);
  }

  /**
   * 退出登录
   */
  public void clean() {
    NimUIKit.setAccount("");
    NimUIKit.logout();
    spHelper.clear();
  }

  public String getObjectName() {
    return getUserId() + "/head_" + UUID.randomUUID().toString() + ".jpg";
  }

  public String getObjectName(String name) {
    return getUserId() + "/" + name + UUID.randomUUID().toString() + ".jpg";
  }

  public String getObjectName(String name, String type) {
    return getUserId() + "/" + name + "_" + UUID.randomUUID().toString() + "." + type;
  }
}
