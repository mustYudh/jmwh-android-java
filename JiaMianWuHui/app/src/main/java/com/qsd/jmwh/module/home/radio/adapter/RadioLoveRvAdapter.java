package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.radio.bean.GetRadioConfigListBean;

import java.util.List;

public class RadioLoveRvAdapter extends BaseQuickAdapter<GetRadioConfigListBean.CdoListBean, BaseViewHolder> {
    private Context context;

    public RadioLoveRvAdapter(int layoutResId, @Nullable List<GetRadioConfigListBean.CdoListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetRadioConfigListBean.CdoListBean item) {
        helper.setText(R.id.tv_type, item.sDatingConfigName);

    }
}
