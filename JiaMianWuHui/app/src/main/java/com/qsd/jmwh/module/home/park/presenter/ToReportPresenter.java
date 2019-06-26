package com.qsd.jmwh.module.home.park.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

/**
 * @author yudneghao
 * @date 2019-06-21
 */
public class ToReportPresenter extends BaseViewPresenter<ToReportViewer> {

  public ToReportPresenter(ToReportViewer viewer) {
    super(viewer);
  }

  @SuppressLint("CheckResult")
  public void addFeedBack(int lBeReportedUserId, int lBizId, String sContent, int nType,
      String sFileUrl) {
    XHttpProxy.proxy(OtherApiServices.class)
        .addFeedBack(lBeReportedUserId, lBizId, sContent, nType, sFileUrl)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            ToastUtils.show("举报成功");
            getActivity().finish();
          }
        });
  }
}
