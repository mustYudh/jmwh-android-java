package com.qsd.jmwh.module.home.message.presenter;

import android.annotation.SuppressLint;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.NoTipRequestSubscriber;
import com.qsd.jmwh.module.home.message.bean.SystemMessageBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
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

  public void getMessageList(int pageIndex, int nType, RefreshLayout refreshLayout,
      int refreshType) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getMsgList(pageIndex, nType)
        .subscribeWith(new NoTipRequestSubscriber<SystemMessageBean>() {
          @Override protected void onSuccess(SystemMessageBean bean) {
            assert getViewer() != null;
            getViewer().getMessageDetail(bean, refreshType);
            if (refreshLayout != null) {
              if (refreshType == 0) {
                refreshLayout.finishRefresh();
                if (bean.cdoList == null || bean.cdoList.size() == 0) {
                  refreshLayout.finishRefreshWithNoMoreData();
                }
              } else {
                refreshLayout.finishLoadMore();
                refreshLayout.finishLoadMoreWithNoMoreData();
              }
            }
          }

          @Override public void onError(ApiException e) {
            super.onError(e);
            if (refreshLayout != null) {
              if (refreshType == 0) {
                refreshLayout.finishRefresh();
                refreshLayout.finishRefreshWithNoMoreData();
              } else {
                refreshLayout.finishLoadMore();
              }
            }
          }
        });
  }
}
