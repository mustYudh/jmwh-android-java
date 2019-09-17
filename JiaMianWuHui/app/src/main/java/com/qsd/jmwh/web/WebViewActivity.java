package com.qsd.jmwh.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.yu.common.ui.ProgressWebViewLayout;
import com.yu.common.web.BaseWebViewClient;
import com.yu.common.web.ProgressWebChromeClient;
import com.yu.common.web.WebViewDownLoadListener;

/**
 * @author yudneghao
 */
public class WebViewActivity extends BaseBarActivity {
    public static final String WEB_TITLE = "webTitle";
    public static final String WEB_URL = "webUrl";
    private WebView webView;
    private WebJs webJs;


    @Override
    protected int getActionBarLayoutId() {
        return R.layout.action_bar_white_web_view;
    }

    /**
     * @param url url
     */
    public static Intent callIntent(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_TITLE, title);
        intent.putExtra(WEB_URL, url);
        return intent;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_common_webview);
        setTitle(getIntent().getStringExtra(WEB_TITLE));
        ProgressWebViewLayout webViewLayout = bindView(R.id.webViewLayout);

        webView = webViewLayout.getWebView();
        webView.setDownloadListener(new WebViewDownLoadListener(getActivity()));
        webView.setWebChromeClient(new ProgressWebChromeClient(webViewLayout.getProgressBar()) {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(getIntent().getStringExtra(WEB_TITLE))) {
                    if (!TextUtils.isEmpty(view.getTitle())) {
                        setTitle(view.getTitle());
                    }
                }
            }
        });
        webView.setWebViewClient(new BaseWebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                judgeCloseBtn();
            }
        });
        initLeftClose();
        initJs();
    }


    @SuppressLint("JavascriptInterface")
    private void initJs() {
        webJs = new WebJs(this, webView);
        webView.addJavascriptInterface(webJs, "android");
    }

    private void initLeftClose() {
        bindView(R.id.action_close).setOnClickListener(v -> onBackPressed());
        bindView(R.id.action_back).setOnClickListener(v -> {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                judgeCloseBtn();
            } else {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webView.reload();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void judgeCloseBtn() {
        bindView(R.id.action_close, webView != null && webView.canGoBack());
    }

    @Override
    protected void loadData() {
        webView.loadUrl(getIntent().getStringExtra(WEB_URL));
    }


    @Override
    protected void onDestroy() {
        if (webJs != null) {
            webJs.destroy();
        }
        if (webView != null) {
            webView.removeJavascriptInterface("android");
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }
}
