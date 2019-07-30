package com.qsd.jmwh.module.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.net.NetLoadingDialog;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.login.LoginActivity;
import com.qsd.jmwh.module.login.bean.LoginInfo;
import com.qsd.jmwh.module.register.RegisterActivity;
import com.qsd.jmwh.module.splash.bean.RegisterSuccess;
import com.qsd.jmwh.module.splash.presenter.SplashPresenter;
import com.qsd.jmwh.module.splash.presenter.SplashViewer;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import com.yu.share.AuthLoginHelp;
import com.yu.share.callback.AuthLoginCallback;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SelectLoginActivity extends BaseActivity
    implements View.OnClickListener, AuthLoginCallback, SplashViewer, TencentLocationListener {

  @PresenterLifeCycle private SplashPresenter mPresenter = new SplashPresenter(this);

  private static int REQUEST_CODE = 0x123;
  private AuthLoginHelp mAuthLoginHelp;

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.splash_activity_layout);
    String[] permiss = {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
        ,Manifest.permission.CHANGE_WIFI_STATE
    };
    if (Build.VERSION.SDK_INT >= 23) {
      final RxPermissions rxPermissions = new RxPermissions(this);
      rxPermissions.request(permiss).subscribe(granted -> {
      });
    }
    if (UserProfile.getInstance().isAppLogin()) {
      getLaunchHelper().startActivityForResult(HomeActivity.class, REQUEST_CODE);
      finish();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override protected void loadData() {
    EventBus.getDefault().register(this);
    bindView(R.id.login, this);
    bindView(R.id.register, this);
    bindView(R.id.wechat_login, this);
    bindView(R.id.qq_login, this);
    bindText(R.id.app_name, getAppName(this));
    mAuthLoginHelp = new AuthLoginHelp(getActivity());
    mAuthLoginHelp.callback(this);
    TencentLocationRequest request = TencentLocationRequest.create()
        .setInterval(1000 * 5 * 60)
        .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
    TencentLocationManager locationManager = TencentLocationManager.getInstance(getActivity());
    locationManager.requestLocationUpdates(request, this);
  }

  public static String getAppName(Context context) {
    PackageInfo pkg = null;
    try {
      pkg = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return pkg.applicationInfo.loadLabel(context.getPackageManager()).toString();
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return "";
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.login:
        getLaunchHelper().startActivity(LoginActivity.class);
        break;
      case R.id.register:
        getLaunchHelper().startActivity(RegisterActivity.class);
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
        boolean installWeChat =
            UMShareAPI.get(getActivity()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN);
        if (installWeChat) {
          mAuthLoginHelp.login(SHARE_MEDIA.WEIXIN);
        } else {
          ToastUtils.show("请先安装微信");
        }
        break;
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
  }

  @Override public void onStart(SHARE_MEDIA media) {

  }

  @Override public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {
    String uuid = map.get("uid");
    String type = "";
    if (media == SHARE_MEDIA.QQ) {
      type = "QQ";
    } else if (media == SHARE_MEDIA.WEIXIN) {
      type = "wx";
    }
    mPresenter.auth(uuid, type);
  }

  @Override public void onError(SHARE_MEDIA media, int i, Throwable throwable) {

  }

  @Override public void onCancel(SHARE_MEDIA media, int i) {
    Log.e("======onFailed", "onFailed");
  }

  @Override public void authLoginSuccess(LoginInfo loginInfo) {
    if (loginInfo != null) {
      NetLoadingDialog.showLoading(getActivity(), false);
      NIMClient.getService(AuthService.class)
          .login(new com.netease.nimlib.sdk.auth.LoginInfo(loginInfo.sIMID, loginInfo.sIMToken))
          .setCallback(new RequestCallback<com.netease.nimlib.sdk.auth.LoginInfo>() {

            @Override public void onSuccess(com.netease.nimlib.sdk.auth.LoginInfo info) {
              UserProfile.getInstance().appLogin(loginInfo);
              UserProfile.getInstance().setSex(loginInfo.nSex);
              NimUIKit.loginSuccess(loginInfo.sIMID);
              NIMClient.toggleNotification(true);
              EventBus.getDefault().post(new RegisterSuccess(true));
              NetLoadingDialog.dismissLoading();
              getLaunchHelper().startActivity(HomeActivity.class);
              setResult(Activity.RESULT_OK);
              finish();
            }

            @Override public void onFailed(int code) {
              if (code == 302 || code == 404) {
                ToastHelper.showToast(SelectLoginActivity.this, "帐号或密码错误");
                NetLoadingDialog.dismissLoading();
              } else {
                ToastHelper.showToast(SelectLoginActivity.this, "登录失败: " + code);
                NetLoadingDialog.dismissLoading();
              }
            }

            @Override public void onException(Throwable throwable) {
              ToastHelper.showToast(SelectLoginActivity.this, "无效输入");
              NetLoadingDialog.dismissLoading();
            }
          });
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onMessageEvent(RegisterSuccess event) {
    if (event.success) {
      finish();
    }
  }

  @Override public void onLocationChanged(TencentLocation location, int i, String s) {
    if (location != null) {
      UserProfile.getInstance().setHomeCityName("");
      UserProfile.getInstance().setLat((float) location.getLatitude());
      UserProfile.getInstance().setLng((float) location.getLongitude());
      UserProfile.getInstance().setCityName(location.getCity());
    }
  }

  @Override public void onStatusUpdate(String s, int i, String s1) {

  }
}
