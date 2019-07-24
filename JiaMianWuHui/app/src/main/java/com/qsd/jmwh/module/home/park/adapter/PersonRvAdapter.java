package com.qsd.jmwh.module.home.park.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qsd.jmwh.R;
import com.qsd.jmwh.module.home.park.bean.HomePersonListBean;
import com.qsd.jmwh.view.CircleImageView;
import com.yu.common.ui.DelayClickImageView;
import com.yu.common.ui.DelayClickTextView;

import java.util.List;

public class PersonRvAdapter extends BaseQuickAdapter<HomePersonListBean.CdoListBean, BaseViewHolder> {
    private Context context;
    private String local_sex;

    public PersonRvAdapter(int layoutResId, @Nullable List<HomePersonListBean.CdoListBean> data, Context context, String local_sex) {
        super(layoutResId, data);
        this.context = context;
        this.local_sex = local_sex;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePersonListBean.CdoListBean item) {
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        DelayClickTextView tv_online = helper.getView(R.id.tv_online);
        Glide.with(context).load(item.sUserHeadPic).into(iv_headimg);
        helper.setText(R.id.tv_user_name, item.sNickName);
        helper.setText(R.id.tv_photo_num, item.nGalaryCapacity + "");
        if (item.distance_um >= 1000) {
            if (item.bHiddenRang) {
                helper.setText(R.id.tv_middle, item.sCity + " · " + item.sJob + " · " + item.sAge);
            } else {
                helper.setText(R.id.tv_middle, item.sCity + " · " + item.sJob + " · " + item.sAge + " · " + (item.distance_um / 1000) + "km");
            }
        } else {
            if (item.bHiddenRang) {
                helper.setText(R.id.tv_middle, item.sCity + " · " + item.sJob + " · " + item.sAge);
            } else {
                helper.setText(R.id.tv_middle, item.sCity + " · " + item.sJob + " · " + item.sAge + " · " + item.distance_um + "m");
            }
        }
        if (item.nOnLine == 0) {
            //在线
            tv_online.setText("当前在线");
            tv_online.setTextColor(context.getResources().getColor(R.color.app_main_color));
        } else if (item.nOnLine == 1) {
            tv_online.setText("当前在线");
            tv_online.setTextColor(context.getResources().getColor(R.color.color_666666));
        }

        helper.setGone(R.id.tv_nInfoSet, item.nInfoSet == 1 ? true : false);

        DelayClickTextView tv_auth_type = helper.getView(R.id.tv_auth_type);
//        if ("0".equals(local_sex)) {
//            if (item.nAuthType == 0) {
//                //未认证
//                helper.setText(R.id.tv_auth_type, "未认证");
//                tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person_no));
//            } else {
//                //已认证
//                helper.setText(R.id.tv_auth_type, "真实");
//                tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person));
//            }
//            tv_auth_type.setVisibility(View.VISIBLE);
//        } else {
//            if (item.bVip) {
//                tv_auth_type.setVisibility(View.VISIBLE);
//                tv_auth_type.setText("VIP");
//                tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person_vip));
//            } else {
//                tv_auth_type.setVisibility(View.GONE);
//            }
//
//        }

        if (item.nSex == 0) {
            //女性
            if (item.nAuthType == 0) {
                //未认证
                helper.setText(R.id.tv_auth_type, "未认证");
                tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person_no));
            } else {
                //已认证
                helper.setText(R.id.tv_auth_type, "真实");
                tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person));
            }
            tv_auth_type.setVisibility(View.VISIBLE);
        } else {
            //男性
            if (item.bVip) {
                tv_auth_type.setVisibility(View.VISIBLE);
                tv_auth_type.setText("VIP");
                tv_auth_type.setBackground(context.getResources().getDrawable(R.drawable.shape_home_person_vip));
            } else {
                tv_auth_type.setVisibility(View.GONE);
            }
        }


        DelayClickImageView iv_love = helper.getView(R.id.iv_love);
        if (item.blove) {
            iv_love.setImageResource(R.drawable.collect_select);
        } else {
            iv_love.setImageResource(R.drawable.collect);
        }
        helper.getView(R.id.rl_love).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPersonItemClickListener != null) {
                    onPersonItemClickListener.setOnPersonItemClick(iv_love, helper.getLayoutPosition(), item.blove, item.lUserId + "");
                }
            }
        });
    }

    public interface OnPersonItemClickListener {
        void setOnPersonItemClick(DelayClickImageView iv_love, int position, boolean is_love, String lLoveUserId);
    }

    OnPersonItemClickListener onPersonItemClickListener;

    public void setOnPersonItemClickListener(OnPersonItemClickListener onPersonItemClickListener) {
        this.onPersonItemClickListener = onPersonItemClickListener;
    }


}

