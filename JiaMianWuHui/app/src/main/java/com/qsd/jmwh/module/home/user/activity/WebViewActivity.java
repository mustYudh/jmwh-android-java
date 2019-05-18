package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class WebViewActivity extends BaseBarActivity {
    @Override

    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.web_view_layout);
    }

    @Override
    protected void loadData() {
        setTitle("用户协议");
        WebView webView = bindView(R.id.web_view);
        webView.loadUrl("http://api.jmwhapp.com/gateway/protocol.html");
    }
}
