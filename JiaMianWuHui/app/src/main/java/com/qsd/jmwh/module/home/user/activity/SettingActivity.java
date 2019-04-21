package com.qsd.jmwh.module.home.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.view.UserItemView;

public class SettingActivity extends BaseBarActivity {

    private UserItemView phoneNumber;
    private UserItemView editPassword;
    private UserItemView push;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.setting_layout);
        initView();
    }

    private void initView() {
        phoneNumber = bindView(R.id.phone_number);
        editPassword = bindView(R.id.edit_password);
        push = bindView(R.id.push);
        initListener();
    }

    private void initListener() {
        push.setOnClickListener(v -> getLaunchHelper().startActivity(PushSettingActivity.class));
        phoneNumber.setOnClickListener(v -> getLaunchHelper().startActivity(SettingPhoneNumberActivity.class));
        editPassword.setOnClickListener(v -> getLaunchHelper().startActivity(EditPasswordActivity.class));
    }

    @Override
    protected void loadData() {
        setTitle("设置");
    }
}
