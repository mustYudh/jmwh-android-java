package com.qsd.jmwh.module.home.message.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.home.message.bean.SystemCountBean;
import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
@SuppressLint("CheckResult") public class SystemMessagePresenter
    extends BaseViewPresenter<SystemMessageViewer> {

  public SystemMessagePresenter(SystemMessageViewer viewer) {
    super(viewer);
  }

  public void getMessageList(int nType) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getMsgList(0, nType)
        .subscribeWith(new NoTipRequestSubscriber<SystemMessageBean>() {
          @Override protected void onSuccess(SystemMessageBean bean) {
            assert getViewer() != null;
            switch (nType) {
              case 0:
                getViewer().getSystemMessage(bean.cdoList);
                break;
              case 1:
                getViewer().getBroadcastMessage(bean.cdoList);
                break;
              case 2:
                getViewer().getEarningsMessage(bean.cdoList);
                break;
              case 4:
                getViewer().getEvaluateMessage(bean.cdoList);
                break;
              default:
            }
          }
        });
  }

  public void getMessageCount() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getMsgCount()
        .subscribeWith(new NoTipRequestSubscriber<SystemCountBean>() {
          @Override protected void onSuccess(SystemCountBean bean) {
            assert getViewer() != null;
            getViewer().getSystemMessageCount(bean);
          }
        });
  }
}
