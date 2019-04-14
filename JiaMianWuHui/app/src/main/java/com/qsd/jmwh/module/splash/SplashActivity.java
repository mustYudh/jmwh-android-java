package com.qsd.jmwh.module.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.login.LoginActivity;
import com.qsd.jmwh.module.register.RegisterActivity;
import com.yu.common.base.BaseActivity;

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private static int REQUEST_CODE = 0x123;
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.splash_activity_layout);
            if (UserProfile.getInstance().isAppLogin()) {
                getLaunchHelper().startActivityForResult(HomeActivity.class,REQUEST_CODE);
            }
    }

    @Override
    protected void loadData() {
        bindView(R.id.login,this);
        bindView(R.id.register,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                getLaunchHelper().startActivity(LoginActivity.class);
                break;
            case R.id.register:
                getLaunchHelper().startActivity(RegisterActivity.class);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && requestCode == Activity.RESULT_OK) {
            finish();
        }
    }
}
