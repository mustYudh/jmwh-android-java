package com.qsd.jmwh.data;

import android.content.Context;
import android.text.TextUtils;

import com.qsd.jmwh.APP;
import com.qsd.jmwh.module.login.bean.LoginInfo;

import java.io.Serializable;
import java.util.UUID;

public class UserProfile implements Serializable {

    private static UserProfile instance;

    private static final String SHARE_PREFERENCES_NAME = ".public_profile";

    private static final String APP_ACCOUNT = "account";
    private static final String APP_TOKEN = "token";
    private static final String PHONE_NO = "phone_no";
    private static final String SEX = "sex";


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
        setSex(userInfo.nSex);
    }

    private void setSex(int sex) {
        spHelper.putInt(SEX,sex);
    }

    public int getSex() {
        return spHelper.getInt(SEX,-1);
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

    public void setPhoneNo(String phoneNo) {
        if (!TextUtils.isEmpty(phoneNo)) {
            spHelper.putString(PHONE_NO,phoneNo);
        }

    }

    public String getPhoneNo() {
        return spHelper.getString(PHONE_NO,"");
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


    public String getObjectName() {
       return getAppAccount() + "/head_" + UUID.randomUUID().toString() + ".jpg";
    }

}
