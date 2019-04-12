package com.qsd.jmwh.module.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.qsd.jmwh.R;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.register.RegisterActivity;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.base.BaseActivity;
import com.yu.common.toast.ToastUtils;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
            setContentView(R.layout.splash_activity_layout);
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
                XHttpProxy.proxy(ApiServices.class)
                    .send("1312412")
                    .subscribeWith(new NoTipRequestSubscriber<SendVerCodeBean>() {
                        @Override
                        protected void onSuccess(SendVerCodeBean sendVerCodeBeanApiResult) {
                            ToastUtils.show(SplashActivity.this,sendVerCodeBeanApiResult.server_timestamp + "");
                        }
                    });
                break;
            case R.id.register:
                getLaunchHelper().startActivity(RegisterActivity.class);
                break;
        }
    }
}
