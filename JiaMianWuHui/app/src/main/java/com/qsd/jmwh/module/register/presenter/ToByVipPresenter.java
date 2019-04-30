package com.qsd.jmwh.module.register.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import com.qsd.jmwh.http.ApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.module.register.bean.VipInfoBean;
import com.qsd.jmwh.utils.PayUtils;
import com.xuexiang.xhttp2.XHttpProxy;
import com.yu.common.framework.BaseViewPresenter;

@SuppressLint("CheckResult")
public class ToByVipPresenter extends BaseViewPresenter<ToByVipViewer> {

    public ToByVipPresenter(ToByVipViewer viewer) {
        super(viewer);
    }


    public void getVipInfo(int lUserId,String token) {
        XHttpProxy.proxy(ApiServices.class)
                .getVipInfoList(lUserId,token)
                .subscribeWith(new TipRequestSubscriber<VipInfoBean>() {
                    @Override
                    protected void onSuccess(VipInfoBean vipInfoBean) {
                        assert getViewer() != null;
                        if (vipInfoBean != null) {
                            getViewer().getVipInfo(vipInfoBean);
                        }
                    }
                });
    }


     public void pay(int lGoodsId,int type) {

      XHttpProxy.proxy(ApiServices.class)
          .pay(lGoodsId,type).subscribeWith(new TipRequestSubscriber<PayInfo>() {
        @Override protected void onSuccess(PayInfo info) {
          PayUtils.getInstance().startWXPay(getActivity(),info).setWXPayResult(new PayUtils.WXPayCallBack() {
            @Override public void onPaySuccess() {
                getActivity().finish();
            }

            @Override public void onCancel() {
              Log.e("=====>","取消支付");
            }
          });
        }
      });
    }
}
