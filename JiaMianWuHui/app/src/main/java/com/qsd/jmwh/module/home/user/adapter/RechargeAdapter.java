package com.qsd.jmwh.module.home.user.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.user.bean.GoodsInfoBean;

import java.util.List;

public class RechargeAdapter extends BaseQuickAdapter<GoodsInfoBean.CdoListBean, BaseViewHolder> {

    public RechargeAdapter(int layoutResId, @Nullable List<GoodsInfoBean.CdoListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfoBean.CdoListBean item) {
        helper.setText(R.id.goods_info,item.sGoodsName);
        if (item.nGoodsRealFee != 0) {
            TextView textView = helper.getView(R.id.goods_real_fee);
            textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
            helper.setText(R.id.goods_real_fee,"¥ " + item.nGoodsRealFee);
        }
        helper.setText(R.id.goods_sale_fee,"¥ " + item.nGoodsSaleFee);

        helper.addOnClickListener(R.id.goods_sale_fee);
    }
}
