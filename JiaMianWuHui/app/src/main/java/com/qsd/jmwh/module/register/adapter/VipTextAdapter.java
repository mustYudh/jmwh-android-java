package com.qsd.jmwh.module.register.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;

import java.util.List;

public class VipTextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public VipTextAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.text,item);
    }
}
