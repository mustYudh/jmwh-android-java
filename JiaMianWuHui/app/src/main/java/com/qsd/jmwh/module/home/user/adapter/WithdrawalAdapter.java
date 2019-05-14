package com.qsd.jmwh.module.home.user.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.user.bean.AccountBalance;
import com.yu.common.utils.ImageLoader;

import java.util.List;

public class WithdrawalAdapter extends BaseQuickAdapter<AccountBalance.CdoAccountBalanceListBean, BaseViewHolder> {

    public WithdrawalAdapter(int layoutResId, @Nullable List<AccountBalance.CdoAccountBalanceListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountBalance.CdoAccountBalanceListBean item) {
        helper.setText(R.id.time, item.dCreateTime);
        helper.setText(R.id.status, item.nStatus == 0 ? "未入账" : "入账成功");
        helper.setText(R.id.desc, item.sRemark);
        helper.setText(R.id.count, "+" + item.nPayFee);
        ImageView header = helper.getView(R.id.header);
        ImageLoader.loadCenterCrop(header.getContext(),item.sUserHeadPic,header);
    }
}
