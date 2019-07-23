package com.qsd.jmwh.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.qsd.jmwh.utils.ActivityManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.yu.common.framework.BasicActivity;

public abstract class BaseActivity extends BasicActivity {

    @Override
    protected void handleNetWorkError(View view) {
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
    }

    @Override protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
