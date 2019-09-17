package com.qsd.jmwh.module.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qsd.jmwh.R;
import com.qsd.jmwh.base.BaseBarActivity;
import com.qsd.jmwh.data.UserProfile;
import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;
import com.qsd.jmwh.module.home.user.dialog.RechargeListDialog;
import com.qsd.jmwh.module.register.adapter.PayTypeAdapter;
import com.qsd.jmwh.module.register.adapter.VipInfoAdapter;
import com.qsd.jmwh.module.register.adapter.VipTextAdapter;
import com.qsd.jmwh.module.register.bean.PayTypeBean;
import com.qsd.jmwh.module.register.bean.VipInfoBean;
import com.qsd.jmwh.module.register.presenter.ToByVipPresenter;
import com.qsd.jmwh.module.register.presenter.ToByVipViewer;
import com.qsd.jmwh.utils.PayUtils;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;
import java.util.ArrayList;
import java.util.List;

public class ToByVipActivity extends BaseBarActivity implements ToByVipViewer {

  @PresenterLifeCycle private ToByVipPresenter mPresenter = new ToByVipPresenter(this);
  private RecyclerView vipTextList;
  private RecyclerView vipInfoList;
  private VipInfoAdapter vipInfoAdapter;
  private RecyclerView payType;
  private PayTypeAdapter payTypeAdapter;
  private VipInfoBean.CdoListBean cdoListBean;
  private TextView payCount;
  private double currentMoney;
  private PayTypeBean currentType;
  private boolean isRegister;
  private boolean canPay;
  private final static String IS_REGISTER = "is_register";

  private int userId = UserProfile.getInstance().getUserId();
  private String userToken = UserProfile.getInstance().getAppToken();

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.to_by_vip_activity);
    initView();
  }

  public Intent getIntent(Context context, boolean isRegister) {
    Intent intent = new Intent(context, ToByVipActivity.class);
    intent.putExtra(IS_REGISTER, isRegister);
    return intent;
  }

  private void initView() {
    vipTextList = bindView(R.id.vip_text);
    vipTextList.setLayoutManager(new LinearLayoutManager(getActivity()));
    vipInfoList = bindView(R.id.vip_info);
    payCount = bindView(R.id.count);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
    vipInfoList.setLayoutManager(linearLayoutManager);
    payType = bindView(R.id.pay_type);
    payType.setLayoutManager(new LinearLayoutManager(getActivity()));
    bindView(R.id.pay, v -> {
      if (currentType != null) {
        if (canPay) {
          mPresenter.pay(cdoListBean.lGoodsId, currentType.type, userId, userToken);
        } else {
          mPresenter.getGoods();
        }
      } else {
        ToastUtils.show("请选择支付方式");
      }
    });
  }

  @Override protected void loadData() {
    setTitle("会员中心");
    mPresenter.getVipInfo(userId, userToken);
  }



  @Override public void getVipInfo(VipInfoBean vipInfoBean) {
    currentMoney = Double.parseDouble(vipInfoBean.nMaskBallCoin);
    VipTextAdapter vipTextAdapter =
        new VipTextAdapter(R.layout.item_vip_text_layout, vipInfoBean.sVIPPrivilegeList);
    vipTextList.setAdapter(vipTextAdapter);
    vipInfoAdapter = new VipInfoAdapter(R.layout.item_vip_info_layout, vipInfoBean.cdoList);
    for (VipInfoBean.CdoListBean bean : vipInfoBean.cdoList) {
      if (bean.bRecommend) {
        bean.selected = true;
        cdoListBean = bean;
      }
    }
    vipInfoList.setAdapter(vipInfoAdapter);

    //for (String payType : vipInfoBean.nPayTypeList) {
    //  PayTypeBean payTypeBean = new PayTypeBean();
    //  if (Integer.parseInt(payType) == 3) {
    //    payTypeBean.money = vipInfoBean.nMoney;
    //  }
    //  if (Integer.parseInt(payType) != 4) {
    //    payTypeBean.type = Integer.parseInt(payType);
    //    payTypeBeans.add(payTypeBean);
    //  }
    //}
    //payTypeBeans.get(0).selected = true;

    List<PayTypeBean> payTypeBeans = new ArrayList<>();
    PayTypeBean jiaMian = new PayTypeBean();
    jiaMian.type = 5;
    jiaMian.selected = true;
    jiaMian.money = vipInfoBean.nMaskBallCoin;
    payTypeBeans.add(jiaMian);
    currentType = payTypeBeans.get(0);
    payTypeAdapter = new PayTypeAdapter(R.layout.item_pay_type_layout, payTypeBeans);
    payType.setAdapter(payTypeAdapter);
    notifyDataSetChanged();


    payCount.setText(cdoListBean.nGoodsSaleFee + "");
    vipInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
      for (Object datum : adapter.getData()) {
        VipInfoBean.CdoListBean bean = (VipInfoBean.CdoListBean) datum;
        bean.selected = false;
      }
      VipInfoBean.CdoListBean bean = (VipInfoBean.CdoListBean) adapter.getData().get(position);
      bean.selected = true;
      adapter.notifyDataSetChanged();
      cdoListBean = (VipInfoBean.CdoListBean) adapter.getData().get(position);
      payCount.setText(cdoListBean.nGoodsSaleFee + "");
      notifyDataSetChanged();
    });
  }



  private void notifyDataSetChanged() {
    if (payTypeAdapter != null) {
      for (PayTypeBean datum : payTypeAdapter.getData()) {
        if (datum.type == 5) {
          if (currentMoney < cdoListBean.nGoodsRealFee) {
            datum.money = "假面币不足";
            canPay = false;
          } else {
            datum.money = currentMoney + "";
            canPay = true;
          }
        }
      }
      payTypeAdapter.notifyDataSetChanged();


      //payTypeAdapter.setOnItemClickListener((adapter, view, position) -> {
      //  if (cdoListBean != null) {
      //    for (Object datum : adapter.getData()) {
      //      PayTypeBean bean = (PayTypeBean) datum;
      //      bean.selected = false;
      //    }
      //    PayTypeBean bean = (PayTypeBean) adapter.getData().get(position);
      //    bean.selected = true;
      //    adapter.notifyDataSetChanged();
      //    currentType = bean;
      //  } else {
      //    ToastUtils.show("请先选择VIP");
      //  }
      //});
    }
  }

  @Override public void setGoodsInfo(List<GoodsInfoBean.CdoListBean> goods) {
    RechargeListDialog dialog =
        new RechargeListDialog(getActivity(), goods, new PayUtils.PayCallBack() {
          @Override public void onPaySuccess(int type) {
            mPresenter.getVipInfo(userId, userToken);
          }

          @Override public void onFailed(int type) {

          }
        });
    dialog.showPopupWindow();
  }
}
