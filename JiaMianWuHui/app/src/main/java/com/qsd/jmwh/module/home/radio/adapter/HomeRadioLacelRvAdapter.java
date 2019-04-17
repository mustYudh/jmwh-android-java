package com.qsd.jmwh.module.home.radio.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;

import java.util.List;

public class HomeRadioLacelRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public HomeRadioLacelRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_label, item);
    }
}
