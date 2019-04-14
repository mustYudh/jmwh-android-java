package com.qsd.jmwh.data;

import android.content.Context;
import android.text.TextUtils;

import com.qsd.jmwh.APP;
import com.qsd.jmwh.module.login.bean.LoginInfo;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private static UserProfile instance;

    private static final String SHARE_PREFERENCES_NAME = ".public_profile";

    private static final String APP_ACCOUNT = "account";
    private static final String APP_TOKEN = "token";


    private SharedPreferencesHelper spHelper;

    private int account;
    private String token = null;

    private UserProfile() {
        spHelper = SharedPreferencesHelper.create(
                APP.getInstance().getSharedPreferences(SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE));
        account = getAppAccount();
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
    }

    public int getAppAccount() {
        return spHelper.getInt(APP_ACCOUNT, -1);
    }

    private void setAppAccount(int account) {
        spHelper.putInt(APP_ACCOUNT, account);
    }


    public String getAppToken() {
        return spHelper.getString(APP_TOKEN,"");
    }

    private void setAppToken(String token) {
        spHelper.putString(APP_TOKEN, token);
    }


    public boolean isAppLogin() {
        return !TextUtils.isEmpty(getAppToken()) && getAppAccount() != -1;
    }


    /**
     * 退出登录
     */
    public void clean() {
        spHelper.clear();
    }


}
