package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.qsd.jmwh.view.CircleImageView;
import com.yu.common.ui.DelayClickTextView;

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
        DelayClickTextView tv_online = helper.getView(R.id.tv_online);
        Glide.with(context).load(item.sUserHeadPic).into(iv_headimg);
        helper.setText(R.id.tv_user_name, item.sNickName);
        helper.setText(R.id.tv_photo_num, item.nGalaryCapacity + "");
        if (item.distance_um >= 1000) {
            if (item.bHiddenRang){
                helper.setText(R.id.tv_middle, item.sDateRange + " · " + item.sJob + " · " + item.sAge);
            }else {
                helper.setText(R.id.tv_middle, item.sDateRange + " · " + item.sJob + " · " + item.sAge + " · " + (item.distance_um / 1000) + "km");
            }
        } else {
            if (item.bHiddenRang){
                helper.setText(R.id.tv_middle, item.sDateRange + " · " + item.sJob + " · " + item.sAge);
            }else {
                helper.setText(R.id.tv_middle, item.sDateRange + " · " + item.sJob + " · " + item.sAge + " · " + item.distance_um + "m");
            }
        }
        if (item.nOnLine == 0) {
            //在线
            tv_online.setText("当前在线");
            tv_online.setTextColor(context.getResources().getColor(R.color.app_main_color));
        } else if (item.nOnLine == 1){
            tv_online.setText("当前在线");
            tv_online.setTextColor(context.getResources().getColor(R.color.color_666666));
        }

        DelayClickTextView tv_auth_type = helper.getView(R.id.tv_auth_type);
        if (item.nAuthType == 1) {
            //已认证
            helper.setText(R.id.tv_auth_type, "真实");
            tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person));
        } else {
            //未认证
            helper.setText(R.id.tv_auth_type, "未认证");
            tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person_no));
        }
    }
}
