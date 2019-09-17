package com.qsd.jmwh.module.register.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.VipInfoBean;

import java.util.List;

public class VipInfoAdapter extends BaseQuickAdapter<VipInfoBean.CdoListBean, BaseViewHolder> {

  public VipInfoAdapter(int layoutResId, @Nullable List<VipInfoBean.CdoListBean> data) {
    super(layoutResId, data);
  }

  @Override protected void convert(BaseViewHolder helper, VipInfoBean.CdoListBean item) {
    helper.setText(R.id.time, item.sGoodsName);
    helper.setText(R.id.money, (int) item.nGoodsRealFee + "假面币/月");
    helper.getView(R.id.root_view).setSelected(item.selected);
    helper.setVisible(R.id.vip_recommended, item.bRecommend);
  }
}
