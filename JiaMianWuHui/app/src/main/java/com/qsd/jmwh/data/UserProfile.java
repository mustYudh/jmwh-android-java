package com.qsd.jmwh.data;

import android.content.Context;

import com.qsd.jmwh.APP;

import java.io.Serializable;

public class UserProfile implements Serializable {

  private static UserProfile instance;

  private static final String SHARE_PREFERENCES_NAME = ".public_profile";

  private static final String ACCOUNT = "account";
  private static final String TOKEN = "account";


  private SharedPreferencesHelper spHelper;

  private String account = "";
  private String token = "";

  private UserProfile() {
    spHelper = SharedPreferencesHelper.create(
        APP.getInstance().getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE));
    account = spHelper.getString(ACCOUNT, "");
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

  // TODO: 2019/3/22 数据
  public String getAccount() {
    return account;
  }


  // TODO: 2019/3/22 数据
  public String getToken() {
    return token;
  }


  // TODO: 2019/3/22 数据
  public boolean isLogin() {
    return false;
  }


  /**
   * 退出登录
   */
  // TODO: 2019/3/22 数据 清除ID之类
  public void clean() {
    spHelper.clear();
  }



}
