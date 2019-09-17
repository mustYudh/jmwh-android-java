package com.yu.common.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;
import com.yu.common.launche.LauncherHelper;

/**
 * 默认调用系统的下载
 */
public class WebViewDownLoadListener implements DownloadListener {

  private Context context;

  public WebViewDownLoadListener(Context context) {
    this.context = context;
  }

  @Override
  public void onDownloadStart(String url, String userAgent, String contentDisposition, String type,
      long contentLength) {
    LauncherHelper.from(context).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
  }
}