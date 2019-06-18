package com.qsd.jmwh.module.home.user.presenter;

import android.annotation.SuppressLint;

import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;
import com.qsd.jmwh.module.home.user.bean.MaskBallCoinBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

import java.util.List;

@SuppressLint("CheckResult") public class JiaMianCoinPresenter
    extends BaseViewPresenter<JiaMianCoinViewer> {

  public JiaMianCoinPresenter(JiaMianCoinViewer viewer) {
    super(viewer);
  }

  public void getInfo(int pageIndex, RefreshLayout refreshLayout, int type) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getAccountBalance(2, pageIndex)
        .subscribeWith(new TipRequestSubscriber<AccountBalance>() {
          @Override protected void onSuccess(AccountBalance accountBalance) {
            assert getViewer() != null;
            getViewer().getInfo(accountBalance, type);
            List<AccountBalance.CdoAccountBalanceListBean> data = accountBalance.cdoAccountBalanceList;
            if (refreshLayout != null) {
              if (type == 0) {
                refreshLayout.finishRefresh();
                if (data == null || data.size() == 0) {
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
              if (type == 0) {
                refreshLayout.finishRefresh();
                refreshLayout.finishRefreshWithNoMoreData();
              } else {
                refreshLayout.finishLoadMore();
              }
            }
          }
        });
  }

  public void getCoinConvertMoney(int money) {
    XHttpProxy.proxy(OtherApiServices.class)
        .getCoinConvertMoney(money)
        .subscribeWith(new TipRequestSubscriber<MaskBallCoinBean>() {
          @Override protected void onSuccess(MaskBallCoinBean maskBallCoinBean) {
            assert getViewer() != null;
            getViewer().coinConvertMoney(maskBallCoinBean);
          }
        });
  }

  public void withdrawMaskBallCoin(int money) {
    XHttpProxy.proxy(OtherApiServices.class)
        .withdrawMaskBallCoin(money)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            ToastUtils.show("申请提现成功，请耐心等待");
            getInfo(0, null, 0);
          }
        });
  }

  public void modifyAliPay(String sAliPayAccount, String sAliPayName) {
    XHttpProxy.proxy(OtherApiServices.class)
        .modifyAliPay(sAliPayAccount, sAliPayName)
        .subscribeWith(new TipRequestSubscriber<Object>() {
          @Override protected void onSuccess(Object o) {
            ToastUtils.show("添加提现账号成功");
            getInfo(0, null, 0);
          }
        });
  }

  public void getGoods() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getGoods(8)
        .subscribeWith(new TipRequestSubscriber<GoodsInfoBean>() {
          @Override protected void onSuccess(GoodsInfoBean goodsInfoBean) {
            assert getViewer() != null;
            getViewer().setGoodsInfo(goodsInfoBean.cdoList);
          }
        });
  }
}
