package com.yu.common.web;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import java.io.Serializable;

/**
 * @author chenwei
 * @date 2017/8/30
 */
public class BaseWebJs implements Serializable {

  private WebView webView;
  private Activity activity;

  private Handler mHandler = new Handler(Looper.getMainLooper());

  public BaseWebJs(Activity activity, WebView webView) {
    this.webView = webView;
    this.activity = activity;
  }

  public WebView getWebView() {
    return webView;
  }

  public void runOnUiThread(final Runnable runnable) {
    runOnUiThread(runnable, 0);
  }

  public void runOnUiThread(final Runnable runnable, int delay) {
    if (runnable != null && !isDestroy()) {
      mHandler.postDelayed(new Runnable() {
        @Override public void run() {
          if (!isDestroy()) {
            runnable.run();
          }
        }
      }, delay);
    }
  }

  public boolean isDestroy() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      return activity == null || activity.isFinishing() || activity.isDestroyed();
    } else {
      return activity == null || activity.isFinishing();
    }
  }

  public Activity getActivity() {
    return activity;
  }

  public void finish() {
    if (activity != null) {
      activity.finish();
    }
  }

  public void destroy() {
    mHandler.removeCallbacksAndMessages(null);
    activity = null;
  }
}
