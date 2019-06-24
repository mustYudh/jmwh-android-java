package com.qsd.jmwh.module.home.message.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

/**
 * @author yudneghao
 * @date 2019-06-24
 */
@SuppressLint("CheckResult") public class MessageDetailPresenter
    extends BaseViewPresenter<MessageDetailVIewer> {

  public MessageDetailPresenter(MessageDetailVIewer viewer) {
    super(viewer);
  }

  public void getMessageList(int nType) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getMsgList(0, nType)
        .subscribeWith(new NoTipRequestSubscriber<SystemMessageBean>() {
          @Override protected void onSuccess(SystemMessageBean bean) {
            assert getViewer() != null;
            getViewer().getMessageDetail(bean);
          }
        });
  }
}
