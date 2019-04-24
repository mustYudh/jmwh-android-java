package com.yu.share;

import android.app.Application;
import android.content.Context;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

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
    PlatformConfig.setWeixin("wx860254f6f0a55fc6", "228c301ab1cc5f57b3e2ca8a5fa4f926");
    PlatformConfig.setQQZone("101567701", "78a5ff5f467d76670540a65f719af131");
    PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
    return SHARE_AUTH_SDK;
  }


  public Context getContent() {
    return CONTEXT;
  }


}
