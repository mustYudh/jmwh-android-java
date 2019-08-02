package com.qsd.jmwh.module.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.dialog.net.NetLoadingDialog;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.register.presenter.EditRegisterCodePresenter;
import com.qsd.jmwh.module.register.presenter.EditRegisterCodeViewer;
import com.qsd.jmwh.module.splash.bean.RegisterSuccess;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import org.greenrobot.eventbus.EventBus;

public class EditRegisterCodeActivity extends BaseBarActivity
    implements View.OnClickListener, EditRegisterCodeViewer {
  @PresenterLifeCycle EditRegisterCodePresenter mPresenter = new EditRegisterCodePresenter(this);

  private int userId = UserProfile.getInstance().getUserId();
  private String userToken = UserProfile.getInstance().getAppToken();
  private int sex = UserProfile.getInstance().getSex();

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.edit_register_code_activity_layout);
  }

  public static int GET_AUTH_CODE_REQUEST = 124;
  public static String GET_AUTH_CODE_RESULT = "get_auth_code_result";

  public static Intent getIntent(Context context) {
    Intent starter = new Intent(context, EditRegisterCodeActivity.class);
    return starter;
  }

  @Override protected void loadData() {
    setTitle("欢迎");
    initListener();
  }

  private void initListener() {
    bindView(R.id.to_register, this);
    bindView(R.id.to_by, this);
    bindView(R.id.login, this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.to_register:
        getLaunchHelper().startActivity(EditUserDataActivity.class);
        break;
      case R.id.to_by:
        getLaunchHelper().startActivityForResult(ToByVipActivity.class, GET_AUTH_CODE_REQUEST);
        break;
      case R.id.login:
        EditText editText = bindView(R.id.code);
        String code = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(code)) {
          SelectHintPop selectHintPop = new SelectHintPop(getActivity());
          selectHintPop.setTitle("验证码验证通过")
              .setMessage("欢迎加入假面舞会！请勿把您的的账户泄露给他人，一经发现登录异常，账户会被自动冻结。")
              .setSingleButton("好的", v1 -> {
                mPresenter.commitCode(userId, userToken, code);
                selectHintPop.dismiss();
              })
              .showPopupWindow();
        } else {
          ToastUtils.show("邀请码输入不能为空");
        }
        break;
      default:
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
      if (requestCode == GET_AUTH_CODE_REQUEST) {
        getLaunchHelper().startActivity(EditUserDataActivity.getIntent(getActivity(), true));
        finish();
      } else if (data != null) {
        String result = data.getStringExtra(GET_AUTH_CODE_RESULT);
        EditText editText = bindView(R.id.code);
        if (!TextUtils.isEmpty(result)) {
          editText.setText(result);
          editText.setSelection(result.length());
        }
      }
    }
  }

  @Override public void registerSuccess() {
    NIMClient.getService(AuthService.class)
        .login(new com.netease.nimlib.sdk.auth.LoginInfo(UserProfile.getInstance().getSimUserId(),
            UserProfile.getInstance().getSimToken()))
        .setCallback(new RequestCallback<LoginInfo>() {
          @Override public void onSuccess(com.netease.nimlib.sdk.auth.LoginInfo info) {
            if (sex == 0) {
              NimUIKit.loginSuccess(UserProfile.getInstance().getSimUserId());
              NIMClient.toggleNotification(true);
              getLaunchHelper().startActivity(HomeActivity.class);
            } else {
              EventBus.getDefault().post(new RegisterSuccess(true));
              getLaunchHelper().startActivity(HomeActivity.class);
              finish();
            }
          }

          @Override public void onFailed(int code) {
            if (code == 302 || code == 404) {
              ToastHelper.showToast(EditRegisterCodeActivity.this, "帐号或密码错误");
              NetLoadingDialog.dismissLoading();
            } else {
              ToastHelper.showToast(EditRegisterCodeActivity.this, "登录失败: " + code);
              NetLoadingDialog.dismissLoading();
            }
          }

          @Override public void onException(Throwable throwable) {
            ToastHelper.showToast(EditRegisterCodeActivity.this, "无效输入");
            NetLoadingDialog.dismissLoading();
          }
        });
  }

  @Override public void getUserCode(String code) {
    EditText editText = bindView(R.id.code);
    editText.setText(code);
  }

  @Override protected void onResume() {
    super.onResume();
    mPresenter.getUserCode(userId, userToken);
  }
}
