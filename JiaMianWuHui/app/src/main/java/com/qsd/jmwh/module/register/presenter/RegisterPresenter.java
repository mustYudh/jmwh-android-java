package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.SendVerCodeBean;
import com.qsd.jmwh.utils.countdown.RxCountDown;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

/**
 * @author yudneghao
 * @date 2019/4/12
 */
@SuppressLint("CheckResult")
public class RegisterPresenter extends BaseViewPresenter<RegisterViewer> {

  public RegisterPresenter(RegisterViewer viewer) {
    super(viewer);
  }

  public void sendVerCode(String number, RxCountDown countDown) {
    XHttpProxy.proxy(ApiServices.class)
        .send(number)
        .subscribeWith(new TipRequestSubscriber<SendVerCodeBean>() {
          @Override
          protected void onSuccess(SendVerCodeBean sendVerCodeBeanApiResult) {
            assert getViewer() != null;
            ToastUtils.show("发送成功");
          }

          @Override public void onError(ApiException e) {
            super.onError(e);
            countDown.stopCoutdown();
          }
        });
  }


  public void register(String phone,String password,String verCode) {
    XHttpProxy.proxy(ApiServices.class)
        .register(verCode,phone,"mobile",password)
        .subscribeWith(new TipRequestSubscriber<Object>() {

          @Override protected void onSuccess(Object o) {
            assert getViewer() != null;
            getViewer().registerSuccess();
          }
        });
  }
}
