package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
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
            if (data.nStatus == 0) {
              getLaunchHelper().startActivityForResult(
                  EditUserDataActivity.getIntent(getActivity(), 333), USERDATA_EDIT_REQUEST);
            } else {
              getViewer().registerSuccess();
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
