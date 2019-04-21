package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.qsd.jmwh.view.CircleImageView;

import java.util.List;

public class PersonRvAdapter extends BaseQuickAdapter<HomePersonListBean.CdoListBean, BaseViewHolder> {
    private Context context;

    public PersonRvAdapter(int layoutResId, @Nullable List<HomePersonListBean.CdoListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePersonListBean.CdoListBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        Glide.with(context).load(item.sUserHeadPic).into(iv_headimg);
        helper.setText(R.id.tv_user_name, item.sNickName);
    }
}
