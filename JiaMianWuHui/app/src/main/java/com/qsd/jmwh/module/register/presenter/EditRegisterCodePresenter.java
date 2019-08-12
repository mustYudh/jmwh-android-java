package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.dialog.net.NetLoadingDialog;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.EditUserDataActivity;
import com.qsd.jmwh.module.register.bean.CommitCodeResultBean;
import com.qsd.jmwh.module.register.bean.UserAuthCodeBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult") public class EditRegisterCodePresenter
    extends BaseViewPresenter<EditRegisterCodeViewer> {

  public final static int USERDATA_EDIT_REQUEST = 887;

  public EditRegisterCodePresenter(EditRegisterCodeViewer viewer) {
    super(viewer);
  }

  public void commitCode(int userCode, String token, String code) {
    NetLoadingDialog.showLoading(getActivity(), false);
    XHttpProxy.proxy(ApiServices.class)
        .getUserAuthByCode(userCode, token, code)
        .subscribeWith(new TipRequestSubscriber<CommitCodeResultBean>() {
          @Override protected void onSuccess(CommitCodeResultBean data) {
            assert getViewer() != null;
            NetLoadingDialog.dismissLoading();
            if (data.nStatus == 0) {
              SelectHintPop selectHintPop = new SelectHintPop(getActivity());
              selectHintPop.setTitle("温馨提示").setMessage("邀请码正确，请完善个人信息!").setSingleButton("好的", v1 -> {
                getLaunchHelper().startActivityForResult(
                    EditUserDataActivity.getIntent(getActivity(), 333), USERDATA_EDIT_REQUEST);
                selectHintPop.dismiss();
              }).showPopupWindow();
            } else {
              SelectHintPop selectHintPop = new SelectHintPop(getActivity());
              selectHintPop.setTitle("验证码验证通过")
                  .setMessage("欢迎加入假面舞会！请勿把您的的账户泄露给他人，一经发现登录异常，账户会被自动冻结。")
                  .setSingleButton("好的", v1 -> {
                    getViewer().registerSuccess();
                    selectHintPop.dismiss();
                  })
                  .showPopupWindow();
            }
          }

          @Override protected void onError(ApiException apiException) {
            NetLoadingDialog.dismissLoading();
            super.onError(apiException);
          }
        });
  }

  public void getUserCode(int code, String token) {
    XHttpProxy.proxy(ApiServices.class)
        .getCod(code, token)
        .subscribeWith(new NoTipRequestSubscriber<UserAuthCodeBean>() {
          @Override protected void onSuccess(UserAuthCodeBean result) {
            if (result != null && !TextUtils.isEmpty(result.sAuthCode)) {
              assert getViewer() != null;
              getViewer().getUserCode(result.sAuthCode);
            }
          }
        });
  }
}
