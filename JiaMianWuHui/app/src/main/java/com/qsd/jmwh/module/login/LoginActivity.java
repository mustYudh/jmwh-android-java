package com.qsd.jmwh.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;

public class LoginActivity extends BaseBarActivity implements View.OnClickListener {
    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.login_activity_layout);
    }

    @Override
    protected void loadData() {
        setTitle("登录");
        bindView(R.id.login, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
//                getLaunchHe   lper().startActivity(HomeActivity.class);

                break;
        }
    }
}
