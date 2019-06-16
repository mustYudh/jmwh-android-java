package com.qsd.jmwh.module.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
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
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends BaseBarActivity implements LoginViewer, View.OnClickListener {
  @PresenterLifeCycle LoginPresenter mPresenter = new LoginPresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.login_activity_layout);
  }

  @Override protected void loadData() {
    setTitle("登录");
    bindView(R.id.login, this);
    setRightMenu("忘记密码", v -> getLaunchHelper().startActivity(EditPasswordActivity.class));
    EventBus.getDefault().register(this);
  }

  @Override public void onClick(View v) {
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

  @Override public void handleLoginResult(LoginInfo loginInfo) {
    if (loginInfo != null) {
      //NIMClient.getService(AuthService.class).login(LoginInfo(loginInfo.sIMID, simid)).setCallback(new )
      NIMClient.getService(AuthService.class)
          .login(new com.netease.nimlib.sdk.auth.LoginInfo(loginInfo.sIMID, loginInfo.sIMToken))
          .setCallback(new RequestCallback<com.netease.nimlib.sdk.auth.LoginInfo>() {

            @Override public void onSuccess(com.netease.nimlib.sdk.auth.LoginInfo info) {
              UserProfile.getInstance().appLogin(loginInfo);
              NimUIKit.loginSuccess(loginInfo.sIMID);
              NIMClient.toggleNotification(true);
              getLaunchHelper().startActivity(HomeActivity.class);
              setResult(Activity.RESULT_OK);
              finish();
            }

            @Override public void onFailed(int code) {
              if (code == 302 || code == 404) {
                ToastHelper.showToast(LoginActivity.this, "帐号或密码错误");
              } else {
                ToastHelper.showToast(LoginActivity.this, "登录失败: " + code);
              }
            }

            @Override public void onException(Throwable throwable) {
              ToastHelper.showToast(LoginActivity.this, "无效输入");
            }
          });
    }
  }


  @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
  public void onEvent(Boolean event) {
    if (event) {
      finish();
    }

  }


  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }
}
