package com.yu.share_auth;

import android.app.Application;
import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author yudneghao
 * @date 2019/4/15
 */
public class ShareAuthSDK {
  private final static ShareAuthSDK SHARE_AUTH_SDK = new ShareAuthSDK();

  private static Context CONTEXT = null;

  private ShareAuthSDK() {
  }

  public static ShareAuthSDK init(Application application, boolean debug) {
    CONTEXT = application;
    UMConfigure.init(application, UMConfigure.DEVICE_TYPE_PHONE, "");
    MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);
    MobclickAgent.setCatchUncaughtExceptions(true);
    UMConfigure.setLogEnabled(debug);
    return SHARE_AUTH_SDK;
  }


  public Context getContent() {
    return CONTEXT;
  }


}
