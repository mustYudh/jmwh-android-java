package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class PushSettingActivity extends BaseBarActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.push_setting_layout);
    }

    @Override
    protected void loadData() {
        setTitle("消息推送设置");
    }
}
