package com.yu.common.web;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.yu.common.utils.NetWorkUtil;

/**
 * @author changwei
 * @date 2017/8/22
 */
public class WebSettingUtils {
  public static void setWebSetting(Context context, WebView web) {
    if (web == null || context == null) {
      return;
    }
    WebSettings set = web.getSettings();
    set.setAppCacheEnabled(true);
    set.setAppCachePath(context.getCacheDir().getAbsolutePath());
    set.setDomStorageEnabled(true);
    set.setJavaScriptEnabled(true);
    set.setJavaScriptCanOpenWindowsAutomatically(true);
    set.setRenderPriority(WebSettings.RenderPriority.HIGH);
    set.setLoadWithOverviewMode(true);
    set.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    set.setUseWideViewPort(true);
    set.setAppCacheMaxSize(1024 * 1024 * 8);
    set.setGeolocationEnabled(true);
    set.setAllowFileAccess(true);
    set.setBlockNetworkImage(false);
    set.setSupportZoom(true);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      set.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
    if (NetWorkUtil.isNetworkAvailable(context)) {
      set.setCacheMode(WebSettings.LOAD_DEFAULT);
    } else {
      set.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }
  }
}
