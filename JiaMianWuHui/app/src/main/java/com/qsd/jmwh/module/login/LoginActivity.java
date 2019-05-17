package com.qsd.jmwh.module.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.home.user.activity.EditPasswordActivity;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.login.presenter.LoginPresenter;
import com.qsd.jmwh.module.login.presenter.LoginViewer;
import com.qsd.jmwh.view.NormaFormItemVIew;
import com.yu.common.mvp.PresenterLifeCycle;

public class LoginActivity extends BaseBarActivity implements LoginViewer, View.OnClickListener {
    @PresenterLifeCycle
    LoginPresenter mPresenter = new LoginPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.login_activity_layout);
    }


    @Override
    protected void loadData() {
        setTitle("登录");
        bindView(R.id.login, this);
        setRightMenu("忘记密码", v -> getLaunchHelper().startActivity(EditPasswordActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mPresenter.login(getTextResult(R.id.account), getTextResult(R.id.password));
                break;
            default:
        }
    }


    private String getTextResult(@IdRes int id) {
        NormaFormItemVIew editText = bindView(id);
        return editText.getText();
    }


    @Override
    public void handleLoginResult(LoginInfo loginInfo) {
        if (loginInfo != null) {
            UserProfile.getInstance().appLogin(loginInfo);
            getLaunchHelper().startActivity(HomeActivity.class);
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
