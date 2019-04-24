package com.qsd.jmwh.module.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.login.LoginActivity;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.RegisterActivity;
import com.qsd.jmwh.module.splash.presenter.SplashPresenter;
import com.qsd.jmwh.module.splash.presenter.SplashViewer;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.share.AuthLoginHelp;
import com.yu.share.callback.AuthLoginCallback;

import java.util.Map;

public class SplashActivity extends BaseActivity
        implements View.OnClickListener, AuthLoginCallback, SplashViewer {

    @PresenterLifeCycle
    private SplashPresenter mPresenter =new SplashPresenter(this);

    private static int REQUEST_CODE = 0x123;
    private AuthLoginHelp mAuthLoginHelp;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.splash_activity_layout);
        if (UserProfile.getInstance().isAppLogin()) {
            getLaunchHelper().startActivityForResult(HomeActivity.class, REQUEST_CODE);
            finish();
        }
    }

    @Override
    protected void loadData() {
        bindView(R.id.login, this);
        bindView(R.id.register, this);
        bindView(R.id.wechat_login, this);
        bindView(R.id.qq_login, this);
        mAuthLoginHelp = new AuthLoginHelp(getActivity());
        mAuthLoginHelp.callback(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                getLaunchHelper().startActivityForResult(LoginActivity.class, REQUEST_CODE);
                break;
            case R.id.register:
                getLaunchHelper().startActivityForResult(RegisterActivity.class, REQUEST_CODE);
                break;
            case R.id.qq_login:
                boolean installQQ = UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.QQ);
                if (installQQ) {
                    mAuthLoginHelp.login(SHARE_MEDIA.QQ);
                } else {
                    ToastUtils.show("请先安装QQ");
                }
                break;
            case R.id.wechat_login:
                boolean installWeChat = UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.QQ);
                if (installWeChat) {
                    mAuthLoginHelp.login(SHARE_MEDIA.WEIXIN);
                } else {
                    ToastUtils.show("请先安装微信");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onStart(SHARE_MEDIA media) {
        Log.e("======onStart", "吊起SDK");
    }

    @Override
    public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {
        String uuid = map.get("uid");
        String type  = "";
        if (media == SHARE_MEDIA.QQ) {
            type = "QQ";
        } else if (media == SHARE_MEDIA.WEIXIN) {
            type = "wx";
        }
        mPresenter.auth(uuid,type);
    }

    @Override
    public void onError(SHARE_MEDIA media, int i, Throwable throwable) {
        Log.e("======onError", "onError");
    }

    @Override
    public void onCancel(SHARE_MEDIA media, int i) {
        Log.e("======onCancel", "onCancel");
    }

    @Override
    public void authLoginSuccess(LoginInfo loginInfo) {
        if (loginInfo != null) {
            UserProfile.getInstance().appLogin(loginInfo);
            getLaunchHelper().startActivity(HomeActivity.class);
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
