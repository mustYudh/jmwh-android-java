package com.qsd.jmwh;

import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.UIKitOptions;
import com.netease.nim.uikit.business.contact.core.query.PinYin;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.http.interceptor.CustomDynamicInterceptor;
import com.qsd.jmwh.http.interceptor.CustomExpiredInterceptor;
import com.qsd.jmwh.http.interceptor.CustomLoggingInterceptor;
import com.qsd.jmwh.module.im.ChatRoomSessionHelper;
import com.qsd.jmwh.module.im.NIMInitManager;
import com.qsd.jmwh.module.im.NimSDKOptionConfig;
import com.qsd.jmwh.module.im.SessionHelper;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xhttp2.model.HttpHeaders;
import com.yu.common.CommonInit;
import com.yu.common.base.BaseApp;
import com.yu.share.ShareAuthSDK;

public class APP extends BaseApp {
  //使用static代码段可以防止内存泄漏
  static {
    SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
      layout.setReboundDuration(1000);
      layout.setHeaderHeight(50);
      layout.setDisableContentWhenLoading(true);
      layout.setDisableContentWhenRefresh(true);
    });
    SmartRefreshLayout.setDefaultRefreshHeaderCreator(
        (context, layout) -> new MaterialHeader(context).setColorSchemeResources(
            R.color.app_main_bg_color, R.color.lb_cm_gray_font, R.color.color_EEEEEE));
    SmartRefreshLayout.setDefaultRefreshFooterCreator(
        (context, layout) -> new ClassicsFooter(context));
  }

  public static final int NET_TYPE = BuildConfig.API_MODE;
  public static final boolean DEBUG = APP.NET_TYPE == 0;
  private static APP instance;

  @Override public void onCreate() {
    APP.instance = this;
    super.onCreate();
    CommonInit.init(this);
    ShareAuthSDK.init(this, DEBUG);
    initHttp();
    initIm();
    initTuiSong();
  }

  private void initIm() {
    NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));
    // 以下逻辑只在主进程初始化时执行
    if (NIMUtil.isMainProcess(this)) {

      PinYin.init(this);
      PinYin.validate();
      // 初始化UIKit模块
      initUIKit();
      NIMInitManager.getInstance().init(true);
    }
  }

  private void initUIKit() {
    // 初始化
    NimUIKit.init(this, buildUIKitOptions());
    // IM 会话窗口的定制初始化。
    SessionHelper.init();
    // 聊天室聊天窗口的定制初始化。
    ChatRoomSessionHelper.init();
  }

  private UIKitOptions buildUIKitOptions() {
    UIKitOptions options = new UIKitOptions();
    // 设置app图片/音频/日志等缓存目录
    options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
    return options;
  }

  private LoginInfo getLoginInfo() {
    String account = UserProfile.getInstance().getSimUserId();
    String token = UserProfile.getInstance().getSimToken();
    if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
      NimUIKit.setAccount(account);
      return new LoginInfo(account, token);
    } else {
      return null;
    }
  }

  private void initHttp() {
    XHttpSDK.init(this);
    if (DEBUG) {
      XHttpSDK.debug();
      XHttpSDK.debug(new CustomLoggingInterceptor());
    }
    XHttpSDK.setBaseUrl(getBaseUrl());
    XHttpSDK.setSubUrl(getSubUrl());
    XHttpSDK.addInterceptor(new CustomDynamicInterceptor());
    XHttpSDK.addInterceptor(new CustomExpiredInterceptor());
    XHttp.getInstance().setTimeout(60000);
    XHttp.getInstance().setRetryCount(3);
    XHttp.getInstance().addCommonHeaders(getHttpHeaders());
  }

  private HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.put("Content-Type", "application/x-www-form-urlencoded");
    headers.put("Accept", "*/*");
    return headers;
  }

  private String getBaseUrl() {
    if (APP.NET_TYPE == 1) {
      return "http://api.jmwhapp.com";
    } else if (APP.NET_TYPE == 2) {
      return "http://api.jmwhapp.com";
    } else {
      return "http://api.jmwhapp.com";
    }
  }



  private void initTuiSong() {
    JPushInterface.setDebugMode(APP.DEBUG);
    JPushInterface.init(this);
  }

  public String getSubUrl() {
    return "/gateway/rest/v3";
  }

  public synchronized static APP getInstance() {
    return instance;
  }
}
