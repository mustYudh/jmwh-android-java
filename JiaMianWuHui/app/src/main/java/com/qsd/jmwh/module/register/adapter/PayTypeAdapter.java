package com.qsd.jmwh.module.register.adapter;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.register.bean.PayTypeBean;

import java.util.List;

public class PayTypeAdapter extends BaseQuickAdapter<PayTypeBean, BaseViewHolder> {

    public PayTypeAdapter(int layoutResId, @Nullable List<PayTypeBean> data) {
        super(layoutResId, data);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, PayTypeBean item) {
        if (item.type == 1) {
            helper.setImageDrawable(R.id.pay_icon, helper.itemView.getContext().getDrawable(R.drawable.ic_alipay));
            helper.setText(R.id.pay_type, "支付宝");
        } else if (item.type == 2) {
            helper.setImageDrawable(R.id.pay_icon, helper.itemView.getContext().getDrawable(R.drawable.ci_wechat_pay));
            helper.setText(R.id.pay_type, "微信");
        } else if (item.type == 3) {
            helper.setImageDrawable(R.id.pay_icon, helper.itemView.getContext().getDrawable(R.drawable.ic_money_bag));
            helper.setText(R.id.pay_type, "钱包余额（" + item.money + "）");
        }
        ImageView imageView = helper.getView(R.id.select_button);
        imageView.setSelected(item.selected);
    }
}
