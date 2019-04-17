package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.RangeData;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019/4/17
 */
@SuppressLint("CheckResult") public class DateRangePresenter
    extends BaseViewPresenter<DateRangeViewer> {

  public DateRangePresenter(DateRangeViewer viewer) {
    super(viewer);
  }

  public void getRangeData(int nLevel, int lParentId, int lUserId, String token) {
    if (lUserId != -1) {
      XHttpProxy.proxy(ApiServices.class)
          .getDateRange(nLevel, lParentId, lUserId, token)
          .subscribeWith(new NoTipRequestSubscriber<RangeData>() {
            @Override protected void onSuccess(RangeData rangeData) {

            }
          });
    }
  }
}
