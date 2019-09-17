package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.adapter.MineRadilLoveUserGvAdapter;
import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.module.register.bean.VipInfoBean;
import com.qsd.jmwh.utils.PayUtils;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;
import com.yu.common.toast.ToastUtils;

@SuppressLint("CheckResult") public class ToByVipPresenter
    extends BaseViewPresenter<ToByVipViewer> {

  public ToByVipPresenter(ToByVipViewer viewer) {
    super(viewer);
  }



  public void getCoinConvertMoney() {
    XHttpProxy.proxy(OtherApiServices.class)
        .getAccountBalance(2, 1)
        .subscribeWith(new TipRequestSubscriber<AccountBalance>() {
          @Override protected void onSuccess(AccountBalance accountBalance) {
            assert getViewer() != null;
            getViewer().coinConvertMoney(accountBalance);

          }
        });
  }




  public void getVipInfo(int lUserId, String token) {
    XHttpProxy.proxy(ApiServices.class)
        .getVipInfoList(lUserId, token)
        .subscribeWith(new TipRequestSubscriber<VipInfoBean>() {
          @Override protected void onSuccess(VipInfoBean vipInfoBean) {
            assert getViewer() != null;
            if (vipInfoBean != null) {
              getViewer().getVipInfo(vipInfoBean);
            }
          }
        });
  }

  public void pay(int lGoodsId, int type, int userId, String token) {
    XHttpProxy.proxy(ApiServices.class)
        .pay(lGoodsId, type)
        .subscribeWith(new TipRequestSubscriber<PayInfo>() {
          @Override protected void onSuccess(PayInfo info) {
            PayUtils.getInstance()
                .pay(getActivity(), type, info)
                .getPayResult(new PayUtils.PayCallBack() {
                  @Override public void onPaySuccess(int type) {
                    MineRadilLoveUserGvAdapter.vip = true;
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                  }

                  @Override public void onFailed(int type) {
                    ToastUtils.show("支付失败，请重试");
                    getActivity().finish();
                  }
                });
          }
        });
  }

  @Override public void willDestroy() {
    super.willDestroy();
    PayUtils.getInstance().recycle();
  }
}
