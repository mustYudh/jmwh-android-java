package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class SettingPhoneNumberActivity extends BaseBarActivity {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.setting_phone_number_layout);
    }

    @Override
    protected void loadData() {
        setTitle("绑定手机号");
    }
}
