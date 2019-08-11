package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.dialog.net.NetLoadingDialog;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.HomeActivity;
import com.qsd.jmwh.module.register.EditRegisterCodeActivity;
import com.qsd.jmwh.module.register.bean.DateProjectBean;
import com.qsd.jmwh.module.register.bean.UploadUserInfoParams;
import com.qsd.jmwh.module.register.bean.UserAuthCodeBean;
import com.qsd.jmwh.module.splash.bean.RegisterSuccess;
import com.qsd.jmwh.thrid.UploadImage;
import com.qsd.jmwh.thrid.oss.PersistenceResponse;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import org.greenrobot.eventbus.EventBus;

@SuppressLint("CheckResult") public class EditUserInfoPresenter
    extends BaseViewPresenter<EditUserInfoViewer> {

  public EditUserInfoPresenter(EditUserInfoViewer viewer) {
    super(viewer);
  }

  public void setHeader(String path, String objectName, String userId, String token) {
    PersistenceResponse response = UploadImage.uploadImage(getActivity(), objectName, path);
    if (response.success) {
      XHttpProxy.proxy(ApiServices.class)
          .uploadHeader(userId, token, response.cloudUrl)
          .safeSubscribe(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              assert getViewer() != null;
              getViewer().setUserHeaderSuccess(response.cloudUrl);
            }
          });
    }
  }

  public void uploadUserInfo(UploadUserInfoParams params, boolean isGirl) {
    if (params.registerCheckEmpty(isGirl)) {
      XHttpProxy.proxy(ApiServices.class)
          .modifyUserInfo(params.sNickName, params.sDateRange, params.sAge, params.sJob,
              params.sDatePro, params.sHeight, params.sWeight, params.lUserId, params.sIntroduce,
              params.token, params.sUserHeadPic, params.sBust, params.QQ, params.WX,
              params.bHiddenQQandWX)
          .subscribeWith(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              assert getViewer() != null;
              getViewer().commitUserInfo();
            }
          });
    }
  }

  public void getDateProject() {
    XHttpProxy.proxy(ApiServices.class)
        .getDateProject()
        .subscribeWith(new TipRequestSubscriber<DateProjectBean>() {
          @Override protected void onSuccess(DateProjectBean dateProjectBean) {
            assert getViewer() != null;
            getViewer().showDateProjectList(dateProjectBean.sDateProList);
          }
        });
  }

  public void getCode(int userId, String token, int sex, boolean isVip, int type) {
    if (sex == 0 || isVip) {
      NetLoadingDialog.showLoading(getActivity(), false);
      NIMClient.getService(AuthService.class)
          .login(new com.netease.nimlib.sdk.auth.LoginInfo(UserProfile.getInstance().getSimUserId(),
              UserProfile.getInstance().getSimToken()))
          .setCallback(new RequestCallback<LoginInfo>() {

            @Override public void onSuccess(com.netease.nimlib.sdk.auth.LoginInfo info) {
              EventBus.getDefault().post(new RegisterSuccess(true));
              NimUIKit.loginSuccess(UserProfile.getInstance().getSimUserId());
              NIMClient.toggleNotification(true);
              NetLoadingDialog.dismissLoading();
              getLaunchHelper().startActivity(HomeActivity.class);
              getActivity().finish();
            }

            @Override public void onFailed(int code) {
              if (code == 302 || code == 404) {
                ToastHelper.showToast(getActivity(), "帐号或密码错误");
                NetLoadingDialog.dismissLoading();
              } else {
                ToastHelper.showToast(getActivity(), "登录失败: " + code);
                NetLoadingDialog.dismissLoading();
              }
            }

            @Override public void onException(Throwable throwable) {
              ToastHelper.showToast(getActivity(), "无效输入");
              NetLoadingDialog.dismissLoading();
            }
          });
    } else {
      if (type == 333) {
        SelectHintPop selectHintPop = new SelectHintPop(getActivity());
        selectHintPop.setTitle("温馨提示")
            .setMessage("欢迎进入假面舞会!")
            .setSingleButton("好的", v1 -> {
              login();
              selectHintPop.dismiss();
            })
            .showPopupWindow();
      } else {
        XHttpProxy.proxy(ApiServices.class)
            .getCod(userId, token)
            .subscribeWith(new TipRequestSubscriber<UserAuthCodeBean>() {
              @Override protected void onSuccess(UserAuthCodeBean result) {
                //              ToastUtils.show("邀请码获取成功");
                Intent intent = new Intent();
                intent.putExtra(EditRegisterCodeActivity.GET_AUTH_CODE_RESULT, result.sAuthCode);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
              }
            });
      }
    }
  }

  private void login() {
    NIMClient.getService(AuthService.class)
        .login(new com.netease.nimlib.sdk.auth.LoginInfo(UserProfile.getInstance().getSimUserId(),
            UserProfile.getInstance().getSimToken()))
        .setCallback(new RequestCallback<LoginInfo>() {
          @Override public void onSuccess(com.netease.nimlib.sdk.auth.LoginInfo info) {
            EventBus.getDefault().post(new RegisterSuccess(true));
            getLaunchHelper().startActivity(HomeActivity.class);
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
          }

          @Override public void onFailed(int code) {
            if (code == 302 || code == 404) {
              ToastHelper.showToast(getActivity(), "帐号或密码错误");
              NetLoadingDialog.dismissLoading();
            } else {
              ToastHelper.showToast(getActivity(), "登录失败: " + code);
              NetLoadingDialog.dismissLoading();
            }
          }

          @Override public void onException(Throwable throwable) {
            ToastHelper.showToast(getActivity(), "无效输入");
            NetLoadingDialog.dismissLoading();
          }
        });
  }
}
