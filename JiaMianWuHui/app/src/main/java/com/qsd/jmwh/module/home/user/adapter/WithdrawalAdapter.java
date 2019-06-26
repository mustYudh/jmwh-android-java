package com.qsd.jmwh.module.home.user.adapter;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.yu.common.ui.Res;
import com.yu.common.utils.ImageLoader;

public class WithdrawalAdapter
    extends BaseQuickAdapter<AccountBalance.CdoAccountBalanceListBean, BaseViewHolder> {

  public WithdrawalAdapter(int layoutResId) {
    super(layoutResId);
  }

  @SuppressLint("SetTextI18n") @Override
  protected void convert(BaseViewHolder helper, AccountBalance.CdoAccountBalanceListBean item) {
    helper.setText(R.id.time, item.dCreateTime);
    helper.setText(R.id.status, item.nStatus == 0 ? "未入账" : "入账成功");
    helper.setText(R.id.desc, item.sRemark);
    TextView view = helper.getView(R.id.count);
    view.setText((item.nABType == 0 ? "+" : "-") + item.nPayFee);
    if (item.nABType != 0) {
      view.setTextColor(Res.color(R.color.color_F43A50));
    }
    ImageView header = helper.getView(R.id.header);
    ImageLoader.loadCenterCrop(header.getContext(), item.sUserHeadPic, header);
  }
}
