package com.qsd.jmwh.http.interceptor;

import android.os.Handler;
import android.os.Looper;
import com.qsd.jmwh.utils.ActivityManager;
import com.qsd.jmwh.utils.JSONUtils;
import com.qsd.jmwh.utils.Result;
import com.xuexiang.xhttp2.interceptor.BaseExpiredInterceptor;
import com.xuexiang.xhttp2.model.ExpiredInfo;
import com.xuexiang.xhttp2.utils.HttpUtils;
import okhttp3.Response;

public class CustomExpiredInterceptor extends BaseExpiredInterceptor {

  @Override protected ExpiredInfo isResponseExpired(Response oldResponse, String bodyString) {
    int code = JSONUtils.getInt(bodyString, Result.CODE, 0);
    ExpiredInfo expiredInfo = new ExpiredInfo(code);
    switch (code) {
      case 106:
        expiredInfo.setExpiredType(106);
        expiredInfo.setBodyString(bodyString);
        break;
      case 107:
        expiredInfo.setExpiredType(106);
        expiredInfo.setBodyString(bodyString);
      default:
    }
    return expiredInfo;
  }

  @Override
  protected Response responseExpired(Response oldResponse, Chain chain, ExpiredInfo expiredInfo) {
    Response response = null;
    switch (expiredInfo.getExpiredType()) {
      case 106:
        response = HttpUtils.getErrorResponse(oldResponse, expiredInfo.getCode(), "登录已经失效，请重新登录！");
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
          @Override
          public void run() {
            ActivityManager.getInstance().reLogin();
          }
        });
        break;

      default:
    }
    return response;
  }
}
