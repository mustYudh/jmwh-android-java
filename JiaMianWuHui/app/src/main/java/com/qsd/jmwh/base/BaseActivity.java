package com.qsd.jmwh.base;

import android.view.View;
import com.umeng.analytics.MobclickAgent;
import com.yu.common.framework.BasicActivity;

public abstract class BaseActivity extends BasicActivity {

    @Override
    protected void handleNetWorkError(View view) {
    }

    @Override protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
