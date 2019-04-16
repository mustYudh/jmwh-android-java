package com.qsd.jmwh;

import com.qsd.jmwh.http.interceptor.CustomDynamicInterceptor;
import com.qsd.jmwh.http.interceptor.CustomLoggingInterceptor;
import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xhttp2.model.HttpHeaders;
import com.yu.common.CommonInit;
import com.yu.common.base.BaseApp;
import com.yu.share.ShareAuthSDK;

public class APP extends BaseApp {
  public static final int NET_TYPE = BuildConfig.API_MODE;
  public static final boolean DEBUG = APP.NET_TYPE == 0;
  private static APP instance;

  @Override public void onCreate() {
    APP.instance = this;
    super.onCreate();
    CommonInit.init(this);
    ShareAuthSDK.init(this, DEBUG);
    initHttp();
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
    //XHttpSDK.addInterceptor(new CustomExpiredInterceptor());
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
    if (DEBUG) {
      return "http://api.jmwhapp.com";
    } else {
      return "http://39.96.169.148";
    }
  }

  public String getSubUrl() {
    return "/gateway/rest/v3";
  }

  public synchronized static APP getInstance() {
    return instance;
  }
}
