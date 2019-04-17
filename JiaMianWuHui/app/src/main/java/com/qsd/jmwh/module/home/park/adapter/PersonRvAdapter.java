package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.view.CircleImageView;

import java.util.List;

public class PersonRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public PersonRvAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        Glide.with(context).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1319625412,2949035306&fm=26&gp=0.jpg").into(iv_headimg);
    }
}
