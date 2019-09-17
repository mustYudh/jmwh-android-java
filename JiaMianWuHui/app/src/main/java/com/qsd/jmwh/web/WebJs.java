package com.qsd.jmwh.web;

import android.app.Activity;
import android.webkit.WebView;

import com.yu.common.web.BaseWebJs;

/**
 * @author chenwei
 * @date 2017/8/30
 */
public class WebJs extends BaseWebJs {
    private WebView webView;

    public WebJs(Activity activity, WebView webView) {
        super(activity, webView);
        this.webView = webView;
    }

}
