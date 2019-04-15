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
    PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
    PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
    return SHARE_AUTH_SDK;
  }


  public Context getContent() {
    return CONTEXT;
  }


}
