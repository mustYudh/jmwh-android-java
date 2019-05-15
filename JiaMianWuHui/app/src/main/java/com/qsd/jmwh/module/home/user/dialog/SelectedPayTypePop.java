package com.qsd.jmwh.module.home.user.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.qsd.jmwh.R;
import com.qsd.jmwh.http.OtherApiServices;
import com.qsd.jmwh.http.subscriber.TipRequestSubscriber;
import com.qsd.jmwh.module.home.user.adapter.SelectPayTypeAdapter;
import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;
import com.qsd.jmwh.module.home.user.bean.PayInfoBean;
import com.qsd.jmwh.module.register.bean.PayInfo;
import com.qsd.jmwh.module.register.bean.PayTypeBean;
import com.qsd.jmwh.utils.PayUtils;
import com.xuexiang.xhttp2.XHttpProxy;
import com.xuexiang.xhttp2.exception.ApiException;
import com.yu.common.toast.ToastUtils;
import com.yu.common.windown.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

public class SelectedPayTypePop extends BasePopupWindow {


    private int currentPayType;
    public SelectedPayTypePop(Context context, GoodsInfoBean.CdoListBean data,PayUtils.PayCallBack payCallBack) {
        super(context, LayoutInflater.from(context).inflate(R.layout.sele_pay_type_pop_layout, null),
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        bindView(R.id.close, v -> dismiss());
        getPayType();
        bindView(R.id.pay, v -> XHttpProxy.proxy(OtherApiServices.class)
                .getBuyDatingPaySign(data.lGoodsId,currentPayType)
                .subscribeWith(new TipRequestSubscriber<PayInfo>() {
            @Override
            protected void onSuccess(PayInfo payInfo) {
                PayUtils.getInstance().pay((Activity) context,currentPayType,payInfo).getPayResult(new PayUtils.PayCallBack() {
                    @Override
                    public void onPaySuccess(int type) {
                        ToastUtils.show("充值成功");
                        payCallBack.onPaySuccess(type);
                        dismiss();
                    }

                    @Override
                    public void onFailed(int type) {
                        ToastUtils.show("充值失败，请重试");
                        dismiss();
                    }
                });

            }

                    @Override
                    protected void onError(ApiException apiException) {
                        super.onError(apiException);
                        dismiss();
                    }
                }));
    }


    @SuppressLint("CheckResult")
    private void getPayType() {
        XHttpProxy.proxy(OtherApiServices.class).getPayInfo().subscribeWith(new TipRequestSubscriber<PayInfoBean>() {
            @Override
            protected void onSuccess(PayInfoBean payInfoBean) {
                RecyclerView payTypeList = bindView(R.id.pay_type);
                List<PayTypeBean> list = new ArrayList<>();
                List<String> typeList = payInfoBean.nPayTypeList;
                for (String type : typeList) {
                    PayTypeBean payTypeBean = new PayTypeBean();
                    payTypeBean.type = Integer.parseInt(type);
                    payTypeBean.money = payInfoBean.cdoData.nMoney + "";
                    list.add(payTypeBean);
                }
                SelectPayTypeAdapter payTypeAdapter = new SelectPayTypeAdapter(R.layout.item_pay_type_layout, list);
                payTypeList.setLayoutManager(new LinearLayoutManager(getContext()));
                payTypeList.setAdapter(payTypeAdapter);

                payTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
                    for (Object datum : adapter.getData()) {
                        PayTypeBean bean = (PayTypeBean) datum;
                        bean.selected = false;
                    }
                    PayTypeBean bean = (PayTypeBean) adapter.getData().get(position);
                    bean.selected = true;
                    currentPayType = bean.type;
                    adapter.notifyDataSetChanged();
                });
            }
        });

    }

    @Override
    protected View getBackgroundShadow() {
        return null;
    }

    @Override
    protected View getContainer() {
        return null;
    }
}
