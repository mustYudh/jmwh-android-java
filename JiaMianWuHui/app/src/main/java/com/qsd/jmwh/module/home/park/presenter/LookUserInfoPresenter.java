package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.dialog.SelectHintPop;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.park.bean.OtherUserInfoBean;
import com.qsd.jmwh.module.home.park.bean.SubViewCount;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult") public class LookUserInfoPresenter
    extends BaseViewPresenter<LookUserInfoViewer> {

  public LookUserInfoPresenter(LookUserInfoViewer viewer) {
    super(viewer);
  }

  public void getUserInfo(int lUserId, double nLat, double nLng, boolean firstLoading) {
    XHttpProxy.proxy(ApiServices.class)
        .getOtherUserInfo(lUserId, nLat, nLng)
        .subscribeWith(new TipRequestSubscriber<OtherUserInfoBean>() {
          @Override protected void onSuccess(OtherUserInfoBean userCenterInfo) {
            assert getViewer() != null;
            getViewer().setUserInfo(userCenterInfo);
          }
        });
  }

  public void buyGalleryPay(int lBuyOtherUserId, int nPayVal) {
    XHttpProxy.proxy(OtherApiServices.class)
        .buyGalleryPay(lBuyOtherUserId, 5, nPayVal)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object info) {
            ToastUtils.show("解锁成功");
            assert getViewer() != null;
            getViewer().refreshData();
          }
        });
  }

  public void buyContactPay(int lBuyOtherUserId, int count) {
    SelectHintPop hint = new SelectHintPop(getActivity());
    hint.setTitle("温馨提示").setMessage("确认支付").setPositiveButton("确定", v1 -> {
      XHttpProxy.proxy(OtherApiServices.class)
          .getBuyContactPaySign(lBuyOtherUserId, 5, count)
          .subscribeWith(new TipRequestSubscriber<Object>() {
            @Override protected void onSuccess(Object o) {
              assert getViewer() != null;
              getViewer().refreshData();
              getViewer().payToChat();
            }
          });
      hint.dismiss();
    }).setNegativeButton("取消", v12 -> hint.dismiss()).showPopupWindow();
  }

  public void getSubViewCount() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getSubViewCount()
        .subscribeWith(new TipRequestSubscriber<SubViewCount>() {
          @Override protected void onSuccess(SubViewCount count) {
            assert getViewer() != null;
            getViewer().getViewCount(count);
          }
        });
  }
}
