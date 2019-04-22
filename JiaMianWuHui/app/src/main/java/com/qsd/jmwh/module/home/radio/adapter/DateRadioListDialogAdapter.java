package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;
import com.yu.common.ui.DelayClickImageView;

import java.util.List;

public class DateRadioListDialogAdapter extends BaseQuickAdapter<GetRadioConfigListBean.CdoListBean, BaseViewHolder> {
    private Context context;
    public DateRadioListDialogAdapter(int layoutResId, @Nullable List<GetRadioConfigListBean.CdoListBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetRadioConfigListBean.CdoListBean item) {
        helper.setText(R.id.project_name, item.sDatingConfigName);
        DelayClickImageView iv_icon = helper.getView(R.id.iv_icon);
        Glide.with(context).load(item.sIconUrl).into(iv_icon);
    }
}
